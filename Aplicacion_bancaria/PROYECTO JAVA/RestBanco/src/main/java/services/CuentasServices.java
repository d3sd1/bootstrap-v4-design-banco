package services;

import Exceptions.CuentaFormatoInvalidoException;
import Exceptions.CuentaNoEncontradaException;
import dao.CuentasDAO;
import dao.MovimientosDAO;
import dao.UsersDAO;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.List;
import model.Cliente;
import model.Cuenta;
import model.User;
import utils.PasswordHash;
import utils.Utils;

public class CuentasServices
{

    public Cuenta getCuentaTitulares(String numeroCuenta) throws CuentaFormatoInvalidoException, CuentaNoEncontradaException
    {
        CuentasDAO dao = new CuentasDAO();
        Utils utils = new Utils();
        boolean formatoCuentaValido = utils.comprobarFormatoCuenta(numeroCuenta);
        if (formatoCuentaValido)
        {
            Cuenta cuenta = new Cuenta();
            cuenta.setNumeroCuenta(numeroCuenta);

            /* Conseguir información de la cuenta */
            cuenta = dao.getCuentaByNumero(cuenta);
            if (null != cuenta.getNumeroCuenta())
            {
                /* Devolver la cuenta con sus titulares */
                return dao.getCuentaTitularesByNumero(cuenta);
            }
            else
            {
                throw new CuentaNoEncontradaException();
            }
        }
        else
        {
            throw new CuentaFormatoInvalidoException();
        }
    }

    public boolean deleteCuentaMovimientos(String numeroCuenta) throws CuentaFormatoInvalidoException
    {
        CuentasDAO cDao = new CuentasDAO();
        MovimientosDAO mDao = new MovimientosDAO();
        Utils utils = new Utils();
        boolean formatoCuentaValido = utils.comprobarFormatoCuenta(numeroCuenta),
                borradoSatisfactorio;
        if (formatoCuentaValido)
        {
            Cuenta cuenta = new Cuenta();
            cuenta.setNumeroCuenta(numeroCuenta);

            /* Conseguir información de la cuenta y revisar que su saldo sea 0 y exista */
            cuenta = cDao.getCuentaByNumero(cuenta);
            if (null != cuenta.getNumeroCuenta() && 0 == cuenta.getSaldo())
            {
                /*
                    Ojo al orden de eliminación.
                    Están primero las tablas que
                    no son las claves primarias,
                    sino las foráneas.
                 */
                /* Eliminar movimientos */
                List<Cliente> titulares = cDao.getCuentaTitularesByNumero(cuenta).getTitulares();
                
                boolean cuentaDesasignada = cDao.deleteClientesCuenta(cuenta),
                        movimientosBorrados = mDao.deleteMovimientos(cuenta),
                        cuentaBorrada = cDao.deleteCuenta(cuenta),
                        titularesBorrados = true;
                for (Cliente titular : titulares)
                {
                    /* Si hay un error al eliminar, detener el bucle */
                    if(!titularesBorrados)
                    {
                        break;
                    }
                    
                    /* Si el conteo de cuentas menos 1
                    (la actual) es 0 o inferior,
                    eliminar cliente
                    */
                    titular.setConteoCuentas(titular.getConteoCuentas()-1);
                    if(titular.getConteoCuentas() <= 0)
                    {
                        titularesBorrados = cDao.deleteCliente(titular);
                    }
                    /* Si no, simplemente reducir 
                    su numero de cuentas */
                    else
                    {
                        titularesBorrados = cDao.reducirCuentasTitular(titular);
                    }
                }
                borradoSatisfactorio = cuentaDesasignada && movimientosBorrados && titularesBorrados && cuentaBorrada;
            }
            else
            {
                borradoSatisfactorio = false;
            }
        }
        else
        {
            throw new CuentaFormatoInvalidoException();
        }
        return borradoSatisfactorio;
    }
}

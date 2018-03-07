package services;

import Exceptions.CuentaFormatoInvalidoException;
import Exceptions.CuentaNoEncontradaException;
import dao.ClientesDAO;
import dao.CuentasDAO;
import dao.MovimientosDAO;
import java.util.List;
import model.Cliente;
import model.Cuenta;
import org.springframework.transaction.annotation.Transactional;
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
                cuenta.setTitulares(dao.getCuentaTitularesByNumero(cuenta));
                return cuenta;
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

    @Transactional
    public boolean deleteCuentaMovimientos(String numeroCuenta) throws CuentaFormatoInvalidoException
    {
        CuentasDAO cDao = new CuentasDAO();
        ClientesDAO clDao = new ClientesDAO();
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
                List<Cliente> titulares = cDao.getCuentaTitularesByNumero(cuenta);
                
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
                        titularesBorrados = clDao.deleteCliente(titular);
                    }
                    /* Si no, simplemente reducir 
                    su numero de cuentas */
                    else
                    {
                        titularesBorrados = clDao.reducirCuentasTitular(titular);
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

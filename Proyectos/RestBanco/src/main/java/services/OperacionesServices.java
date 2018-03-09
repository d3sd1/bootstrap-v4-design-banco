package services;

import Exceptions.FondosInsuficientesException;
import Exceptions.CuentaFormatoInvalidoException;
import Exceptions.CuentaNoEncontradaException;
import dao.ClientesDAO;
import dao.CuentasDAO;
import dao.MovimientosDAO;
import model.Cuenta;
import model.Operacion;
import org.springframework.transaction.annotation.Transactional;
import utils.Utils;

public class OperacionesServices
{

    @Transactional
    public boolean reintegro(Operacion operacion) throws CuentaFormatoInvalidoException, CuentaNoEncontradaException, FondosInsuficientesException
    {
        CuentasDAO cDao = new CuentasDAO();
        ClientesDAO clDao = new ClientesDAO();
        MovimientosDAO mDao = new MovimientosDAO();

        Utils utils = new Utils();
        boolean success = true;
        boolean formatoCuentaValido = utils.comprobarFormatoCuenta(operacion.getAccountNumber());
        if (!formatoCuentaValido)
        {
            throw new CuentaFormatoInvalidoException();
        }

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(operacion.getAccountNumber());

        /* Conseguir información de la cuenta */
        cuenta = cDao.getCuentaByNumero(cuenta);
        if (null == cuenta.getNumeroCuenta())
        {
            throw new CuentaNoEncontradaException();
        }
        /* Comprobar que la cuenta tiene suficientes fondos */
        if (cuenta.getSaldo() < operacion.getAmount())
        {
            throw new FondosInsuficientesException();
        }
        cuenta.setTitulares(cDao.getCuentaTitularesByNumero(cuenta));
        /* Eliminar saldo de la tabla cuentas */
        if (!cDao.reducirSaldoCuenta(operacion, cuenta))
        {
            success = false;
        }

        /* Modificar saldo clientes, sólo si los tiene asociados. */
        if (!cuenta.getTitulares().isEmpty())
        {
            if (!clDao.reducirSaldoClientes(operacion, cuenta.getTitulares()))
            {
                success = false;
            }
        }

        /* Grabar movimiento */
        if (!mDao.grabarMovimiento(operacion))
        {
            success = false;
        }
        return success;
    }

    @Transactional
    public boolean ingreso(Operacion operacion) throws CuentaFormatoInvalidoException, CuentaNoEncontradaException, FondosInsuficientesException
    {
        CuentasDAO cDao = new CuentasDAO();
        ClientesDAO clDao = new ClientesDAO();
        MovimientosDAO mDao = new MovimientosDAO();

        Utils utils = new Utils();
        boolean success = true;
        boolean formatoCuentaValido = utils.comprobarFormatoCuenta(operacion.getAccountNumber());
        if (!formatoCuentaValido)
        {
            throw new CuentaFormatoInvalidoException();
        }

        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(operacion.getAccountNumber());

        /* Conseguir información de la cuenta */
        cuenta = cDao.getCuentaByNumero(cuenta);
        if (null == cuenta.getNumeroCuenta())
        {
            throw new CuentaNoEncontradaException();
        }
        cuenta.setTitulares(cDao.getCuentaTitularesByNumero(cuenta));

        /* Actualizar saldo de la tabla cuentas */
        if (!cDao.aumentarSaldoCuenta(operacion, cuenta))
        {
            success = false;
        }

        /* Modificar saldo clientes */
        if (!clDao.aumentarSaldoClientes(operacion, cuenta.getTitulares()))
        {
            success = false;
        }

        /* Grabar movimiento */
        if (!mDao.grabarMovimiento(operacion))
        {
            success = false;
        }
        return success;
    }

}

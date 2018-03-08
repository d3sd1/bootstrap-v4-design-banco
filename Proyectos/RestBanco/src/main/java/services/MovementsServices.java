package services;

import Exceptions.CuentaNoEncontradaException;
import dao.CuentasDAO;
import dao.MovimientosDAO;
import java.util.List;
import model.Cuenta;
import model.FiltrarMovimientos;
import model.Movimiento;

public class MovementsServices {
    public List<Movimiento> getAllMovimientos()
    {
        MovimientosDAO dao = new MovimientosDAO();
        return dao.getAllMovimientos();
    }
    public List<Movimiento> getMovimientosFiltrados(FiltrarMovimientos filtroMovimientos) throws CuentaNoEncontradaException
    {
        MovimientosDAO dao = new MovimientosDAO();
        CuentasDAO cuentasDao = new CuentasDAO();
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(filtroMovimientos.getNumeroCuenta());
        cuenta = cuentasDao.getCuentaByNumero(cuenta);
        
        List<Movimiento> movimientos;
        if(null != cuenta.getNumeroCuenta())
        {
            movimientos = dao.getMovimientosFiltrados(filtroMovimientos);
        }
        else
        {
            throw new CuentaNoEncontradaException();
        }
        return movimientos;
    }
}

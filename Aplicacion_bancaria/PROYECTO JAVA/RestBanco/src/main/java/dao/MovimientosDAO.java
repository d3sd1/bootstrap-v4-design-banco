package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.FiltrarMovimientos;
import model.Movimiento;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class MovimientosDAO
{

    private final String SQL_QUERY_GET_MOVIMIENTOS = "SELECT numero_cuenta,fecha,descripcion,importe FROM movimientos";
    private final String SQL_QUERY_GET_MOVIMIENTOS_FILTRADOS = "SELECT numero_cuenta,fecha,descripcion,importe FROM movimientos WHERE numero_cuenta=? AND fecha BETWEEN ? AND ?";

    public List<Movimiento> getAllMovimientos()
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<Movimiento> movimientos = new ArrayList();
        try
        {
            movimientos = jtm.query(SQL_QUERY_GET_MOVIMIENTOS, (ResultSet rs, int rowNum) ->
            {
                Movimiento mov = new Movimiento();
                mov.setNumeroCuenta(rs.getString(1));
                mov.setFecha(rs.getTimestamp(2));
                mov.setDescripcion(rs.getString(3));
                mov.setImporte(rs.getDouble(4));
                return mov;
            });
        }
        catch (DataAccessException e)
        {
            
        }
        return movimientos;
    }
    public List<Movimiento> getMovimientosFiltrados(FiltrarMovimientos filtroMovimientos)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<Movimiento> movimientos = new ArrayList();
        try
        {
            movimientos = jtm.query(SQL_QUERY_GET_MOVIMIENTOS_FILTRADOS, (ResultSet rs, int rowNum) ->
            {
                Movimiento mov = new Movimiento();
                mov.setNumeroCuenta(rs.getString(1));
                mov.setFecha(rs.getTimestamp(2));
                mov.setDescripcion(rs.getString(3));
                mov.setImporte(rs.getDouble(4));
                return mov;
            },filtroMovimientos.getNumeroCuenta(),filtroMovimientos.getFechaInicio(),filtroMovimientos.getFechaFinal());
        }
        catch (DataAccessException e)
        {
            
        }
        return movimientos;
    }
}

package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Cuenta;
import model.FiltrarMovimientos;
import model.Movimiento;
import model.Operacion;
import model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

public class MovimientosDAO
{

    private final String SQL_QUERY_GET_MOVIMIENTOS = "SELECT numero_cuenta,fecha,descripcion,importe FROM movimientos";
    private final String SQL_QUERY_GET_MOVIMIENTOS_FILTRADOS = "SELECT numero_cuenta,fecha,descripcion,importe FROM movimientos WHERE numero_cuenta=? AND fecha BETWEEN ? AND ?";
    private final String SQL_QUERY_DEL_MOVIMIENTOS = "DELETE FROM movimientos WHERE numero_cuenta=?";
    private final String SQL_QUERY_ADD_MOVIMIENTO = "INSERT INTO movimientos (numero_cuenta,fecha,descripcion,importe) VALUES (?,?,?,?)";

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
            }, filtroMovimientos.getNumeroCuenta(), filtroMovimientos.getFechaInicio(), filtroMovimientos.getFechaFinal());
        }
        catch (DataAccessException e)
        {

        }
        return movimientos;
    }

    public boolean deleteMovimientos(Cuenta cuenta)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success;
        try
        {
            jtm.update(SQL_QUERY_DEL_MOVIMIENTOS, cuenta.getNumeroCuenta());
            success = true;
        }
        catch (DataAccessException e)
        {
            success = false;
        }
        return success;
    }

    public boolean grabarMovimiento(Operacion operacion)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success = false;

        double amount;
        System.out.println("type " + operacion.getType());
        if(operacion.getType().equals("reintegro"))
        {
            amount = operacion.getAmount()*-1;
        }
        else
        {
            amount = operacion.getAmount();
        }
        try
        {
            int rowsInserted = jtm.update(SQL_QUERY_ADD_MOVIMIENTO, operacion.getAccountNumber(), new Timestamp(System.currentTimeMillis()),
                    operacion.getDescription(), amount);
            
            if (0 != rowsInserted)
            {
                success = true;
            }
        }
        catch (DataAccessException e)
        {
            success = false;
        }
        return success;
    }
}

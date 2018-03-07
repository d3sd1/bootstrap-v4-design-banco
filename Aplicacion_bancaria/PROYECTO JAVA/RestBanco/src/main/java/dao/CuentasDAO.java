package dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Cliente;
import model.Cuenta;
import model.Operacion;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class CuentasDAO
{

    private final String SQL_QUERY_GET_CUENTA = "SELECT numero_cuenta,saldo FROM cuentas WHERE numero_cuenta = ?";
    private final String SQL_QUERY_DEL_CUENTA = "DELETE FROM cuentas WHERE numero_cuenta=?";
    private final String SQL_QUERY_UPD_LESSSALDOCUENTA = "UPDATE cuentas SET saldo=saldo-? WHERE numero_cuenta=?";
    private final String SQL_QUERY_UPD_MORESALDOCUENTA = "UPDATE cuentas SET saldo=saldo+? WHERE numero_cuenta=?";
    private final String SQL_QUERY_DEL_CLIENTES_CUENTA = "DELETE FROM clientes_cuentas WHERE numero_cuenta=?";
    private final String SQL_QUERY_GET_CLIENTE_BY_CUENTA = "SELECT cl.dni,cl.nombre,cl.direccion,cl.telefono,cl.email,"
            + "cl.fecha_nacimiento,cl.fecha_conexion,cl.conteo_cuentas,cl.saldo "
            + "FROM clientes_cuentas cc "
            + "JOIN clientes cl ON cc.dni=cl.dni "
            + "WHERE cc.numero_cuenta = ?";

    public boolean reducirSaldoCuenta(Operacion operacion, Cuenta cuenta)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success;
        try
        {
            jtm.update(SQL_QUERY_UPD_LESSSALDOCUENTA, operacion.getAmount(), cuenta.getNumeroCuenta());
            success = true;
        }
        catch (DataAccessException e)
        {
            success = false;
        }
        return success;
    }
    
    public boolean aumentarSaldoCuenta(Operacion operacion, Cuenta cuenta)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success;
        try
        {
            jtm.update(SQL_QUERY_UPD_MORESALDOCUENTA, operacion.getAmount(), cuenta.getNumeroCuenta());
            success = true;
        }
        catch (DataAccessException e)
        {
            success = false;
        }
        return success;
    }

    public Cuenta getCuentaByNumero(Cuenta cuenta)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        try
        {
            cuenta = jtm.queryForObject(SQL_QUERY_GET_CUENTA, (ResultSet rs, int rowNum) ->
            {
                Cuenta foundCuenta = new Cuenta();
                foundCuenta.setNumeroCuenta(rs.getString(1));
                foundCuenta.setSaldo(rs.getDouble(2));
                return foundCuenta;
            }, cuenta.getNumeroCuenta());
        }
        catch (DataAccessException e)
        {
            cuenta = new Cuenta();
        }
        return cuenta;
    }

    public List<Cliente> getCuentaTitularesByNumero(Cuenta cuenta)
    {
        List<Cliente> titulares = new ArrayList<>();
        /* Añadir los titulares de la cuenta */
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        try
        {
            titulares = jtm.query(SQL_QUERY_GET_CLIENTE_BY_CUENTA, (ResultSet rs, int rowNum) ->
            {
                Cliente cliente = new Cliente();
                cliente.setDni(rs.getString(1));
                cliente.setNombre(rs.getString(2));
                cliente.setDireccion(rs.getString(3));
                cliente.setTelefono(rs.getString(4));
                cliente.setEmail(rs.getString(5));
                cliente.setFechaNacimiento(rs.getTimestamp(6));
                cliente.setFechaConexion(rs.getTimestamp(7));
                cliente.setConteoCuentas(rs.getInt(8));
                cliente.setSaldo(rs.getDouble(9));
                return cliente;
            }, cuenta.getNumeroCuenta());
        }
        catch (DataAccessException e)
        {
            cuenta = new Cuenta();
        }
        return titulares;
    }

    public boolean deleteCuenta(Cuenta cuenta)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success;
        try
        {
            jtm.update(SQL_QUERY_DEL_CUENTA, cuenta.getNumeroCuenta());
            success = true;
        }
        catch (DataAccessException e)
        {
            success = false;
        }
        return success;
    }


    public boolean deleteClientesCuenta(Cuenta cuenta)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success;
        try
        {
            jtm.update(SQL_QUERY_DEL_CLIENTES_CUENTA, cuenta.getNumeroCuenta());
            success = true;
        }
        catch (DataAccessException e)
        {
            success = false;
        }
        return success;
    }
}

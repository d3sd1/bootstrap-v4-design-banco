package dao;

import java.sql.ResultSet;
import java.util.List;
import model.Cliente;
import model.Operacion;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class ClientesDAO
{

    private final String SQL_QUERY_UPD_LESSSALDOCLIENTE = "UPDATE clientes SET saldo=saldo-? WHERE dni=?";
    private final String SQL_QUERY_UPD_MORESALDOCLIENTE = "UPDATE clientes SET saldo=saldo+? WHERE dni=?";
    private final String SQL_QUERY_DEL_CLIENTE = "DELETE FROM clientes WHERE dni=?";
    private final String SQL_QUERY_ADD_CLIENTE = "INSERT INTO clientes "
            + "(dni,nombre,direccion,telefono,email,fecha_nacimiento,fecha_conexion,conteo_cuentas,saldo,pin) "
            + "VALUES (?,?,?,?,?,?,?,?,?,?)";
    private final String SQL_QUERY_UPD_LESSCONTEOCUENTAS = "UPDATE clientes SET conteo_cuentas=conteo_cuentas-1 WHERE dni=?";
    private final String SQL_QUERY_UPD_MORECONTEOCUENTAS = "UPDATE clientes SET conteo_cuentas=conteo_cuentas+1 WHERE dni=?";
    private final String SQL_QUERY_GET_CLIENT_DNI = "SELECT dni,nombre,direccion,telefono,email,fecha_nacimiento,fecha_conexion,"
            + "conteo_cuentas,saldo FROM clientes WHERE dni=?";

    public boolean addCliente(Cliente cliente)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success = false;
        try
        {
            jtm.update(SQL_QUERY_ADD_CLIENTE, 
                cliente.getDni(),cliente.getNombre(),cliente.getDireccion(),cliente.getTelefono(),cliente.getEmail(),cliente.getFechaNacimiento(),
                cliente.getFechaConexion(),cliente.getConteoCuentas(),cliente.getSaldo(),cliente.getPin());
            success = true;
        }
        catch (DataAccessException e)
        {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    public boolean reducirSaldoClientes(Operacion operacion, List<Cliente> clientes)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success = false;
        try
        {
            for (Cliente cliente : clientes)
            {
                jtm.update(SQL_QUERY_UPD_LESSSALDOCLIENTE, operacion.getAmount(), cliente.getDni());
                success = true;
            }
        }
        catch (DataAccessException e)
        {
            success = false;
        }
        return success;
    }

    public boolean aumentarSaldoClientes(Operacion operacion, List<Cliente> clientes)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success = false;
        try
        {
            for (Cliente cliente : clientes)
            {
                jtm.update(SQL_QUERY_UPD_MORESALDOCLIENTE, operacion.getAmount(), cliente.getDni());
                success = true;
            }
        }
        catch (DataAccessException e)
        {
            success = false;
        }
        return success;
    }

    public boolean reducirCuentasTitular(Cliente cliente)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success;
        try
        {
            jtm.update(SQL_QUERY_UPD_LESSCONTEOCUENTAS, cliente.getDni());
            success = true;
        }
        catch (DataAccessException e)
        {
            success = false;
        }
        return success;
    }

    public boolean aumentarCuentasTitular(Cliente cliente)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success;
        try
        {
            jtm.update(SQL_QUERY_UPD_MORECONTEOCUENTAS, cliente.getDni());
            success = true;
        }
        catch (DataAccessException e)
        {
            success = false;
        }
        return success;
    }

    public boolean deleteCliente(Cliente cliente)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success;
        try
        {
            jtm.update(SQL_QUERY_DEL_CLIENTE, cliente.getDni());
            success = true;
        }
        catch (DataAccessException e)
        {
            success = false;
        }
        return success;
    }

    public Cliente getClienteByDni(Cliente cliente)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        try
        {
            cliente = jtm.queryForObject(SQL_QUERY_GET_CLIENT_DNI, (ResultSet rs, int rowNum) ->
            {
                Cliente foundCliente = new Cliente();
                foundCliente.setDni(rs.getString(1));
                foundCliente.setNombre(rs.getString(2));
                foundCliente.setDireccion(rs.getString(3));
                foundCliente.setTelefono(rs.getString(4));
                foundCliente.setEmail(rs.getString(5));
                foundCliente.setFechaNacimiento(rs.getTimestamp(6));
                foundCliente.setFechaConexion(rs.getTimestamp(7));
                foundCliente.setConteoCuentas(rs.getInt(8));
                foundCliente.setSaldo(rs.getDouble(9));
                return foundCliente;
            }, cliente.getDni());
        }
        catch (DataAccessException e)
        {
            cliente = new Cliente();
        }
        return cliente;
    }
}

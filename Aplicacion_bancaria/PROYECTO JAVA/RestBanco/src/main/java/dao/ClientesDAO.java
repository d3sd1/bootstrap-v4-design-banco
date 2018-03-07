package dao;

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
    private final String SQL_QUERY_UPD_CONTEOCUENTAS = "UPDATE clientes SET conteo_cuentas=? WHERE dni=?";

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
            jtm.update(SQL_QUERY_UPD_CONTEOCUENTAS, cliente.getConteoCuentas(), cliente.getDni());
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
}

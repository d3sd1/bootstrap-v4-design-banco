package dao;

import java.sql.ResultSet;
import model.Cuenta;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class CuentasDAO {
    private final String SQL_QUERY_GET_CUENTA = "SELECT numero_cuenta,dni1,dni2,saldo FROM cuentas WHERE numero_cuenta = ?";
    public Cuenta getCuentaByNumero(Cuenta cuenta)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        try
        {
            cuenta = jtm.queryForObject(SQL_QUERY_GET_CUENTA, (ResultSet rs, int rowNum) ->
            {
                Cuenta foundCuenta = new Cuenta();
                foundCuenta.setNumeroCuenta(rs.getString(1));
                foundCuenta.setDni1(rs.getString(2));
                foundCuenta.setDni2(rs.getString(3));
                foundCuenta.setSaldo(rs.getDouble(4));
                return foundCuenta;
            }, cuenta.getNumeroCuenta());
        }
        catch (DataAccessException e)
        {
            cuenta = new Cuenta();
        }
        return cuenta;
    }
}

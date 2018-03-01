package dao;

import java.sql.ResultSet;
import model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import utils.Utils;

public class UsersDAO {
    private final String SQL_QUERY_GET_USER_DNI = "SELECT name,surnames,token,pass FROM users WHERE dni=?";
    private final String SQL_QUERY_GET_USER_TOKEN = "SELECT id,dni,name,surnames,token,pass FROM users WHERE token=?";
    private final String SQL_QUERY_UPDATE_USER_TOKEN = "UPDATE users SET token=? WHERE dni=?";
    public User getUserByDni(User user)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        try
        {
            user = jtm.queryForObject(SQL_QUERY_GET_USER_DNI, (ResultSet rs, int rowNum) ->
            {
                User foundUser = new User();
                foundUser.setName(rs.getString(1));
                foundUser.setSurnames(rs.getString(2));
                foundUser.setToken(rs.getString(3));
                foundUser.setPassword(rs.getString(4));
                return foundUser;
            }, user.getDni());
        }
        catch (DataAccessException e)
        {
            user = new User();
        }
        return user;
    }
    public User getUserByToken(User user)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        try
        {
            user = jtm.queryForObject(SQL_QUERY_GET_USER_TOKEN, (ResultSet rs, int rowNum) ->
            {
                User foundUser = new User();
                foundUser.setId(rs.getInt(1));
                foundUser.setName(rs.getString(2));
                foundUser.setSurnames(rs.getString(3));
                foundUser.setToken(rs.getString(4));
                foundUser.setPassword(rs.getString(5));
                return foundUser;
            }, user.getToken());
        }
        catch (DataAccessException e)
        {
            user = new User();
        }
        return user;
    }
    public String generateLoginToken(User user)
    {
        Utils utils = new Utils();
        String token = utils.randomAlphaNumeric(95);

        try
        {
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            if (jtm.update(SQL_QUERY_UPDATE_USER_TOKEN, token, user.getDni()) == 0)
            {
                token = "";
            }
        }
        catch (Exception ex)
        {
            token = "";
        }

        return token;
    }
}

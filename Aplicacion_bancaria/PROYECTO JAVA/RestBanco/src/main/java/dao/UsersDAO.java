package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import utils.Utils;

public class UsersDAO
{

    private final String SQL_QUERY_GET_USER_DNI = "SELECT dni,nombre,apellidos,token,pass,id FROM usuarios WHERE dni=?";
    private final String SQL_QUERY_GET_USER_TOKEN = "SELECT id,dni,nombre,apellidos,token,pass FROM usuarios WHERE token=?";
    private final String SQL_QUERY_UPDATE_USER_TOKEN = "UPDATE usuarios SET token=? WHERE dni=?";
    private final String SQL_QUERY_GET_USERS = "SELECT id,nombre,apellidos,dni FROM usuarios";
    private final String SQL_QUERY_ADD_USUARIO = "INSERT INTO usuarios (nombre,apellidos,dni,pass,token) VALUES (?,?,?,?,null)";
    private final String SQL_QUERY_MOD_USUARIO = "UPDATE usuarios SET nombre=?,apellidos=?,dni=?,pass=? WHERE id=?";
    private final String SQL_QUERY_DEL_USUARIO = "DELETE FROM usuarios WHERE id=?";

    public User getUserByDni(User user)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        try
        {
            user = jtm.queryForObject(SQL_QUERY_GET_USER_DNI, (ResultSet rs, int rowNum) ->
            {
                User foundUser = new User();
                foundUser.setDni(rs.getString(1));
                foundUser.setName(rs.getString(2));
                foundUser.setSurnames(rs.getString(3));
                foundUser.setToken(rs.getString(4));
                foundUser.setPassword(rs.getString(5));
                foundUser.setId(rs.getInt(6));
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
                foundUser.setDni(rs.getString(2));
                foundUser.setName(rs.getString(3));
                foundUser.setSurnames(rs.getString(4));
                foundUser.setToken(rs.getString(5));
                foundUser.setPassword(rs.getString(6));
                return foundUser;
            }, user.getToken());
        }
        catch (DataAccessException e)
        {
            user = new User();
        }
        return user;
    }

    public User updateUserToken(User user)
    {
        try
        {
            System.out.println("user token: " + user.getToken());
            System.out.println("user dni: " + user.getDni());
            JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
            int rows = jtm.update(SQL_QUERY_UPDATE_USER_TOKEN, user.getToken(), user.getDni());
            System.out.println("ROWS AFF " + rows);
            if (rows == 0)
            {
                user.setToken("");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            user.setToken("");
        }

        return user;
    }

    public List<User> getAllUsers()
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        List<User> movimientos = new ArrayList();
        try
        {
            movimientos = jtm.query(SQL_QUERY_GET_USERS, (ResultSet rs, int rowNum) ->
            {
                User user = new User();
                user.setId(rs.getInt(1));
                user.setName(rs.getString(2));
                user.setSurnames(rs.getString(3));
                user.setDni(rs.getString(4));
                return user;
            });
        }
        catch (DataAccessException e)
        {

        }
        return movimientos;
    }

    public User addUsuario(User user)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        User fUser = user;
        try
        {
            GeneratedKeyHolder holder = new GeneratedKeyHolder();
            jtm.update(new PreparedStatementCreator()
            {
                @Override
                public PreparedStatement createPreparedStatement(Connection con) throws SQLException
                {
                    PreparedStatement statement = con.prepareStatement(SQL_QUERY_ADD_USUARIO, Statement.RETURN_GENERATED_KEYS);
                    statement.setString(1, fUser.getName());
                    statement.setString(2, fUser.getSurnames());
                    statement.setString(3, fUser.getDni());
                    statement.setString(4, fUser.getPassword());
                    return statement;
                }
            }, holder);

            int insertedId = holder.getKey().intValue();
            user.setId(insertedId);
        }
        catch (DataAccessException e)
        {
            user = new User();
        }
        return user;
    }

    public boolean modUsuario(User user)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success;
        try
        {
            jtm.update(SQL_QUERY_MOD_USUARIO, user.getName(), user.getSurnames(), user.getDni(), user.getPassword(), user.getId());
            success = true;
        }
        catch (DataAccessException e)
        {
            success = false;
        }
        return success;
    }

    public boolean delUsuario(User user)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        boolean success;
        try
        {
            jtm.update(SQL_QUERY_DEL_USUARIO, user.getId());
            success = true;
        }
        catch (DataAccessException e)
        {
            success = false;
        }
        return success;
    }
}

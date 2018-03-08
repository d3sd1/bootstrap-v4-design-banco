package dao;

import config.Configuration;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DBConnection
{

    public DBConnection()
    {

    }
    private static DBConnection dbconection = null;

    public static DBConnection getInstance()
    {
        if (dbconection == null)
        {
            dbconection = new DBConnection();
        }

        return dbconection;
    }

    public Connection getConnection() throws Exception
    {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = null;

        connection = DriverManager.getConnection(
                "jdbc:mysql://" + Configuration.getInstance().getMysqlHost() + ":" + Configuration.getInstance().getMysqlPort() + "/" + Configuration.getInstance().getMysqlDatabase() + "?useSSL=false&useUnicode=true&characterEncoding=utf-8",
                Configuration.getInstance().getMysqlUser(),
                Configuration.getInstance().getMysqlPass());

        return connection;
    }

    public DataSource getDataSource()
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://" + Configuration.getInstance().getMysqlHost() + ":" + Configuration.getInstance().getMysqlPort() + "/" + Configuration.getInstance().getMysqlDatabase() + "?useSSL=false&useUnicode=true&characterEncoding=utf-8");
        dataSource.setUsername(Configuration.getInstance().getMysqlUser());
        dataSource.setPassword(Configuration.getInstance().getMysqlPass());

        return dataSource;
    }

    public void closeCon(Connection connection)
    {
        try
        {
            if (connection != null)
            {
                connection.close();
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeStmt(Statement statement)
    {
        try
        {
            if (statement != null)
            {
                statement.close();
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeStmt(PreparedStatement statement)
    {
        try
        {
            if (statement != null)
            {
                statement.close();
            }
        }
        catch (SQLException ex)
        {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

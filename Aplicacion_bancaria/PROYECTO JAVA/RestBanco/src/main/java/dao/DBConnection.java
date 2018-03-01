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

public class DBConnection {

    public DBConnection() {

    }
    private static DBConnection dbconection = null;

    public static DBConnection getInstance(){
        if (dbconection == null)
            dbconection = new DBConnection();
       
        return dbconection;
    }
    
    public Connection getConnection() throws Exception {
        Class.forName(Configuration.getInstance().getDriverDB());
        Connection connection = null;

        connection = DriverManager.getConnection(
          Configuration.getInstance().getUrlDB(),
          Configuration.getInstance().getUserDB(),
          Configuration.getInstance().getPassDB());

        return connection;
    }

    public DataSource getDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName(Configuration.getInstance().getDriverDB());
        dataSource.setUrl(Configuration.getInstance().getUrlDB());
        dataSource.setUsername(Configuration.getInstance().getUserDB());
        dataSource.setPassword(Configuration.getInstance().getPassDB());

        return dataSource;
    }

    public void closeCon(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void closeStmt(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void closeStmt(PreparedStatement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

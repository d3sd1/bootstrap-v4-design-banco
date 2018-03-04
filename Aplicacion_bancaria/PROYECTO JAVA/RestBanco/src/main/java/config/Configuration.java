package config;

import java.io.InputStream;
import javax.servlet.ServletContext;
import org.yaml.snakeyaml.Yaml;

public class Configuration {

    private static Configuration config;

    private Configuration() {

    }

    public static Configuration getInstance() {

        return config;
    }

    public static Configuration getInstance(InputStream file,ServletContext sc) {
        if (config == null) {
            Yaml yaml = new Yaml();
            config = (Configuration) yaml.loadAs(file, Configuration.class);
        }
        return config;
    }
    private String mysqlHost;
    private String mysqlPort;
    private String mysqlUser;
    private String mysqlPass;
    private String mysqlDatabase;

    public static Configuration getConfig()
    {
        return config;
    }

    public static void setConfig(Configuration config)
    {
        Configuration.config = config;
    }

    public String getMysqlHost()
    {
        return mysqlHost;
    }

    public void setMysqlHost(String mysqlHost)
    {
        this.mysqlHost = mysqlHost;
    }

    public String getMysqlPort()
    {
        return mysqlPort;
    }

    public void setMysqlPort(String mysqlPort)
    {
        this.mysqlPort = mysqlPort;
    }

    public String getMysqlUser()
    {
        return mysqlUser;
    }

    public void setMysqlUser(String mysqlUser)
    {
        this.mysqlUser = mysqlUser;
    }

    public String getMysqlPass()
    {
        return mysqlPass;
    }

    public void setMysqlPass(String mysqlPass)
    {
        this.mysqlPass = mysqlPass;
    }

    public String getMysqlDatabase()
    {
        return mysqlDatabase;
    }

    public void setMysqlDatabase(String mysqlDatabase)
    {
        this.mysqlDatabase = mysqlDatabase;
    }
    
    
}

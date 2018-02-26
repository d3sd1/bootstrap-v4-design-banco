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
    private String urlDB;
    private String driverDB;
    private String userDB;
    private String passDB;

    public String getUrlDB() {
        return urlDB;
    }

    public void setUrlDB(String urlDB) {
        this.urlDB = urlDB;
    }

    public String getDriverDB() {
        return driverDB;
    }

    public void setDriverDB(String driverDB) {
        this.driverDB = driverDB;
    }

    public String getUserDB() {
        return userDB;
    }

    public void setUserDB(String userDB) {
        this.userDB = userDB;
    }

    public String getPassDB() {
        return passDB;
    }

    public void setPassDB(String passDB) {
        this.passDB = passDB;
    }
}

package dao;

import config.Configuration;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import model.ApiKey;
import org.springframework.jdbc.core.JdbcTemplate;

public class ApiSpringJDBC
{

    private final String QUERY_GET_APIKEY = "SELECT ak.key,ak.description,"
            + "(SELECT COUNT(*) FROM api_calls ac WHERE ac.api_key=ak.key AND ac.call_timestamp>?) "
            + "FROM api_keys ak WHERE ak.key=?";
    private final String QUERY_ADD_APICALL = "INSERT INTO api_calls (api_key,call_timestamp,call_page) VALUES (?,?,?)";

    public ApiKey getApiKeyInfo(String apiKey) throws SQLException
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        Date now = new Date();
        now.setTime(now.getTime() - Configuration.getInstance().getApiIntervalSeconds() * 1000);
        ApiKey api = new ApiKey();
        try
        {
            api = jtm.queryForObject(QUERY_GET_APIKEY, (ResultSet rs, int rowNum)
                    ->
            {
                ApiKey getApi = new ApiKey();
                getApi.setApiKey(rs.getString(1));
                getApi.setDescription(rs.getString(2));
                getApi.setTimesCalled(rs.getInt(3));
                if (getApi.getApiKey() != null && !getApi.getApiKey().equals(""))
                {
                    getApi.setIsValid(true);
                }

                return getApi;
            }, new java.sql.Timestamp(now.getTime()), apiKey);
        }
        catch (Exception e)
        {
            
        }
        return api;
    }

    public void addCall(String apiKey, String call)
    {
        JdbcTemplate jtm = new JdbcTemplate(DBConnection.getInstance().getDataSource());
        jtm.update(QUERY_ADD_APICALL, apiKey, new java.sql.Timestamp(new Date().getTime()), call);
    }
}

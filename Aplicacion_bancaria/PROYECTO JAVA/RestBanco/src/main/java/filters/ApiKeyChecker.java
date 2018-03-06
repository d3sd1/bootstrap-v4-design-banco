package filters;

import config.Configuration;
import dao.ApiSpringJDBC;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;
import java.sql.SQLException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.Constantes;
import model.ApiKey;

/**
 *
 * @author Andrei
 */
@WebFilter(filterName = "ApiKeyChecker", urlPatterns =
{
    "/*"
})
public class ApiKeyChecker implements Filter
{

    private static final boolean debug = false;

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public ApiKeyChecker()
    {
    }

    public void doFilter(ServletRequest req, ServletResponse resp,
            FilterChain chain)
            throws IOException, ServletException
    {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        ApiSpringJDBC api = new ApiSpringJDBC();

        
        /*
        Si el dominio de la petición es el mismo que
        el de la aplicación oficial del banco, no
        comprobamos api keys por que ya está autorizado
        (es el oficial). Si no, sí comprobamos la clave api.
        */
        if (request.getHeader("origin").equals(Constantes.CLIENTE_OFICIAL_BANCO_URL))
        {
            chain.doFilter(request, response);
        }
        else
        {
            String apiKey = request.getHeader("rest-api-key");
            try
            {
                ApiKey key = api.getApiKeyInfo(apiKey);
                if (key.getTimesCalled() >= Configuration.getInstance().getApiMaxIntervalCalls())
                {
                    response.setStatus(406);
                }
                else if (!key.getIsValid())
                {

                    response.setStatus(401);
                }
                else
                {
                    /* IS A VALID CALL. ADD THAT CALL TO DATABASE AND LET ACCROSS CORS */
                    api.addCall(key.getApiKey(), request.getServletPath());
                    response.addHeader("Access-Control-Allow-Origin", "*");
                    chain.doFilter(request, response);
                }
            }
            catch (SQLException e)
            {
                response.setStatus(500);
            }
            catch (Exception e)
            {
                response.setStatus(400);
            }
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig()
    {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy()
    {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig)
    {
        this.filterConfig = filterConfig;
        if (filterConfig != null)
        {
            if (debug)
            {
                log("ApiKeyDomain:Initializing filter");
            }
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString()
    {
        if (filterConfig == null)
        {
            return ("ApiKeyDomain()");
        }
        StringBuffer sb = new StringBuffer("ApiKeyDomain(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response)
    {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals(""))
        {
            try
            {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            }
            catch (Exception ex)
            {
            }
        }
        else
        {
            try
            {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            }
            catch (Exception ex)
            {
            }
        }
    }

    public static String getStackTrace(Throwable t)
    {
        String stackTrace = null;
        try
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        }
        catch (Exception ex)
        {
        }
        return stackTrace;
    }

    public void log(String msg)
    {
        filterConfig.getServletContext().log(msg);
    }

}

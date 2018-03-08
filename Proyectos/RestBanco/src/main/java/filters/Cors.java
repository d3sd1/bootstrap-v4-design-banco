package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import utils.Constantes;

@WebFilter(filterName = "Cors", urlPatterns =
{
    "/*"
})
public class Cors implements Filter
{

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain filterChain) throws IOException, ServletException
    {

        if (response instanceof HttpServletResponse)
        {
            HttpServletResponse alteredResponse = ((HttpServletResponse) response);
            addCorsHeader(alteredResponse);
        }

        filterChain.doFilter(request, response);
    }

    private void addCorsHeader(HttpServletResponse response)
    {
        /* 
        Si la petición no es del cliente oficial, por defecto el CORS la limita. Si su api-key es válida, se lo permite.
        Recordemos que la aplicación oficial no hace uso de api-key. El resto sí.
        */
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "*");
        response.addHeader("Access-Control-Request-Headers", "*");
        response.addHeader("Access-Control-Max-Age", "1728000");
    }

    @Override
    public void destroy()
    {
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException
    {
    }

}

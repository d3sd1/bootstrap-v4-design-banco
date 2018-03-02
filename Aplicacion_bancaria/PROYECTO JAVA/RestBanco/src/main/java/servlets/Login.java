package servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import services.UsersServices;

@WebServlet(name = "Login", urlPatterns =
{
    "/login/*"
})
public class Login extends HttpServlet
{

    /*
    Comprueba si el usuario está conectado
    mediante un path param en la url (TOKEN),
    pero no devuelve sus datos (solo comprobación).
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            UsersServices srv = new UsersServices();
            String pathInfo = request.getPathInfo();
            String[] pathParts = pathInfo.split("/");
            String token = pathParts[1];
            boolean foundToken = srv.checkToken(token);
            if (foundToken)
            {
                response.setStatus(200);
            }
            else
            {
                response.setStatus(406);
            }
        }
        catch (Exception e)
        {
            response.setStatus(406);
        }
    }

    /*
    Recibe las credenciales del usuario
    y las revisa. Si son correctas
    devuelve un token.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        /* Conexión */
        ObjectMapper mapper = new ObjectMapper();
        UsersServices srv = new UsersServices();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        User loginUser;
        try
        {
            loginUser = mapper.readValue(request.getAttribute("json").toString(), new TypeReference<User>()
            {
            });
        }
        catch (Exception e)
        {
            loginUser = new User();
        }
        /* Conectar, si se conecta, se añade el token al usuario */
        loginUser = srv.doLogin(loginUser);
        /* Comprobar si se ha generado un token */
        if (null != loginUser.getToken())
        {
            request.setAttribute("data", loginUser.getToken());
        }
        else
        {
            response.setStatus(406);
        }
    }

    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}

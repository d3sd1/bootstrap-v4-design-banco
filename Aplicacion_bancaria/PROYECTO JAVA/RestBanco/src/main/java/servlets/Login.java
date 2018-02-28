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

@WebServlet(name = "Login", urlPatterns =
{
    "/login"
})
public class Login extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        /* Conexión */
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        User loginUser;
        try
        {
            loginUser = mapper.readValue(request.getAttribute("json").toString(), new TypeReference<User>(){});
        }
        catch (UnrecognizedPropertyException e)
        {
            loginUser = new User();
        }
        System.out.println("process " + loginUser.getDni());
    }

    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}

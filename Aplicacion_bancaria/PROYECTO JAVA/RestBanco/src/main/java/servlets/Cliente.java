package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.ClientesServices;

@WebServlet(name = "Cliente", urlPatterns =
{
    "/cliente/*"
})
public class Cliente extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            ClientesServices cs = new ClientesServices();
            String pathInfo = request.getPathInfo();
            String[] pathParts = pathInfo.split("/");
            String dni = pathParts[1];
            model.Cliente cliente = cs.getDatos(dni);
            System.out.println("dni " + dni);
            if(null != cliente.getDni())
            {
                request.setAttribute("data", cliente);
            }
            else
            {
                response.setStatus(406);
                response.getWriter().print("La cuenta introducida no fue encontrada.");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            response.setStatus(400);
        }
    }

}

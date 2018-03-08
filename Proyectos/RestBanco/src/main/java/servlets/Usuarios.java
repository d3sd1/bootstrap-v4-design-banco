package servlets;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.User;
import services.UsersServices;

@WebServlet(name = "Usuarios", urlPatterns =
{
    "/usuarios/*"
})
public class Usuarios extends HttpServlet
{

    /* Devuelve el listado de usuarios */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        UsersServices uServ = new UsersServices();
        request.setAttribute("data", uServ.getAllUsuarios());
    }

    /* Actualizar usuario */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        UsersServices srv = new UsersServices();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        User user;
        try
        {
            user = mapper.readValue(request.getAttribute("json").toString(), new TypeReference<User>()
            {
            });
            if (srv.modUsuario(user))
            {
                response.setStatus(200);
            }
            else
            {
                response.setStatus(406);
                response.getWriter().print("El usuario no pudo ser modificado.");
            }
        }
        catch (Exception e)
        {
            response.setStatus(406);
            response.getWriter().print("Error al actualizar usuario");
        }
    }

    /* Añadir usuario */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        UsersServices srv = new UsersServices();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        User user;
        try
        {
            user = mapper.readValue(request.getAttribute("json").toString(), new TypeReference<User>() {});
            user = srv.addUsuario(user);
            if (0 != user.getId())
            {
                request.setAttribute("data", user);
                response.setStatus(200);
            }
            else
            {
                response.setStatus(406);
                response.getWriter().print("El usuario no se añadió correctamente.");
            }
        }
        catch (Exception e)
        {
            response.setStatus(406);
            response.getWriter().print("Ha ocurrido un error al añadir el usuario.");
        }
    }

     /* Eliminar usuario */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            UsersServices srv = new UsersServices();
            String pathInfo = request.getPathInfo();
            String[] pathParts = pathInfo.split("/");
            int id = Integer.parseInt(pathParts[1]);
            if(srv.delUsuario(id))
            {
                response.setStatus(200);
            }
            else
            {
                response.setStatus(406);
                response.getWriter().print("El usuario no se eliminó correctamente.");
            }
        }
        catch (Exception e)
        {
            response.setStatus(406);
            response.getWriter().print("Ha ocurrido un error al añadir el usuario.");
        }
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}

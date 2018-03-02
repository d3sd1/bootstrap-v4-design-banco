package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.MovementsServices;

@WebServlet(name = "Movimientos", urlPatterns =
{
    "/movimientos"
})
public class Movimientos extends HttpServlet
{

    /* Listado de movimientos general */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            MovementsServices srv = new MovementsServices();
            
            request.setAttribute("data", srv.getAllMovimientos());
        }
        catch (Exception e)
        {
            response.setStatus(406);
        }
    }
    /* Listado de movimientos por fecha y/o cuenta */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // aqui abrir
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // aqui cerrar
        /*
        •	Eliminación del registro de la tabla de cuentas.
•	Eliminar los titulares si es su única cuenta abierta o restar uno al número de cuentas abiertas en el caso de que el titular tenga más cuentas.
•	Eliminar los movimientos de la cuenta.

        */
    }
}

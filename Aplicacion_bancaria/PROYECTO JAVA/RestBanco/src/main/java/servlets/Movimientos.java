package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Movimientos", urlPatterns =
{
    "/movimientos"
})
public class Movimientos extends HttpServlet
{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        // devuelve el listado de movimientos de
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

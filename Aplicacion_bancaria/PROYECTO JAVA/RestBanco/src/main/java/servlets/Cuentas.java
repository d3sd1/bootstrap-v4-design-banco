package servlets;

import Exceptions.CuentaEncontradaException;
import Exceptions.CuentaFormatoInvalidoException;
import Exceptions.CuentaNoEncontradaException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cliente;
import services.CuentasServices;

@WebServlet(name = "Cuentas", urlPatterns =
{
    "/cuenta/*"
})
public class Cuentas extends HttpServlet
{

    /* Devuelve su información y la de sus clientes */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            CuentasServices cs = new CuentasServices();
            String pathInfo = request.getPathInfo();
            String[] pathParts = pathInfo.split("/");
            String numeroCuenta = pathParts[1];
            request.setAttribute("data", cs.getCuentaTitulares(numeroCuenta));
        }
        catch (CuentaFormatoInvalidoException e)
        {
            response.setStatus(406);
            response.getWriter().print("Formato de cuenta inválido.");
        }
        catch (CuentaNoEncontradaException e)
        {
            response.setStatus(406);
            response.getWriter().print("Cuenta no encontrada.");
        }
        catch (Exception e)
        {
            response.setStatus(400);
        }
    }

    /* Añade una cuenta y sus clientes */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            ObjectMapper mapper = new ObjectMapper();
            CuentasServices cs = new CuentasServices();
            String pathInfo = request.getPathInfo();
            String[] pathParts = pathInfo.split("/");
            String numeroCuenta = pathParts[1];
            List<Cliente> clientes;
            try
            {
                clientes = mapper.readValue(request.getAttribute("json").toString(), new TypeReference<List<Cliente>>()
                {
                });
            }
            catch (Exception e)
            {
                clientes = new ArrayList();
            }
            if (cs.addCuentaTitulares(numeroCuenta, clientes))
            {
                response.setStatus(200);
            }
            else
            {
                response.setStatus(400);
                response.getWriter().print("Algo salió mal al agregar la cuenta.");
            }
        }
        catch (CuentaFormatoInvalidoException e)
        {
            response.setStatus(406);
            response.getWriter().print("Formato de cuenta inválido.");
        }
        catch (CuentaEncontradaException e)
        {
            response.setStatus(406);
            response.getWriter().print("La cuenta ya existía, por favor, introduce otra.");
        }
        catch (Exception e)
        {
            response.setStatus(400);
        }
    }

    /* Elimina una cuenta y sus movimientos */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        try
        {
            CuentasServices cs = new CuentasServices();
            String pathInfo = request.getPathInfo();
            String[] pathParts = pathInfo.split("/");
            String numeroCuenta = pathParts[1];
            boolean borrada = cs.deleteCuentaMovimientos(numeroCuenta);
            if (borrada)
            {
                response.setStatus(200);
            }
            else
            {
                response.setStatus(406);
                response.getWriter().print("Error al eliminar cuenta");
            }
        }
        catch (CuentaFormatoInvalidoException e)
        {
            response.setStatus(406);
            response.getWriter().print("Formato de cuenta inválido.");
        }
        catch (Exception e)
        {
            response.setStatus(400);
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

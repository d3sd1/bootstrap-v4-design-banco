/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import Exceptions.CuentaFormatoInvalidoException;
import Exceptions.CuentaNoEncontradaException;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import services.CuentasServices;

/**
 *
 * @author DAW
 */
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

    /* Actuliza una cuenta y sus clientes */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

    }

    /* Añade una cuenta y sus clientes */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

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
            if(borrada)
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

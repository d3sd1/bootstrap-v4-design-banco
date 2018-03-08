package servlets;

import Exceptions.CuentaNoEncontradaException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.FiltrarMovimientos;
import model.Movimiento;
import services.MovementsServices;
import utils.Utils;

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
        ObjectMapper mapper = new ObjectMapper();
        MovementsServices srv = new MovementsServices();
        mapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        try
        {
            FiltrarMovimientos filtroMovimientos = mapper.readValue(request.getAttribute("json").toString(), new TypeReference<FiltrarMovimientos>(){});
            Utils utils = new Utils();
            
            /* Comprobar las fechas */
            if (filtroMovimientos.getFechaInicio().after(filtroMovimientos.getFechaFinal()))
            {
                response.setStatus(406);
                response.getWriter().print("La fecha inicial debe ser antes de la final.");
            }
            /* Comprobar el formato del numero de cuenta */
            else if(!utils.comprobarFormatoCuenta(filtroMovimientos.getNumeroCuenta()))
            {
                response.setStatus(406);
                response.getWriter().print("El formato del número de cuenta es incorrecto.");
            }
            else
            {
                List<Movimiento> movimientos = srv.getMovimientosFiltrados(filtroMovimientos);
                if (movimientos.isEmpty())
                {
                    /* No hay movimientos */
                    response.setStatus(406);
                    response.getWriter().print("Sin movimientos entre las fechas especificadas.");
                }
                else
                {
                    request.setAttribute("data", movimientos);
                }
            }
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
}

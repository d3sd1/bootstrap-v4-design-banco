package servlets;

import Exceptions.CuentaFormatoInvalidoException;
import Exceptions.CuentaNoEncontradaException;
import Exceptions.FondosInsuficientesException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Operacion;
import services.OperacionesServices;

@WebServlet(name = "Operaciones", urlPatterns =
{
    "/operaciones"
})
public class Operaciones extends HttpServlet
{

    /* Reintegro */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        Operacion operacion;
        try
        {
            OperacionesServices os = new OperacionesServices();
            operacion = mapper.readValue(request.getAttribute("json").toString(), new TypeReference<Operacion>()
            {
            });
            operacion.setType("reintegro");
            boolean estadoReintegro = os.reintegro(operacion);
            if (estadoReintegro)
            {
                response.setStatus(200);
            }
            else
            {
                response.setStatus(406);
                response.getWriter().print("Algo no ha salido bien.");
            }
        }
        catch (CuentaNoEncontradaException e)
        {
            response.setStatus(406);
            response.getWriter().print("Cuenta no encontrada.");
        }
        catch (CuentaFormatoInvalidoException e)
        {
            response.setStatus(406);
            response.getWriter().print("Formato de cuenta inválido.");
        }
        catch (FondosInsuficientesException e)
        {
            response.setStatus(406);
            response.getWriter().print("Cuenta con fondos insuficientes.");
        }
        catch (Exception e)
        {
            response.setStatus(400);
        }
    }

    /* Ingreso */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {

        ObjectMapper mapper = new ObjectMapper();
        Operacion operacion;
        try
        {
            OperacionesServices os = new OperacionesServices();
            operacion = mapper.readValue(request.getAttribute("json").toString(), new TypeReference<Operacion>()
            {
            });
            operacion.setType("ingreso");
            boolean estadoReintegro = os.ingreso(operacion);
            if (estadoReintegro)
            {
                response.setStatus(200);
            }
            else
            {
                response.setStatus(406);
                response.getWriter().print("Algo no ha salido bien.");
            }
        }
        catch (CuentaNoEncontradaException e)
        {
            response.setStatus(406);
            response.getWriter().print("Cuenta no encontrada.");
        }
        catch (CuentaFormatoInvalidoException e)
        {
            response.setStatus(406);
            response.getWriter().print("Formato de cuenta inválido.");
        }
        catch (FondosInsuficientesException e)
        {
            response.setStatus(406);
            response.getWriter().print("Cuenta con fondos insuficientes.");
        }
        catch (Exception e)
        {
            response.setStatus(400);
        }
    }
}

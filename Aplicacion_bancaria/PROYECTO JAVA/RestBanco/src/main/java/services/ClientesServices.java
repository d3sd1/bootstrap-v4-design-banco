package services;

import dao.ClientesDAO;
import model.Cliente;
import utils.Utils;

public class ClientesServices
{

    public Cliente getDatos(String dni)
    {
        ClientesDAO dao = new ClientesDAO();
        Utils utils = new Utils();
        Cliente cliente = new Cliente();
        if (utils.comprobarDni(dni))
        {
            System.out.println("here");
            cliente.setDni(dni);
            cliente = dao.getClienteByDni(cliente);
        }
        else
        {
            cliente = new Cliente();
        }
        return cliente;
    }
}

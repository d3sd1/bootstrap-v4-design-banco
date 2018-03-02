package services;

import model.Movimiento;
import dao.MovimientosDAO;
import java.util.List;

public class MovementsServices {
    public List<Movimiento> getAllMovimientos()
    {
        MovimientosDAO dao = new MovimientosDAO();
        return dao.getAllMovimientos();
    }
}

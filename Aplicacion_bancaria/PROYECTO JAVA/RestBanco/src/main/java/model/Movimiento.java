package model;

import java.sql.Timestamp;

public class Movimiento {
    private String numeroCuenta;
    private Timestamp fecha;
    private String descripcion;
    private double importe;

    public String getNumeroCuenta()
    {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta)
    {
        this.numeroCuenta = numeroCuenta;
    }

    public Timestamp getFecha()
    {
        return fecha;
    }

    public void setFecha(Timestamp fecha)
    {
        this.fecha = fecha;
    }

    public String getDescripcion()
    {
        return descripcion;
    }

    public void setDescripcion(String descripcion)
    {
        this.descripcion = descripcion;
    }

    public double getImporte()
    {
        return importe;
    }

    public void setImporte(double importe)
    {
        this.importe = importe;
    }
}

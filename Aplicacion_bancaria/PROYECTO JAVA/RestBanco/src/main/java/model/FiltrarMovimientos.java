package model;

import java.sql.Timestamp;

public class FiltrarMovimientos {
    private String numeroCuenta;
    private Timestamp fechaInicio;
    private Timestamp fechaFinal;

    public String getNumeroCuenta()
    {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta)
    {
        this.numeroCuenta = numeroCuenta;
    }

    public Timestamp getFechaInicio()
    {
        return fechaInicio;
    }

    public void setFechaInicio(Timestamp fechaInicio)
    {
        this.fechaInicio = fechaInicio;
    }

    public Timestamp getFechaFinal()
    {
        return fechaFinal;
    }

    public void setFechaFinal(Timestamp fechaFinal)
    {
        this.fechaFinal = fechaFinal;
    }
    
}

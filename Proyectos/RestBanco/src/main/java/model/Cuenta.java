package model;

import java.util.HashMap;
import java.util.List;

public class Cuenta {
    private String numeroCuenta;
    private double saldo;
    private List<Cliente> titulares;

    public String getNumeroCuenta()
    {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta)
    {
        this.numeroCuenta = numeroCuenta;
    }

    public double getSaldo()
    {
        return saldo;
    }

    public void setSaldo(double saldo)
    {
        this.saldo = saldo;
    }

    public List<Cliente> getTitulares()
    {
        return titulares;
    }

    public void setTitulares(List<Cliente> titulares)
    {
        this.titulares = titulares;
    }
    
    
}

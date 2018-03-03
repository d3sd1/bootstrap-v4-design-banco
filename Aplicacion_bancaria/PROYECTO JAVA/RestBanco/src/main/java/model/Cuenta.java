package model;

public class Cuenta {
    private String numeroCuenta;
    private String dni1;
    private String dni2;
    private double saldo;

    public String getNumeroCuenta()
    {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta)
    {
        this.numeroCuenta = numeroCuenta;
    }

    public String getDni1()
    {
        return dni1;
    }

    public void setDni1(String dni1)
    {
        this.dni1 = dni1;
    }

    public String getDni2()
    {
        return dni2;
    }

    public void setDni2(String dni2)
    {
        this.dni2 = dni2;
    }

    public double getSaldo()
    {
        return saldo;
    }

    public void setSaldo(double saldo)
    {
        this.saldo = saldo;
    }
    
}

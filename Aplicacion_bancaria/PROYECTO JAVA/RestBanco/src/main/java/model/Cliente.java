package model;

import java.sql.Timestamp;

public class Cliente {
    private String dni;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;
    private Timestamp fechaNacimiento;
    private Timestamp fechaConexion;
    private int conteoCuentas;
    private int pin;
    private double saldo;

    public String getDni()
    {
        return dni;
    }

    public void setDni(String dni)
    {
        this.dni = dni;
    }

    public String getDireccion()
    {
        return direccion;
    }

    public void setDireccion(String direccion)
    {
        this.direccion = direccion;
    }

    
    public String getNombre()
    {
        return nombre;
    }

    public void setNombre(String nombre)
    {
        this.nombre = nombre;
    }

    public String getTelefono()
    {
        return telefono;
    }

    public void setTelefono(String telefono)
    {
        this.telefono = telefono;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Timestamp getFechaNacimiento()
    {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Timestamp fechaNacimiento)
    {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Timestamp getFechaConexion()
    {
        return fechaConexion;
    }

    public void setFechaConexion(Timestamp fechaConexion)
    {
        this.fechaConexion = fechaConexion;
    }

    public int getConteoCuentas()
    {
        return conteoCuentas;
    }

    public void setConteoCuentas(int conteoCuentas)
    {
        this.conteoCuentas = conteoCuentas;
    }

    public double getSaldo()
    {
        return saldo;
    }

    public void setSaldo(double saldo)
    {
        this.saldo = saldo;
    }

    public int getPin()
    {
        return pin;
    }

    public void setPin(int pin)
    {
        this.pin = pin;
    }
    
}

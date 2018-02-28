package model;

public class User {
    private int id;
    private String dni;
    private String name;
    private String surnames;
    private String password;

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getDni()
    {
        return dni;
    }

    public void setDni(String dni)
    {
        this.dni = dni;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurnames()
    {
        return surnames;
    }

    public void setSurnames(String surnames)
    {
        this.surnames = surnames;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }
    
}

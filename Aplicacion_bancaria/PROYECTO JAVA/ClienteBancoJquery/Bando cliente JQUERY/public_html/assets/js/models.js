function User(name,surnames,dni,token)
{
    this.name = name;
    this.surnames = surnames;
    this.dni = dni;
    this.token = token;
}
function Movimiento(numeroCuenta,fecha,descripcion,importe)
{
    this.numeroCuenta = numeroCuenta;
    this.fecha = fecha;
    this.descripcion = descripcion;
    this.importe = importe;
}
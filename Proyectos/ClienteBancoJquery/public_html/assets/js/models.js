function User(name,surnames,dni,token,id,password)
{
    this.name = name;
    this.surnames = surnames;
    this.dni = dni;
    this.token = token;
    this.id = id;
    this.password = password;
}
function Movimiento(numeroCuenta,fecha,descripcion,importe)
{
    this.numeroCuenta = numeroCuenta;
    this.fecha = fecha;
    this.descripcion = descripcion;
    this.importe = importe;
}
function Cuenta(numeroCuenta,saldo,titulares)
{
    this.numeroCuenta = numeroCuenta;
    this.saldo = saldo;
    this.titulares = titulares;
}
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
   <meta content="text/html; charset=UTF-8" http-equiv="content-type">
   <title>Crear base de datos</title>
</head>
<body>
<br>
<?php
 $con = mysqli_connect("localhost", "root", "nohay2sin3") or
        die("No se pudo establecer la conexión con el servidor MySQL");
 echo "Conexión establecida.<br>";
 echo "Procedo a crear la base de datos <br>";
 $sql = 'CREATE DATABASE Banco';
 if (!mysqli_query($con, $sql)) {
     die ('No se puede crear la B.D. Banco:' .
          mysqli_error($con));
 }
 echo "Base de datos creada <br>";
 $db = mysqli_select_db($con, 'Banco');
 if (!$db) {
     die ('No se puede seleccionar la B.D. Banco:' .
          mysqli_error($con));
 };
 echo "¡ Operación de selección perfecta !<br>";
 echo "Procedo a crear la tabla clientes<br>";
 $sql = "CREATE TABLE clientes (cl_dni VARCHAR(9)  NOT NULL, " .
                               "cl_nom VARCHAR(50) NOT NULL, " .
                               "cl_dir VARCHAR(60) NOT NULL, " .
                               "cl_tel VARCHAR(9)  NOT NULL, " .
                               "cl_ema VARCHAR(65) NOT NULL, " .
                               "cl_fna DATE, " .
                               "cl_fcl DATE        NOT NULL, " .
                               "cl_ncu TINYINT(2)  NOT NULL, " .
                               "cl_sal INT(8)      NOT NULL, " .
                                   "PRIMARY KEY (cl_dni)) ENGINE = MYISAM;";
if (!mysqli_query($con, $sql)) {
    die ('No se puede crear la tabla clientes:' .
          mysqli_error($con));
 };
 echo "Se ha creado la tabla de clientes<br>";
 $sql = "INSERT INTO clientes (cl_dni, cl_nom, cl_dir, cl_tel, cl_ema, cl_fna, cl_fcl, cl_ncu, cl_sal) VALUES
                       ('11111111A', 'Antonio Perez', 'Avda. Andalucia Km. 6,200', '913170047', 'tierno@galvan.es',
                        '1963-02-26', '2010-03-17', 1, 6000);";
if (!mysqli_query($con, $sql)) {
    die ('No se puede dar de alta el primer cliente:' .
          mysqli_error($con));
};
echo "Se ha dado de alta el primer cliente<br>";
 $sql = "INSERT INTO clientes (cl_dni, cl_nom, cl_dir, cl_tel, cl_ema, cl_fna, cl_fcl, cl_ncu, cl_sal) VALUES
                       ('11111112B', 'Santiago Alonso', 'Avda. Andalucia Km. 6,200', '913170047', 'tierno@galvan.es',
                        '1963-02-26', '2012-03-25', 2, 7500);";
if (!mysqli_query($con, $sql)) {
    die ('No se puede dar de alta el segundo cliente:' .
          mysqli_error($con));
};
echo "Se ha dado de alta el segundo cliente<br>";
 $sql = "INSERT INTO clientes (cl_dni, cl_nom, cl_dir, cl_tel, cl_ema, cl_fna, cl_fcl, cl_ncu, cl_sal) VALUES
                        ('22222222C', 'Julian Orozco', 'Avda. Andalucia Km. 6,200', '913170047', 'tierno@galvan.es',
                        '1963-02-26', '2012-02-20', 2, 3300);";
if (!mysqli_query($con, $sql)) {
    die ('No se puede dar de alta el tercer cliente:<br>' .
          mysqli_error($con));
};
echo "Se ha dado de alta el tercer cliente<br>";
 $sql = "INSERT INTO clientes (cl_dni, cl_nom, cl_dir, cl_tel, cl_ema, cl_fna, cl_fcl, cl_ncu, cl_sal) VALUES
                       ('33333333D', 'Pedro Aranguren', 'Avda. Andalucia Km. 6,200', '913170047', 'tierno@galvan.es',
                        '1963-02-26', '2012-02-20', 1, 1800);";
if (!mysqli_query($con, $sql)) {
    die ('No se puede dar de alta el cuarto cliente:' .
          mysqli_error($con));
};
echo "Se ha dado de alta el cuarto cliente<br>";
 echo "Procedo a crear la tabla de cuentas<br>";
 $sql = "CREATE TABLE cuentas (cu_ncu VARCHAR(10)  NOT NULL, " .
                              "cu_dn1 VARCHAR(9)   NOT NULL, " .
                              "cu_dn2 VARCHAR(9), " .
                              "cu_sal INT(8)       NOT NULL, " .
                                  "PRIMARY KEY (cu_ncu), " .
                                  "FOREIGN KEY (cu_dn1, cu_dn2) REFERENCES clientes(cl_dni, cl_dni)" .
                                  ") ENGINE = MYISAM;";
if (!mysqli_query($con, $sql)) {
    die ('No se puede crear la tabla cuentas:' .
          mysqli_error($con));
 };
 echo "Se ha creado la tabla de cuentas<br>";
 $sql = "INSERT INTO cuentas (cu_ncu, cu_dn1, cu_dn2, cu_sal) VALUES ('0000000011', '11111111A', '11111112B', 6000);";
if (!mysqli_query($con, $sql)) {
    die ('No se puede dar de alta la primera cuenta:' .
          mysqli_error($con));
};
echo "Se ha dado de alta la primera cuenta<br>";
 $sql = "INSERT INTO cuentas (cu_ncu, cu_dn1, cu_dn2, cu_sal) VALUES ('0000000022', '11111112B', '22222222C', 1500);";
if (!mysqli_query($con, $sql)) {
    die ('No se puede dar de alta la segunda cuenta:' .
          mysqli_error($con));
};
echo "Se ha dado de alta la segunda cuenta<br>";
 $sql = "INSERT INTO cuentas (cu_ncu, cu_dn1, cu_dn2, cu_sal) VALUES ('0000000033', '22222222C', '33333333D', 1800);";
if (!mysqli_query($con, $sql)) {
    die ('No se puede dar de alta la tercera cuenta:' .
          mysqli_error($con));
};
echo "Se ha dado de alta la tercera cuenta<br>";
 echo "Procedo a crear la tabla de movimientos<br>";
 $sql = "CREATE TABLE movimientos (mo_ncu VARCHAR(10)  NOT NULL, " .
                                  "mo_fec DATE         NOT NULL, " .
                                  "mo_hor VARCHAR(6)   NOT NULL, " .
                                  "mo_des VARCHAR(80)  NOT NULL, " .
                                  "mo_imp INT(8)       NOT NULL, " .
                                      "PRIMARY KEY (mo_ncu, mo_fec, mo_hor)) ENGINE = MYISAM;";
if (!mysqli_query($con, $sql)) {
    die ('No se puede crear la tabla movimientos:' .
          mysqli_error($con));
 };
 echo "Se ha creado la tabla de movimientos<br>";
 $sql = "INSERT INTO movimientos (mo_ncu, mo_fec, mo_hor, mo_des, mo_imp) VALUES 
 								('0000000011', '2012-03-18', '101510', 'Alta de cuenta', 5000);";
if (!mysqli_query($con, $sql)) {
    die ('No se puede dar de alta el primer movimiento:' .
          mysqli_error($con));
};
echo "Se ha dado de alta el primer movimiento<br>";
 $sql = "INSERT INTO movimientos (mo_ncu, mo_fec, mo_hor, mo_des, mo_imp) VALUES 
 								('0000000022', '2012-03-18', '101620', 'Alta de cuenta', 1500);";
if (!mysqli_query($con, $sql)) {
    die ('No se puede dar de alta el segundo movimiento:' .
          mysqli_error($con));
};
echo "Se ha dado de alta el segundo movimiento<br>";
 $sql = "INSERT INTO movimientos (mo_ncu, mo_fec, mo_hor, mo_des, mo_imp) VALUES 
 								('0000000033', '2012-03-18', '101710', 'Alta de cuenta', 1800);";
if (!mysqli_query($con, $sql)) {
    die ('No se puede dar de alta el tercer movimiento:' .
          mysqli_error($con));
};
echo "Se ha dado de alta el tercer movimiento<br>";
 $sql = "INSERT INTO movimientos (mo_ncu, mo_fec, mo_hor, mo_des, mo_imp) VALUES 
 								('0000000011', '2012-03-18', '101720', 'Ingreso en caja', 1000);";
if (!mysqli_query($con, $sql)) {
    die ('No se puede dar de alta el tercer movimiento:' .
          mysqli_error($con));
};
echo "Se ha dado de alta el cuarto movimiento<br>";
?>
<br>
<br>
</body>
</html>

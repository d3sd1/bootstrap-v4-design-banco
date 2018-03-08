<!DOCTYPE html>
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
 echo "Procedo a crear la tabla de cuentas<br>";
 $sql = "CREATE TABLE cuentas (cu_ncu VARCHAR(10)  NOT NULL, " .
                              "cu_dn1 VARCHAR(9)   NOT NULL, " .
                              "cu_dn2 VARCHAR(9), " .
                              "cu_sal INT(8)      NOT NULL, " .
                              "PRIMARY KEY (cu_ncu), " .
                              "FOREIGN KEY (cu_dn1, cu_dn2) REFERENCES clientes(cl_dni, cl_dni)" .
                              ") ENGINE = MYISAM;";
if (!mysqli_query($con, $sql)) {
    die ('No se puede crear la tabla cuentas:' .
          mysqli_error($con));
 };
 echo "Se ha creado la tabla de cuentas<br>";
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
?>
<br>
<br>
</body>
</html>

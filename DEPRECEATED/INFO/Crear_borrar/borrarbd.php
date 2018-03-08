<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
   <meta content="text/html; charset=UTF-8" http-equiv="content-type">
   <title>Borrar base de datos</title>
</head>
<body>
<br>
<?php
 $con = mysqli_connect("localhost", "root", "nohay2sin3") or
        die("No se pudo establecer la conexión con el servidor MySQL");
 echo "¡BIEN!, Conexión establecida.\n";
 echo 'Procedo a borrar la base de datos <br>';
 $sql = 'DROP DATABASE Banco';
 if (mysqli_query($con, $sql)) {
     echo "Se ha BORRADO la base de datos Banco\n";
  } else {
     echo "Se ha producido un error al BORRAR la B.D. Banco" .
          mysqli_error($con) . "\n";
  }
?>
<br>
<br>
</body>
</html>

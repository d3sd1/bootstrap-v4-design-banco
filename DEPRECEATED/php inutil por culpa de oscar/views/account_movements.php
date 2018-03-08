<!DOCTYPE html>
<html>
    <head>
        <title>Movimientos de la cuenta</title>
        <!--Import Google Icon Font-->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <!--Import materialize.css-->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css" media="screen,projection">
        <link rel="stylesheet" href="//cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css" media="screen,projection">
        <link rel="stylesheet" href="assets/css/main.css" media="screen,projection">

        <!--Let browser know website is optimized for mobile-->
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
        <link rel="icon" href="assets/favicon.ico" type="image/x-icon" />
    </head>

    <body>
        <nav class="navbar-fixed bbva-color-bg">
            <div class="nav-wrapper">
                <a href="/" class="brand-logo center"><img class="logo" draggable="false" src="assets/images/logo.jpg"/></a>
                <ul id="nav-mobile" class="left hide-on-med-and-down">
                    <li><a href="index.php?page=movimientos">Movimientos</a></li>
                    <li><a href="index.php?page=ingresos_reintegros">Ingresos y reintegros</a></li>
                </ul>
                <ul id="nav-mobile" class="right hide-on-med-and-down">
                    <li><a href="index.php?page=abrir_cuenta">Apertura de cuentas</a></li>
                    <li><a href="index.php?page=cerrar_cuenta">Cierre de cuentas</a></li>
                </ul>
            </div>
        </nav>
        <div class="parallax-container">
            <div class="parallax"><img src="assets/images/backgrounds/bg3.jpg"></div>
        </div>
        <div class="row">
            <div class="col s12 center-align">
                <form id="searchAccount">
                    <div class="input-field inline">
                        <input name="numero_cuenta" type="number" class="validate">
                        <label for="email">Número de cuenta</label>
                    </div>
                    <div class="input-field inline">
                        <input name="fecha_inicio" type="text" class="datepicker">
                        <label for="fecha_inicio">Fecha de inicio</label>
                    </div>
                    <div class="input-field inline">
                        <input name="fecha_final" type="text" class="datepicker">
                        <label for="fecha_final">Fecha final</label>
                    </div>
                    <button class="waves-effect waves-light btn" type="submit"><i class="material-icons right">arrow_forward</i>Buscar</button>
                </form>
            </div>
        </div>
        <div class="row">
            <div class="col s12">
                <table id="movements" class="display centered" cellspacing="0" width="100%">
                    <thead>
                        <tr>
                            <th>Número de cuenta</th>
                            <th>Fecha del movimiento</th>
                            <th>Hora del movimiento</th>
                            <th>Descripción</th>
                            <th>Importe</th>
                        </tr>
                    </thead>
                    <tbody>
                        <?php
                        foreach ($movimientos as $movimiento)
                        {
                            echo "
                          <tr>
                          <td>" . $movimiento["mo_ncu"] . "</td>
                          <td>" . $movimiento["mo_fec"] . "</td>
                          <td class='prettyhour'>" . $movimiento["mo_hor"] . "</td>
                          <td>" . $movimiento["mo_des"] . "</td>
                          <td>" . $movimiento["mo_imp"] . "</td>
                          </tr>";
                        }
                        ?>
                    </tbody>
                </table>
            </div>
        </div>
        <div id="error" class="modal">
            <div class="modal-content">
                <h4>¡Error!</h4>
                <p></p>
            </div>
            <div class="modal-footer">
                <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Aceptar</a>
            </div>
        </div>
        <div id="loading" class="modal">
            <div class="modal-content">
                <h1 class="center-align">Cargando...</h1>
                <div class="progress">
                    <div class="indeterminate"></div>
                </div>
            </div>
        </div>

        <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <script src="//cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
        <script src="assets/js/common.js"></script>
        <script src="assets/js/movements.js"></script>
    </body>
</html>
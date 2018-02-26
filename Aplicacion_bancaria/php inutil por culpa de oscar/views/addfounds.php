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
            <div class="parallax"><img src="assets/images/backgrounds/bg1.jpg"></div>
        </div>
        <div class="row padding-top-2">
            <form id="addFounds" class="col s6 offset-s3 center-align">
                <div class="input-field col s12">
                    <input name="numero_cuenta" type="number" class="validate">
                    <label for="numero_cuenta">Número de cuenta</label>
                </div>
                <div class="input-field col s12">
                    <input name="importe" type="number" class="validate">
                    <label for="importe">Importe</label>
                </div>
                <div class="input-field col s12">
                    <textarea name="descripcion" class="materialize-textarea"></textarea>
                    <label for="descripcion">Descripción</label>
                </div>
                <button class="waves-effect waves-light btn" type="submit"><i class="material-icons right">attach_money</i>Enviar fondos</button>
            </form>
        </div>
        <div class="parallax-container">
            <div class="parallax"><img src="assets/images/backgrounds/bg3.jpg"></div>
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
        <div id="success" class="modal">
            <div class="modal-content">
                <h4>¡Bien!</h4>
                <p></p>
            </div>
            <div class="modal-footer">
                <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Aceptar</a>
            </div>
        </div>
        <div id="confirm" class="modal">
            <div class="modal-content">
                <h4>Confirmación</h4>
                <p data-originaltext="¿Seguro que deseas ingresar {amount} en la cuenta {account}?"></p>
            </div>
            <div class="modal-footer">
                <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>
                <a href="#!" id="confirmTransaction" class="waves-effect waves-green btn-flat">Aceptar</a>
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
        <script src="assets/js/addfounds.js"></script>
    </body>
</html>
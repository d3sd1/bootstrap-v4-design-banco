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
            <form id="comprobarCuenta" class="col s6 offset-s3 center-align">
                <div class="input-field col s12">
                    <input name="numero_cuenta" type="number" class="validate">
                    <label for="numero_cuenta">Número de cuenta</label>
                </div>
                <button class="waves-effect waves-light btn" type="submit"><i class="material-icons right">trending_up</i>Iniciar proceso</button>
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

        <div id="insertDni" class="modal center-align">
            <form class="col s12" id="dniForm">
                <div class="modal-content">
                    <h4>Insertar DNI</h4>
                    <div class="row">
                        <div class="row">
                            <div class="input-field col s6 offset-s3">
                                <input required name="dni" pattern="(([X-Z]{1})([-]?)(\d{7})([-]?)([A-Z]{1}))|((\d{8})([A-Z]{1}))" title="Introduce tu DNI en formato 12345678A" type="text" class="validate">
                                <label for="first_name">Introduce el DNI</label>
                            </div>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>
                    <button href="#!" class="waves-effect waves-green btn-flat" type="submit">Aceptar</button>
                </div>
            </form>
        </div>
        <div id="editPerson" class="modal center-align">
            <form class="col s12" id="editPerson">
                <div class="modal-content">
                    <h4>Datos de cliente</h4>
                    <div class="row">
                        <div class="input-field col s12">
                            <input required name="dni" pattern="(([X-Z]{1})([-]?)(\d{7})([-]?)([A-Z]{1}))|((\d{8})([A-Z]{1}))" title="Introduce tu DNI en formato 12345678A" type="text" class="validate">
                            <label for="dni">Introduce el DNI</label>
                        </div>
                        <div class="input-field col s12">
                            <input required name="nombre" title="Introduce el nombre" type="text" class="validate">
                            <label for="nombre">Introduce el nombre</label>
                        </div>
                        <div class="input-field col s12">
                            <input required name="email" title="Introduce el email" type="text" class="validate">
                            <label for="email">Introduce el email</label>
                        </div>
                        <div class="input-field col s12">
                            <input required name="telephone" title="Introduce el teléfono" type="text" class="validate">
                            <label for="telephone">Introduce el teléfono</label>
                        </div>
                        <div class="input-field col s12">
                            <input required name="address" title="Introduce la dirección" type="text" class="validate">
                            <label for="address">Introduce la dirección</label>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <a href="#!" class="modal-action modal-close waves-effect waves-green btn-flat">Cancelar</a>
                    <button href="#!" class="waves-effect waves-green btn-flat" type="submit">Aceptar</button>
                </div>
            </form>
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
        <script src="assets/js/addaccount.js"></script>
    </body>
</html>
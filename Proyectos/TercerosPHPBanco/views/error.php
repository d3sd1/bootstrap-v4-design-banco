<!DOCTYPE html>
<html>
    <head>
        <title>¡Error!</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <link rel="icon" href="http://cdn.nba.net/assets/icons/favicon16.ico" type="image/x-icon" />
    </head>

    <body>
        <nav class="navbar-fixed white">
            <div class="nav-wrapper">
                <a href="/" class="brand-logo center"><img draggable="false" style="width: 100px;" src="http://www.stickpng.com/assets/images/58428defa6515b1e0ad75ab4.png"/></a>
            </div>
        </nav>
        <div class="parallax-container">
            <div class="parallax"><img src="https://images5.alphacoders.com/467/467394.jpg"></div>
        </div>
        <div class="row">
            <div class="col s12 center-align">
                <h1>¡Ha ocurrido un error!</h1>
                <p>La consulta con la api no fue satisfactoria.</p>
                <a class="waves-effect waves-light btn" href="/"><i class="material-icons left">arrow_back</i>Volver al inicio</a>
            </div>
        </div>
        <!--Import jQuery before materialize.js-->
        <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/js/materialize.min.js"></script>
        <script src="//cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js"></script>
        <script>
            $(document).ready(function () {
                $('.parallax').parallax();
            });
        </script>
    </body>
</html>
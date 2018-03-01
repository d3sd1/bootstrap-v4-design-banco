$(document).ready(function () {
    /* Utilizando promise */
    isUserConnected().then(function (loggedIn) {
        if(!loggedIn)
        {
            window.location.replace("login.html");
        }
    });
    $(".parallax").parallax();
    $(".button-collapse").sideNav();
    $(".tap-target").tapTarget("open");
});
/* 
 * Se utiliza data-trigger ya que es un estándar de atributos
 * para jQuery y el selector ID es único, mientras que el botón
 * de desconectar está disponible desde varios sitios.
 * También se podría haber seleccionado el selector con clases, 
 * pero se ha evitado para no confundir con la guía de estilos.
 * La función de abajo desconecta al usuario de la sesión.
 */
$('[data-trigger="logout"]').bind("click",function(){
    localStorage.removeItem("token");
    window.location.replace("login.html");
});
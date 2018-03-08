var user = new User(); /* Usuario actual en un objeto */
$(document).ready(function () {
    /* Utilizando promise */
    isUserConnected().catch(function () {
        window.location.replace("login.html");
    });
    $('.modal').modal({
        dismissible: false
    });
    $(".parallax").parallax();
    $(".button-collapse").sideNav();
    $(".tap-target").tapTarget("open");
    $('.collapsible').collapsible();
    printUserInfo();
    preventExpiredSession();
});
/* 
 * Se utiliza data-trigger ya que es un estándar de atributos
 * para jQuery y el selector ID es único, mientras que el botón
 * de desconectar está disponible desde varios sitios.
 * También se podría haber seleccionado el selector con clases, 
 * pero se ha evitado para no confundir con la guía de estilos.
 * La función de abajo desconecta al usuario de la sesión.
 */
$('[data-trigger="logout"]').bind("click", function () {
   logout(); 
});
function logout()
{
    Materialize.toast("Te has desconectado correctamente.", 4000);
    localStorage.removeItem("token");
    window.location.replace("login.html");
}
/* Reemplazar los contenidos por username */
function preventExpiredSession()
{
    var user = getTokenUser(),
        exp = new Date(0),
        now = (new Date()).getTime();
    exp.setUTCSeconds(user.exp)
    if(exp.getTime() < now)
    {
        logout();
    }
}
function printUserInfo()
{
    var user = getTokenUser();
    replaceVariableView("user_name",user.name);
}
function checkBankAccountFormat(bankAccount)
{
    var suma = 0;
    for (var i = 0; i < bankAccount.toString().length; i++)
    {
        if (i < 9)
        {
            var numero = parseInt(bankAccount.toString().substr(i, 1));
            suma += numero;
        } else
        {
            break;
        }
    }
    var numeroFinalResultado = suma % 9;
    return numeroFinalResultado === parseInt(bankAccount.toString().substr(9, 1));
}
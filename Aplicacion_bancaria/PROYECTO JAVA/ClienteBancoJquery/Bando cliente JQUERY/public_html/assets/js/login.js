$(document).ready(function () {
    /* Utilizando promise */
    isUserConnected().then(function () {
        window.location.replace("home.html");
    });
});
$("#login").submit(function (event) {
    $.ajax({
        url: API_REST_URL + "/login",
        type: "POST",
        data: formToJson($("#login")),
        beforeSend: function ()
        {
            $("#loading").show();
        },
        error: function ()
        {
            Materialize.toast("DNI o contraseña incorrectos.", 4000);
        },
        success: function (result) {
            localStorage.setItem("token", JSON.parse(result));
            Materialize.toast("Te has conectado correctamente.", 4000);
            window.location.replace("home.html");
        },
        complete: function ()
        {
            $("#loading").hide();
        }
    });
    event.preventDefault();
});
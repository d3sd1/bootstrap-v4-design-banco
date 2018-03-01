$(document).ready(function () {
    /* Utilizando promise */
    isUserConnected().then(function (loggedIn) {
        if(loggedIn)
        {
            window.location.replace("home.html");
        }
    });
});
$("#login").submit(function (event) {
    $.ajax({
        url: API_REST_URL + "/login",
        type: "POST",
        data: loginFormToJson(),
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
function loginFormToJson() {

    var returnArray = {},
            dataArr = $("#login").serializeArray();
    for (var data in dataArr)
    {
        var input = dataArr[data];
        returnArray[input.name] = input.value;
    }
    return JSON.stringify(returnArray);
}
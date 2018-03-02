var accountNumber;
$("#openAccount").submit(function (e) {
    accountNumber = $("#openAccount").find("input[name='bank_account']").val();
    if (checkBankAccountFormat(accountNumber))
    {
        $.ajax({
            url: API_REST_URL + "/login",
            type: "POST",
            data: accountNumber,
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
    }
    else
    {
        Materialize.toast("El formato de la cuenta es incorrecto.", 4000);
    }
    e.preventDefault();
});
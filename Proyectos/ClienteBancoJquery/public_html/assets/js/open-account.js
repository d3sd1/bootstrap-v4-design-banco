var accountNumber, clientes = [];
$("#openAccount").submit(function (e) {
    accountNumber = $("#openAccount").find("input[name='bank_account']").val();
    if (checkBankAccountFormat(accountNumber))
    {
        $("#confirmAdd").modal("open");
    }
    else
    {
        Materialize.toast("El formato de la cuenta es incorrecto.", 4000);
    }
    e.preventDefault();
});
$("#addClient").click(function () {
    $("#searchUserByDni").modal("open");
});
$("#doSearch").click(function () {
    $("#searchUserByDni").modal("close");
    $.ajax({
        url: API_REST_URL + "/clientes/" + $("#userSearchForm").find("input[name='dni']").val(),
        type: "GET",
        beforeSend: function (request)
        {
            request.setRequestHeader("token", localStorage.getItem("token"));
            $("#cargandoCuenta").modal("open");
        },
        error: function (xhr)
        {
            Materialize.toast(xhr.responseText, 4000);
            var cliente = {dni: $("#userSearchForm").find("input[name='dni']").val()},
                    tmpUser = templateCliente;
            if (xhr.status === 406)
            {
                clientes.push(cliente);
                tmpUser = tmpUser.replace(/{{nombre}}/g, "");
                tmpUser = tmpUser.replace(/{{dni}}/g, cliente.dni);
                tmpUser = tmpUser.replace(/{{address}}/g, "");
                tmpUser = tmpUser.replace(/{{telefono}}/g, "");
                tmpUser = tmpUser.replace(/{{email}}/g, "");
                tmpUser = tmpUser.replace(/{{birth_date}}/g, "");
                tmpUser = tmpUser.replace(/{{found}}/g, "");
                $("#usuariosCuenta").append(tmpUser);
                Materialize.updateTextFields();
                $('.datepicker').pickadate({
                    selectMonths: true,
                    selectYears: 100,
                    today: 'Hoy',
                    clear: 'Reiniciar',
                    close: 'Aceptar',
                    closeOnSelect: true,
                    format: "yyyy-mm-dd"
                });
            }
            clientes.push(cliente);
        },
        success: function (result) {
            var cliente = JSON.parse(result),
                    tmpUser = templateCliente;
            if (clientes.length === 0 || clientes.find(cl => cl.dni === cliente.dni).dni === "undefined")
            {
                clientes.push(cliente);
                var birthDate = new Date(cliente.fechaNacimiento);
                tmpUser = tmpUser.replace(/{{nombre}}/g, cliente.nombre);
                tmpUser = tmpUser.replace(/{{dni}}/g, cliente.dni);
                tmpUser = tmpUser.replace(/{{address}}/g, cliente.direccion);
                tmpUser = tmpUser.replace(/{{telefono}}/g, cliente.telefono);
                tmpUser = tmpUser.replace(/{{email}}/g, cliente.email);
                tmpUser = tmpUser.replace(/{{birth_date}}/g, birthDate.getFullYear() + "-" + birthDate.getMonth() + "-" + birthDate.getDate());
                tmpUser = tmpUser.replace(/{{found}}/g, " readonly");
                $("#usuariosCuenta").append(tmpUser);
                Materialize.updateTextFields();
            }
            else
            {
                Materialize.toast("El usuario introducido ya estaba entre los clientes de la cuenta.", 4000);
            }
        },
        complete: function ()
        {
            $("#cargandoCuenta").modal("close");
            $("#userSearchForm").find("input[name='dni']").val("");
        }
    });
});
$("#addAccount").click(function ()
{
    $("#confirmAdd").modal("close");
    /* aqui falta AJAX */
    /*
     al enviar comprobar box y usuarios y agregar a db usuarios y cuentas (se suben por el body).
     para coger los usuarios le siguiente codigo:*/
    var numeroCuenta = $("#openAccount").find("input[name='bank_account']").val(),
            clientesAdd = [];
    $("form[data-client]").each(function () {
        var clienteActual = {
            dni: $(this).find("input[name='dni']").val(),
            nombre: $(this).find("input[name='nombre']").val(),
            direccion: $(this).find("input[name='direccion']").val(),
            telefono: $(this).find("input[name='telefono']").val(),
            email: $(this).find("input[name='email']").val(),
            fechaNacimiento: $(this).find("input[name='fecha_nacimiento']").val()
        };
        clientesAdd.push(clienteActual);
    });
    $.ajax({
        url: API_REST_URL + "/cuenta/" + numeroCuenta,
        type: "PUT",
        data: JSON.stringify(clientesAdd),
        beforeSend: function (request)
        {
            request.setRequestHeader("token", localStorage.getItem("token"));
            $("#cargandoCuenta").modal("open");
        },
        error: function (xhr)
        {
            Materialize.toast(xhr.responseText, 4000);
        },
        success: function (result) {
            Materialize.toast("Cuenta agregada correctamente.", 4000);
            $("#usuariosCuenta").html("");
            $("#openAccount").find("input[name='bank_account']").val("");
            Materialize.updateTextFields();
        },
        complete: function ()
        {
            $("#cargandoCuenta").modal("close");
        }
    });
});
$(document).ready(function () {
    $('.modal').modal();
});
var numeroCuenta, dni1;
$("#comprobarCuenta").submit(function (event) {
    numeroCuenta = $('#comprobarCuenta input[name="numero_cuenta"]').val();
    if(!hasValue(numeroCuenta))
    {
        showModal("error", "Por favor, rellena todos los campos.");
    }
    else if(!comprobarNumeroCuenta(numeroCuenta))
    {
        showModal("error", "La cuenta introducida no tiene un formato correcto.");
    }
    else
    {
        comprobarCuentaAjax();
    }
    event.preventDefault();
});
$("#dniForm").submit(function (event) {
    var actual_dni = $("#dniForm input[name='dni']").val();
    if(dni1 === null)
    {
        dni1 = actual_dni;
    }
    else
    {
        dni2 = actual_dni;
    }
    comprobarDniAjax(actual_dni);
    event.preventDefault();
});
function showPersonModal()
{
    $('#editPerson').modal('open');
    if(msg !== null)
    {
        $('#' + modal).find("p").text(msg);
    }
}
function comprobarDniAjax(dni)
{
    $.ajax({
        data: "page=ajax&ajax_action=revisar_dni&dni=" + dni,
        url: "index.php",
        method: "GET",
        dataType: 'json',
        beforeSend: function () {
            showModal("loading");
        },
        success: function (result) {
            console.log(result);
            if (result.success)
            {
                hideModal("insertDni");
                showPersonModal("error", "aui deberia ir");
            }
            else
            {
                Materialize.toast(result.description, 4000);
            }
        },
        error: function (error) {
            hideModal("insertDni");
            showModal("error", "Ha ocurrido un error: " + error.responseText);
        },
        complete: function () {
            hideModal("loading");
        }
    });
}
function comprobarCuentaAjax()
{
    $.ajax({
        data: "page=ajax&ajax_action=abrir_cuenta_revisar&accnumber=" + numeroCuenta,
        url: "index.php",
        method: "GET",
        dataType: 'json',
        beforeSend: function () {
            showModal("loading");
        },
        success: function (result) {
            if (result.success)
            {
                showModal("insertDni");
            }
            else
            {
                showModal("error", result.description);
            }
        },
        error: function (error) {
            showModal("error", "Ha ocurrido un error: " + error.responseText);
        },
        complete: function () {
            hideModal("loading");
        }
    });
}
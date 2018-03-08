var $dataTable;
$(document).ready(function () {
    $('.modal').modal();
});
$("#addFounds").submit(function (event) {
    var numeroCuenta = $('#addFounds input[name="numero_cuenta"]').val(),
            importe = $('#addFounds input[name="importe"]').val(),
            descripcion = $('#addFounds textarea[name="descripcion"]').val();
    if (!hasValue(numeroCuenta, importe, descripcion))
    {
        showModal("error", "Por favor, rellena todos los campos.");
    }
    else if (!comprobarNumeroCuenta(numeroCuenta))
    {
        showModal("error", "La cuenta introducida tiene un formato incorrecto.");
    }
    else
    {
        sendAjax(numeroCuenta, importe, descripcion, false);
    }
    event.preventDefault();
});
function sendAjax(numeroCuenta, importe, description, force)
{
    $.ajax({
        data: "page=ajax&ajax_action=ingresos_reintegros" +
                "&accnumber=" + numeroCuenta + "&importe=" + importe + "&descripcion=" + description + "&forzar=" + force.toString(),
        url: "index.php",
        method: "GET",
        dataType: 'json',
        beforeSend: function () {
            showModal("loading");
        },
        success: function (result) {
            $("#confirmTransaction").off("click");
            if (result.success && !result.forced)
            {
                var text = $('#confirm').find("p").attr("data-originaltext").replace("{amount}", importe).replace("{account}", numeroCuenta);
                $('#confirm').find("p").text(text);
                $('#confirm').modal('open');
                $("#confirmTransaction").on("click", function () {
                    sendAjax(numeroCuenta, importe, description, true);
                    $('#confirm').modal('close');
                });
            }
            else if (result.success && result.forced)
            {
                showModal("success", "El ingreso se ha procesado correctamente.");
            }
            else if (!result.success)
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
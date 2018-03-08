var $dataTable;
$(document).ready(function () {
    $dataTable = $('#movements').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
        },
        "order": [[4, "desc"]],
        "initComplete": function () {
            $('select').material_select();
        }
    });
    $('.datepicker').pickadate({
        selectMonths: true, // Creates a dropdown to control month
        selectYears: 15, // Creates a dropdown of 15 years to control year,
        today: 'Today',
        clear: 'Clear',
        close: 'Ok',
        closeOnSelect: false, // Close upon selecting a date,
        format: 'yyyy-mm-dd'
    });
    $('.modal').modal();
});
$(".prettyhour").each(function( index, el ) {
    $(this).text(prettyHour($(el).text()));
});
$("#searchAccount").submit(function (event) {
    var numeroCuenta = $('#searchAccount input[name="numero_cuenta"]').val(),
            fechaInicio = $('#searchAccount input[name="fecha_inicio"]').val(),
            fechaFinal = $('#searchAccount input[name="fecha_final"]').val();
    if (!hasValue(numeroCuenta))
    {
        showModal("error", "Por favor, introduce un número de cuenta.");
    }
    else if (!comprobarNumeroCuenta(numeroCuenta))
    {
        showModal("error", "El número de cuenta introducido no tiene un formato válido.");
    }
    /* Número de cuenta válido */
    else
    {
        if (hasValue(fechaInicio) && !hasValue(fechaFinal))
        {
            /* Si no ha introducido la fecha final pero si la de inicio, marcamos como actual la final */
            fechaFinal = getActualDate();
        }
        else if (!hasValue(fechaInicio) && hasValue(fechaFinal))
        {
            /* Si no ha introducido la fecha de inicio pero si la final, marcamos como actual la de inicio */
            fechaInicio = getActualDate();
        }

        /* Comprobar errores */
        if (fechaFinal < fechaInicio)
        {
            showModal("error", "La fecha final debe ser mayor que la de inicio.");
        }
        else
        {
            sendAjax(numeroCuenta, fechaInicio, fechaFinal);
        }
    }
    event.preventDefault();
});
function sendAjax(numeroCuenta, fechaInicio, fechaFinal)
{
    var parameters;
    if (hasValue(fechaInicio) && hasValue(fechaFinal))
    {
        parameters = "accnumber=" + numeroCuenta + "&begindate=" + fechaInicio + "&enddate=" + fechaFinal;
    }
    else
    {
        parameters = "accnumber=" + numeroCuenta;
    }
    $.ajax({
        data: "page=ajax&ajax_action=movements&" + parameters,
        url: "index.php",
        method: "GET",
        dataType: 'json',
        beforeSend: function () {
            showModal("loading");
        },
        success: function (result) {
            var data = result.data;
            $dataTable.clear();
            for (var row in data)
            {
                $dataTable.row.add([
                    data[row]["mo_ncu"],
                    data[row]["mo_fec"],
                    prettyHour(data[row]["mo_hor"]),
                    data[row]["mo_des"],
                    data[row]["mo_imp"]
                ]);
            }

            $dataTable.draw();
        },
        error: function (error) {
            showModal("error", "Ha ocurrido un error: " + error);
        },
        complete: function () {
            hideModal("loading");
        }
    });
}
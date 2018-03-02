var $tablaMovimientos;
$(document).ready(function () {
    $tablaMovimientos = $('#movimientos').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
        },
        "drawCallback": function () {
            $('select').material_select();
        },
        "initComplete": function () {
            listarMovimientos();
        }
    });
    $('.datepicker').pickadate({
        selectMonths: true,
        selectYears: 1,
        today: 'Hoy',
        clear: 'Reiniciar',
        close: 'Aceptar',
        closeOnSelect: true,
        format: "yyyy-mm-dd"
    });

});
$("#filtrarMovimientos").submit(function (e) {
    var numeroCuenta = $("#filtrarMovimientos input[name='numeroCuenta']").val(),
            fechaInicio = $("#filtrarMovimientos input[name='fechaInicio']").val(),
            fechaFinal = $("#filtrarMovimientos input[name='fechaFinal']").val();

    if (numeroCuenta === null || numeroCuenta === "" ||
            fechaInicio === null || fechaInicio === "" ||
            fechaFinal === null || fechaFinal === "")
    {
        Materialize.toast("Para filtrar movimientos debes rellenar todos los datos.", 4000);
    }
    else if (!checkBankAccountFormat(numeroCuenta))
    {
        Materialize.toast("El formato de la cuenta es incorrecto.", 4000);
    }
    else
    {
        var data = formToJson($("#filtrarMovimientos"));
        filtrarMovimientos(data);
    }
    e.preventDefault();
});
function listarMovimientos()
{
    $.ajax({
        url: API_REST_URL + "/movimientos",
        type: "GET",
        beforeSend: function ()
        {
            $("#cargandoMovimientos").modal("open");
        },
        error: function ()
        {
            Materialize.toast("No se han podido cargar movimientos.", 4000);
        },
        success: function (result) {
            var movimientos = JSON.parse(result);
            rellenarTablaMovimientos(movimientos);
        },
        complete: function ()
        {
            $("#cargandoMovimientos").modal("close");
        }
    });
}
function filtrarMovimientos(data)
{
    $.ajax({
        url: API_REST_URL + "/movimientos",
        type: "POST",
        data: data,
        beforeSend: function ()
        {
            $("#cargandoMovimientos").modal("open");
        },
        error: function (xhr)
        {
            Materialize.toast(xhr.responseText, 4000);
        },
        success: function (result) {
            var movimientos = JSON.parse(result);
            rellenarTablaMovimientos(movimientos);
        },
        complete: function ()
        {
            $("#cargandoMovimientos").modal("close");
        }
    });
}
function rellenarTablaMovimientos(movimientos)
{
    $tablaMovimientos.clear();
    for (var movimiento of movimientos)
    {
        $tablaMovimientos.row.add([
            movimiento["numeroCuenta"],
            movimiento["fecha"],
            movimiento["descripcion"],
            movimiento["importe"]
        ]).draw().node();
    }
}
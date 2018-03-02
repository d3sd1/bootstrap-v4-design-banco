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
    var camposVacios = false;
    $("#filtrarMovimientos input").each(function () {

    });
    if (camposVacios)
    {
        Materialize.toast("Para filtrar movimientos debes rellenar todos los datos.", 4000);
    }
    else
    {
        var data = formToJson($("#filtrarMovimientos"));
        filtrarMovimientos(data);
        e.preventDefault();
    }
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
    console.log(data);
    $.ajax({
        url: API_REST_URL + "/movimientos",
        type: "POST",
        data: data,
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
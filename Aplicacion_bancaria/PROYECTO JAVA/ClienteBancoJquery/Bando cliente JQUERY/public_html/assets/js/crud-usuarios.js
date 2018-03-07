var $tablaUsuarios;
$(document).ready(function () {
    $tablaUsuarios = $('#usuarios').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/1.10.16/i18n/Spanish.json"
        },
        "drawCallback": function () {
            $('select').material_select();
        },
        "initComplete": function () {
            listarUsuarios();
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
$("#addUsuario").click(function (e) {
    addUsuario();
    e.preventDefault();
});
function listarUsuarios()
{
    $.ajax({
        url: API_REST_URL + "/usuarios",
        type: "GET",
        beforeSend: function (request)
        {
            request.setRequestHeader("token", localStorage.getItem("token"));
            $("#cargandoUsuarios").modal("open");
        },
        error: function (request, textStatus, errorThrown) {
            Materialize.toast("No se han podido cargar usuarios.", 4000);
        },
        success: function (result) {
            var usuarios = JSON.parse(result);
            rellenarTablaUsuarios(usuarios);
        },
        complete: function ()
        {
            $("#cargandoUsuarios").modal("close");
        }
    });
}
function rellenarTablaUsuarios(usuarios)
{
    $tablaUsuarios.clear();
    for (var usuario of usuarios)
    {
        $tablaUsuarios.row.add([
            usuario["id"],
            usuario["dni"],
            usuario["name"],
            usuario["surnames"],
            `<a class='dropdown-button btn' href='#' data-activates='user-manage-${usuario["id"]}'>Acciones</a>
            <ul id='user-manage-${usuario["id"]}' class='dropdown-content'>
                <li><a href='javascript:updateUsuarioModal(${usuario["id"]},"${usuario["dni"]}","${usuario["name"]}","${usuario["surnames"]}")'>Editar</a></li>
                <li><a href="javascript:deleteUsuario(${usuario["id"]})">Eliminar</a></li>
            </ul>`
        ]).draw().node();
    }
    $('.dropdown-button').dropdown({
        constrainWidth: true,
        gutter: 0,
        belowOrigin: true,
        stopPropagation: false
    });
}
function limpiarFormularioUsuario()
{
    $("#manageUsuario").find("input").each(function () {
        $(this).val("");
    });
}
function addUsuario()
{
    limpiarFormularioUsuario();
    $("#manageUsuario").find("input[name='formtype']").val("add");
    $("#manageUsuario").find("input[name='id']").val("N/A");
    Materialize.updateTextFields();
    $("#manageUsuario").modal("open");
}
function updateUsuarioModal(id, dni, nombre, apellidos)
{
    limpiarFormularioUsuario();
    $("#manageUsuario").find("input[name='formtype']").val("update");
    $("#manageUsuario").find("input[name='id']").val(id);
    $("#manageUsuario").find("input[name='dni']").val(dni);
    $("#manageUsuario").find("input[name='nombre']").val(nombre);
    $("#manageUsuario").find("input[name='apellidos']").val(apellidos);
    Materialize.updateTextFields();
    $("#manageUsuario").modal("open");
}
function deleteUsuario(id)
{
    $.ajax({
        url: API_REST_URL + "/usuarios/" + id,
        type: "DELETE",
        beforeSend: function (request)
        {
            request.setRequestHeader("token", localStorage.getItem("token"));
            $("#cargandoUsuarios").modal("open");
        },
        error: function ()
        {
            Materialize.toast("No se ha podido eliminar el usuario.", 4000);
        },
        success: function () {
            $tablaUsuarios.rows().every(function (index) {
                if (this.data()[0] === id)
                {
                    $tablaUsuarios.row(index).remove().draw();
                }
            });
            Materialize.toast("Usuario eliminado correctamente.", 4000);
        },
        complete: function ()
        {
            $("#cargandoUsuarios").modal("close");
        }
    });
}
$("#datosUsuario").submit(function (e) {
    var tipo = $("#datosUsuario").find("input[name='formtype']").val(),
            id = $("#datosUsuario").find("input[name='id']").val(),
            dni = $("#datosUsuario").find("input[name='dni']").val(),
            password = $("#datosUsuario").find("input[name='password']").val(),
            nombre = $("#datosUsuario").find("input[name='nombre']").val(),
            apellidos = $("#datosUsuario").find("input[name='apellidos']").val(),
            usuario = new User(nombre, apellidos, dni, null, id, password);
    if (tipo === "add")
    {
        usuario.id = 0;
        $.ajax({
            url: API_REST_URL + "/usuarios",
            type: "PUT",
            data: JSON.stringify(usuario),
            beforeSend: function (request)
            {
                request.setRequestHeader("token", localStorage.getItem("token"));
                $("#manageUsuario").modal("close");
                $("#cargandoUsuarios").modal("open");
            },
            error: function ()
            {
                Materialize.toast("No se ha podido añadir al usuario.", 4000);
            },
            success: function (result) {
                var newUser = JSON.parse(result);
                console.log(result);
                console.log(newUser);
                console.log(newUser["dni"]);
                $tablaUsuarios.row.add([
                    newUser["id"],
                    newUser["dni"],
                    newUser["name"],
                    newUser["surnames"],
                    `<a class='dropdown-button btn' href='#' data-activates='user-manage-${newUser["id"]}'>Acciones</a>
            <ul id='user-manage-${newUser["id"]}' class='dropdown-content'>
                <li><a href='javascript:updateUsuarioModal(${newUser["id"]},"${newUser["dni"]}","${newUser["name"]}","${newUser["surnames"]}")'>Editar</a></li>
                <li><a href="javascript:deleteUsuario(${newUser["id"]})">Eliminar</a></li>
            </ul>`
                ]).draw().node();
            },
            complete: function ()
            {
                $("#cargandoUsuarios").modal("close");
            }
        });
    }
    else
    {
        $.ajax({
            url: API_REST_URL + "/usuarios",
            type: "POST",
            data: JSON.stringify(usuario),
            beforeSend: function (request)
            {
                request.setRequestHeader("token", localStorage.getItem("token"));
                $("#manageUsuario").modal("close");
                $("#cargandoUsuarios").modal("open");
            },
            error: function ()
            {
                Materialize.toast("No se ha podido actualizar el usuario.", 4000);
            },
            success: function () {
                $tablaUsuarios.rows().every(function (index) {
                    if (typeof this.data() !== "undefined")
                    {
                        if (this.data()[0] == usuario["id"])
                        {
                            $tablaUsuarios.row(index).remove().draw();
                        }
                    }
                });
                $tablaUsuarios.row.add([
                    usuario["id"],
                    usuario["dni"],
                    usuario["name"],
                    usuario["surnames"],
                    `<a class='dropdown-button btn' href='#' data-activates='user-manage-${usuario["id"]}'>Acciones</a>
            <ul id='user-manage-${usuario["id"]}' class='dropdown-content'>
                <li><a href='javascript:updateUsuarioModal(${usuario["id"]},"${usuario["dni"]}","${usuario["name"]}","${usuario["surnames"]}")'>Editar</a></li>
                <li><a href="javascript:deleteUsuario(${usuario["id"]})">Eliminar</a></li>
            </ul>`
                ]).draw().node();
            },
            complete: function ()
            {
                $("#cargandoUsuarios").modal("close");
            }
        });
    }
    e.stopPropagation();
    e.preventDefault();
});
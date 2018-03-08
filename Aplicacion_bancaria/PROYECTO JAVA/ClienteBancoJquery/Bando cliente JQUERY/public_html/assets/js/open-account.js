var accountNumber, clientes = [],
        templateCliente = `<li>
                                <div class="collapsible-header"><i class="material-icons">filter_drama</i>{{dni}}</div>
                                <div class="collapsible-body">
                                    <span>
                                        <div class="row">
                                            <form class="col s12">
                                              <div class="row">
                                                <div class="input-field col s6">
                                                  <i class="material-icons prefix">credit_card</i>
                                                  <input value="{{dni}}" type="text"{{found}}>
                                                  <label>DNI</label>
                                                </div>
                                                <div class="input-field col s6">
                                                  <i class="material-icons prefix">contact_phone</i>
                                                  <input value="{{telefono}}" type="text"{{found}}>
                                                  <label>Teléfono</label>
                                                </div>
                                              </div>
                                            </form>
                                        </div>
                                    </span>
                                </div>
                        </li>`;
$("#openAccount").submit(function (e) {
    accountNumber = $("#openAccount").find("input[name='bank_account']").val();
    if (checkBankAccountFormat(accountNumber))
    {
        //ajax pal servidor
    }
    else
    {
        Materialize.toast("El formato de la cuenta es incorrecto.", 4000);
    }
    e.preventDefault();
});
$("#addClient").click(function() {
    /*
    si existe poner sus datos y los campos de datos no editables
             y sino pues poner solo el dni y los campos de datos editabkles.
                    al enviar comprobar box y usuarios y agregar a db usuarios y cuentas (se suben por el body) */
    $("#searchUserByDni").modal("open");
    console.log("open");
});
$("#doSearch").click(function() {
    $("#searchUserByDni").modal("close");
    $.ajax({
        url: API_REST_URL + "/cliente/" + $("#userSearchForm").find("input[name='dni']").val(),
        type: "GET",
        beforeSend: function (request)
        {
            request.setRequestHeader("token", localStorage.getItem("token"));
            $("#cargandoCuenta").modal("open");
        },
        error: function (xhr)
        {
            Materialize.toast(xhr.responseText, 4000);
            clientes.push({dni: $("#userSearchForm").find("input[name='dni']").val()});
            tmpUser = tmpUser.replace("{{nombre}}","");
            tmpUser = tmpUser.replace("{{dni}}","");
            tmpUser = tmpUser.replace("{{nombre}}","");
            tmpUser = tmpUser.replace("{{found}}","");
            $("#usuariosCuenta").append(tmpUser);
        },
        success: function (result) {
            var cliente = JSON.parse(result),
                tmpUser = templateCliente;
        if(clientes.find(function(usr){ return usr.dni === cliente.dni;}))
        {
            clientes.push(cliente);
            tmpUser = tmpUser.replace("{{nombre}}",usuario.nombre);
            tmpUser = tmpUser.replace("{{dni}}",usuario.dni);
            tmpUser = tmpUser.replace("{{email}}",usuario.nombre);
            tmpUser = tmpUser.replace("{{found}}","");
            $("#usuariosCuenta").append(tmpUser);
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
var accountNumber,
    templateUser = `<li>
        <div class="collapsible-header"><i class="material-icons">person</i>{{cliente_nombre}}</div>
        <div class="collapsible-body">
            <span>
                <div class="row">
                    <form class="col s12">
                      <div class="row">
                        <div class="input-field col s6">
                          <i class="material-icons prefix">credit_card</i>
                          <input value="{{dni}}" type="text" readonly>
                          <label>DNI</label>
                        </div>
                        <div class="input-field col s6">
                          <i class="material-icons prefix">contact_phone</i>
                          <input value="{{telefono}}" type="text" readonly>
                          <label>Teléfono</label>
                        </div>
                      </div>
                    </form>
                </div>
            </span>
        </div>
    </li>`;
$("#closeAccount").submit(function (e) {
    accountNumber = $("#closeAccount").find("input[name='bank_account']").val();
    if (checkBankAccountFormat(accountNumber))
    {
        $.ajax({
            url: API_REST_URL + "/cuenta/" + accountNumber,
            type: "GET",
            beforeSend: function ()
            {
                $("#cargandoCuenta").modal("open");
            },
            error: function (xhr)
            {
                Materialize.toast(xhr.responseText, 4000);
            },
            success: function (result) {
                var cuenta = JSON.parse(result);
                
                /* Mensaje de error del saldo */
                if(cuenta.saldo === 0)
                {
                    $("#closeAccountErrorMessage").hide();
                    $("#cerrarCuenta").prop("disabled",false);
                }
                else
                {
                    $("#closeAccountErrorMessage").show();
                    $("#cerrarCuenta").prop("disabled",true);
                }
                /* Información de la cuenta */
                $("#infoCuenta").find("input[name='numero_cuenta']").val(cuenta.numeroCuenta);
                $("#infoCuenta").find("input[name='saldo']").val(cuenta.saldo);
                $("#infoCuenta").find("input[name='titulares']").val(cuenta.titulares.length);
                
                /* Mensaje de error de titulares */
                if(cuenta.titulares.length > 0)
                {
                    $("#noclientesAccountErrorMessage").hide();
                    cuenta.titulares.forEach( function(titular,index) {
                        var actualTitular = templateUser;
                        $("#actualAccountClients").html("");
                        actualTitular = actualTitular.replace(/{{cliente_nombre}}/g, titular.nombre);
                        actualTitular = actualTitular.replace(/{{dni}}/g, titular.dni);
                        actualTitular = actualTitular.replace(/{{telefono}}/g, titular.telefono);
                        $("#actualAccountClients").append(actualTitular);
                    });
                    $('.collapsible').collapsible('open', 0);
                }
                else
                {
                    $("#noclientesAccountErrorMessage").show();
                }
                
                /* Renderizar y abrir modal */
                Materialize.updateTextFields();
                $("#infoCuenta").modal("open");
            },
            complete: function ()
            {
                $("#cargandoCuenta").modal("close");
                $("#closeAccount").find("input[name='bank_account']").val("");
            }
        });
    }
    else
    {
        Materialize.toast("El formato de la cuenta es incorrecto.", 4000);
    }
    e.preventDefault();
});
$("#cerrarCuenta").click(function(e){
    $.ajax({
        url: API_REST_URL + "/cuenta/" + accountNumber,
        type: "DELETE",
        beforeSend: function ()
        {
            $("#cargandoCuenta").modal("open");
        },
        error: function (xhr)
        {
            Materialize.toast(xhr.responseText, 4000);
        },
        success: function (result) {
            Materialize.toast("Cuenta eliminada correctamente.", 4000);
        },
        complete: function ()
        {
            $("#cargandoCuenta").modal("close");
        }
    });
    e.preventDefault();
});
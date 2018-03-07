var transaction;
$("#putTransation").submit(function (e) {
    var modalP = $("#infoTransaccion").find("p"),
            type = $("#putTransation").find("input[name='opType']").prop('checked'),
            typeReal = type ? "reintegro" : "ingreso",
            amount = $("#putTransation").find("input[name='amount']").val(),
            description = $("#putTransation").find("textarea[name='description']").val(),
            accountNumber = $("#putTransation").find("input[name='bank_account']").val();
    transaction = {type: typeReal, amount: amount, description: description, accountNumber: accountNumber};
    modalP.html(modalP.attr("data-text")
            .replace("{{type}}", typeReal)
            .replace("{{amount}}", amount)
            .replace("{{account}}", accountNumber)
            .replace("{{description}}", description));
    $("#infoTransaccion").modal("open");
    e.preventDefault();
});
$("#doTransaction").click(function (e) {
    $("#infoTransaccion").modal("close");
    if (transaction.type === "reintegro")
    {
        $.ajax({
            url: API_REST_URL + "/operaciones",
            type: "POST",
            data: JSON.stringify(transaction),
            beforeSend: function (request)
            {
                console.log(localStorage.getItem("token"));
                request.setRequestHeader("token", localStorage.getItem("token"));
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
    }
    else //ingreso
    {
        $.ajax({
            url: API_REST_URL + "/operaciones",
            type: "PUT",
            data: JSON.stringify(transaction),
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
                Materialize.toast("Cuenta eliminada correctamente.", 4000);
            },
            complete: function ()
            {
                $("#cargandoCuenta").modal("close");
            }
        });
    }

    e.preventDefault();
});
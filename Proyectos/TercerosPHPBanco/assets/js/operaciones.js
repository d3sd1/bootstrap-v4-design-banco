$("#ingreso").click(function(e){
    goUrl("ingreso");
    e.preventDefault();
});
$("#reintegro").click(function(e){
    goUrl("reintegro");
    e.preventDefault();
});

function goUrl(tipo)
{
    var cantidad = $("#loginForm").find("input[name='cantidad']").val(),
        cuenta = $("#loginForm").find("input[name='cuenta']").val();
    window.location = "?page=dooperacion&cantidad=" + cantidad + "&tipo=" + tipo + "&cuenta=" + cuenta;
}
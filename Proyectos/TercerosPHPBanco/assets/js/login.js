$("#loginForm").submit(function(e){
    var dni = $("#loginForm").find("input[name='dni']").val(),
        pin = $("#loginForm").find("input[name='pin']").val();
    window.location = "?page=dologin&dni=" + dni + "&pin=" + pin;
    e.preventDefault();
});
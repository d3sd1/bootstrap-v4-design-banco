const API_REST_URL = "http://localhost:8000";
function isUserConnected()
{
    var token = localStorage.getItem("token");
    /* 
     * La promise sirve para que la función asíncrona devuelva
     * el valor tras ejecutarse, y no antes de ello.
     */
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: API_REST_URL + "/login/" + token,
            type: "GET",
            error: function ()
            {
                resolve(false);
            },
            success: function (result) {
                resolve(true);
            }
        })
    });
}
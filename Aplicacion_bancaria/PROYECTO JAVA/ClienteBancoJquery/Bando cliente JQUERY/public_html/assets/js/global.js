const API_REST_URL = "http://localhost:8000";
$.ajaxSetup({
    error: function (xhr) {
        if(xhr.status === 407) //token caducado!!
        {
            logout();
        }
    }
});
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
                reject(false);
            },
            success: function (result) {
                resolve(true);
            }
        });
    });
}
function replaceVariableView(varName, varValue)
{
    $(":contains('{{" + varName + "}}')").each(function () {
        if ($(this).children().length === 0 || $(this).children(":contains('{{" + varName + "}}')").length === 0)
        {
            $(this).html(function () {
                return $(this).html().replace("{{" + varName + "}}", varValue);
            });
        }
    });
}
function formToJson($form) {

    var returnArray = {},
            dataArr = $form.serializeArray();
    for (var data in dataArr)
    {
        var input = dataArr[data];
        returnArray[input.name] = input.value;
    }
    return JSON.stringify(returnArray);
}
function parseJwt(token) {
    var base64Url = token.split('.')[1];
    var base64 = base64Url.replace('-', '+').replace('_', '/');
    return JSON.parse(window.atob(base64));
}
function getTokenUser()
{
    var token = localStorage.getItem("token"),
            userInfo = parseJwt(token);
    return userInfo;
}
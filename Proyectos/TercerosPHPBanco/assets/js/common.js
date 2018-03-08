$(document).ready(function () {
    $('.parallax').parallax();
});
function prettyHour(hour)
{
    return hour.replace(/(?!^)(?=(?:\d{2})+(?:\.|$))/gm, ':');
}
function getActualDate()
{
    var today = new Date(),
            dd = today.getDate(),
            mm =today.getMonth() + 1,
            yyyy = today.getFullYear();
    if (dd < 10) {
        dd = '0' + dd;
    }
    if (mm < 10) {
        mm = '0' + mm;
    }
    return yyyy + '-' + mm + '-' + dd;
}
function hasValue(...variables)
{
    var success = true;
    for (var variable of variables) {
        if (variable === null || variable === "")
        {
            success = false;
        }
    }
    return success;
}
function showModal(modal,msg = null)
{
    $('#' + modal).modal('open');
    if(msg !== null)
    {
        $('#' + modal).find("p").text(msg);
    }
}
function hideModal(modal,msg = null)
{
    $('#' + modal).modal('close');
}
function comprobarNumeroCuenta(accountNumber)
{
    var suma = 0;
    for (var i = 0; i < accountNumber.toString().length; i++)
    {
        if (i < 9) //posición 10, empezando en 0
        {
            var numero = parseInt(accountNumber.toString().substr(i, 1));
            suma += numero;
        }
        else
        {
            break;
        }
    }
    var numeroFinalResultado = suma % 9;
    return numeroFinalResultado === parseInt(accountNumber.toString().substr(9, 1));
}
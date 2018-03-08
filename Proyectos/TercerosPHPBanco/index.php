<?php
require 'init.php';

use Controllers\Views;
use Utils\Constants;

$page = (isset($_REQUEST[Constants::PAR_PAGE]) ? $_REQUEST[Constants::PAR_PAGE]:"");
/* Revisar si el usuario está conectado */
$cont = new Views();
switch($page)
{
    case "dologin":
        $dni = @$_REQUEST[Constants::PAR_DNI];
        $pin = @$_REQUEST[Constants::PAR_PIN];
        $cont->doLogin($dni,$pin);
    break;
    case "error":
        $cont->error();
    break;
    default:
        $cont->login();
}
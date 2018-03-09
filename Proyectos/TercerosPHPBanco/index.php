<?php
require 'init.php';

use Controllers\Views;
use Utils\Constants;

$page = (isset($_REQUEST[Constants::PAR_PAGE]) ? $_REQUEST[Constants::PAR_PAGE]:"");
/* Revisar si el usuario está conectado */
$cont = new Views();
switch($page)
{
    case "dooperacion":
        $tipo = @$_REQUEST[Constants::PAR_TIPO];
        $cantidad = @$_REQUEST[Constants::PAR_CANTIDAD];
        $cuenta = @$_REQUEST[Constants::PAR_CUENTA];
        $cont->doOperacion($tipo,$cantidad, $cuenta);
    break;
    default:
        $cont->operaciones();
}
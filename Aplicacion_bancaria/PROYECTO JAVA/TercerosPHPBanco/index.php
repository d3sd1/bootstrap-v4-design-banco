<?php
require 'init.php';

use Controllers\Account;
use Controllers\Ajax;
use Utils\Constants;
use Utils\Utils;

$utils = new Utils();
$page = $utils->sanitizeParameter(Constants::PAR_PAGE);
/* Revisar si el usuario está conectado */
$account = new Account();
$ajax = new Ajax();
switch($page)
{
    case "ajax":
        $action = $utils->sanitizeParameter(Constants::PAR_AJAX);
        $ajax->getAjax($action);
    break;
    case "error":
        $account->error();
    break;
    case "ingresos_reintegros":
        $account->addFounds();
    break;
    case "abrir_cuenta":
        $account->addAccount();
    break;
    case "cerrar_cuenta":
        $account->delAccount();
    break;
    default:
        $account->account_movements();
}
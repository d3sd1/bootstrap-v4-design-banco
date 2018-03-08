<?php

namespace Controllers;
use Services\Database;
use Utils\Constants;
use Utils\Utils;

class Ajax {
    /* Funciones globales */
    public function getAjax($action)
    {
        $utils = new Utils();
        switch($action)
        {
            case "movements":
                $accountNumber = $utils->sanitizeParameter(Constants::PAR_ACCOUNTNUMBER);
                $beginDate = $utils->sanitizeParameter(Constants::PAR_BEGINDATE);
                $endDate = $utils->sanitizeParameter(Constants::PAR_ENDDATE);
                $response = $this->getMovements($accountNumber,$beginDate,$endDate);
            break;
            case "ingresos_reintegros":
                $accountNumber = $utils->sanitizeParameter(Constants::PAR_ACCOUNTNUMBER);
                $amount = $utils->sanitizeParameter(Constants::PAR_AMOUNT);
                $description = $utils->sanitizeParameter(Constants::PAR_DESCRIPTION);
                $force = filter_var($utils->sanitizeParameter(Constants::PAR_FORCE), FILTER_VALIDATE_BOOLEAN);
                $response = $this->doIngreso($accountNumber,$amount,$description,$force);
            break;
            case "abrir_cuenta_revisar":
                $accountNumber = $utils->sanitizeParameter(Constants::PAR_ACCOUNTNUMBER);
                $response = $this->checkCuenta($accountNumber);
            break;
            case "revisar_dni":
                $accountNumber = $utils->sanitizeParameter(Constants::PAR_DNI);
                $response = $this->checkDni($accountNumber);
            break;
            default:
                $response = $this->getDefaultResponse($action);
        }
        echo json_encode($response);
    }
    
    private function makeAjax($success,$description = null,$data = null,$forced = false)
    {
        $response = array(
            "success" => $success,
            "forced" => $forced,
            "description" => (!$success ? ($description == null ? Constants::DEFAULT_AJAX_ERROR_DESC:$description):""),
            "data" => ($data == null ? array():$data)
        );
        return $response;
    }
    /* Funciones específicas */
    private function getDefaultResponse($action)
    {
        return $this->makeAjax(false,str_replace("{action}",$action,Constants::DEFAULT_AJAX_ERROR_ACTIONNOTFOUND));
    }
    private function getMovements($accountNumber, $beginDate, $endDate){
        $success = false;
        $descripcionError = null;
        $utils = new Utils();
        $movimientos = null;
        $db = new Database();
        
        /* Comprobación de errores */
        if(!$utils->comprobarNumeroCuenta($accountNumber))
        {
            $descripcionError = "El formato de la cuenta bancaria introducido es incorrecto.";
        }
        else if(!$db->checkCuentaExiste($accountNumber))
        {
            $descripcionError = "No se ha encontrado la cuenta bancaria introducida.";
        }
        /* Comprobar valores contra la DB. Primero, si se han introducido fechas. Segundo si no. */
        else if($utils->checkHasValue($accountNumber,$beginDate,$endDate))
        {
            if(!$utils->checkDateFormat($beginDate,$endDate))
            {
                $descripcionError = "El formato de fechas introducido es incorrecto.";
            }
            else if($beginDate > $endDate)
            {
                $descripcionError = "La primera fecha debe ser menor que la segunda.";
            }
            else
            {
                $movimientos = $db->getMovimientosEntreFechas($accountNumber, $beginDate, $endDate);
            }
        }
        else if($utils->checkHasValue($accountNumber))
        {
            $movimientos = $db->getMovimientos($accountNumber);
        }
        
        /* Comprobar si hay registros */
        if($movimientos == null && $descripcionError == null)
        {
            $descripcionError = "No se han encontrado movimientos.";
        }
        else if($descripcionError == null)
        {
            $success = true;
        }
        return $this->makeAjax($success,$descripcionError,$movimientos);
    }
    public function doIngreso($accountNumber,$amount,$description,$force)
    {
        $success = false;
        $descripcionError = null;
        $utils = new Utils();
        $movimientos = null;
        $db = new Database();
        /* Comprobación de errores */
        if(!$utils->checkHasValue($accountNumber,$amount,$description))
        {
            $descripcionError = "Faltan parámetros.";
        }
        else if(!$utils->comprobarNumeroCuenta($accountNumber))
        {
            $descripcionError = "El formato de la cuenta bancaria introducido es incorrecto.";
        }
        else if(!$db->checkCuentaExiste($accountNumber))
        {
            $descripcionError = "La cuenta bancaria introducida no está dada de alta.";
        }
        else if(boolval($force))
        {
            /* Todo ha ido bien y se ha confirmado la acción, así que se procede a la inserción en la base de datos. */
            $addToCuenta = $db->addSaldoCuenta($accountNumber,$amount);
            $addToSaldoCliente = $db->addToSaldoClientesCuenta($accountNumber,$amount);
            $addMovimiento = $db->addMovimiento($accountNumber,$description,$amount);
            $success = ($addToCuenta && $addToSaldoCliente && $addMovimiento);
        }
        else if(!$force)
        {
            /* Todo ha ido bien pero no se ha confirmado la acción, así que se solicita la confirmación al cliente.*/
            $success = true;
        }
        return $this->makeAjax($success,$descripcionError,$movimientos,$force);
    }
    public function checkCuenta($accountNumber)
    {
        $db = new Database();
        $utils = new Utils();
        $descriptionError = "";
        $existe = $db->checkCuentaExiste($accountNumber);
        if(!$utils->comprobarNumeroCuenta($accountNumber))
        {
            $descriptionError = "La cuenta introducida tiene un formato incorrecto.";
        }
        else if($existe)
        {
            $descriptionError = "La cuenta introducida ya existe.";
        }
        return $this->makeAjax(!$existe, $descriptionError);
    }
    public function checkDni($dni)
    {
        $db = new Database();
        $utils = new Utils();
        $descriptionError = "";
        $clienteExiste = $db->checkClienteExiste($dni);
        $clientData = null;
        $success = true;
        if(!$utils->comprobarFormatoDni($dni))
        {
            $descriptionError = "El DNI introducido tiene un formato incorrecto.";
            $success = false;
        }
        else if($clienteExiste)
        {
            /* Devolver los datos del cliente */
            $clientData = $db->getDatosCliente($dni);
        }
        return $this->makeAjax($success, $descriptionError,$clientData);
    }
}

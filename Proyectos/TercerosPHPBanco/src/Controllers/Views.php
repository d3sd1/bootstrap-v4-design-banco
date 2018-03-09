<?php

namespace Controllers;
use Services\API;

class Views {
    public function operaciones(){
        $api = new API();
        include "views/operaciones.php";
    }
    public function doOperacion($tipo, $cantidad, $cuenta){
        $api = new API();
        $operacion = array("type" => $tipo,
            "amount" => $cantidad,
            "description" => ucfirst($tipo)." de Endesa.",
            "accountNumber" => $cuenta);
        if($tipo === "ingreso")
        {
            $response = $api->doIngreso($operacion);
        }
        else
        {
            $response = $api->doReintegro($operacion);
        }
        
        include "views/dooperacion.php";
        
    }
}

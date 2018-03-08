<?php

namespace Utils;

class Utils
{

    public function sanitizeParameter($requestParameter)
    {
        return (isset($_REQUEST[$requestParameter]) ? filter_input(INPUT_GET, $requestParameter) : "");
    }

    public function checkHasValue(...$parameters)
    {
        $success = true;
        foreach ($parameters as $parameter)
        {
            if (!isset($parameter) || $parameter == null || $parameter == "")
            {
                $success = false;
            }
        }
        return $success;
    }

    public function comprobarNumeroCuenta($accountNumber)
    {
        $suma = 0;
        for ($i = 0; $i < strlen($accountNumber); $i++)
        {
            if ($i < 9) //posición 10, empezando en 0
            {
                $numero = substr($accountNumber, $i, 1);
                $suma += $numero;
            } else
            {
                break;
            }
        }
        $numeroFinalResultado = $suma % 9;
        $formatoCorrecto = ($numeroFinalResultado == substr($accountNumber, 9, 1));
        $longitudCorrecta = (strlen($accountNumber) == 10);
        return $formatoCorrecto && $longitudCorrecta;
    }

    public function comprobarFormatoDni($dni)
    {
        $valido = false;
        if (preg_match('/[0-9]{7,8}[A-Z]/', $dni) == 1)
        {
            $valido = true;
        }
        return $valido;
    }

    public function checkDateFormat(...$dates)
    {
        $formatsSuccess = true;
        foreach ($dates as $date)
        {
            if (preg_match('/([12]\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\d|3[01]))/', $date) == 0)
            {
                $formatsSuccess = false;
            }
        }
        return $formatsSuccess;
    }

}

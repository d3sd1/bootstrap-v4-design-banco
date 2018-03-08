<?php

namespace Services;

use Utils\Config;

class Database
{

    const SQL_ALL_MOVEMENTS = "SELECT * FROM movimientos";
    const SQL_FILTER_MOVEMENTS = "SELECT * FROM movimientos WHERE mo_ncu=?";
    const SQL_FILTER_ACCOUNT = "SELECT * FROM cuentas WHERE cu_ncu=?";
    const SQL_FILTER_CLIENTS = "SELECT * FROM clientes WHERE cl_dni=?";
    const SQL_FILTER_MOVEMENTS_WITHDATE = "SELECT * FROM movimientos WHERE mo_ncu=? AND mo_fec > ? AND mo_fec < ?";
    const SQL_INSERT_MOVEMENT = "INSERT INTO movimientos (mo_ncu,mo_fec,mo_hor,mo_des,mo_imp) VALUES (?,?,?,?,?)";
    const SQL_UPDATE_CLIENT_MONEY = "UPDATE clientes SET cl_sal=? WHERE cl_dni=?";
    const SQL_UPDATE_ACCOUNT_MONEY = "UPDATE cuentas SET cu_sal=? WHERE cu_ncu=?";
    const SQL_GET_ACCOUNT_MONEY = "SELECT cu_sal FROM cuentas WHERE cu_ncu=?";
    const SQL_GET_CLIENT_MONEY = "SELECT cl_sal FROM clientes WHERE cl_dni=?";
    const SQL_GET_CLIENT = "SELECT * FROM clientes WHERE cl_dni=?";
    const SQL_GET_CLIENTS_BYCUENTA = "SELECT cu_dn1,cu_dn2 FROM cuentas WHERE cu_ncu=?";

    private function connect()
    {
        $con = new \mysqli(Config::DB_HOST, Config::DB_USER, Config::DB_PASS, Config::DB_NAME);
        if ($con->connect_error)
        {
            echo "Error en la conexión: " . $con->connect_error;
        }
        return $con;
    }

    public function getAllMovimientos()
    {
        $con = $this->connect();
        $data = array();
        $stmt = $con->prepare(Database::SQL_ALL_MOVEMENTS);
        $stmt->execute();
        $result = $stmt->get_result();
        while ($row = $result->fetch_assoc())
        {
            $data[] = $row;
        }
        $stmt->close();
        $con->close();
        return $data;
    }
    public function checkCuentaExiste($accountNumber)
    {
        $con = $this->connect();
        $stmt = $con->prepare(Database::SQL_FILTER_ACCOUNT);
        $stmt->bind_param("s", $accountNumber);

        $stmt->execute();
        $stmt->store_result();
        $countNumber = $stmt->num_rows;
        $stmt->close();
        $con->close();
        return $countNumber > 0;
    }
    public function checkClienteExiste($dni)
    {
        $con = $this->connect();
        $stmt = $con->prepare(Database::SQL_FILTER_CLIENTS);
        $stmt->bind_param("s", $dni);

        $stmt->execute();
        $stmt->store_result();
        $countNumber = $stmt->num_rows;
        $stmt->close();
        $con->close();
        return $countNumber > 0;
    }
    public function getMovimientos($accountNumber)
    {
        $con = $this->connect();
        $data = array();
        $stmt = $con->prepare(Database::SQL_FILTER_MOVEMENTS);
        $stmt->bind_param("s", $accountNumber);
        $stmt->execute();
        $result = $stmt->get_result();
        while ($row = $result->fetch_assoc())
        {
            $data[] = $row;
        }
        $stmt->close();
        $con->close();
        return $data;
    }
    public function getMovimientosEntreFechas($accountNumber, $beginDate, $endDate)
    {
        $con = $this->connect();
        $data = array();
        $stmt = $con->prepare(Database::SQL_FILTER_MOVEMENTS_WITHDATE);
        $stmt->bind_param("sss", $accountNumber, $beginDate, $endDate);
        $stmt->execute();
        $result = $stmt->get_result();
        while ($row = $result->fetch_assoc())
        {
            $data[] = $row;
        }
        $stmt->close();
        $con->close();
        return $data;
    }
    public function addMovimiento($accountNumber,$description,$amount)
    {
        $date = date("Y-m-d");
        $hour = date("His");
        $con = $this->connect();
        $stmt = $con->prepare(Database::SQL_INSERT_MOVEMENT);
        $stmt->bind_param("sssss", $accountNumber,$date,$hour, $description, $amount);
        $stmt->execute();
        $affectedRows = $stmt->affected_rows;
        $stmt->close();
        $con->close();
        return $affectedRows > 0;
    }
    public function getClientesCuenta($accountNumber)
    {
        $con = $this->connect();
        $clientes = array();
        $stmt = $con->prepare(Database::SQL_GET_CLIENTS_BYCUENTA);
        $stmt->bind_param("s", $accountNumber);
        $stmt->execute();
        $result = $stmt->get_result();
        while ($row = $result->fetch_assoc())
        {
            $clientes[] = $row["cu_dn1"];
            $clientes[] = $row["cu_dn2"];
        }
        $stmt->close();
        $con->close();
        return $clientes;
    }
    public function getSaldoCliente($dni)
    {
        $con = $this->connect();
        $stmt = $con->prepare(Database::SQL_GET_CLIENT_MONEY);
        $stmt->bind_param("s", $dni);
        $stmt->execute();
        $result = $stmt->get_result();
        $data = $result->fetch_assoc()["cl_sal"];
        $stmt->close();
        $con->close();
        return $data;
    }
    public function getDatosCliente($dni)
    {
        $con = $this->connect();
        $stmt = $con->prepare(Database::SQL_GET_CLIENT);
        $stmt->bind_param("s", $dni);
        $stmt->execute();
        $result = $stmt->get_result()->fetch_assoc();
        $data = array(
            "cl_dni" => $result["cl_dni"],
            "cl_nom" => $result["cl_nom"],
            "cl_tel" => $result["cl_tel"],
            "cl_ema" => $result["cl_ema"],
            "cl_dir" => $result["cl_dir"]
        );
        
        $stmt->close();
        $con->close();
        return $data;
    }
    public function addSaldoCliente($accountNumber,$amount,$dni)
    {
        $nuevoSaldo = $amount + $this->getSaldoCliente($dni);
        
        $con = $this->connect();
        $data = array();
        $stmt = $con->prepare(Database::SQL_UPDATE_CLIENT_MONEY);
        $stmt->bind_param("is", $nuevoSaldo, $dni);
        $stmt->execute();
        $affectedRows = $stmt->affected_rows;
        $stmt->close();
        $con->close();
        return $affectedRows > 0;
    }
    public function addToSaldoClientesCuenta($accountNumber,$amount)
    {
        $clientes = $this->getClientesCuenta($accountNumber);
        $success = true;
        foreach($clientes as $cliente)
        {
            $status = $this->addSaldoCliente($accountNumber,$amount,$cliente);
            if(!$status)
            {
                $success = false;
            }
        }
        return $success;
    }
    public function getSaldoCuenta($accountNumber)
    {
        $con = $this->connect();
        $stmt = $con->prepare(Database::SQL_GET_ACCOUNT_MONEY);
        $stmt->bind_param("s", $accountNumber);
        $stmt->execute();
        $result = $stmt->get_result();
        $data = $result->fetch_assoc()["cu_sal"];
        $stmt->close();
        $con->close();
        return $data;
    }
    public function addSaldoCuenta($accountNumber,$amount)
    {
        $nuevoSaldo = $amount + $this->getSaldoCuenta($accountNumber);
        
        $con = $this->connect();
        $stmt = $con->prepare(Database::SQL_UPDATE_ACCOUNT_MONEY);
        $stmt->bind_param("is", $nuevoSaldo, $accountNumber);
        $stmt->execute();
        $affectedRows = $stmt->affected_rows;
        $stmt->close();
        $con->close();
        return $affectedRows > 0;
    }

}

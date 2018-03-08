<?php

namespace Controllers;
use Services\Database;

class Account {
    public function account_movements(){
        $db = new Database();
        $movimientos = $db->getAllMovimientos();
        include "views/account_movements.php";
    }
    public function addFounds(){
        include "views/addfounds.php";
    }
    public function addAccount(){
        include "views/addaccount.php";
    }
    public function delAccount(){
        include "views/delaccount.php";
    }
    public function error(){
        include "views/error.php";
    }
}

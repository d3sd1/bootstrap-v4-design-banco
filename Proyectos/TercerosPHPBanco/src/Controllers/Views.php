<?php

namespace Controllers;
use Services\API;

class Views {
    public function login(){
        $api = new API();
        include "views/login.php";
    }
    public function doLogin($dni,$pin){
        $api = new API();
        include "views/dologin.php";
        $loginSuccess = $api->doLogin($dni,$pin);
        if($loginSuccess)
        {
            header("Location: /?page=error");
            die();
        }
        else
        {
            header("Location: /?page=error");
            die();
        }
    }
    public function error(){
        include "views/error.php";
    }
}

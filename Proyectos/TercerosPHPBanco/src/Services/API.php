<?php

namespace Services;

use GuzzleHttp\Client;
use Utils\Constants;

class API
{

    private function apiCall($url, $type = 'GET', $bodyObj = null)
    {
        $headers = [
            'Accept' => 'application/json',
            'Content-type' => 'application/json',
            'rest-api-key' => Constants::API_KEY
        ];
        try
        {

            $client = new Client(['verify' => false,'headers' => $headers]);
            switch ($type)
            {
                case "GET":
                    $response = $client->request("GET", $url, [
                        'headers' => $headers]);
                    break;
                case "POST":
                    $response = $client->post($url, [
                        \GuzzleHttp\RequestOptions::JSON => $bodyObj
                    ]);
                    break;
                case "PUT":
                    $response = $client->put($url, [
                        \GuzzleHttp\RequestOptions::JSON => $bodyObj
                    ]);
                    break;
            }
            $result = array("code" => $response->getStatusCode(), "message" => $response->getBody());
        } catch (\GuzzleHttp\Exception\ClientException $e)
        {
            $result = array("code" => $e->getResponse()->getStatusCode(), "message" => $e->getResponse()->getBody());
        }


        return $result;
    }

    public function doIngreso($operacion)
    {
        return $this->apiCall(Constants::API_URL.Constants::API_URL_OPERACIONES, "PUT", $operacion);
    }

    public function doReintegro($operacion)
    {
        return $this->apiCall(Constants::API_URL.Constants::API_URL_OPERACIONES, "POST", $operacion);
    }

}

<?php

namespace Services;

use GuzzleHttp\Client;
use Utils\Constants;

class API
{
    private function apiCall($url, $type = 'GET')
    {
        try
        {
            $client = new Client(['verify' => false]);
            $response = $client->request($type, $url, [
                'headers' => [
                    'Accept'       => 'application/json',
                    'Content-type' => 'application/json'
            ]]);
            $result = json_decode($response->getBody()->getContents(), true);
        }
        catch(\GuzzleHttp\Exception\ClientException $e)
        {
            $result = false;
        }
        
        return $result;
    }
    public function doLogin($playerId)
    {
        return $this->apiCall($playerId,Constants::API_URL + Constants::API_URL_CLIENTLOGIN);
    }

}

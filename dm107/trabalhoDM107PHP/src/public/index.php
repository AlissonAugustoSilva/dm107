<?php
	use \Psr\Http\Message\ServerRequestInterface as Request;
	use \Psr\Http\Message\ResponseInterface as Response;
	require '../vendor/autoload.php';
	$config['displayErrorDetails'] = true;
	$config['addContentLengthHeader'] = false;
	$config['db']['host'] = "localhost";
	$config['db']['user'] = "root";
	$config['db']['pass'] = "root";
	$config['db']['dbname'] = "dm107";
	$app = new \Slim\App(["config" => $config]);
	$app->add(new Tuupola\Middleware\HttpBasicAuthentication([
			"users" => [
				"admin" => "admin"
		]
	]));
	$container = $app->getContainer();
	$container['db'] = function ($c) {
		$dbConfig = $c['config']['db'];
		$pdo = new PDO("mysql:host=" . $dbConfig['host'] . ";dbname=" .
		$dbConfig['dbname'],
		$dbConfig['user'], $dbConfig['pass']);
		$pdo->setAttribute(PDO::ATTR_ERRMODE,
		PDO::ERRMODE_EXCEPTION);
		$pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE,
		PDO::FETCH_ASSOC);
		$db = new NotORM($pdo);
		return $db;
	};
	$app->delete('/apiEntrega/{id}', function(Request $request, Response $response){
		$idEntrega = $request->getAttribute('id');
		$entrega = $this->db->entrega[$idEntrega];
		if(!empty($entrega)){
			$entrega->delete();
		}else{
			return $response->withStatus(404);
		}
	});
	$app->patch('/apiEntrega/{id}', function(Request $request, Response $response){
		$idEntrega = $request->getAttribute("id");
		$entrega = $this->db->entrega[$idEntrega];
		if(empty($entrega)){
			return $response->withStatus(404);
		}
		$body = $request->getBody();
		$data = json_decode($body);
		$id = $data->id;
		$numPedido = $data->numPedido;
		$idCliente = $data->idCliente;
		$nomeRecebedor = $data->nomeRecebedor;
		$cpfRecebedor = $data->cpfRecebedor;
		$dataEntrega = $data->dataEntrega;
		if(empty($nomeRecebedor) || empty($cpfRecebedor) || empty($dataEntrega)){
			return $response->withStatus(400);
		}
		$updEntrega = array(
			"id" => $id,
			"numPedido" => $numPedido,
			"idCliente" => $idCliente,
			"nomeRecebedor" => $nomeRecebedor,
			"cpfRecebedor" => $cpfRecebedor,
			"dataEntrega" => date("Y-m-d H:i:s",strtotime(str_replace('/','-',$dataEntrega)))
        );
		$result = $entrega->update($updEntrega);
		return $result;
	});
	$app->run();
?>

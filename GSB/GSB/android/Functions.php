<?php
	require(dirname(__FILE__).'/Database.php');
	$response = array();
	
	
	if(isset($_GET['requete'])){
		$bdd = Database::connect();
	
	
		switch($_GET['requete']){

/******************************************************************************************/
/******************************************************************************************/

			case 'connexion':
				$user = checkInput($_POST['username']);
				$pass = checkInput($_POST['password']);
				$response['ok_connection'] = false;
				$req = $bdd->prepare('SELECT * FROM visiteurs WHERE username = ? and password = ? ');
				$req->execute(array($user, $pass));
				$ligne = $req->fetch();

				//Vérification de la concordance username et password
				if($ligne['username'] == $user && $ligne['password'] == $pass )
				{
					$response['ok_connection'] = true;
					$response['username'] = $user;
					$response['password'] = $pass;
					$response['id'] 	= $ligne['id'];
				}
				break;

/******************************************************************************************/
/******************************************************************************************/

			case 'inscription':
				$req = $bdd->prepare('INSERT INTO visiteurs(id, username, password) VALUES(NULL, ? , ? )');
				$req->execute(array($user, $pass));
				$response['username'] = $user;
				$response['password'] = $pass;
				$response['message'] = true;
			break;

/******************************************************************************************/
/******************************************************************************************/
			case 'sauvegarde_km':
				$km = $_POST['km'];
				$date = $_POST['date'];
				$id = $_POST['id'];
				$req = $bdd->prepare('INSERT INTO frais_km(id, nombre_km, date, id_visiteur) VALUES(?, ?, ?, ?) ');
				$req->execute(array(NULL, $km, $date, $id));
				
				$response['id'] = $id;
				$response['km'] = $km;
				$response['date'] = $date;

/******************************************************************************************/
/******************************************************************************************/
			
			case 'sauvegarde_repas':
			$id = $_POST['id'];
			$date = $_POST['date'];
			$repas = $_POST['repas'];
			$req = $bdd->prepare('INSERT INTO frais_repas(id, prix_repas, date, id_visiteur) VALUES(?, ?, ?, ?) ');
			$req->execute(array(NULL, $repas, $date, $id));

			$response['id'] = $id;
			$response['date'] = $date;
			$response['repas'] = $repas;

/******************************************************************************************/
/******************************************************************************************/

			case 'sauvegarde_nuitee':
			$id = $_POST['id'];
			$date = $_POST['date'];
			$repas = $_POST['nuitee'];
			$req = $bdd->prepare('INSERT INTO frais_nuitee(id, prix_nuitee, date, id_visiteur) VALUES(?, ?, ?, ?) ');
			$req->execute(array(NULL, $nuitee, $date, $id));

			$response['id'] = $id;
			$response['date'] = $date;
			$response['repas'] = $nuitee;

/******************************************************************************************/
/******************************************************************************************/
		}

		Database::disconnect();
	}
echo json_encode($response);






function checkInput($data){
	$data = trim($data);
	$data = stripslashes($data);
	$data = htmlspecialchars($data);
	return $data;
}
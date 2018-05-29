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
			break;

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
				break;

/******************************************************************************************/
/******************************************************************************************/
			
			case 'sauvegarde_etape':
				$id = $_POST['id'];
				$date = $_POST['date'];
				$etape = $_POST['etape'];
				$req = $bdd->prepare('INSERT INTO frais_etape(id, cout_etape, date, id_visiteur) VALUES(?, ?, ?, ?) ');
				$req->execute(array(NULL, $etape, $date, $id));

				$response['id'] = $id;
				$response['date'] = $date;
				$response['etape'] = $etape;
				break;

/******************************************************************************************/
/******************************************************************************************/

			case 'sauvegarde_nuitee':
				$id = $_POST['id'];
				$date = $_POST['date'];
				$nuitee = $_POST['nuitee'];
				$req = $bdd->prepare('INSERT INTO frais_nuitee(id, prix_nuitee, date, id_visiteur) VALUES(?, ?, ?, ?) ');
				$req->execute(array(NULL, $nuitee, $date, $id));

				$response['id'] = $id;
				$response['date'] = $date ;
				$response['nuitee'] = $nuitee;
			break;

/******************************************************************************************/
/******************************************************************************************/

			case 'sauvegarde_hors_forfait':
				$id = $_POST['id'];
				$date = $_POST['date'];
				$motif = $_POST['motif'];
				$horsForfait = $_POST['horsForfait'];

				$req = $bdd->prepare('INSERT INTO frais_hors_forfait(id, date, motif, cout_frais, id_visiteur) VALUES(?, ?, ?, ?, ?) ');
				$req->execute(array(NULL, $date, $motif, $horsForfait, $id));

				$response['id'] = $id;
				$response['date'] = $date ;
				$response['motif'] = $motif;
				$response['horsForfait'] = $horsForfait;
			break;

/******************************************************************************************/
/******************************************************************************************/

			case 'recapitulatif':
				$id = $_POST['id'];
				$response = array();

				/*************************FRAIS REPAS*************************/
				$req = $bdd->prepare('	SELECT  visiteurs.id,
										DATE_FORMAT(frais_repas.date, \'%d/%m/%Y\') AS date,
										frais_repas.prix_repas
										FROM visiteurs
										INNER JOIN frais_repas
										ON visiteurs.id = frais_repas.id_visiteur
										WHERE visiteurs.id = ?
										ORDER BY frais_repas.date DESC
									');

				$req->execute(array($id));
				while($row = $req->fetch())
				{
					array_push($response, array( 
						'ELEMENT'	=>'REPAS',
						'ID'		=>$row[0], 
						'DATE'		=>$row[1],
						'PRIX'		=>$row[2]));
				}	
				
				/*************************FRAIS KM*************************/
				$req = $bdd->prepare('	SELECT  visiteurs.id,
										DATE_FORMAT(frais_km.date, \'%d/%m/%Y\') AS date,
										frais_km.nombre_km
										FROM visiteurs
										INNER JOIN frais_km
										ON visiteurs.id = frais_km.id_visiteur
										WHERE visiteurs.id = ?
										ORDER BY frais_km.date DESC
									');

				$req->execute(array($id));
				while($row = $req->fetch())
				{					
					array_push($response, array( 
						'ELEMENT'	=>'KM',
						'ID'		=>$row[0],
						'DATE'		=>$row[1],
						'PRIX'		=>$row[2]));
				}	

				/*************************FRAIS NUITEE*************************/
				$req = $bdd->prepare('	SELECT  visiteurs.id,
										DATE_FORMAT(frais_nuitee.date, \'%d/%m/%Y\') AS date,
										frais_nuitee.prix_nuitee
										FROM visiteurs
										INNER JOIN frais_nuitee
										ON visiteurs.id = frais_nuitee.id_visiteur
										WHERE visiteurs.id = ?
										ORDER BY frais_nuitee.date DESC
									');

				$req->execute(array($id));
				while($row = $req->fetch())
				{					
					array_push($response, array( 
						'ELEMENT'	=>'NUITEE',
						'ID'		=>$row[0],
						'DATE'		=>$row[1],
						'PRIX'		=>$row[2]));
				}

				/*************************FRAIS ETAPE*************************/
				$req = $bdd->prepare('	SELECT  visiteurs.id,
										DATE_FORMAT(frais_etape.date, \'%d/%m/%Y\') AS date,
										frais_etape.cout_etape
										FROM visiteurs
										INNER JOIN frais_etape
										ON visiteurs.id = frais_etape.id_visiteur
										WHERE visiteurs.id = ?
										ORDER BY frais_etape.date DESC
									');

				$req->execute(array($id));
				while($row = $req->fetch())
				{					
					array_push($response, array( 
						'ELEMENT'	=>'ETAPE',
						'ID'		=>$row[0],
						'DATE'		=>$row[1],
						'PRIX'		=>$row[2]));
				}

				/*************************FRAIS HORS-FORFAIT*************************/
				$req = $bdd->prepare('	SELECT  visiteurs.id,
										DATE_FORMAT(frais_hors_forfait.date, \'%d/%m/%Y\') AS date,
										frais_hors_forfait.cout_frais
										FROM visiteurs
										INNER JOIN frais_hors_forfait
										ON visiteurs.id = frais_hors_forfait.id_visiteur
										WHERE visiteurs.id = ?
										ORDER BY frais_hors_forfait.date DESC
									');

				$req->execute(array($id));
				while($row = $req->fetch())
				{					
					array_push($response, array( 
						'ELEMENT'	=>'HORS-FORFAIT',
						'ID'		=>$row[0],
						'DATE'		=>$row[1],
						'PRIX'		=>$row[2]));
				}


/******************************************************************************************/
/******************************************************************************************/

		}

		Database::disconnect();
	}
echo json_encode($response);


/******************************************************************************************/
/******************************************************************************************/
/******************************************************************************************/
/******************************************************************************************/


function checkInput($data){
	$data = trim($data); 
	$data = stripslashes($data);
	$data = htmlspecialchars($data);
	return $data;
}
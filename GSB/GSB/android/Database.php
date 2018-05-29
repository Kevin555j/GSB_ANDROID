<?php
	
	class Database
	{
			private static $dbHost = "localhost";
			private static $dbName = "gsb_android";
			private static $dbUser = "root";
			private static $dbUserPassword = "";

			private static $connection = null;

		//Fonction permettant de se connecter
		public static function connect()
		{
			try
			{
				self::$connection = new PDO('mysql:host='. self::$dbHost .'; dbname='.self::$dbName.'; charset=utf8', self::$dbUser,  self::$dbUserPassword, array(PDO::ATTR_ERRMODE => PDO:: ERRMODE_EXCEPTION));	
			}
			catch(Exception $e)
			{
				die('Erreur :'.$e->getMessage());
			}

			return self::$connection;
		}

		//Fonction permettant de se déconnecter
		public static function disconnect()
		{
			self::$connection = null;
		}

	}

?>
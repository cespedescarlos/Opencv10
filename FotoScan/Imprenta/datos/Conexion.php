<?php
class Conexion {
	private static $instancia = null;
	private static $conexion = null;

	private function __construct() {
	}

	public static function get_instance() {
		if (self::$instancia == null) {
			self::$instancia = new Conexion();
		}
		return self::$instancia;
	}

	public function obtener_conexion() {
		if (self::$conexion == null) {
			try {
				self::$conexion = new PDO('mysql:host=localhost;dbname=imprenta', 'root', '');
				self::$conexion->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
				self::$conexion->exec("SET CHARACTER SET utf8");
			} catch (PDOException $ex) {
				print "ERROR: ". $ex->getMessage(). "<br>";
				die();
			}
		}
		return self::$conexion;
	}

	public static function cerrar_conexion() {
		if (isset(self::$conexion)) {
			self::$conexion = null;
		}
	}
}
?>
<?php
include_once 'Conexion.php';

class DEmpresa {
	private $id;
	private $nombre;
	private $telefono;
	private $nombre_contacto;
	private $telefono_contacto;
	private $conexion;

	public function __construct() {
	}

	public function get_id() {
		return $this->id;
	}

	public function get_nombre() {
		return $this->nombre;
	}

	public function get_telefono() {
		return $this->telefono;
	}

	public function get_nombre_contacto() {
		return $this->nombre_contacto;
	}

	public function get_telefono_contacto() {
		return $this->telefono_contacto;
	}

	public function set_id($id) {
		$this->id = $id;
	}

	public function set_nombre($nombre) {
		$this->nombre = $nombre;
	}

	public function set_telefono($telefono) {
		$this->telefono= $telefono;
	}

	public function set_nombre_contacto($nombre_contacto) {
		$this->nombre_contacto = $nombre_contacto;
	}

	public function set_telfono_contacto($telfono_contacto) {
		$this->telfono_contacto = $telfono_contacto;
	}

	public function insertar() {
		$conexion = Conexion::get_instance()->obtener_conexion();
		try {
			$sql = "INSERT INTO empresa(nombre, telefono, nombre_contacto, telefono_contacto) VALUES(:nombre, :telefono, :nombre_contacto, :telefono_contacto)";
			$sentencia = $conexion->prepare($sql);
			$sentencia->bindParam(":nombre", $this->nombre, PDO::PARAM_STR);
			$sentencia->bindParam(":telefono", $this->telefono, PDO::PARAM_STR);
			$sentencia->bindParam(":nombre_contacto", $this->nombre_contacto, PDO::PARAM_STR);
			$sentencia->bindParam(":telefono_contacto", $this->telefono_contacto, PDO::PARAM_STR);
			$sentencia->execute();
		} catch (PDOException $e) {
			print "ERROR: ". $e->getMessage();
		}
	}
}
?>
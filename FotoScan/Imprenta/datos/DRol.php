<?php
include_once 'Conexion.php';

class DRol {
	private $id;
	private $nombre;
	private $activo;
	private $conexion;

	public function __construct() {
	}

	public function get_id() {
		return $this->id;
	}

	public function get_nombre() {
		return $this->nombre;
	}

	public function get_activo() {
		return $this->activo;
	}

	public function set_id($id) {
		$this->id = $id;
	}

	public function set_nombre($nombre) {
		$this->nombre = $nombre;	
	}

	public function set_activo($activo) {
		$this->activo = $activo;
	}

	public function obtener_roles_activos() {
		$conexion = Conexion::get_instance()->obtener_conexion();
		$roles = array();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "SELECT * FROM rol WHERE activo = 1";
				$sentencia = $conexion->prepare($sql);
				$sentencia->execute();
				$resultado = $sentencia->fetchAll();
				if (count($resultado)) {
					foreach ($resultado as $fila) {
						$roles[] = $fila;
					}
				}
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}
		return $roles;
	}

	public function insertar() {
		$conexion = Conexion::get_instance()->obtener_conexion();
		$insertado = false;
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "INSERT INTO rol(nombre, activo) VALUES(:nombre, :activo)";
				$sentencia = $conexion->prepare($sql);
				$sentencia->bindParam(":nombre", $this->nombre, PDO::PARAM_STR);
				$sentencia->bindParam(":activo", $this->activo, PDO::PARAM_INT);
				$insertado = $sentencia->execute();
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}
		return $insertado;
	}

	public function obtener_ultimo_id() {
		$conexion = Conexion::get_instance()->obtener_conexion();
		$id = null;
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "SELECT * FROM rol ORDER BY id DESC LIMIT 1";
				$sentencia = $conexion->prepare($sql);
				$sentencia->execute();
				$resultado = $sentencia->fetch();
				if (!empty($resultado)) {
					$id = $resultado['id'];
				}
			} catch (PDOException $ex) {
				print "ERROR: ". $ex->getMessage();
			}
		}
		return $id;
	}

	public function modificar_estado() {
		
	}

	public function nombre_existe() {
		$conexion = Conexion::get_instance()->obtener_conexion();
		$existe = false;
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "SELECT * FROM rol WHERE nombre = :nombre";
				$sentencia = $conexion->prepare($sql);
				$sentencia->execute();
				$resultado = $sentencia->fetch();
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}
	}
}
?>
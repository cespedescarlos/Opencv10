<?php
include_once 'Conexion.php';

class DPermiso {
	private $id;
	private $nombre;
	private $conexion;

	public function __construct() {
	}

	public function get_id() {
		return $this->id;
	}

	public function get_nombre() {
		return $this->nombre;
	}

	public function set_id($id) {
		$this->id = $id;
	}

	public function set_nombre($nombre) {
		$this->nombre = $nombre;	
	}

	public function devolver_nombre() {

	}

	public function obtener_permisos() {
		$conexion = Conexion::get_instance()->obtener_conexion();
		$permisos = array();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "SELECT * FROM permiso";
				$sentencia = $conexion->prepare($sql);
				$sentencia->execute();
				$resultado = $sentencia->fetchAll();
				if (count($resultado)) {
					foreach ($resultado as $fila) {
						$permiso = new DPermiso();
						$permiso->set_id($fila['id']);
						$permiso->set_nombre($fila['nombre']);
						$permisos[] = $permiso;
					}
				}
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}
		return $permisos;
	}
}
?>
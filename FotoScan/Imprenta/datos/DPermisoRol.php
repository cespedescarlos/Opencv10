<?php
include_once 'Conexion.php';

class DPermisoRol {
	private $id_permiso;
	private $id_rol;
	private $conexion;

	public function __construct() {
	}

	public function get_id_permiso() {
		return $this->id_permiso;
	}

	public function get_id_rol() {
		return $this->id_permiso;
	}

	public function set_id_permiso($id_permiso) {
		$this->id_permiso = $id_permiso;
	}

	public function set_id_rol($id_rol) {
		$this->id_rol = $id_rol;
	}

	public function obtener_permisos() {

	}

	public function insertar() {
		$conexion = Conexion::get_instance()->obtener_conexion();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "INSERT INTO rol_permiso(id_permiso, id_rol) VALUES(:id_permiso, :id_rol)";
				$sentencia = $conexion->prepare($sql);
				$sentencia->bindParam(":id_permiso", $this->id_permiso, PDO::PARAM_INT);
				$sentencia->bindParam(":id_rol", $this->id_rol, PDO::PARAM_INT);
				$sentencia->execute();
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}
	}
}
?>
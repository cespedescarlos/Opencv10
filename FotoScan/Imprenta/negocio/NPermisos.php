<?php
include_once '../datos/DPermiso.php';
include_once '../datos/DPermisoRol.php';

class NPermisos {
	private $dpermiso;
	private $dpermiso_rol;
	private $id_permiso;
	private $id_rol;


	public function __construct() {
	}

	public function set_id_permiso($id_permiso) {
		$this->id_permiso = $id_permiso;
	}

	public function set_id_rol($id_rol) {
		$this->id_rol = $id_rol;
	}

	public function obtener_permisos() {
		$permisos = new DPermiso();
		return $permisos->obtener_permisos();
	}

	public function asignar_permisos_rol() {
		$dpermiso_rol = new DPermiso_Rol();
		$dpermiso_rol->set_id_permiso($this->id_permiso);
		$dpermiso_rol->set_id_rol($this->id_rol);
		$dpermiso_rol->insertar();
	}
}
?>
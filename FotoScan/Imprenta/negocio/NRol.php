<?php
include_once '../datos/DRol.php';
include_once 'NPermisos.php';

class NRol {
	private $npermisos;
	private $drol;

	public function __construct() {
	}

	public function agregar($nombre) {
		$drol = new DRol();
		$drol->set_nombre($nombre);
		$mensaje = array();
		if ($drol->insertar()) {
			$mensaje = array(['respuesta' => 'Correcto.']);
		}
		return $mensaje;
	}

	public function listar_permisos() {
		$npermisos = new NPermisos();
		return $npermisos->obtener_permisos();
	}

	public function obtener_roles() {
		$drol = new DRol();
		return $drol->obtener_roles_activos();
	}
}
?>
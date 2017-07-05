<?php
include_once 'NRol.php';
include_once '../datos/DUsuario.php';

class NUsuario {
	private $nrol;
	private $dusuario;

	public function __construct() {
	}

	public function listar_roles() {
		$nrol = new NRol();
		return $nrol->obtener_roles();
	}

	public function agregar($nombre, $correo, $clave, $id_rol) {
		$dusuario = new DUsuario();
		$dusuario->set_nombre($nombre);
		$dusuario->set_correo($correo);
		$dusuario->set_clave($clave);
		$dusuario->set_estado(1);
		$dusuario->set_id_rol($id_rol);
		$mensaje = array();
		if ($dusuario->insertar()) {
			$mensaje = array(['respuesta' => 'Correcto.']);
		}
		return $mensaje;
	}

	public function modificar($id, $nombre, $correo, $clave, $id_rol) {
		$dusuario = new DUsuario();
		$dusuario->set_id($id);
		$dusuario->set_nombre($nombre);
		$dusuario->set_correo($correo);
		$dusuario->set_clave($clave);
		$dusuario->set_id_rol($id_rol);
		$mensaje = array();
		if ($dusuario->modificar()) {
			$mensaje = array(['respuesta' => 'Correcto.']);
		}
		return $mensaje;
	}

	public function obtener_datos($correo, $clave) {
		$dusuario = new DUsuario();
		$dusuario->set_correo($correo);
		$dusuario->set_clave($clave);
		return $dusuario->consultar_datos();
	}

	public function obtener_permiso($id_usuario, $id_permiso) {
		$dusuario = new DUsuario();
		$dusuario->set_id($id_usuario);
		return $dusuario->obtener_permiso($id_permiso);
	}

	public function validar_datos($nombre, $correo, $clave1, $clave2) {
		$dusuario = new DUsuario();
		$dusuario->set_nombre($nombre);
		$dusuario->set_correo($correo);
		$mensaje = array();
		if ($this->datos_vacios($nombre, $correo, $clave1, $clave2)) {
			$mensaje = array(['respuesta' => 'Todos los campos debe ser rellenados.']);
		} else if ($clave1 !== $clave2) {
			$mensaje = array(['respuesta' => 'Las contraseñas no coinciden.']);
		} else if ($dusuario->nombre_existe()) {
			$mensaje = array(['respuesta' => 'El nombre ya existe.']);
		} else if ($dusuario->correo_existe()) {
			$mensaje = array(['respuesta' => 'El correo ya existe.']); 
		}
		return $mensaje;
	}

	public function datos_vacios($nombre, $correo, $clave1, $clave2) {
		if (empty($nombre) || empty($correo) || empty($clave1) || empty($clave2)) {
			return true;
		}
		return false;
	}
}
?>
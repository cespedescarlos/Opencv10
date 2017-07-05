<?php
include_once 'NUsuario.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	$nombre = $_POST['nombre'];
	$correo = $_POST['correo'];
	$clave1 = $_POST['clave1'];
	$clave2 = $_POST['clave2'];
	$rol = $_POST['rol'];
	$nusuario = new NUsuario();
	$datos = $nusuario->validar_datos($nombre, $correo, $clave1, $clave2);
	if (empty($datos)) {
		$datos = $nusuario->agregar($nombre, $correo, $clave1, $rol);
	}

	echo json_encode($datos);
}
?>
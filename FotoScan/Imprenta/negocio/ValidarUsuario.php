<?php
include_once 'NUsuario.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	$correo = $_POST['correo'];
	$clave = $_POST['clave'];

	$nusuario = new NUsuario();
	$datos = $nusuario->obtener_datos($correo, $clave);

	echo json_encode($datos);
}
?>
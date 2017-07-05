<?php
include_once 'NUsuario.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
	$id_usuario = $_POST['id_usuario'];
	$id_permiso = $_POST['id_permiso'];

	$nusuario = new NUsuario();
	$datos = $nusuario->obtener_permiso($id_usuario, $id_permiso);

	echo json_encode($datos);
}
?>
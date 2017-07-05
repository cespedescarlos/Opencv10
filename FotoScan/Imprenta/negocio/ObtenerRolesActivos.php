<?php
include_once 'NRol.php';

if ($_SERVER['REQUEST_METHOD'] == 'GET') {
	$nrol = new NRol();
	$datos = $nrol->obtener_roles();

	echo json_encode($datos);
}
?>
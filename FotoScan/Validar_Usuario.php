<?php
	include_once 'Conexion.php';
	$correo=$_GET["correo"];
	$password=$_GET["password"];

		$conexion = Conexion::get_instance()->obtener_conexion();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "SELECT id FROM usuario WHERE correo='$correo' AND password='$password'";
				$sentencia = $conexion->prepare($sql);
				$sentencia->execute();
				if (!empty($aux = $sentencia->fetchAll(PDO::FETCH_ASSOC))) {
					echo json_encode($aux);
				}
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}

?>

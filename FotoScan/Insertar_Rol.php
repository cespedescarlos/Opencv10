<?php
	include_once 'Conexion.php';
	$nombre=$_GET["nombre"];

		$conexion = Conexion::get_instance()->obtener_conexion();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "INSERT INTO rol(nombre) 
									VALUES(:nombre)";
				$sentencia = $conexion->prepare($sql);
				$sentencia->bindParam(":nombre", $nombre, PDO::PARAM_STR);
				$sentencia->execute();
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}

?>

<?php
	include_once 'Conexion.php';
	$nombre=$_GET["nombre"];

		$conexion = Conexion::get_instance()->obtener_conexion();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "SELECT id FROM permiso WHERE nombre='$nombre'";
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

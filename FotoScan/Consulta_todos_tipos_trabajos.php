<?php
	include_once 'Conexion.php';

		$conexion = Conexion::get_instance()->obtener_conexion();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "SELECT * FROM tipo_trabajo";
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


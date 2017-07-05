<?php
	include_once 'Conexion.php';
	$rol=$_GET["rol"];
	$permiso=$_GET["permiso"];

		$conexion = Conexion::get_instance()->obtener_conexion();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "SELECT COUNT(*) AS cantidad FROM rolpermiso WHERE id_rol='$rol' AND id_permiso='$permiso'";
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

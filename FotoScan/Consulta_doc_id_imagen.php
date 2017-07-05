<?php
	include_once 'Conexion.php';
	$id=$_GET["id"];

		$conexion = Conexion::get_instance()->obtener_conexion();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "SELECT documento.id, documento.nombre, documento.fecha, documento.cotizacion, documento.precio, documento.ruta_archivos, documento.cantidad, documento.estado_placas 
						FROM `documento` WHERE documento.id in (SELECT imagen.id_doc from imagen where id='$id')";
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


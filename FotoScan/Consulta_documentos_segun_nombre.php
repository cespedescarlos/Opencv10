<?php
	include_once 'Conexion.php';
	$nombre=$_GET["nombre"];

		$conexion = Conexion::get_instance()->obtener_conexion();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "SELECT doc.id,doc.nombre,doc.fecha,doc.ruta_archivos,emp.nombre as nombre_empresa,tip.nombre as nombre_tipo_trabajo FROM documento doc, empresa emp, tipo_trabajo tip where doc.nombre='$nombre'  AND doc.id_empresa=emp.id AND doc.id_trabajo=tip.id ORDER BY doc.nombre";
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


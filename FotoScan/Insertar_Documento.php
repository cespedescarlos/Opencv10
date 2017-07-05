<?php
	include_once 'Conexion.php';
	$nombre=$_GET["nombre"];
	$fecha=$_GET["fecha"];
	$cotizacion=$_GET["cotizacion"];
	$precio=$_GET["precio"];
	$ruta_archivos=$_GET["ruta_archivos"];
	$cantidad=$_GET["cantidad"];
	$estado_placas=$_GET["estado_placas"];
	$id_trabajo=$_GET["id_trabajo"];
	$id_empresa=$_GET["id_empresa"];
	$id_usuario=$_GET["id_usuario"];

		$conexion = Conexion::get_instance()->obtener_conexion();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "INSERT INTO documento(nombre, fecha, cotizacion, precio, ruta_archivos, cantidad, estado_placas, id_trabajo, id_empresa, id_usuario) 
									VALUES(:nombre ,:fecha, :cotizacion, :precio, :ruta_archivos, :cantidad, :estado_placas, :id_trabajo, :id_empresa, :id_usuario)";
				$sentencia = $conexion->prepare($sql);
				$sentencia->bindParam(":nombre", $nombre, PDO::PARAM_STR);
				$sentencia->bindParam(":fecha", $fecha, PDO::PARAM_STR);
				$sentencia->bindParam(":cotizacion", $cotizacion, PDO::PARAM_INT);
				$sentencia->bindParam(":precio", $precio, PDO::PARAM_INT);
				$sentencia->bindParam(":ruta_archivos", $ruta_archivos, PDO::PARAM_STR);
				$sentencia->bindParam(":cantidad", $cantidad, PDO::PARAM_INT);	
				$sentencia->bindParam(":estado_placas", $estado_placas, PDO::PARAM_STR);
				$sentencia->bindParam(":id_trabajo", $id_trabajo, PDO::PARAM_INT);
				$sentencia->bindParam(":id_empresa", $id_empresa, PDO::PARAM_INT);
				$sentencia->bindParam(":id_usuario", $id_usuario, PDO::PARAM_INT);
				$sentencia->execute();
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}

?>

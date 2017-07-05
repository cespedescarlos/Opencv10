<?php

	include_once 'Conexion.php';
	$nombre=$_GET["nombre"];
	$size=$_GET["size"];
	$direccion=$_GET["direccion"];
	$filas=$_GET["filas"];
	$columnas=$_GET["columnas"];
	$id_doc=$_GET["id_doc"];

		$conexion = Conexion::get_instance()->obtener_conexion();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "INSERT INTO imagen(nombre_trabajo, file_size, file_path, filas, columnas, id_doc) 
									VALUES(:nombre ,:size, :direccion, :filas, :columnas, :id_doc)";
				$sentencia = $conexion->prepare($sql);
				$sentencia->bindParam(":nombre", $nombre, PDO::PARAM_STR);
				$sentencia->bindParam(":size", $size, PDO::PARAM_INT);
				$sentencia->bindParam(":direccion", $direccion, PDO::PARAM_STR);
				$sentencia->bindParam(":filas", $filas, PDO::PARAM_INT);
				$sentencia->bindParam(":columnas", $columnas, PDO::PARAM_INT);
				$sentencia->bindParam(":id_doc", $id_doc, PDO::PARAM_INT);	
				$sentencia->execute();
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}

?>

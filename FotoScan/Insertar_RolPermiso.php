<?php
	include_once 'Conexion.php';
	$permiso=$_GET["permiso"];
	$rol=$_GET["rol"];

		$conexion = Conexion::get_instance()->obtener_conexion();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "INSERT INTO rolpermiso(id_permiso,id_rol) 
									VALUES(:id_permiso,:id_rol)";
				$sentencia = $conexion->prepare($sql);
				$sentencia->bindParam(":id_permiso", $permiso, PDO::PARAM_INT);
				$sentencia->bindParam(":id_rol", $rol, PDO::PARAM_INT);
				$sentencia->execute();
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}

?>

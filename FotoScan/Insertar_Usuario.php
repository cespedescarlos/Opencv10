<?php
	include_once 'Conexion.php';
	$nombre=$_GET["nombre"];
	$correo=$_GET["correo"];
	$password=$_GET["password"];
	$id_rol=$_GET["id_rol"];

		$conexion = Conexion::get_instance()->obtener_conexion();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "INSERT INTO usuario(correo,nombre,password,id_rol) 
									VALUES(:correo,:nombre,:password,:id_rol)";
				$sentencia = $conexion->prepare($sql);
				$sentencia->bindParam(":correo", $correo, PDO::PARAM_STR);
				$sentencia->bindParam(":nombre", $nombre, PDO::PARAM_STR);
				$sentencia->bindParam(":password", $password, PDO::PARAM_STR);
				$sentencia->bindParam(":id_rol", $id_rol, PDO::PARAM_INT);
				$sentencia->execute();
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}

?>

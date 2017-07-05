<?php
	include_once 'Conexion.php';
	$nombre=$_GET["nombre"];
	$telefono=$_GET["telefono"];
	$nombre_contacto=$_GET["nombre_contacto"];
	$telefono_contacto=$_GET["telefono_contacto"];

		$conexion = Conexion::get_instance()->obtener_conexion();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "INSERT INTO empresa(nombre,telefono,nombre_contacto,telefono_contacto) 
									VALUES(:nombre,:telefono,:nombre_contacto,:telefono_contacto)";
				$sentencia = $conexion->prepare($sql);
				$sentencia->bindParam(":nombre", $nombre, PDO::PARAM_STR);
				$sentencia->bindParam(":telefono", $telefono, PDO::PARAM_STR);
				$sentencia->bindParam(":nombre_contacto", $nombre_contacto, PDO::PARAM_STR);
				$sentencia->bindParam(":telefono_contacto", $telefono_contacto, PDO::PARAM_STR);
				$sentencia->execute();
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}

?>

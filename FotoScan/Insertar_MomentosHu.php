<?php
/*
posicion_momento int not null,
	m0 double not null,
	m1 double not null,
	m2 double not null,
	m3 double not null,
	m4 double not null,
	m5 double not null,
	m6 double not null,
	promedio double not null,

	id_imagen int not null,
*/

	include_once 'Conexion.php';
	$posicion_momento=$_GET["posicion_momento"];
	$m0=$_GET["m0"];
	$m1=$_GET["m1"];
	$m2=$_GET["m2"];
	$m3=$_GET["m3"];
	$m4=$_GET["m4"];
	$m5=$_GET["m5"];
	$m6=$_GET["m6"];
	$promedio=$_GET["promedio"];
	$id_imagen=$_GET["id_imagen"];

		$conexion = Conexion::get_instance()->obtener_conexion();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "INSERT INTO momentoshu(posicion_momento, m0, m1, m2, m3, m4, m5, m6, promedio, id_imagen) 
									VALUES(:posicion_momento, :m0, :m1, :m2, :m3, :m4, :m5, :m6, :promedio, :id_imagen)";
				$sentencia = $conexion->prepare($sql);
				$sentencia->bindParam(":posicion_momento", $posicion_momento, PDO::PARAM_INT);	
				$sentencia->bindParam(":m0", $m0, PDO::PARAM_INT);
				$sentencia->bindParam(":m1", $m1, PDO::PARAM_INT);
				$sentencia->bindParam(":m2", $m2, PDO::PARAM_INT);
				$sentencia->bindParam(":m3", $m3, PDO::PARAM_INT);
				$sentencia->bindParam(":m4", $m4, PDO::PARAM_INT);
				$sentencia->bindParam(":m5", $m5, PDO::PARAM_INT);
				$sentencia->bindParam(":m6", $m6, PDO::PARAM_INT);
				$sentencia->bindParam(":promedio", $promedio, PDO::PARAM_INT);
				$sentencia->bindParam(":id_imagen", $id_imagen, PDO::PARAM_INT);
				$sentencia->execute();
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}

?>

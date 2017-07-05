<?php
	include_once 'Conexion.php';

	$m0=$_GET["m0"];
	$m1=$_GET["m1"];
	$m2=$_GET["m2"];
	$m3=$_GET["m3"];
	$m4=$_GET["m4"];
	$m5=$_GET["m5"];
	$m6=$_GET["m6"];

		$conexion = Conexion::get_instance()->obtener_conexion();
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "SELECT id_imagen FROM `momentoshu` WHERE m0='$m0' AND m1='$m1' AND m2='$m2' AND m3='$m3' AND m4='$m4' AND m5='$m5' AND m6= '$m6'";
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


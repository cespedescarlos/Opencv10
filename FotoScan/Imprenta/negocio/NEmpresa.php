<?php
include_once '../datos/DEmpresa.php';

class NEmpresa {
	private $dempresa;

	public function __construct() {
	}

	public function agregar() {
		$dempresa = new DEmpresa();
		$dempresa->insertar();
	}
}
?>
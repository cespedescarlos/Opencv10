<?php
include_once 'Conexion.php';

class DUsuario {
	private $id;
	private $nombre;
	private $correo;
	private $clave;
	private $estado;
	private $id_rol;
	private $conexion;

	public function __construct() {
	}

	public function get_id() {
		return $this->id;
	}

	public function get_nombre() {
		return $this->nombre;
	}

	public function get_correo() {
		return $this->correo;
	}

	public function get_clave() {
		return $this->clave;
	}

	public function get_estado() {
		return $this->estado;
	}

	public function get_id_rol() {
		return $this->id_rol;
	}

	public function set_id($id) {
		$this->id = $id;
	}

	public function set_nombre($nombre) {
		$this->nombre = $nombre;
	}

	public function set_correo($correo) {
		$this->correo = $correo;
	}

	public function set_clave($clave) {
		$this->clave = $clave;
	}

	public function set_estado($estado) {
		$this->estado = $estado;
	}

	public function set_id_rol($id_rol) {
		$this->id_rol = $id_rol;
	}

	public function insertar() {
		$conexion = Conexion::get_instance()->obtener_conexion();
		$insertado = false;
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "INSERT INTO usuario(nombre, correo, clave, estado, id_rol) VALUES(:nombre, :correo, :clave, :estado, (SELECT id FROM rol WHERE nombre = :rol))";
				$sentencia = $conexion->prepare($sql);
				$sentencia->bindParam(":nombre", $this->nombre, PDO::PARAM_STR);
				$sentencia->bindParam(":correo", $this->correo, PDO::PARAM_STR);
				$sentencia->bindParam(":clave", $this->clave, PDO::PARAM_STR);
				$sentencia->bindParam(":estado", $this->estado, PDO::PARAM_INT);
				$sentencia->bindParam(":rol", $this->id_rol, PDO::PARAM_STR);
				$insertado = $sentencia->execute();
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}
		return $insertado;
	}

	public function modificar() {
		$conexion = Conexion::get_instance()->obtener_conexion();
		$insertado = false;
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "UPDATE usuario SET nombre = :nombre, correo = :correo, clave = :clave, id_rol = (SELECT id FROM rol WHERE nombre = :rol) WHERE id = :id";
				$sentencia = $conexion->prepare($sql);
				$sentencia->bindParam(":nombre", $this->nombre, PDO::PARAM_STR);
				$sentencia->bindParam(":correo", $this->correo, PDO::PARAM_STR);
				$sentencia->bindParam(":clave", $this->clave, PDO::PARAM_STR);
				$sentencia->bindParam(":rol", $this->id_rol, PDO::PARAM_STR);
				$sentencia->bindParam(":id", $this->id, PDO::PARAM_INT);
				$insertado = $sentencia->execute();
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}
		return $insertado;
	}

	public function consultar_datos() {
		$conexion = Conexion::get_instance()->obtener_conexion();
		$usuario = null;
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "SELECT * FROM usuario WHERE correo = :correo AND clave = :clave";
				$sentencia = $conexion->prepare($sql);
				$sentencia->bindParam(":correo", $this->correo, PDO::PARAM_STR);
				$sentencia->bindParam(":clave", $this->clave, PDO::PARAM_STR);
				$sentencia->execute();
				$resultado = $sentencia->fetchAll();
				if (!empty($resultado)) {
					$usuario = $resultado;
				}
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}
		return $usuario;
	}

	public function obtener_permiso($id_permiso) {
		$conexion = Conexion::get_instance()->obtener_conexion();
		$permiso = null;
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "SELECT * FROM usuario, rol, rol_permiso WHERE usuario.id_rol = rol.id AND rol_permiso.id_rol = rol.id AND usuario.id = :id_usuario AND rol_permiso.id_permiso = :id_permiso";
				$sentencia = $conexion->prepare($sql);
				$sentencia->bindParam(":id_usuario", $this->id, PDO::PARAM_INT);
				$sentencia->bindParam(":id_permiso", $id_permiso, PDO::PARAM_INT);
				$sentencia->execute();
				$resultado = $sentencia->fetchAll();
				if (!empty($resultado)) {
					$permiso = $resultado;
				}
			} catch(PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}
		return $permiso;
	}

	public function nombre_existe() {
		$conexion = Conexion::get_instance()->obtener_conexion();
		$existe = false;
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "SELECT * FROM usuario WHERE nombre = :nombre";
				$sentencia = $conexion->prepare($sql);
				$sentencia->bindParam(":nombre", $this->nombre, PDO::PARAM_STR);
				$sentencia->execute();
				$resultado = $sentencia->fetchAll();
				if (!empty($resultado)) {
					$existe = true;
				}
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}
		return $existe;
	}

	public function correo_existe() {
		$conexion = Conexion::get_instance()->obtener_conexion();
		$existe = false;
		if (isset($conexion) && !is_null($conexion)) {
			try {
				$sql = "SELECT * FROM usuario WHERE correo = :correo";
				$sentencia = $conexion->prepare($sql);
				$sentencia->bindParam(":correo", $this->correo, PDO::PARAM_STR);
				$sentencia->execute();
				$resultado = $sentencia->fetchAll();
				if (!empty($resultado)) {
					$existe = true;
				}
			} catch (PDOException $e) {
				print "ERROR: ". $e->getMessage();
			}
		}
		return $existe;
	}
}
?>
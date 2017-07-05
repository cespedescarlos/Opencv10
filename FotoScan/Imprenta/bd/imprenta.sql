CREATE DATABASE imprenta;
USE imprenta;

CREATE TABLE permiso(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(150) NOT NULL
);

CREATE TABLE rol(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(150) NOT NULL,
	activo TINYINT NOT NULL
);

CREATE TABLE rol_permiso(
	id_permiso INT NOT NULL,
	id_rol INT NOT NULL,
	PRIMARY KEY(id_permiso, id_rol),
	FOREIGN KEY(id_permiso) REFERENCES permiso(id) ON UPDATE CASCADE ON DELETE CASCADE,
	FOREIGN KEY(id_rol) REFERENCES rol(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE usuario(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(150) NOT NULL,
	correo VARCHAR(150) NOT NULL UNIQUE,
	clave VARCHAR(500) NOT NULL,
	estado TINYINT NOT NULL,
	id_rol INT NOT NULL,
	FOREIGN KEY(id_rol) REFERENCES rol(id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE empresa(
	id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	nombre VARCHAR(150) NOT NULL,
	telefono VARCHAR(8) NOT NULL,
	nombre_contacto VARCHAR(150) NOT NULL,
	telefono_contacto VARCHAR(8) NOT NULL
);

INSERT INTO permiso(nombre) VALUES("Gestionar documentos.");
INSERT INTO permiso(nombre) VALUES("Visualizar información de impresión.");
INSERT INTO permiso(nombre) VALUES("Gestionar usuarios.");
INSERT INTO permiso(nombre) VALUES("Gestionar roles.");
INSERT INTO permiso(nombre) VALUES("Gestionar empresas.");
INSERT INTO permiso(nombre) VALUES("Gestionar tipos de trabajo.");

create table rol(
	id int auto_increment  primary key,
	nombre varchar (150) not null
);

create table permiso(
	id int auto_increment  primary key,
	nombre varchar (150) not null
);

create table rolpermiso(
	id_rol int,
	id_permiso int,
	PRIMARY KEY(id_rol,id_permiso),
	foreign key (id_rol) references Rol(id), 
	foreign key (id_permiso) references Permiso(id) 
);

create table tipo_trabajo(
	id int auto_increment  primary key,
	nombre varchar (150) not null
);

create table empresa(
	id int auto_increment  primary key,
	nombre varchar (150) not null,
	telefono varchar(8) not null,
	nombre_contacto varchar (150) not null,
	telefono_contacto varchar(8) not null
);



create table usuario(
    id int AUTO_INCREMENT PRIMARY KEY,
	correo varchar(150) not null,
	nombre varchar(150) not null,
	password varchar(15) not null,
	id_rol int not null,
	foreign key (id_rol) references Rol(id)
);

create table bitacora(
	id int auto_increment  primary key,
	fecha datetime not null,
	operacion varchar(150) not null,
	mac varchar (17),
	id_usuario int not null,
	foreign key (id_usuario) references Usuario(id)
);

create table documento(
	id int auto_increment  primary key,
	nombre varchar(150) not null,
	fecha date not null,
	cotizacion float not null,
	precio float not null,
	ruta_archivos varchar(255) not null,
	cantidad int not null,
	estado_placas char(1) not null,
	id_trabajo int not null,
	id_empresa int not null,
	id_usuario int not null,
	foreign key (id_usuario) references Usuario(id),
	foreign key (id_trabajo) references tipo_trabajo(id),
	foreign key (id_empresa) references empresa(id)	
);

create table imagen(
	id int auto_increment  primary key,
	nombre_trabajo varchar (100) not null,
	file_size float not null,
	file_path varchar(250),
	filas int not null,
	columnas int not null,

	id_doc int not null,
	foreign key (id_doc) references Documento(id) 
);

create table momentoshu(
	id int auto_increment  primary key,
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
	foreign key (id_imagen) references Imagen(id) 
);
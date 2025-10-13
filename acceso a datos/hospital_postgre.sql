-- Database: hospital_postgre

-- DROP DATABASE IF EXISTS hospital_postgre;

CREATE DATABASE hospital_postgre
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Galician_Spain.1252'
    LC_CTYPE = 'Galician_Spain.1252'
    LOCALE_PROVIDER = 'libc'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;

	create schema hospital;

create table hospital.Salas(
id_sala serial primary key,
nombre_sala varchar(100),
ubicacion varchar(100)
);

create table hospital.Especialidades(
id_especialidad serial primary key,
nombre_especialidad varchar(100)
);

create type hospital.datos_contacto as(nombre_contacto varchar(100),nif varchar(9),telefono varchar(9),email varchar(100));

create table hospital.Medicos(
id_medico serial primary key,
nombre_medico varchar(100),
contacto hospital.datos_contacto
);


create table hospital.Tratamientos(
id_tratamiento integer primary key,
id_medico integer,
id_especialidad integer,
foreign key (id_medico) references hospital.Medicos(id_medico)
on delete cascade on update cascade,
foreign key(id_especialidad) references hospital.Especialidades(id_especialidad)
on delete cascade on update cascade
);

create table hospital.Salas_Tratamientos(
id_sala integer,
id_tratamiento integer,
primary key(id_sala,id_tratamiento),
foreign key(id_sala) references hospital.Salas(id_sala)
on delete cascade on update cascade,
foreign key(id_tratamiento) references hospital.Tratamientos(id_tratamiento)
on delete cascade on update cascade
);
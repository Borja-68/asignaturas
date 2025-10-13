drop database if exists hospital_mysql;
create database hospital_mysql;
use hospital_mysql;	

create table Pacientes(
id_paciente int primary key,
nombre varchar(100),
email varchar(100),
fecha_nacimiento date
);

create table Tratamientos(
id_tratamiento int primary key,
nombre_tratamiento varchar(100),
descripcion text
);


create table Citas(
id_cita INt primary key,
id_paciente Int,
fecha date,
foreign key (id_paciente) references Pacientes(id_paciente)
on delete cascade on update cascade);


create table Pacientes_Tratamientos(
id_paciente int ,
id_tratamiento int ,
fecha_inicio date,
primary key (id_paciente,id_tratamiento),
foreign key (id_paciente) references Pacientes(id_paciente)
on delete cascade on update cascade,
foreign key(id_tratamiento) references Tratamientos(id_tratamiento)
on delete cascade on update cascade
);
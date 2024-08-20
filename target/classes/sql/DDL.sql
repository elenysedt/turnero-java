-- Active: 1723471288350@@127.0.0.1@3306
SELECT sqlite_version();

drop Table if exists pacientes;

drop table if exists medicos;

drop table if exists turnos;

CREATE TABLE pacientes (
    id INTEGER NOT NULL,
    dni INTEGER CHECK (LENGTH(dni) >= 7 AND LENGTH(dni) <= 8) NOT NULL,
    nombre TEXT CHECK (LENGTH(nombre) >= 3 AND LENGTH(nombre) <= 45) NOT NULL,
    apellido TEXT CHECK (LENGTH(apellido) >= 3 AND LENGTH(apellido) <= 45) NOT NULL,
    edad INTEGER CHECK (edad >= 18 AND edad <= 120) NOT NULL,
    direccion TEXT CHECK (LENGTH(direccion) >= 3 AND LENGTH(direccion) <= 100) NOT NULL,
    nro_contacto VARCHAR(13) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE medicos (
    id INTEGER NOT NULL,
    dni INTEGER CHECK (LENGTH(dni) >= 7 AND LENGTH(dni) <= 8) NOT NULL,
    matricula VARCHAR(20) CHECK (length(matricula) >= 5 AND length(matricula) <= 20) NOT NULL,
    nombre TEXT CHECK (LENGTH(nombre) >= 3 AND LENGTH(nombre) <= 45) NOT NULL,
    apellido TEXT CHECK (LENGTH(apellido) >= 3 AND LENGTH(apellido) <= 45) NOT NULL,
    especialidad TEXT CHECK (especialidad IN ('CLINICA_MEDICA','CARDIOLOGIA','DERMATOLOGIA','NEUROLOGIA','GASTROENTEROLOGIA','OFTALMOLOGIA')) NOT NULL,
    nro_contacto VARCHAR(13) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE turnos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    id_pacientes INTEGER NOT NULL,
    id_medicos INTEGER NOT NULL,
    fecha DATE NOT NULL,
    hora TIME NOT NULL CHECK (hora IN ('H10_00', 'H10_30', 'H11_00', 'H11_30',
                                    'H12_00', 'H12_30', 'H13_00', 'H13_30',
                                    'H14_00', 'H14_30', 'H15_00', 'H15_30',
                                    'H16_00', 'H16_30', 'H17_00', 'H17_30',
                                    'H18_00')),
    estado TEXT CHECK (estado IN ( 'CONFIRMADO',
                                'PENDIENTE',
                                'CANCELADO')) NOT NULL,
    FOREIGN KEY (id_pacientes) REFERENCES pacientes (id),
    FOREIGN KEY (id_medicos) REFERENCES medicos (id)
);
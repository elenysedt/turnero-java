-- 1- Obtener todos los pacientes con sus respectivos turnos:
SELECT pacientes.id, pacientes.nombre, pacientes.apellido, turnos.fecha, turnos.hora
        FROM pacientes
        JOIN turnos ON pacientes.id = turnos.id_pacientes;

-- 2- Obtener los pacientes que tengan turno con el Doctor 'Manuel Rodriguez':
SELECT pacientes.id, pacientes.nombre, pacientes.apellido, turnos.fecha, turnos.hora
        FROM pacientes
        JOIN turnos ON pacientes.id = turnos.id_pacientes
        JOIN medicos ON turnos.id_medicos = medicos.id
        WHERE medicos.nombre = 'Manuel' AND medicos.apellido = 'Rodriguez';

-- 3- Obtener los turnos y sus respectivos medicos, ordenados por la especialidad del medico:
SELECT turnos.id, medicos.especialidad, medicos.nombre, medicos.apellido
        FROM turnos
        JOIN medicos ON medicos.id = turnos.id_medicos
        ORDER BY medicos.especialidad;
-- 4- Obtener los turnos y sus respectivos pacientes, ordenados por la especialidad:
SELECT turnos.id, medicos.especialidad, pacientes.nombre, pacientes.apellido
        FROM turnos
        JOIN pacientes ON pacientes.id = turnos.id_pacientes
        JOIN medicos ON medicos.id = turnos.id_medicos
        ORDER BY medicos.especialidad;

-- 5- Obtener los pacientes que tienen turnos en especialidades que atiendan el dia '2024-07-05' :
SELECT pacientes.id, pacientes.nombre, pacientes.apellido, medicos.especialidad
        FROM pacientes
        JOIN turnos ON pacientes.id = turnos.id_pacientes
        JOIN medicos ON medicos.id = turnos.id_medicos
        WHERE turnos.fecha = '2024-07-05';

-- 6- Obtener los pacientes y los turnos, mostrando solo aquellos turnos que tengan mas de 1 paciente:
SELECT pacientes.id, pacientes.nombre, pacientes.apellido,turnos.id, medicos.especialidad, turnos.fecha, turnos.hora, turnos.estado
        FROM pacientes
        JOIN turnos ON pacientes.id = turnos.id_pacientes
        JOIN medicos ON medicos.id = turnos.id_medicos
        WHERE turnos.id IN (
                SELECT id
                FROM turnos
                GROUP BY id
                HAVING COUNT(*) > 1
        );

 -- 7 obtener medicos segun especialidad:

 SELECT * FROM medicos WHERE especialidad = ?;       
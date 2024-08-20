

function cargarMedicosPorEspecialidad() {
    const especialidad = document.getElementById('especialidad').value;
    const idPaciente = document.getElementById('id_pacientes').value; // Obtener el id del paciente
    const idMedicoSelect = document.getElementById('id_medico');

    // Verificar si el paciente ya tiene un turno con esta especialidad
    fetch(`/verificarTurnoExistente?idPaciente=${idPaciente}&especialidad=${especialidad}`)
        .then(response => response.json())
        .then(existe => {
            if (existe) {
                alert("El paciente ya tiene un turno con esta especialidad.");
            } else {
                // Limpiar médicos existentes antes de cargar los nuevos
                idMedicoSelect.innerHTML = '<option value="" disabled selected>Seleccione un médico</option>';

                // Lógica para cargar médicos según la especialidad seleccionada
                fetch(`/medicosPorEspecialidad?especialidad=${especialidad}`)
                    .then(response => response.json())
                    .then(data => {
                        // Agregar nuevas opciones de médicos
                        data.forEach(medico => {
                            const option = document.createElement('option');
                            option.value = medico.id;
                            option.text = `${medico.apellido}, ${medico.nombre}`;
                            idMedicoSelect.appendChild(option);
                        });
                    })
                    .catch(error => console.error('Error al cargar médicos:', error));
            }
        })
        .catch(error => console.error('Error al verificar turno existente:', error));
}


function validarFecha() {
    var input = document.getElementById('fecha');
    var date = new Date(input.value);
    var day = date.getDay();

    // Si es sábado (6) o domingo (0), se borra la fecha seleccionada
    if (day === 6 || day === 0) {
        alert("Los sábados y domingos no están permitidos.");
        input.value = ''; // Borra la fecha seleccionada
    }
}
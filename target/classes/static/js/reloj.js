function mueveReloj(){
    momentoActual = new Date()
    hora = momentoActual.getHours()
    minuto = momentoActual.getMinutes()
    segundo = momentoActual.getSeconds()

    horaImprimible =    hora.toString().padStart(2, '0') + " : " + 
                        minuto.toString().padStart(2, '0') + " : " + 
                        segundo.toString().padStart(2, '0')

    document.getElementById("reloj").innerText = horaImprimible

    setTimeout("mueveReloj()",1000)
}
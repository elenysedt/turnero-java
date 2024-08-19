package centro35.ele.consultoriomedicoele.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Paciente {
    private int id;
    private int dni;
    private String nombre;
    private String apellido;
    private int edad;
    private String direccion;
    private String nro_contacto;

}

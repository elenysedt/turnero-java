package centro35.ele.consultoriomedicoele.entities;

import centro35.ele.consultoriomedicoele.enums.Especialidad;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Medico {
    private int id;
    private int dni;
    private String matricula;
    private String nombre;
    private String apellido;
    private Especialidad especialidad;
    private String nro_contacto;

}

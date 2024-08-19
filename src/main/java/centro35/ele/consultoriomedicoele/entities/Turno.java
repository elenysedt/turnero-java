package centro35.ele.consultoriomedicoele.entities;

import centro35.ele.consultoriomedicoele.enums.Estado;
import centro35.ele.consultoriomedicoele.enums.Hora;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Turno {
    private int id;
    private Integer id_pacientes;
    private Integer id_medicos;
    private String fecha;
    private Hora hora;
    private Estado estado;
}

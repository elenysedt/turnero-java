package centro35.ele.consultoriomedicoele.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import centro35.ele.consultoriomedicoele.entities.Paciente;
import centro35.ele.consultoriomedicoele.repositories.PacienteRepository;
import centro35.ele.consultoriomedicoele.repositories.TurnoRepository;

@Controller
public class PacienteController {

    private String mensaje = "Ingrese un nuevo Paciente";
    private PacienteRepository pacienteRepository = new PacienteRepository();
    private TurnoRepository turnoRepository = new TurnoRepository();

    @GetMapping("/pacientes")
    public String getPaciente(Model model, @RequestParam(name = "buscar", defaultValue = "") String buscar) {
        Paciente paciente = new Paciente();
        model.addAttribute("mensaje", mensaje);
        model.addAttribute("paciente", paciente);
        model.addAttribute("turnos", turnoRepository.getAll());
        model.addAttribute("likeApellido", pacienteRepository.getLikeApellido(buscar));
        return "pacientes";
    }

    @PostMapping("/savePaciente")
    public String savePaciente(@ModelAttribute Paciente paciente) {
        // System.out.println("********************************************************************");
        // System.out.println(paciente);
        // System.out.println("********************************************************************");
        pacienteRepository.save(paciente);
        if (paciente.getId() > 0) {
            mensaje = "Se guardo el paciente id " + paciente.getId();
        } else {
            mensaje = "Ocurrio un problema, no se guardo el paciente!";
        }
        return "redirect:pacientes";
    }

}

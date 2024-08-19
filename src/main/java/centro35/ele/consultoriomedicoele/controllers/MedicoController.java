package centro35.ele.consultoriomedicoele.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import centro35.ele.consultoriomedicoele.entities.Medico;
import centro35.ele.consultoriomedicoele.enums.Especialidad;
import centro35.ele.consultoriomedicoele.repositories.MedicoRepository;
import centro35.ele.consultoriomedicoele.repositories.TurnoRepository;

@Controller
public class MedicoController {
    private String mensaje = "Ingrese un nuevo Medico";
    private MedicoRepository medicoRepository = new MedicoRepository();
    private TurnoRepository turnoRepository = new TurnoRepository();

    @GetMapping("/medicos")
    public String getMedicos(Model model, @RequestParam(name = "buscar", defaultValue = "") String buscar) {
        Medico medico = new Medico();
        model.addAttribute("especialidad", Especialidad.values());
        model.addAttribute("mensaje", mensaje);
        model.addAttribute("medico", medico);
        model.addAttribute("turnos", turnoRepository.getAll());
        model.addAttribute("likeApellido", medicoRepository.getLikeApellido(buscar));
        return "medicos";
    }

    @PostMapping("/saveMedico")
    public String saveMedico(@ModelAttribute Medico medico) {
        // System.out.println("********************************************************************");
        // System.out.println(medico);
        // System.out.println("********************************************************************");
        medicoRepository.save(medico);
        if (medico.getId() > 0) {
            mensaje = "Se guardo el medico id " + medico.getId();
        } else {
            mensaje = "Ocurrio un problema, no se guardo el medico!";
        }
        return "redirect:medicos";
    }

}

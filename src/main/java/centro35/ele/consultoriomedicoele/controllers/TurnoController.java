package centro35.ele.consultoriomedicoele.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import centro35.ele.consultoriomedicoele.entities.Medico;
import centro35.ele.consultoriomedicoele.entities.Paciente;
import centro35.ele.consultoriomedicoele.entities.Turno;
import centro35.ele.consultoriomedicoele.enums.Especialidad;
import centro35.ele.consultoriomedicoele.enums.Estado;
import centro35.ele.consultoriomedicoele.enums.Hora;
import centro35.ele.consultoriomedicoele.repositories.MedicoRepository;
import centro35.ele.consultoriomedicoele.repositories.TurnoRepository;
import centro35.ele.consultoriomedicoele.repositories.PacienteRepository;

@Controller
public class TurnoController {
    private String mensaje = "Ingrese un nuevo turno";
    private MedicoRepository medicoRepository = new MedicoRepository();
    private TurnoRepository turnoRepository = new TurnoRepository();
    private PacienteRepository pacienteRepository = new PacienteRepository();

    @GetMapping("/turnos")
    public String getTurnos(Model model,
            @RequestParam(name = "buscar", defaultValue = "0") int buscar) {

        Turno newTurno = new Turno();

        // Si se proporciona un ID en "buscar", buscar los turnos con ese ID
        List<Turno> turnoList;
        if (buscar > 0) {
            turnoList = turnoRepository.getLikeIdTurnos(buscar);
        } else {
            // De lo contrario, buscar todos los turnos
            turnoList = turnoRepository.getAll();
        }

        List<Paciente> pacienteList = pacienteRepository.getAll();
        List<Medico> medicoList = medicoRepository.getAll();
        System.out.println("Turnos obtenidos: " + turnoList.size());

        Map<Integer, Paciente> pacientesMap = pacienteList.stream()
                .collect(Collectors.toMap(Paciente::getId, Function.identity()));

        Map<Integer, Medico> medicosMap = medicoList.stream()
                .collect(Collectors.toMap(Medico::getId, Function.identity()));

        Map<Integer, Especialidad> especialidadesMap = Arrays.stream(Especialidad.values())
                .collect(Collectors.toMap(Especialidad::ordinal, Function.identity()));

        model.addAttribute("turno", newTurno); // Asegúrate de pasar una instancia de Turno para el formulario
        model.addAttribute("turnos", turnoList);
        model.addAttribute("pacienteList", pacienteList);
        model.addAttribute("pacientesMap", pacientesMap);
        model.addAttribute("medicosMap", medicosMap);
        model.addAttribute("especialidadesMap", especialidadesMap);
        model.addAttribute("likeIdTurno", turnoRepository.getLikeIdTurnos(buscar));
        model.addAttribute("especialidades", Arrays.asList(Especialidad.values()));
        model.addAttribute("medicoList", medicoList);
        model.addAttribute("horas", Hora.values());
        model.addAttribute("estado", Estado.values());
        model.addAttribute("mensaje", mensaje);

        return "turnos";
    }

    @PostMapping("/saveTurnos")
    public String saveTurno(@ModelAttribute Turno turno) {
        if (turno.getId_pacientes() > 0 && turno.getId_medicos() > 0) {
            boolean isOcupado = turnoRepository.isTurnoOcupado(turno.getId_medicos(), turno.getFecha(),
                    turno.getHora());
            if (isOcupado) {
                mensaje = "El horario ya está ocupado, elija otra fecha u hora.";
            } else {
                turnoRepository.save(turno);
                mensaje = "Se guardó el turno id " + turno.getId();
            }
        } else {
            mensaje = "Ocurrió un problema, no se guardó el turno!";
        }
        return "redirect:/turnos";
    }

    @GetMapping("/formulario")
    public String mostrarFormulario(Model model) {
        List<Paciente> pacientes = pacienteRepository.getAll(); // Obtener todos los pacientes
        model.addAttribute("pacientes", pacientes); // Pasar la lista al modelo
        return "turnos"; // Nombre de la plantilla HTML
    }

    @GetMapping("/medicosPorEspecialidad")
    @ResponseBody
    public List<Medico> getMedicosPorEspecialidad(@RequestParam Especialidad especialidad) {
        return medicoRepository.getByEspecialidad(especialidad);
    }

    @GetMapping("/verificarTurnoExistente")
    @ResponseBody
    public boolean verificarTurnoExistente(@RequestParam int idPaciente, @RequestParam Especialidad especialidad) {
        List<Medico> medicos = medicoRepository.getByEspecialidad(especialidad);
        for (Medico medico : medicos) {
            if (turnoRepository.isTurnoExistenteParaEspecialidad(idPaciente, medico.getId())) {
                return true;
            }
        }
        return false;
    }
}

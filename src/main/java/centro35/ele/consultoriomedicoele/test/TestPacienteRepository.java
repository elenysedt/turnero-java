package centro35.ele.consultoriomedicoele.test;

import centro35.ele.consultoriomedicoele.entities.Paciente;
import centro35.ele.consultoriomedicoele.repositories.PacienteRepository;

public class TestPacienteRepository {
    public static void main(String[] args) {

        PacienteRepository pacienteRepository = new PacienteRepository();

        System.out.println("-- Método .save() --");
        Paciente paciente = new Paciente(0, 95345231, "Mauro", "Juarez", 18, "av la plata 530,CABA", "+541109765576");
        pacienteRepository.save(paciente);
        System.out.println(paciente);

        System.out.println("-- Método .getById() --");
        System.out.println(pacienteRepository.getById(20));

        System.out.println("-- Método .remove() --");
        pacienteRepository.remove(pacienteRepository.getById(22));

        System.out.println("-- Método .getLikeApellido() --");
        pacienteRepository.getLikeApellido("ju").forEach(System.out::println);

        System.out.println("-- Método .getAll() --");
        pacienteRepository.getAll().forEach(System.out::println);
    }
}

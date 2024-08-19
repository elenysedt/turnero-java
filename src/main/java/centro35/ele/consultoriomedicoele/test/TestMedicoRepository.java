package centro35.ele.consultoriomedicoele.test;

import centro35.ele.consultoriomedicoele.entities.Medico;
import centro35.ele.consultoriomedicoele.enums.Especialidad;
import centro35.ele.consultoriomedicoele.repositories.MedicoRepository;

public class TestMedicoRepository {
    public static void main(String[] args) {

        MedicoRepository medicoRepository = new MedicoRepository();

        System.out.println("-- Método .save() --");
        Medico medico = new Medico(
                0,
                95345531,
                "vyh7567",
                "Manuel",
                "Diaz",
                Especialidad.DERMATOLOGIA,
                "+541109765576");
        medicoRepository.save(medico);
        System.out.println(medico);

        System.out.println("-- Método .getById() --");
        System.out.println(medicoRepository.getById(21));

        System.out.println("-- Método .remove() --");
        medicoRepository.remove(medicoRepository.getById(21));

        System.out.println("-- Método .getLikeApellido() --");
        medicoRepository.getLikeApellido("sa").forEach(System.out::println);

        System.out.println("-- Método .getAll() --");
        medicoRepository.getAll().forEach(System.out::println);
    }
}

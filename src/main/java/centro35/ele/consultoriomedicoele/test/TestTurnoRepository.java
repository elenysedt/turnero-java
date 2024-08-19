package centro35.ele.consultoriomedicoele.test;

import centro35.ele.consultoriomedicoele.entities.Turno;
import centro35.ele.consultoriomedicoele.enums.Estado;
import centro35.ele.consultoriomedicoele.enums.Hora;
import centro35.ele.consultoriomedicoele.repositories.TurnoRepository;

public class TestTurnoRepository {
    public static void main(String[] args) {

        TurnoRepository turnoRepository = new TurnoRepository();

        System.out.println("-- Método .save() --");
        Turno turno = new Turno(
                0,
                5,
                5,
                "2024-07-16",
                Hora.H11_00,
                Estado.CONFIRMADO);
        turnoRepository.save(turno);
        System.out.println(turno);

        System.out.println("-- Método .getById() --");
        System.out.println(turnoRepository.getById(21));

        System.out.println("-- Método .remove() --");
        turnoRepository.remove(turnoRepository.getById(21));

        System.out.println("-- Método .getLikeFecha() --");
        turnoRepository.getLikeFecha("2024-07-16").forEach(System.out::println);

        System.out.println("-- Método .getAll() --");
        turnoRepository.getAll().forEach(System.out::println);

        System.out.println("-- Método .getLikeIdTurnos() --");
        turnoRepository.getLikeIdTurnos(20).forEach(System.out::println);
    }
}

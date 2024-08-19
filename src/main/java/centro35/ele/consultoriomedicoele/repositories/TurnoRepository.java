package centro35.ele.consultoriomedicoele.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import centro35.ele.consultoriomedicoele.connectors.Connector;
import centro35.ele.consultoriomedicoele.entities.Paciente;
import centro35.ele.consultoriomedicoele.entities.Turno;
import centro35.ele.consultoriomedicoele.enums.Especialidad;
import centro35.ele.consultoriomedicoele.enums.Estado;
import centro35.ele.consultoriomedicoele.enums.Hora;
import ch.qos.logback.core.model.Model;

@Repository
public class TurnoRepository {
    private final Connection conn = Connector.getConnection();

    public void save(Turno turno) {
        if (turno == null)
            return;
        try (PreparedStatement ps = conn.prepareStatement(
                "insert into turnos (id_pacientes, id_medicos, fecha, hora, estado) values (?,?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, turno.getId_pacientes());
            ps.setInt(2, turno.getId_medicos());
            ps.setString(3, turno.getFecha());
            ps.setString(4, turno.getHora().toString());
            ps.setString(5, turno.getEstado().toString());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
                turno.setId(rs.getInt(1));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Turno> getAll() {
        List<Turno> list = new ArrayList<>();
        try (ResultSet rs = conn.createStatement().executeQuery("select * from turnos")) {
            while (rs.next()) {
                list.add(new Turno(
                        rs.getInt("id"),
                        rs.getInt("id_pacientes"),
                        rs.getInt("id_medicos"),
                        rs.getString("fecha"),
                        Hora.valueOf(rs.getString("hora")),
                        Estado.valueOf(rs.getString("estado"))));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public void remove(Turno turno) {
        if (turno == null)
            return;
        try (PreparedStatement ps = conn.prepareStatement("delete from turnos where id=?")) {
            ps.setInt(1, turno.getId());
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Turno getById(int id) {
        return getAll()
                .stream()
                .filter(turno -> turno.getId() == id)
                .findAny()
                .orElse(new Turno());
    }

    public List<Turno> getLikeFecha(String fecha) {
        if (fecha == null)
            return new ArrayList();
        return getAll()
                .stream()
                .filter(turno -> turno.getFecha().toLowerCase().contains(fecha.toLowerCase()))
                .toList();
    }

    public boolean isTurnoOcupado(int idMedico, String fecha, Hora hora) {
        String query = "SELECT COUNT(*) FROM turnos WHERE id_medicos = ? AND fecha = ? AND hora = ?";
        try (Connection conn = Connector.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, idMedico);
            pstmt.setString(2, fecha);
            pstmt.setString(3, hora.name()); // Enum `Hora` a string
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isTurnoExistenteParaEspecialidad(int idPaciente, int idMedico) {
        String query = "SELECT COUNT(*) FROM turnos WHERE id_pacientes = ? AND id_medicos = ?";
        try (Connection connection = Connector.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, idPaciente);
            preparedStatement.setInt(2, idMedico);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Turno> getLikeIdTurnos(int id) {
        if (id <= 0)
            return new ArrayList<>();
        return getAll()
                .stream()
                .filter(turno -> turno.getId() == id)
                .toList();
    }

}

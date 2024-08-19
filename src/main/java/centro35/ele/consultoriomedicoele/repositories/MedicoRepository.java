package centro35.ele.consultoriomedicoele.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import centro35.ele.consultoriomedicoele.connectors.Connector;
import centro35.ele.consultoriomedicoele.entities.Medico;
import centro35.ele.consultoriomedicoele.enums.Especialidad;

public class MedicoRepository {
    private Connection conn = Connector.getConnection();

    public void save(Medico medico) {
        if (medico == null)
            return;
        try (PreparedStatement ps = conn.prepareStatement(
                "insert into medicos (dni, matricula, nombre, apellido, especialidad, nro_contacto) values (?,?,?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, medico.getDni());
            ps.setString(2, medico.getMatricula());
            ps.setString(3, medico.getNombre());
            ps.setString(4, medico.getApellido());
            ps.setString(5, medico.getEspecialidad().toString());
            ps.setString(6, medico.getNro_contacto());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
                medico.setId(rs.getInt(1));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Medico> getAll() {
        List<Medico> list = new ArrayList();
        try (ResultSet rs = conn.createStatement().executeQuery("select * from medicos")) {
            while (rs.next()) {
                list.add(new Medico(
                        rs.getInt("id"),
                        rs.getInt("dni"),
                        rs.getString("matricula"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        Especialidad.valueOf(rs.getString("especialidad")),
                        rs.getString("nro_contacto")));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public void remove(Medico medico) {
        if (medico == null)
            return;
        try (PreparedStatement ps = conn.prepareStatement("delete from medicos where id=?")) {
            ps.setInt(1, medico.getId());
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Medico getById(int id) {
        return getAll()
                .stream()
                .filter(medico -> medico.getId() == id)
                .findAny()
                .orElse(new Medico());
    }

    public List<Medico> getLikeApellido(String apellido) {
        if (apellido == null)
            return new ArrayList();
        return getAll()
                .stream()
                .filter(medico -> medico.getApellido().toLowerCase().contains(apellido.toLowerCase()))
                .toList();
    }

    public List<Medico> getByEspecialidad(Especialidad especialidad) {
        List<Medico> medicos = new ArrayList<>();
        String sql = "SELECT * FROM medicos WHERE especialidad = ?";

        try (Connection conn = Connector.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, especialidad.name());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Medico medico = new Medico();
                medico.setId(rs.getInt("id"));
                medico.setApellido(rs.getString("apellido"));
                medico.setNombre(rs.getString("nombre"));
                medico.setEspecialidad(Especialidad.valueOf(rs.getString("especialidad")));
                medicos.add(medico);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return medicos;
    }
}
package centro35.ele.consultoriomedicoele.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import centro35.ele.consultoriomedicoele.connectors.Connector;
import centro35.ele.consultoriomedicoele.entities.Paciente;

public class PacienteRepository {
    private Connection conn = Connector.getConnection();

    public void save(Paciente paciente) {
        if (paciente == null)
            return;
        try (PreparedStatement ps = conn.prepareStatement(
                "insert into pacientes (dni, nombre, apellido, edad, direccion, nro_contacto) values (?,?,?,?,?,?)",
                PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, paciente.getDni());
            ps.setString(2, paciente.getNombre());
            ps.setString(3, paciente.getApellido());
            ps.setInt(4, paciente.getEdad());
            ps.setString(5, paciente.getDireccion());
            ps.setString(6, paciente.getNro_contacto());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
                paciente.setId(rs.getInt(1));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Paciente> getAll() {
        List<Paciente> list = new ArrayList();
        try (ResultSet rs = conn.createStatement().executeQuery("select * from Pacientes")) {
            while (rs.next()) {
                list.add(new Paciente(
                        rs.getInt("id"),
                        rs.getInt("dni"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getInt("edad"),
                        rs.getString("direccion"),
                        rs.getString("nro_contacto")));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public void remove(Paciente paciente) {
        if (paciente == null)
            return;
        try (PreparedStatement ps = conn.prepareStatement("delete from pacientes where id=?")) {
            ps.setInt(1, paciente.getId());
            ps.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public Paciente getById(int id) {
        return getAll()
                .stream()
                .filter(paciente -> paciente.getId() == id)
                .findAny()
                .orElse(new Paciente());
    }

    public List<Paciente> getLikeApellido(String apellido) {
        if (apellido == null)
            return new ArrayList();
        return getAll()
                .stream()
                .filter(paciente -> paciente.getApellido().toLowerCase().contains(apellido.toLowerCase()))
                .toList();
    }

}

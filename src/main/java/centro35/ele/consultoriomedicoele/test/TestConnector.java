package centro35.ele.consultoriomedicoele.test;

import java.sql.ResultSet;

import centro35.ele.consultoriomedicoele.connectors.Connector;

public class TestConnector {
    public static void main(String[] args) {
        try (ResultSet rs = Connector
                .getConnection()
                .createStatement()
                .executeQuery("select * from turnos")) {
            if (rs.next()) {
                System.out.println("Se conecto a " + rs.getString("estado"));
            } else {
                System.out.println("No se pudo conectar a la BD");
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("No se pudo conectar a la BD");
        }
    }
}

package centro35.ele.consultoriomedicoele.connectors;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

    private static Connection conn = null;

    private Connector() {
    }

    public static Connection getConnection() {
        try {
            if (conn == null || conn.isClosed()) {
                // Cargar las variables de entorno desde el archivo .env si está presente
                //Dotenv dotenv = Dotenv.load();
                Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
                
                // Obtener las variables de entorno, usando valores por defecto si es necesario
                String url = System.getenv("DB_URL");
                String user = System.getenv("DB_USER");
                String password = System.getenv("DB_PASSWORD");
                
                if (url == null) url = dotenv.get("DB_URL");
                if (user == null) user = dotenv.get("DB_USER");
                if (password == null) password = dotenv.get("DB_PASSWORD");

                // Imprimir los valores para depuración
                //System.out.println("Database URL: " + url);
                //System.out.println("Database User: " + user);
                //System.out.println("Database Password: " + password);
            
                // Cargar el driver JDBC
                conn = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }

    public static String getUrl() {
        return System.getenv("DB_URL");
    }
}

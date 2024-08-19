package centro35.ele.consultoriomedicoele.connectors;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connector {

    // SQlite
    private static String url = "jdbc:sqlite:./data/dataele.db";

    private static Connection conn = null;

    private Connector() {
    }

    public static Connection getConnection() {
        try {
            conn = DriverManager.getConnection(url);
        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }

    public static String getUrl() {
        return url;
    }

}
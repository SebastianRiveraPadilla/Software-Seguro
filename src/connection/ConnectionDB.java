package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/rrhh_institucion?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root"; 
    private static final String CLAVE = "CLAVE_AQUI"; // Se debe cambiar por la contraseña real de la base de datos

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontró el driver de MySQL. Revisa la carpeta lib.", e);
        }
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
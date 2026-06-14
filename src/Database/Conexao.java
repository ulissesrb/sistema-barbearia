package Database;

import java.sql.*;


public class Conexao {
    private static final String URL = "jdbc:mysql://barbearia-db-sistema-barbearia.a.aivencloud.com:22786/defaultdb?useSSL=true";
    private static final String USER = "avnadmin";
    private static final String PASSWORD = "AVNS_aoq8HSKxxiSruZQibgX";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
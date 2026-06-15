package Database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties; // Nativo do Java

public class Conexao {

    private static final Properties props = new Properties();

    // bloco "static" serve para inicializar o arquivo config.properties
    static {
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            // O Java lê o arquivo e guarda as chaves e valores na variável 'props'
            props.load(fis);
        } catch (IOException e) {
            System.err.println("ERRO: Não foi possível carregar o arquivo config.properties!");
            e.printStackTrace();
        }
    }

    // Buscamos os dados de dentro do objeto 'props'
    private static final String HOST = props.getProperty("db.host");
    private static final String PORTA = props.getProperty("db.port");
    private static final String BANCO = props.getProperty("db.name");
    private static final String USUARIO = props.getProperty("db.user");
    private static final String SENHA = props.getProperty("db.pass");

    private static final String URL = "jdbc:mysql://" + HOST + ":" + PORTA + "/" + BANCO + "?useSSL=true";

    public static Connection conectar() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USUARIO, SENHA);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL não encontrado!", e);
        }
    }
}
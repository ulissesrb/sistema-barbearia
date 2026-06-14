import com.mysql.cj.protocol.Resultset;

import java.sql.*;
import Database.Conexao;

public class testeJDBC {
    public static void main(String[] args) {

                    try {
                        Connection conn = Conexao.conectar();

                        if (conn != null) {
                            System.out.println("Conexão realizada com sucesso!");
                        }

                        conn.close();

                    } catch (SQLException e) {
                        System.out.println("Erro:");
                        e.printStackTrace();
                    }



        }
    }


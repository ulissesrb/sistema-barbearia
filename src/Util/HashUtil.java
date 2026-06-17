package Util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {

    public static String gerarHash(String senhaPura) {
        try {
            // Instancia o algoritmo SHA-256
            MessageDigest algorithm = MessageDigest.getInstance("SHA-256");

            // Transforma a senha em bytes e aplica o hash
            byte[] messageDigest = algorithm.digest(senhaPura.getBytes());

            // Converte os bytes resultantes em uma String Hexadecimal
            StringBuilder hexString = new StringBuilder(); // Cria um StringBuilder para armazenar a senha protegida
            for (byte b : messageDigest) { // Itera sobre os bytes do hash
                hexString.append(String.format("%02x", b)); // parte central da senha protegida
            }

            return hexString.toString(); // Retorna a senha protegida

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return senhaPura; // Em caso de erro catastrófico, retorna a original
        }
    }
}
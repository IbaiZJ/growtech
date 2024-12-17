package growtech.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.mindrot.jbcrypt.BCrypt;

public class Enkriptazioa {

    private static final String ALGORITMOA = "AES";

    public static String kontrasenaEnkriptatu(String kontrasena) {
        return BCrypt.hashpw(kontrasena, BCrypt.gensalt());
    }

    public static boolean kontrasenaKonprobatu(String kontrasena, String kontrasenaEnkriptatuta) {
        return BCrypt.checkpw(kontrasena, kontrasenaEnkriptatuta);
    }

    // Genera una clave AES de 128 bits
    public static SecretKey generateKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITMOA);
        keyGen.init(128, new SecureRandom());
        return keyGen.generateKey();
    }

    // Guarda la clave en un archivo (en formato Base64)
    public static void saveKey(SecretKey key, String filePath) throws IOException {
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(encodedKey);
        }
    }

    // Carga la clave desde un archivo
    public static SecretKey loadKey(String filePath) throws IOException {
        String encodedKey;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            encodedKey = reader.readLine();
        }
        byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
        return new SecretKeySpec(decodedKey, ALGORITMOA);
    }

    // Cifra un archivo con AES
    public static void encryptFile(SecretKey key, String inputFilePath, String outputFilePath) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMOA);
        cipher.init(Cipher.ENCRYPT_MODE, key);

        try (FileInputStream fis = new FileInputStream(inputFilePath);
             FileOutputStream fos = new FileOutputStream(outputFilePath);
             CipherOutputStream cos = new CipherOutputStream(fos, cipher)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                cos.write(buffer, 0, bytesRead);
            }
        }
    }

    // Descifra un archivo con AES
    public static void decryptFile(SecretKey key, String inputFilePath, String outputFilePath) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITMOA);
        cipher.init(Cipher.DECRYPT_MODE, key);

        try (FileInputStream fis = new FileInputStream(inputFilePath);
             CipherInputStream cis = new CipherInputStream(fis, cipher);
             FileOutputStream fos = new FileOutputStream(outputFilePath)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = cis.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
    }

    
}


/*public static void main(String[] args) {
    try {
        // Generar una clave AES
        SecretKey key = generateKey();
        String keyFile = "claveAES.txt";

        // Guardar la clave en un archivo
        saveKey(key, keyFile);
        System.out.println("Clave guardada en: " + keyFile);

        // Rutas de los archivos
        String inputFile = "hash_contraseña.txt";
        String encryptedFile = "hash_contraseña_cifrado.txt";
        String decryptedFile = "hash_contraseña_descifrado.txt";

        // Cifrar el archivo
        encryptFile(key, inputFile, encryptedFile);
        System.out.println("Archivo cifrado: " + encryptedFile);

        // Cargar la clave desde el archivo
        SecretKey loadedKey = loadKey(keyFile);

        // Descifrar el archivo
        decryptFile(loadedKey, encryptedFile, decryptedFile);
        System.out.println("Archivo descifrado: " + decryptedFile);

    } catch (Exception e) {
        e.printStackTrace();
    }
}*/

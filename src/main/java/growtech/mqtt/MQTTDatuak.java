package growtech.mqtt;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class MQTTDatuak {
    public static final String FITXERO_PATH = "datuak/";
    public static double AZKEN_TENPERATURA = -1;
    public static double AZKEN_HEZETASUNA = -1;

    public static void idatziJasotakoDatua(String topic, double zenbakia) {
        FileWriter file = null;
        LocalDate data = LocalDate.now();
        String path = FITXERO_PATH + data + " " + topic + ".txt";

        azkenTenperaturaHezetasunAktualizatu(topic, zenbakia);

        try {
            file = new FileWriter(path, true);
            file.write(zenbakia + "\n");
        } catch (IOException e) {
            System.out.println("Errore bat egon da: " + e.getMessage());
        } finally {
            try {
                if (file != null) {
                    file.close();
                }
            } catch (IOException e) {
                System.out.println("Errore bat egon da: " + e.getMessage());
            }
        }
    }

    private static void azkenTenperaturaHezetasunAktualizatu(String topic, double zenbakia) {
        if(topic.equals("Hezetasuna")) {
            AZKEN_HEZETASUNA = zenbakia;
        }
        else if(topic.equals("Tenperatura")) {
            AZKEN_TENPERATURA = zenbakia;
        }
    }

}

package growtech.util.userKudeaketa;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import growtech.util.Enkriptazioa;

public class ErabiltzaileKudeaketa {
    public static Erabiltzailea ERABILTZAILEA;

    public static void sortuErabiltzailea(Erabiltzailea erabiltzailea) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // File file = new File(getClass().getResource("/users.json").toURI());
            File file = new File("src/main/resources/users.json");
            List<Erabiltzailea> erabiltzaileak;

            if (file.exists() && file.length() > 0) {
                erabiltzaileak = mapper.readValue(file, new TypeReference<List<Erabiltzailea>>() {
                });
            } else {
                erabiltzaileak = new ArrayList<>();
            }
            erabiltzaileak.add(erabiltzailea);

            mapper.writerWithDefaultPrettyPrinter().writeValue(file, erabiltzaileak);
        } catch (Exception ex) {
            System.out.println("Errore JSON: " + ex.getMessage());
        }
    }

    // TODO Stream kendu ahal bada
    public static void ezabatuErabiltzailea(List<Erabiltzailea> ezabatuBeharrekoErabiltzaileak) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File("src/main/resources/users.json");
            List<Erabiltzailea> erabiltzaileak;

            if (file.exists() && file.length() > 0) {
                erabiltzaileak = mapper.readValue(file, new TypeReference<List<Erabiltzailea>>() {
                });
            } else {
                erabiltzaileak = new ArrayList<>();
            }
            erabiltzaileak.removeIf(erabiltzailea -> ezabatuBeharrekoErabiltzaileak.stream()
                    .anyMatch(ezabatu -> erabiltzailea.getIzena().equals(ezabatu.getIzena()) &&
                            erabiltzailea.getAbizena().equals(ezabatu.getAbizena()) &&
                            erabiltzailea.getMota() == ezabatu.getMota()));
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, erabiltzaileak);
        } catch (Exception ex) {
            System.out.println("Errore JSON: " + ex.getMessage());
        }
    }

    public List<Erabiltzailea> erabiltzaileakIrakurri() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream file = ErabiltzaileKudeaketa.class.getClassLoader().getResourceAsStream("users.json");

            if (file == null) {
                throw new FileNotFoundException("Ez da .json fitxategirik aurkitu");
            }

            List<Erabiltzailea> erabiltzaileak = mapper.readValue(file, new TypeReference<List<Erabiltzailea>>() {
            });
            return erabiltzaileak;
        } catch (Exception ex) {
            System.out.println("Errore JSON: " + ex.getMessage());
        }
        return new ArrayList<>();
    }

    /*
     * public List<Erabiltzailea> erabiltzaileakIrakurri() {
     * try {
     * ObjectMapper mapper = new ObjectMapper();
     * File file = new File(getClass().getResource("/users.json").toURI());
     * List<Erabiltzailea> erabiltzaileak;
     * 
     * if (file.exists() && file.length() > 0) {
     * erabiltzaileak = mapper.readValue(file, new
     * TypeReference<List<Erabiltzailea>>(){});
     * } else {
     * erabiltzaileak = new ArrayList<>();
     * }
     * return erabiltzaileak;
     * }
     * catch (Exception ex) {
     * System.out.println("Errore JSON: " + ex.getMessage());
     * }
     * return null;
     * }
     */

    public static Erabiltzailea bilatuErabiltzailea(String erabiltzaile, String pasahitza) {
        ErabiltzaileKudeaketa erabiltzaileKudeaketa = new ErabiltzaileKudeaketa();
        List<Erabiltzailea> erabiltzaileak = erabiltzaileKudeaketa.erabiltzaileakIrakurri();
        for (Erabiltzailea erabiltzailea : erabiltzaileak) {
            if (erabiltzailea.getErabiltzaile().equals(erabiltzaile) &&
                    Enkriptazioa.kontrasenaKonprobatu(String.valueOf(pasahitza), erabiltzailea.getPasahitza())) {
                return erabiltzailea;
            }
        }
        return null;
    }

}

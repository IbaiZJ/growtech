package growtech.util.negutegiKudeaketa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jxmapviewer.viewer.GeoPosition;

import growtech.util.userKudeaketa.ErabiltzaileKudeaketa;
import growtech.util.userKudeaketa.ErabiltzaileMota;

public class NegutegiKudeaketa {
    private final String negutegiTxtPath = "src/main/resources/negutegiak.txt";

    public List<Negutegia> hasieratuNegutegiak(List<Negutegia> negutegiak) {
        try (BufferedReader irakurri = new BufferedReader(new FileReader(negutegiTxtPath))) {
            String linea;
            while ((linea = irakurri.readLine()) != null) { // lineak irakurri bukaerara heldu harte
                String[] zatiak = linea.split(",");
                if (zatiak.length >= 6) {
                    int id = Integer.valueOf(zatiak[0].trim());
                    String herria = zatiak[1].trim(); // .trim() espazioak kentzeko
                    String lurraldea = zatiak[2].trim();
                    double latitude = Double.parseDouble(zatiak[3].trim());
                    double longitude = Double.parseDouble(zatiak[4].trim());
                    int partzelaKop = Integer.valueOf(zatiak[5].trim());

                    negutegiak.add(
                            new Negutegia(id, herria, lurraldea, new GeoPosition(latitude, longitude), partzelaKop));
                }
            }
            
        } catch (IOException e) {
            System.err.println("Errore bat gertatu bat negutegiak irakurtzerakoan");
        }
        return negutegiak;
    }

    // TODO Hobetu filtroa
    public List<Negutegia> jasoBeharDirenNegutegiak(List<Negutegia> negutegiak) {
        List<Negutegia> beharrezkoNegutegiak = new ArrayList<>();

        if (ErabiltzaileKudeaketa.ERABILTZAILEA.getMota() == ErabiltzaileMota.ADMIN) {
            return negutegiak;
        } else {
            List<Integer> erabiltzaileNegutegiId = ErabiltzaileKudeaketa.ERABILTZAILEA.getNegutegiak();
            for (Negutegia negutegia : negutegiak) {
                for (Integer i : erabiltzaileNegutegiId) {
                    if(i == negutegia.getId()) {
                        beharrezkoNegutegiak.add(negutegia);
                    }
                }
            }
        }

        return beharrezkoNegutegiak;
    }

}

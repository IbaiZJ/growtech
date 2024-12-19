package growtech.util.negutegiKudeaketa;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.jxmapviewer.viewer.GeoPosition;

public class NegutegiKudeaketa {
    private final String negutegiTxtPath = "src/main/resources/negutegiak.txt";

    public void hasieratuNegutegiak(List<Negutegia> negutegiak) {
        try (BufferedReader irakurri = new BufferedReader(new FileReader(negutegiTxtPath))) {
            String linea;
            while((linea = irakurri.readLine()) != null) { // lineak irakurri bukaerara heldu harte
                String [] zatiak = linea.split(",");

                if(zatiak.length >= 5) {
                    String herria = zatiak[0].trim(); // .trim() espazioak kentzeko
                    String lurraldea = zatiak[1].trim();
                    double latitude = Double.parseDouble(zatiak[2].trim());
                    double longitude = Double.parseDouble(zatiak[3].trim()); 
                    int partzelaKop = Integer.valueOf(zatiak[4].trim());

                    negutegiak.add(new Negutegia(herria, lurraldea, new GeoPosition(latitude, longitude), partzelaKop));
                }
            }
        }
        catch (IOException e) {
            System.err.println("Errore bat gertatu bat negutegiak irakurtzerakoan");
        }
    }
}

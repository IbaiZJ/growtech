package growtech.ui;

import java.awt.Image;

import org.jxmapviewer.viewer.GeoPosition;

import lombok.Getter;

@Getter
public class Negutegia {
    private String izena;
    private GeoPosition posizioa;
    private Image irudi;
    
    public Negutegia(String izena, GeoPosition posizioa) {
        this.izena = izena;
        this.posizioa = posizioa;
    }

    @Override
    public String toString() {
        return "Negutegia [izena=" + izena + ", posizioa=" + posizioa + "]";
    }
}

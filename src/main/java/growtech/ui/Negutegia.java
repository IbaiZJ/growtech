package growtech.ui;

import java.awt.Image;

import org.jxmapviewer.viewer.GeoPosition;

import lombok.Getter;

@Getter
public class Negutegia {
    private String lurraldea;
    private String herria;
    private GeoPosition posizioa;
    private Image irudi;
    
    public Negutegia(String herria, String lurraldea, GeoPosition posizioa) {
        this.herria = herria;
        this.lurraldea = lurraldea;
        this.posizioa = posizioa;
    }

    @Override
    public String toString() {
        return "Negutegia [lurraldea=" + lurraldea + ", herria=" + herria + ", posizioa=" + posizioa + ", irudi="
                + irudi + "]";
    }


}

package growtech.util.klaseak;


import javax.swing.ImageIcon;

import org.jxmapviewer.viewer.GeoPosition;

import lombok.Getter;

@Getter
public class Negutegia {
    private String lurraldea;
    private String herria;
    private GeoPosition posizioa;
    private ImageIcon irudi;
    
    public Negutegia(String herria, String lurraldea, GeoPosition posizioa, String irudi) {
        this.herria = herria;
        this.lurraldea = lurraldea;
        this.posizioa = posizioa;
        this.irudi = new ImageIcon(getClass().getResource(irudi));
    }

    @Override
    public String toString() {
        return "Negutegia [lurraldea=" + lurraldea + ", herria=" + herria + ", posizioa=" + posizioa + ", irudi="
                + irudi + "]";
    }


}

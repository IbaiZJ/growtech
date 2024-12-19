package growtech.util.negutegiKudeaketa;


import java.util.ArrayList;
import java.util.List;

import org.jxmapviewer.viewer.GeoPosition;

import growtech.util.userKudeaketa.Erabiltzailea;
import lombok.Getter;

@Getter
public class Negutegia {
    private String lurraldea;
    private String herria;
    private GeoPosition posizioa;
    private int partzelaKop;
    private List<Erabiltzailea> erabiltzaileak;
    
    public Negutegia(String herria, String lurraldea, GeoPosition posizioa, int partzelaKop) {
        this.herria = herria;
        this.lurraldea = lurraldea;
        this.posizioa = posizioa;
        this.partzelaKop = partzelaKop;
        erabiltzaileak = new ArrayList<>();
    }

    public void gehituErabiltzailea(Erabiltzailea erabiltzaile) {
        erabiltzaileak.add(erabiltzaile);
    }

    @Override
    public String toString() {
        return "Negutegia [lurraldea=" + lurraldea + ", herria=" + herria + ", posizioa=" + posizioa + ", partzelaKop="
                + partzelaKop + ", erabiltzaileak=" + erabiltzaileak + "]";
    }


    

}

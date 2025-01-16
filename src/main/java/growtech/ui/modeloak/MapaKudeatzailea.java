package growtech.ui.modeloak;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import growtech.ui.ItxuraPrintzipala;
import growtech.ui.panelak.MapaPanela;
import growtech.util.filtro.FiltroSelektore;
import growtech.util.negutegiKudeaketa.Negutegia;

public class MapaKudeatzailea {
    private static PropertyChangeSupport aldaketak;
    private ItxuraPrintzipala itxuraPrintzipala;
    public final static String P_MAPA_HANDITUTA = "MAPAHANDITUTA";
    public final static String P_MAPA_NORMAL = "MAPANORMAL";
    public final static String P_TENP_HEZE_AKTUALIZATU = "P_TENP_HEZE_AKTUALIZATU";

    public MapaKudeatzailea(ItxuraPrintzipala itxuraPrintzipala, MapaPanela mapaPanela) {
        aldaketak = new PropertyChangeSupport(mapaPanela);
        this.itxuraPrintzipala = itxuraPrintzipala;
    }

    public void mapaHandituta() {
        aldaketak.firePropertyChange(P_MAPA_HANDITUTA, -1, null);
    }

    public void mapaPred() {
        aldaketak.firePropertyChange(P_MAPA_NORMAL, -1, null);
    }

    public static void mapaTenpHezeAktualizatu() {
        if (aldaketak != null)
            aldaketak.firePropertyChange(P_TENP_HEZE_AKTUALIZATU, null, null);
    }

    public String[] jasoAukerak(FiltroSelektore<Negutegia, String> selector, String mota) {
        List<String> aukerak1 = new ArrayList<>();
        Map<String, List<Negutegia>> agrupacion = taldekatu(selector);
        Set<Entry<String, List<Negutegia>>> aukerak = agrupacion.entrySet();
        for (Entry<String, List<Negutegia>> aukera : aukerak) {
            aukerak1.add(aukera.getKey());
        }
        aukerak1.add(0, mota);
        return (aukerak1.toArray(new String[0]));
    }

    public Map<String, List<Negutegia>> taldekatu(FiltroSelektore<Negutegia, String> selector) {
        Map<String, List<Negutegia>> agrupazioa = new HashMap<>();
        for (Negutegia Negutegia : itxuraPrintzipala.getNegutegi()) {
            String clave = selector.selektorea(Negutegia);
            if (clave != null) {
                List<Negutegia> prodZerrenda = agrupazioa.get(clave);
                if (prodZerrenda == null) {
                    prodZerrenda = new ArrayList<>();
                }
                prodZerrenda.add(Negutegia);
                agrupazioa.put(clave, prodZerrenda);
            }
        }
        return agrupazioa;
    }

    public List<Negutegia> filtratu(FiltroSelektore<Negutegia, String> selector) {
        List<Negutegia> taldea = new ArrayList<>();
        for (Negutegia Negutegia : itxuraPrintzipala.getNegutegi()) {
            if (selector.filtroa(Negutegia)) {
                taldea.add(Negutegia);
            }
        }
        return taldea;
    }

    public List<Negutegia> filtratu(FiltroSelektore<Negutegia, String> selector, List<Negutegia> bistaratzekoakList) {
        List<Negutegia> Negutegiak = new ArrayList<>();
        for (Negutegia Negutegia : bistaratzekoakList) {
            if (selector.filtroa(Negutegia)) {
                Negutegiak.add(Negutegia);
            }
        }
        return Negutegiak;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        aldaketak.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        aldaketak.removePropertyChangeListener(listener);
    }

}

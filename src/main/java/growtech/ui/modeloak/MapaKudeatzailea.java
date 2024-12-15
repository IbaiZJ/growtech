package growtech.ui.modeloak;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import growtech.ui.ItxuraPrintzipala;
import growtech.ui.Negutegia;
import growtech.ui.panelak.MapaPanela;
import growtech.util.filtro.FiltroSelektore;

public class MapaKudeatzailea {
    private MapaPanela mapaPanela;
    private ItxuraPrintzipala itxuraPrintzipala;

    public MapaKudeatzailea(MapaPanela mapaPanela, ItxuraPrintzipala itxuraPrintzipala) {
        this.mapaPanela = mapaPanela;
        this.itxuraPrintzipala = itxuraPrintzipala;
    }

    public void mapaHanditu(Negutegia negutegia) {
        mapaPanela.getMapa().setZoom(3);
        mapaPanela.getMapa().setAddressLocation(negutegia.getPosizioa());
    }

    public void mapaPanelaTxikituta()  {
        mapaPanela.getMapa().setZoom(9);
        mapaPanela.getMapa().setAddressLocation(mapaPanela.getHasierakoPosizioa());
    }
    
    public void mapaPanelaHandituta(Negutegia negutegia) {
        JSplitPane panela = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
        true, mapaPanela.getMapa(), sortuNegutegiInformazioaPanela());
        panela.setDividerLocation(550);
        panela.setDividerSize(10);
        // panela.setEnabled(false);
        
        mapaPanela.getMapaJPanel().removeAll();
        mapaPanela.getMapaJPanel().revalidate();
        mapaPanela.getMapaJPanel().repaint(); 

        
        mapaPanela.getMapaJPanel().add(panela);
        mapaHanditu(negutegia);
    }

    private Component sortuNegutegiInformazioaPanela() {
        JPanel panela = new JPanel(new BorderLayout());
        panela.setBackground(Color.white);

        return panela;
    }

    public void mapaPanelaPred() {
        mapaPanela.getMapaJPanel().removeAll();
        mapaPanela.getMapaJPanel().revalidate();
        mapaPanela.getMapaJPanel().repaint(); 

        mapaPanela.getMapaJPanel().add(mapaPanela.getMapa());
        mapaPanelaTxikituta();
    }

    /*public void negutegiJLAktualizatu() {
        bistaratzekoak = sortuBistaratzekoak();
        negutegiJL.setListData(bistaratzekoak);
    }*/

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
}

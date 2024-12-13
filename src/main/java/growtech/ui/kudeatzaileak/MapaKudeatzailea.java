package growtech.ui.kudeatzaileak;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import growtech.ui.Negutegia;
import growtech.ui.panelak.MapaPanela;

public class MapaKudeatzailea {
    private MapaPanela mapaPanela;

    public MapaKudeatzailea(MapaPanela mapaPanela) {
        this.mapaPanela = mapaPanela;
    }

    public void mapaHanditu(Negutegia negutegia) {
        mapaPanela.getMapa().setZoom(3);
        mapaPanela.getMapa().setAddressLocation(negutegia.getPosizioa());
    }

    public void mapaPanelaTxikituta()  {
        mapaPanela.getMapa().setZoom(3);
        mapaPanela.getMapa().setAddressLocation(mapaPanela.getHasierakoPosizioa());
    }
    
    public void mapaPanelaHandituta(Negutegia negutegia) {
        JSplitPane panela = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
        true, mapaPanela.getMapa(), sortuNegutegiInformazioaPanela());
        panela.setDividerLocation(550);
        panela.setDividerSize(10);
        
        mapaPanela.getMapaJPanel().removeAll();
        mapaPanela.getMapaJPanel().revalidate();
        mapaPanela.getMapaJPanel().repaint(); 

        
        mapaPanela.getMapaJPanel().add(panela);
        mapaHanditu(negutegia);
    }

    private Component sortuNegutegiInformazioaPanela() {
        JPanel panela = new JPanel(new BorderLayout());

        return panela;
    }

    public void mapaPanelaPred() {
        mapaPanela.getMapaJPanel().removeAll();
        mapaPanela.getMapaJPanel().revalidate();
        mapaPanela.getMapaJPanel().repaint(); 

        mapaPanela.getMapaJPanel().add(mapaPanela.getMapa());
        mapaPanelaTxikituta();
    }
}

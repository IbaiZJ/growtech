package growtech.ui.ktrl;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import growtech.ui.Negutegia;
import growtech.ui.kudeatzaileak.MapaKudeatzailea;
import growtech.ui.panelak.MapaPanela;

public class MapaKtrl implements ListSelectionListener {
    MapaPanela mapaPanela;
    MapaKudeatzailea mapaKudeatzailea;

    public MapaKtrl(MapaPanela mapaPanela, MapaKudeatzailea mapaKudeatzailea) {
        this.mapaPanela = mapaPanela;
        this.mapaKudeatzailea = mapaKudeatzailea;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting())
            return;
        if(mapaPanela.getNegutegiJL().getSelectedValue() != null) {
            Negutegia negutegia = mapaPanela.getNegutegiJL().getSelectedValue();
            mapaKudeatzailea.mapaPanelaHandituta(negutegia);
        }
        if(mapaPanela.getNegutegiJL().getSelectedValue() == null) {
            mapaKudeatzailea.mapaPanelaPred();
        }
    }
}

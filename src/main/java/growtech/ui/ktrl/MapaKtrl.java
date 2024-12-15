package growtech.ui.ktrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import growtech.ui.Negutegia;
import growtech.ui.modeloak.MapaKudeatzailea;
import growtech.ui.panelak.MapaPanela;

public class MapaKtrl implements ListSelectionListener, ActionListener, ItemListener {
    private MapaPanela mapaPanela;
    private MapaKudeatzailea mapaKudeatzailea;

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
            mapaPanela.getBotoia().setEnabled(true);
        }
        if(mapaPanela.getNegutegiJL().getSelectedValue() == null) {
            mapaKudeatzailea.mapaPanelaPred();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Botoia")) {
            mapaKudeatzailea.mapaPanelaPred();
            mapaPanela.getNegutegiJL().clearSelection();
            mapaPanela.getBotoia().setEnabled(false);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        mapaPanela.negutegiJLAktualizatu();
        mapaPanela.waypointakMarraztu();
    }
}

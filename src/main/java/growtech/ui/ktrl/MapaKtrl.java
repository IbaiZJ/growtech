package growtech.ui.ktrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import growtech.ui.modeloak.MapaKudeatzailea;
import growtech.ui.panelak.MapaPanela;
import growtech.util.URL;
import growtech.util.negutegiKudeaketa.Negutegia;

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
            mapaKudeatzailea.mapaHandituta();
            mapaPanela.getBotoia().setEnabled(true);
        }
        if(mapaPanela.getNegutegiJL().getSelectedValue() == null) {
            mapaKudeatzailea.mapaPred();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Botoia")) {
            mapaKudeatzailea.mapaPred();
            mapaPanela.getNegutegiJL().clearSelection();
            mapaPanela.getBotoia().setEnabled(false);
        }
        else if(e.getActionCommand().equals("maps")) {
            Negutegia negutegia = mapaPanela.getNegutegiJL().getSelectedValue();
            URL.sabalduUrlNabigatzailean("https://www.google.com/maps/dir/?api=1&destination="+ negutegia.getPosizioa().getLatitude() +","+ negutegia.getPosizioa().getLongitude());
        }
        else if(e.getActionCommand().equals("enter")) {
            mapaKudeatzailea.mapatikNegutegiraMugitu();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        mapaPanela.negutegiJLAktualizatu();
        mapaPanela.waypointakMarraztu();
    }
}

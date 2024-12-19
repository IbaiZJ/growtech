package growtech.ui.ktrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import growtech.ui.ItxuraPrintzipala;
import growtech.ui.modeloak.MapaKudeatzailea;
import growtech.ui.panelak.MapaPanela;
import growtech.util.AppUtils;
import growtech.util.negutegiKudeaketa.Negutegia;

public class MapaKtrl implements ListSelectionListener, ActionListener, ItemListener {
    private MapaPanela mapaPanela;
    private MapaKudeatzailea mapaKudeatzailea;
    public final static String P_MAPA_NEGUTEGI_INFO_CLICK = "NEGUTEGIINFOCLICK";
    PropertyChangeSupport aldaketak;

    public MapaKtrl(MapaPanela mapaPanela, MapaKudeatzailea mapaKudeatzailea, ItxuraPrintzipala itxuraPrintzipala) {
        this.mapaPanela = mapaPanela;
        this.mapaKudeatzailea = mapaKudeatzailea;
        aldaketak = new PropertyChangeSupport(itxuraPrintzipala);
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
        if(e.getActionCommand().equals("maps")) {
            Negutegia negutegia = mapaPanela.getNegutegiJL().getSelectedValue();
            AppUtils.sabalduUrlNabigatzailean("https://www.google.com/maps/dir/?api=1&destination="+ negutegia.getPosizioa().getLatitude() +","+ negutegia.getPosizioa().getLongitude());
        }
        if(e.getActionCommand().equals("enter")) {
            aldaketak.firePropertyChange(P_MAPA_NEGUTEGI_INFO_CLICK, -1, null);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        mapaPanela.negutegiJLAktualizatu();
        mapaPanela.waypointakMarraztu();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        aldaketak.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        aldaketak.removePropertyChangeListener(listener);
    }
}

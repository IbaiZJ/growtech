package growtech.ui.modeloak;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.time.LocalDate;
import java.util.Date;


import growtech.ui.panelak.NegutegiInfoPanela;
import growtech.util.Grafikoa;

public class NegutegiInfoKudeatzailea {
    PropertyChangeSupport aldaketak;
    public final static String P_ONOFF_BOTOIA = "PONOFFBOTOIA";
    NegutegiInfoPanela negutegiInfoPanela;

    static boolean botoiBalorea = false;

    public NegutegiInfoKudeatzailea(NegutegiInfoPanela negutegiInfoPanela) {
        aldaketak = new PropertyChangeSupport(negutegiInfoPanela);
        this.negutegiInfoPanela = negutegiInfoPanela;    
    }

    public void onOffBotoiaExekutatu() {
        aldaketak.firePropertyChange(P_ONOFF_BOTOIA, -1, botoiBalorea);
        if(botoiBalorea) botoiBalorea = false;
        else botoiBalorea = true;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        aldaketak.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        aldaketak.removePropertyChangeListener(listener);
    }

}

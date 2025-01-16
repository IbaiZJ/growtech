package growtech.ui.modeloak;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import growtech.ui.ItxuraPrintzipala;
import growtech.ui.panelak.DatuHistorialPanela;
import growtech.ui.panelak.InformazioPanela;
import growtech.ui.panelak.NegutegiInfoPanela;

public class NegutegiInfoKudeatzailea {
    private static PropertyChangeSupport aldaketak;
    public final static String P_ONOFF_BOTOIA = "PONOFFBOTOIA";
    public final static String P_TENP_HEZE_AKTUALIZATU = "PTENPHEZEAKTUALIZATU";
    NegutegiInfoPanela negutegiInfoPanela;
    private static boolean botoiBalorea = false;
    private ItxuraPrintzipala itxuraPrintzipala;

    public NegutegiInfoKudeatzailea(NegutegiInfoPanela negutegiInfoPanela, ItxuraPrintzipala itxuraPrintzipala) {
        aldaketak = new PropertyChangeSupport(negutegiInfoPanela);
        this.itxuraPrintzipala = itxuraPrintzipala;
        this.negutegiInfoPanela = negutegiInfoPanela;    
    }

    public void onOffBotoiaExekutatu() {
        aldaketak.firePropertyChange(P_ONOFF_BOTOIA, null, botoiBalorea);
        if(botoiBalorea) botoiBalorea = false;
        else botoiBalorea = true;
    }

    public static void negutegiTenpHezeAktualizatu() {
        if(aldaketak != null)
            aldaketak.firePropertyChange(P_TENP_HEZE_AKTUALIZATU, null, null);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        aldaketak.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        aldaketak.removePropertyChangeListener(listener);
    }

    public void sortuHistorialTab() {
        DatuHistorialPanela datuHistorialPanela = new DatuHistorialPanela();
        if(itxuraPrintzipala.getTabPanela().getTabCount() >= 2) {
            itxuraPrintzipala.getTabPanela().addTab(" HISTORIALA ", datuHistorialPanela.historialPanelaSortu());
        }
        itxuraPrintzipala.getTabPanela().setSelectedIndex(2);
    }

    public void sortuInformazioTab() {
        InformazioPanela informazioPanela = new InformazioPanela();
        if(itxuraPrintzipala.getTabPanela().getTabCount() >= 2) {
            itxuraPrintzipala.getTabPanela().addTab(" INFORMAZIOA ", informazioPanela.sortuInformazioPanela());
        }
        itxuraPrintzipala.getTabPanela().setSelectedIndex(2);
    }

}

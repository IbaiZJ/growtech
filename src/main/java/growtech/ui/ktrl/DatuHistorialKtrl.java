package growtech.ui.ktrl;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;

import growtech.ui.modeloak.DatuHistorialKudeatzailea;
import growtech.ui.panelak.DatuHistorialPanela;

public class DatuHistorialKtrl implements PropertyChangeListener {
    private DatuHistorialKudeatzailea datuHistorialKudeatzailea;

    public DatuHistorialKtrl(DatuHistorialKudeatzailea datuHistorialKudeatzailea) {
        this.datuHistorialKudeatzailea = datuHistorialKudeatzailea;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (DatuHistorialPanela.PROPIETATE_DATA_BERRIA.equals(evt.getPropertyName())) {
            datuHistorialKudeatzailea.grafikoaAktualizatu((Date) evt.getNewValue());
        }
    }
}

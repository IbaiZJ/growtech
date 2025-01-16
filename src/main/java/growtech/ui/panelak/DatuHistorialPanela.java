package growtech.ui.panelak;

import java.awt.BorderLayout;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JPanel;

import org.jdesktop.swingx.JXDatePicker;

import growtech.ui.ktrl.DatuHistorialKtrl;
import growtech.ui.modeloak.DatuHistorialKudeatzailea;
import growtech.util.Grafikoa;

public class DatuHistorialPanela {
    private DatuHistorialKtrl datuHistorialKtrl;
    private DatuHistorialKudeatzailea datuHistorialKudeatzailea;
    public static final String PROPIETATE_DATA_BERRIA = "date";

    public DatuHistorialPanela() {
        datuHistorialKudeatzailea = new DatuHistorialKudeatzailea();
        datuHistorialKtrl = new DatuHistorialKtrl(datuHistorialKudeatzailea);
    }

    public Component historialPanelaSortu() {
        JPanel panela = new JPanel(new BorderLayout());
        JPanel datePickerPanela = new JPanel();

        JXDatePicker jxDatePicker = new JXDatePicker();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        jxDatePicker.addPropertyChangeListener(PROPIETATE_DATA_BERRIA, datuHistorialKtrl);
        jxDatePicker.setFormats(dateFormat);
        jxDatePicker.setDate(new Date());
        datePickerPanela.add(jxDatePicker);

        panela.add(datePickerPanela, BorderLayout.NORTH);
        panela.add(Grafikoa.sortuTenpHezHistorialGrafikoa(), BorderLayout.CENTER);

        return panela;
    }
}

package growtech.ui.panelak;

import java.awt.BorderLayout;
import java.awt.Component;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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
    private JXDatePicker jxDatePicker;

    public DatuHistorialPanela() {
        datuHistorialKudeatzailea = new DatuHistorialKudeatzailea();
        datuHistorialKtrl = new DatuHistorialKtrl(datuHistorialKudeatzailea);
    }

    public Component historialPanelaSortu() {
        JPanel panela = new JPanel(new BorderLayout());
        JPanel datePickerPanela = new JPanel();

        jxDatePicker = new JXDatePicker();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        jxDatePicker.addPropertyChangeListener(PROPIETATE_DATA_BERRIA, datuHistorialKtrl);
        jxDatePicker.setFormats(dateFormat);
        jxDatePicker.setDate(new Date());
        datePickerPanela.add(jxDatePicker);

        panela.add(datePickerPanela, BorderLayout.NORTH);
        panela.add(Grafikoa.sortuTenpHezHistorialGrafikoa(), BorderLayout.CENTER);

        return panela;
    }

    public void datePickerDataAtzera() {
        LocalDate data = LocalDate.parse(DatuHistorialKudeatzailea.datePickerData);
        LocalDate atzokoData = data.minusDays(1);
        Date atzokoDataDate = Date.from(atzokoData.atStartOfDay(ZoneId.systemDefault()).toInstant());
        DatuHistorialKudeatzailea.datePickerData = String.valueOf(atzokoData);
        jxDatePicker.setDate(atzokoDataDate);
    }

    public void datePickerDataAurrera() {
        LocalDate data = LocalDate.parse(DatuHistorialKudeatzailea.datePickerData);
        if(!data.equals(LocalDate.now())){
            LocalDate biharkoData = data.plusDays(1);
            Date biharkoDataDate = Date.from(biharkoData.atStartOfDay(ZoneId.systemDefault()).toInstant());
            DatuHistorialKudeatzailea.datePickerData = String.valueOf(biharkoData);
            jxDatePicker.setDate(biharkoDataDate);
        }
    }
}

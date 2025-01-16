package growtech.ui.modeloak;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.swing.JOptionPane;

import growtech.util.Grafikoa;

public class DatuHistorialKudeatzailea {
    public static String datePickerData = String.valueOf(LocalDate.now());

    public DatuHistorialKudeatzailea() {
    }

    public void grafikoaAktualizatu(Date dataBerria) {
        SimpleDateFormat dataFormatuarekin = new SimpleDateFormat("yyyy-MM-dd");
        datePickerData = dataFormatuarekin.format(dataBerria);
        Grafikoa.grafikoDatasetHistorialEzarri(dataFormatuarekin.format(dataBerria));
        if (Grafikoa.getChartHistoriala() != null)
            Grafikoa.getChartHistoriala().setTitle(dataFormatuarekin.format(dataBerria) + "ko Tenperatura eta Hezetasuna");
        System.out.println(dataFormatuarekin.format(dataBerria));
        if (Grafikoa.getDatasetHistorial().getRowCount() == 0 && Grafikoa.getDatasetHistorial().getColumnCount() == 0) {
            JOptionPane.showMessageDialog(null, "Ez dago egun honetako daturik.", "Errorea", JOptionPane.ERROR_MESSAGE);
        }
    }
}

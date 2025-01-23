package growtech.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import growtech.ui.adaptadore.ErabiltzaileAdaptadorea;
import growtech.util.filtro.ErabiltzaileFactory;
import growtech.util.userKudeaketa.ErabiltzaileKudeaketa;
import growtech.util.userKudeaketa.Erabiltzailea;

public class ErabiltzaileakIkusiDialogoa extends JDialog {
    private JList<Erabiltzailea> erabiltzaileJL;
    List<Erabiltzailea> erabiltzaileak;
    ErabiltzaileKudeaketa erabiltzaileKudeaketa;

    public ErabiltzaileakIkusiDialogoa(JFrame leihoa) {
        super(leihoa, "Erabiltzaileak Ikusi", true);
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/GrowTech.png"));
        this.setIconImage(icon.getImage());
        this.setSize(400, 550);
        this.setContentPane(sortuDialogoPanela());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private Container sortuDialogoPanela() {
        JPanel panela = new JPanel(new BorderLayout());
        panela.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        JScrollPane jlPanela = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        erabiltzaileak = new ArrayList<>();
        erabiltzaileKudeaketa = new ErabiltzaileKudeaketa();
        erabiltzaileak = erabiltzaileKudeaketa.erabiltzaileakIrakurri();
        Collections.sort(erabiltzaileak, ErabiltzaileFactory.getIzenKonparatzailea());

        erabiltzaileJL = new JList<>();
        erabiltzaileJL.setCellRenderer(new ErabiltzaileAdaptadorea(false));
        erabiltzaileJL.setListData(erabiltzaileak.toArray(new Erabiltzailea[0]));
        jlPanela.setViewportView(erabiltzaileJL);

        panela.add(jlPanela);

        return panela;
    }
}

package growtech.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import growtech.ui.adaptadore.ErabiltzaileEzabatuAdaptadorea;
import growtech.util.userKudeaketa.ErabiltzaileKudeaketa;
import growtech.util.userKudeaketa.Erabiltzailea;

public class ErabiltzaileakEzabatuDialogoa extends JDialog implements ActionListener {
    JFrame leihoa;
    private JList<Erabiltzailea> erabiltzaileJL;
    List<Erabiltzailea> erabiltzaileak;
    ErabiltzaileKudeaketa erabiltzaileKudeaketa;

    public ErabiltzaileakEzabatuDialogoa(JFrame leihoa) {
        super(leihoa, "Ezabatu Erabiltzailea", true);

        ImageIcon icon = new ImageIcon(getClass().getResource("/img/GrowTech.png"));
        this.setIconImage(icon.getImage());
        this.setSize(400, 550);
        this.setContentPane(sortuDialogoPanela());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.leihoa = leihoa;
    }

    private Container sortuDialogoPanela() {
        JPanel panela = new JPanel(new BorderLayout());
        JScrollPane jlPanela = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        panela.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        erabiltzaileak = new ArrayList<>();
        erabiltzaileKudeaketa = new ErabiltzaileKudeaketa();
        erabiltzaileak = erabiltzaileKudeaketa.erabiltzaileakIrakurri();

        erabiltzaileJL = new JList<>();
        erabiltzaileJL.setCellRenderer(new ErabiltzaileEzabatuAdaptadorea());
        erabiltzaileJL.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        erabiltzaileJL.setListData(erabiltzaileak.toArray(new Erabiltzailea[0]));
        jlPanela.setViewportView(erabiltzaileJL);

        JButton ezabatuBotoia = new JButton("Ezabatu");
        ezabatuBotoia.addActionListener(this);

        panela.add(jlPanela, BorderLayout.CENTER);
        panela.add(ezabatuBotoia, BorderLayout.SOUTH);

        return panela;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        StringBuilder erabiltzaileakString = new StringBuilder();
        erabiltzaileakString.append("Erabiltzaile hauek ezabatuko dituzu:\n");
        for (Erabiltzailea erabiltzailea : erabiltzaileJL.getSelectedValuesList()) {
            erabiltzaileakString.append(erabiltzailea.getIzena()).append(" ").append(erabiltzailea.getAbizena())
                    .append("\n");
        }
        int erantzuna = JOptionPane.showConfirmDialog(leihoa, erabiltzaileakString, "Ezabatu erabiltzaileak",
                JOptionPane.OK_CANCEL_OPTION);
        if (erantzuna == JOptionPane.OK_OPTION) {
            ErabiltzaileKudeaketa.ezabatuErabiltzailea(erabiltzaileJL.getSelectedValuesList());
            erabiltzaileak.removeAll(erabiltzaileJL.getSelectedValuesList());
            erabiltzaileJL.setListData(erabiltzaileak.toArray(new Erabiltzailea[0]));
        }
    }

}

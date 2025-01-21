package growtech.ui.dialog;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import growtech.ui.ItxuraPrintzipala;
import growtech.ui.adaptadore.MapaJLAdaptadorea;
import growtech.util.Enkriptazioa;
import growtech.util.negutegiKudeaketa.Negutegia;
import growtech.util.userKudeaketa.ErabiltzaileKudeaketa;
import growtech.util.userKudeaketa.ErabiltzaileMota;
import growtech.util.userKudeaketa.Erabiltzailea;

public class ErabiltzaileaSortuDialogoa extends JDialog implements ActionListener {
    JTextField izenaField;
    JTextField abizenaField;
    JTextField erabiltzaileaField;
    JPasswordField pasahitzaField;
    JComboBox<String> erabiltzaileMota;
    JButton sortuBotoia;
    JLabel erroreLabel;
    ItxuraPrintzipala itxuraPrintzipala;
    JList<Negutegia> negutegiJL;

    public ErabiltzaileaSortuDialogoa(ItxuraPrintzipala itxuraPrintzipala) {
        super(itxuraPrintzipala, "Sortu Erabiltzailea", true);
        this.setSize(300, 400);
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/GrowTech.png"));
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.itxuraPrintzipala = itxuraPrintzipala;
        this.setContentPane(sortuDialogoPanela());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private Container sortuDialogoPanela() {
        JPanel panela = new JPanel(new GridLayout(13, 1));
        panela.setBorder(BorderFactory.createEmptyBorder(5, 50, 5, 50));

        izenaField = new JTextField();
        abizenaField = new JTextField();
        erabiltzaileaField = new JTextField();
        pasahitzaField = new JPasswordField();
        String[] motak = { "Erabiltzailea", "Administratzailea" };
        erabiltzaileMota = new JComboBox<>(motak);
        sortuBotoia = new JButton("Sortu");
        erroreLabel = new JLabel();

        sortuBotoia.addActionListener(this);

        panela.add(new JLabel("Izena"));
        panela.add(izenaField);
        panela.add(new JLabel("Abizena"));
        panela.add(abizenaField);
        panela.add(new JLabel("Erabiltzailea eta pasahitza"));
        panela.add(erabiltzaileaField);
        panela.add(pasahitzaField);
        panela.add(erabiltzaileMota);
        panela.add(negutegiAukeratu());
        panela.add(new JLabel());
        panela.add(erroreLabel);
        panela.add(sortuBotoia);

        return panela;
    }

    private Component negutegiAukeratu() {
        JScrollPane jlPanela = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        List<Negutegia> negutegiak = new ArrayList<>();
        negutegiak = itxuraPrintzipala.hasieratuNegutegiak(negutegiak);

        negutegiJL = new JList<>();
        negutegiJL.setCellRenderer(new MapaJLAdaptadorea());
        negutegiJL.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        negutegiJL.setListData(negutegiak.toArray(new Negutegia[0]));
        jlPanela.setViewportView(negutegiJL);

        return jlPanela;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!(izenaField.getText().isEmpty() || abizenaField.getText().isEmpty()
                || erabiltzaileaField.getText().isEmpty()
                || pasahitzaField.getPassword() == null)) {
            ErabiltzaileMota mota;
            if (erabiltzaileMota.getSelectedItem().equals("Erabiltzailea"))
                mota = ErabiltzaileMota.USER;
            else
                mota = ErabiltzaileMota.ADMIN;
            Erabiltzailea erabiltzaileBerria = new Erabiltzailea(izenaField.getText(), abizenaField.getText(),
                    erabiltzaileaField.getText(),
                    Enkriptazioa.kontrasenaEnkriptatu(String.valueOf(pasahitzaField.getPassword())),
                    mota);
            for (Negutegia negu : negutegiJL.getSelectedValuesList()) {
                erabiltzaileBerria.gehituNegutegia(negu.getId());
            }
            ErabiltzaileKudeaketa.sortuErabiltzailea(erabiltzaileBerria);
            JOptionPane.showMessageDialog(
                    itxuraPrintzipala,
                    izenaField.getText() + " izeneko erabiltzailea sortuta",
                    "Erabiltzailea",
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            erroreLabel.setText("Errorea!!!");
        }

    }
}
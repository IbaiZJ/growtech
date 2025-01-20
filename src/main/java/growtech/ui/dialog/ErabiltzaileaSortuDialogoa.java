package growtech.ui.dialog;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import growtech.util.Enkriptazioa;
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
    JFrame leihoa;

    public ErabiltzaileaSortuDialogoa(JFrame leihoa) {
        super(leihoa, "Sortu Erabiltzailea", true);
        this.setSize(300, 400);
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/GrowTech.png"));
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setContentPane(sortuDialogoPanela());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.leihoa = leihoa;
    }

    private Container sortuDialogoPanela() {
        JPanel panela = new JPanel(new GridLayout(12, 1));
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
        panela.add(new JLabel());
        panela.add(erroreLabel);
        panela.add(sortuBotoia);

        return panela;
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
            // erabiltzaileBerria.gehituNegutegia(1);
            // erabiltzaileBerria.gehituNegutegia(4);
            // erabiltzaileBerria.gehituNegutegia(6);
            ErabiltzaileKudeaketa.sortuErabiltzailea(erabiltzaileBerria);
            JOptionPane.showMessageDialog(
                    leihoa,
                    izenaField.getText() + " izeneko erabiltzailea sortuta",
                    "Erabiltzailea",
                    JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
        } else {
            erroreLabel.setText("Errorea!!!");
        }

    }
}
// TODO: Erabiltzailei negutegia asignatu
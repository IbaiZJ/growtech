package growtech.ui.dialog;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import growtech.util.IrudiaTestuFormatua;
import growtech.util.userKudeaketa.ErabiltzaileKudeaketa;
import lombok.Getter;

public class AdminUserDialogoa extends JDialog implements ActionListener {
    public @Getter boolean ITXI_DA_X = true;
    JTextField erabiltzailea;
    JPasswordField pasahitza;
    JLabel alerta;

    public AdminUserDialogoa(JFrame leihoa) {
        super(leihoa, "Grow Tech", true);
        this.setSize(300,400);
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/GrowTech.png")); 
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setContentPane(sortuDialogoPanela());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
    }

    private Container sortuDialogoPanela() {
        JPanel panela = new JPanel(new GridLayout(2,1));
        panela.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        JPanel barnePanela = new JPanel(new GridLayout(6,1));

        JLabel irudi = new JLabel(IrudiaTestuFormatua.irudiaEskalatu(
            getClass().getResource("/img/GrowTech.png"), 100,100));

        erabiltzailea = new JTextField();
        pasahitza = new JPasswordField();
        pasahitza.addActionListener(this);
        alerta = new JLabel();
        
        JButton botoia = new JButton("OK");
        botoia.setPreferredSize(new Dimension(50, 10));
        botoia.addActionListener(this);
        
        erabiltzailea.setToolTipText("Erabiltzailea");
        pasahitza.setToolTipText("Pasahitza");
        
        barnePanela.add(new JLabel("Erabiltzailea"));
        barnePanela.add(erabiltzailea);
        barnePanela.add(new JLabel("Pasahitza"));
        barnePanela.add(pasahitza);
        barnePanela.add(alerta);
        barnePanela.add(botoia);

        panela.add(irudi);
        panela.add(barnePanela);

        return panela;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(ErabiltzaileKudeaketa.bilatuErabiltzailea(erabiltzailea.getText(), String.valueOf(pasahitza.getPassword()))) {
            ITXI_DA_X = false;
            this.dispose();
        }
        else {
            alerta.setFont(new Font("Arial", Font.BOLD, 9));
            alerta.setText("*Erabiltzailea edo pasahitza ez da zuzena");
            alerta.setForeground(Color.red);
            erabiltzailea.setText("");
            erabiltzailea.requestFocus();
            pasahitza.setText("");
        }
    }
}


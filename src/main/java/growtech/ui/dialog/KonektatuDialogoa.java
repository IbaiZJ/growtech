package growtech.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import growtech.mqtt.MQTT;
import growtech.util.IrudiaTestuFormatua;

public class KonektatuDialogoa extends JDialog implements WindowListener {
    JProgressBar progressBar;
    private MQTT mqtt;

    public KonektatuDialogoa(JFrame leihoa, MQTT mqtt) {
        super(leihoa, "Konektatzen", true);
        this.mqtt = mqtt;
        this.setSize(300,160);
        this.setLocationRelativeTo(null);
        this.setContentPane(sortuDialogoPanela());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // this.setUndecorated(true);
        this.addWindowListener(this);
		this.setVisible(true);

    }

    private Container sortuDialogoPanela() {
        JPanel panela = new JPanel(new BorderLayout());
        panela.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanela = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanela.add(IrudiaTestuFormatua.sortuTestuaFormatuarekin(
            "Konektatzen...", new Font("Arial", Font.BOLD, 30), Color.BLACK));

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setString("[MQTT] Connecting to broker:");
        progressBar.setValue(33);
        
        panela.add(topPanela, BorderLayout.CENTER);
        panela.add(progressBar, BorderLayout.SOUTH);

        return panela;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        try {
            mqtt.klienteraKonektatu();
            progressBar.setString("[MQTT] Subscribe ");
            progressBar.setValue(66);
            mqtt.klienteraSubskribatu();
            progressBar.setString("[MQTT] Ready ");
            progressBar.setValue(100);
            this.dispose();
        }
        catch(Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore bat egon da\nkonektatzerakoan", 
            "Errorea", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowClosing(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    
}

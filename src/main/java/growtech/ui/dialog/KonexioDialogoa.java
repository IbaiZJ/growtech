package growtech.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import growtech.mqtt.MQTT;

public class KonexioDialogoa extends JDialog implements ActionListener {
    JTextField brockerField;
    ButtonGroup botoiTaldea;
    JButton okButton;

    public KonexioDialogoa(JFrame leihoa) {
        super(leihoa, "Konexio Konfigurazioa", true);
        this.setSize(300, 400);
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/GrowTech.png"));
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setContentPane(sortuDialogoPanela());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    private Container sortuDialogoPanela() {
        JPanel panela = new JPanel(new GridLayout(6, 1));
        panela.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        brockerField = new JTextField();
        JPanel brokerFieldPanela = new JPanel();
        JLabel broker = new JLabel("Broker");
        JLabel labelQos = new JLabel("Qos");
        botoiTaldea = new ButtonGroup();
        okButton = new JButton("OK");

        broker.setFont(new Font("Arial", Font.BOLD, 30));
        brockerField.setPreferredSize(new Dimension(180, 40));
        brockerField.setText(MQTT.BROKER);
        brokerFieldPanela.add(brockerField);
        labelQos.setFont(new Font("Arial", Font.BOLD, 30));
        okButton.addActionListener(this);

        panela.add(broker);
        panela.add(brokerFieldPanela);
        panela.add(labelQos);
        panela.add(sortuBotoiPanela());
        panela.add(new JLabel());
        panela.add(okButton);
        return panela;
    }

    private Component sortuRadioButon(String izena) {
        JPanel panela = new JPanel(new BorderLayout());
        JRadioButton botoia = new JRadioButton(izena);
        botoiTaldea.add(botoia);
        botoia.setSelected(true);
        botoia.setActionCommand(izena);
        panela.add(botoia);
        return panela;
    }

    private Component sortuBotoiPanela() {
        JPanel p = new JPanel(new GridLayout(1, 3));
        p.add(sortuRadioButon("0"));
        p.add(sortuRadioButon("1"));
        p.add(sortuRadioButon("2"));
        return p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        MQTT.BROKER = brockerField.getText();
        if (botoiTaldea.getSelection() != null) {
            String aukeratutakoBotoia = botoiTaldea.getSelection().getActionCommand();
            if ("0".equals(aukeratutakoBotoia)) {
                MQTT.QoS = 0;
            } else if ("1".equals(aukeratutakoBotoia)) {
                MQTT.QoS = 1;
            } else if ("2".equals(aukeratutakoBotoia)) {
                MQTT.QoS = 2;
            }
        }
        System.out.println(MQTT.BROKER);
        System.out.println(MQTT.QoS);
        this.dispose();
    }
}

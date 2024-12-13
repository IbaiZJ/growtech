package growtech.ui.panelak;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import growtech.util.IrudiaTestuFormatua;

public class DeskonektatutaPanela {

    public DeskonektatutaPanela() {}

    public JPanel sortuDeskonektatutaPanela() {
        JPanel panela = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
    
        JLabel warning = new JLabel(IrudiaTestuFormatua.irudiaEskalatu(getClass().getResource("/img/warning-512.png"), 40, 40));
        JLabel warningTextua = new JLabel("Deskonektatuta");
        warningTextua.setFont(new Font("Arial", Font.BOLD, 18));
        warningTextua.setForeground(Color.BLACK);
        
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER));
        p.add(warning);
        p.add(warningTextua);
    
        panela.add(p, gbc);
    
        return panela;
    }
    
}

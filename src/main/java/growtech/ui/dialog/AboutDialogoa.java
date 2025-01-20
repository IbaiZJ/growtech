package growtech.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXHyperlink;

import growtech.config.AppKonfigurazioa;
import growtech.util.AppUtils;
import growtech.util.IrudiaTestuFormatua;


public class AboutDialogoa extends JDialog implements ActionListener {

    public AboutDialogoa(JFrame leihoa) {
        super(leihoa, "About", true);
        this.setSize(250,370);
        this.setLocationRelativeTo(null);
        this.setContentPane(sortuDialogoPanela());
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        // this.setUndecorated(true);
		this.setVisible(true);
    }

    private Container sortuDialogoPanela() {
        JPanel panela = new JPanel(new BorderLayout());
        panela.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel topPanela = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanela.add(new JLabel(IrudiaTestuFormatua.irudiaEskalatu(
            getClass().getResource("/img/GrowTech.png"), 50,50)));
        topPanela.add(IrudiaTestuFormatua.sortuTestuaFormatuarekin(
            AppKonfigurazioa.APP_IZENA, new Font("Arial", Font.BOLD, 18), Color.BLACK));

        JPanel centerPanela = new JPanel(new GridLayout(9, 1, 5, 5));
        JLabel bertsioaLabel = new JLabel("Bertsioa: " + AppUtils.getAppBertsioa());
        JLabel javaRuntimeLabel = new JLabel("Java runtime: %s".formatted(Runtime.version()));
        JXHyperlink githubLinkLabel = new JXHyperlink();
        githubLinkLabel.setText(AppKonfigurazioa.GITHUB_URL);
        JLabel copyrightLabel = new JLabel("© 2024-2025 MU\n");

        githubLinkLabel.addActionListener(this);
        githubLinkLabel.setFocusPainted(false);
        githubLinkLabel.setActionCommand(AppKonfigurazioa.GITHUB_URL);

        centerPanela.add(bertsioaLabel);
        centerPanela.add(javaRuntimeLabel);
        centerPanela.add(githubLinkLabel);
        centerPanela.add(copyrightLabel);
        centerPanela.add(new JLabel(" ->Ibai Zorrilla"));
        centerPanela.add(new JLabel(" ->Ibai Lazkano"));
        centerPanela.add(new JLabel(" ->Ibai Ochoa de Echaguen"));
        centerPanela.add(new JLabel(" ->Alex Zabaleta"));
        centerPanela.add(new JLabel(" ->Mikel Alvarez"));

        JPanel botoiPanela = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton botoia = new JButton("OK");
        botoia.addActionListener(this);
        botoia.setActionCommand("Botoia");
        botoiPanela.add(botoia);

        panela.add(topPanela, BorderLayout.NORTH);
        panela.add(centerPanela, BorderLayout.CENTER);
        panela.add(botoiPanela, BorderLayout.SOUTH);

        return panela;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String komandoa = e.getActionCommand();

        if(komandoa == "Botoia") {
            this.dispose();
        }
        if(komandoa == AppKonfigurazioa.GITHUB_URL) {
            AppUtils.sabalduUrlNabigatzailean(AppKonfigurazioa.GITHUB_URL);
        }
        
    }
}

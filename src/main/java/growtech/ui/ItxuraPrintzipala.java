package growtech.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;

import org.jxmapviewer.viewer.GeoPosition;

import growtech.config.AppKonfigurazioa;
import growtech.mqtt.MQTT;
import lombok.Getter;


public class ItxuraPrintzipala extends JFrame {
    private MQTT mqtt;
    private @Getter MenuBarra menuBarra;
    private JTabbedPane tabPanela;
    private @Getter JLabel zenbakia;

    private @Getter List<Negutegia> negutegi;

    public ItxuraPrintzipala() {
        this.setTitle(AppKonfigurazioa.APP_IZENA);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/GrowTech.png")); 
        this.setIconImage(icon.getImage());
        this.setSize(AppKonfigurazioa.APP_WIDTH, AppKonfigurazioa.APP_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        try {
            mqtt = new MQTT(this);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        tabPanela = new JTabbedPane();
        menuBarra = new MenuBarra(this, mqtt);
        negutegi = new ArrayList<>();
        hasieratuNegutegiak();
        this.setJMenuBar(menuBarra.sortuMenuBar());
        this.setContentPane(sortuLeihoPanela());
        
        this.setVisible(true);

        menuBarra.konektatuAkzioaEman();
    }

    private Container sortuLeihoPanela() {
        JPanel panela = new JPanel(new BorderLayout());

        panela.add(sortuToolBar(), BorderLayout.WEST);
        panela.add(sortuLeihoZentrala(), BorderLayout.CENTER);

        return panela;
    }

    private Component sortuLeihoZentrala() {
        MapaPanela mapaPanela = new MapaPanela(this);
        JPanel panel1 = new JPanel(new FlowLayout());
        
        zenbakia = new JLabel("Bidalitako balioa");
        panel1.add(zenbakia);

        tabPanela.addTab(getTitle(), panel1);
        tabPanela.addTab(" MAPA ", mapaPanela.sortuMapaPanela());
        // tabPanela.addTab(getTitle(), panel3);

        return tabPanela;
    }

    private Component sortuToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setOrientation(JToolBar.VERTICAL);
        toolBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
        
        toolBar.add(menuBarra.getKonektatu());
        toolBar.add(menuBarra.getDeskonektatu());
        toolBar.add(menuBarra.getKonekzioKonf());
        toolBar.addSeparator();
        toolBar.add(Box.createVerticalGlue());
        toolBar.add(menuBarra.getUser());

        return toolBar;
    }

    private void hasieratuNegutegiak() {
        negutegi.add(new Negutegia("Gazteiz", new GeoPosition(42.87514, -2.662218)));
        negutegi.add(new Negutegia("Bilbo", new GeoPosition(43.27549, -2.920400)));
        negutegi.add(new Negutegia("Donostia", new GeoPosition(43.29478, -1.983199)));
        negutegi.add(new Negutegia("Amorebieta", new GeoPosition(43.21962, -2.744389)));
        negutegi.add(new Negutegia("Elorrio", new GeoPosition(43.13518, -2.543006)));
        negutegi.add(new Negutegia("Bergara", new GeoPosition(43.11608, -2.418478)));
        negutegi.add(new Negutegia("Aretxabaleta", new GeoPosition(43.02098, -2.509590)));
        negutegi.add(new Negutegia("Beasain", new GeoPosition(43.06259, -2.204373)));
        negutegi.add(new Negutegia("Ondarroa", new GeoPosition(43.33234, -2.453796)));
        negutegi.add(new Negutegia("Meñaka", new GeoPosition(43.36791, -2.797169)));
        negutegi.add(new Negutegia("Azpeitia", new GeoPosition(43.18431, -2.258009)));
    }
}

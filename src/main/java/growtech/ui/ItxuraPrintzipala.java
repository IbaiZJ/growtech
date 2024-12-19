package growtech.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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

import growtech.config.AppKonfigurazioa;
import growtech.mqtt.MQTT;
import growtech.ui.ktrl.MapaKtrl;
import growtech.ui.panelak.DeskonektatutaPanela;
import growtech.ui.panelak.MapaPanela;
import growtech.ui.panelak.NegutegiInfoPanela;
import growtech.util.negutegiKudeaketa.NegutegiKudeaketa;
import growtech.util.negutegiKudeaketa.Negutegia;
import lombok.Getter;

@Getter 
public class ItxuraPrintzipala extends JFrame implements PropertyChangeListener {
    private MQTT mqtt;
    private MenuBarra menuBarra;
    private JTabbedPane tabPanela;
    private JLabel zenbakia;
    private MapaPanela mapaPanela;
    private List<Negutegia> negutegi;
    private MapaKtrl mapaKtrl;

    public ItxuraPrintzipala() {
        this.setTitle(AppKonfigurazioa.APP_IZENA);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/GrowTech.png")); 
        this.setIconImage(icon.getImage());
        this.setSize(AppKonfigurazioa.APP_WIDTH, AppKonfigurazioa.APP_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.mapaPanela = new MapaPanela(this);
        this.mapaPanela.addPropertyChangeListener(this);
        mapaKtrl = mapaPanela.getMapaKtrl();
        mapaKtrl.addPropertyChangeListener(this);
        
        try {
            mqtt = new MQTT(this);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        
        
        menuBarra = new MenuBarra(this, mqtt);
        menuBarra.addPropertyChangeListener(this);
        tabPanela = new JTabbedPane();
        negutegi = new ArrayList<>();
        hasieratuNegutegiak();
        this.setContentPane(sortuLeihoPanela()); 
        this.setJMenuBar(menuBarra.sortuMenuBar());
        
        this.setVisible(true);

        menuBarra.konektatuAkzioaEman();
    }

    private Container sortuLeihoPanela() {
        JPanel panela = new JPanel(new BorderLayout());

        panela.add(sortuToolBar(), BorderLayout.WEST);
        panela.add(tabPanela, BorderLayout.CENTER);

        return panela;
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
        NegutegiKudeaketa negutegiKudeaketa = new NegutegiKudeaketa();
        negutegiKudeaketa.hasieratuNegutegiak(negutegi);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propietatea = evt.getPropertyName();

        if(propietatea.equals(MenuBarra.P_KONEKTATU)) {
            tabPanela.removeAll();
            tabPanela.addTab(" MAPA ", mapaPanela.sortuMapaPanela());
        }
        if(propietatea.equals(MenuBarra.P_DESKONEKTATU)) {
            DeskonektatutaPanela deskonektatutaPanela = new DeskonektatutaPanela();
            tabPanela.removeAll();
            tabPanela.addTab(" KONEKTATU ", deskonektatutaPanela.sortuDeskonektatutaPanela());
        }
        if(propietatea.equals(MenuBarra.P_DESKONEKTATU)) {

        }
        if(propietatea.equals(MapaPanela.P_MAPA_NEGUTEGI_INFO)) {
            NegutegiInfoPanela negutegiInfoPanela = new NegutegiInfoPanela(this);
            if (tabPanela.getTabCount() == 1)
                tabPanela.addTab(" NEGUTEGI ", negutegiInfoPanela.negutegiInfoPZentrala());
        }
        if(propietatea.equals(MapaPanela.P_MAPA_NEGUTEGI_INFO_EXIT)) {
            if (tabPanela.getTabCount() > 1)
                tabPanela.remove(1);
        }
        if(propietatea.equals(MapaKtrl.P_MAPA_NEGUTEGI_INFO_CLICK)) {
            tabPanela.setSelectedIndex(1);
        }
    }
}

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
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import growtech.config.AppKonfigurazioa;
import growtech.mqtt.MQTT;
import growtech.ui.ktrl.MapaKtrl;
import growtech.ui.modeloak.MapaKudeatzailea;
import growtech.ui.panelak.DatuHistorialPanela;
import growtech.ui.panelak.DeskonektatutaPanela;
import growtech.ui.panelak.InformazioPanela;
import growtech.ui.panelak.MapaPanela;
import growtech.ui.panelak.NegutegiInfoPanela;
import growtech.util.negutegiKudeaketa.NegutegiKudeaketa;
import growtech.util.negutegiKudeaketa.Negutegia;
import growtech.util.userKudeaketa.ErabiltzaileKudeaketa;
import growtech.util.userKudeaketa.ErabiltzaileMota;
import lombok.Getter;

@Getter
public class ItxuraPrintzipala extends JFrame implements PropertyChangeListener, ChangeListener {
    private MQTT mqtt;
    private @Getter MenuBarra menuBarra;
    private JTabbedPane tabPanela;
    private JToolBar toolBar;
    private List<Negutegia> negutegi;

    private MapaPanela mapaPanela;
    private MapaKtrl mapaKtrl;
    private DeskonektatutaPanela deskonektatutaPanela;
    private NegutegiInfoPanela negutegiInfoPanela;
    private DatuHistorialPanela datuHistorialPanela;
    private InformazioPanela informazioPanela;

    private boolean infoZabalikDago, historialZabalikDago;

    public ItxuraPrintzipala() {
        this.setTitle(AppKonfigurazioa.APP_IZENA);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/GrowTech.png"));
        this.setIconImage(icon.getImage());
        this.setSize(AppKonfigurazioa.APP_WIDTH, AppKonfigurazioa.APP_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.hasieratuAldagaiak();

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
        toolBar = new JToolBar();
        toolBar.setOrientation(JToolBar.VERTICAL);
        toolBar.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));

        toolBar.add(menuBarra.getKonektatu());
        toolBar.add(menuBarra.getDeskonektatu());
        toolBar.add(menuBarra.getKonekzioKonf());
        toolBar.addSeparator();
        toolBar.add(menuBarra.getMapa());
        toolBar.add(menuBarra.getZoomHanditu());
        toolBar.add(menuBarra.getZoomTxikitu());
        toolBar.add(menuBarra.getDataKendu());
        toolBar.add(menuBarra.getDataGehitu());
        toolBar.add(Box.createVerticalGlue());
        if (ErabiltzaileKudeaketa.ERABILTZAILEA.getMota() == ErabiltzaileMota.ADMIN) {
            toolBar.add(menuBarra.getUserKendu());
            toolBar.add(menuBarra.getUserGehitu());
        }
        toolBar.add(menuBarra.getUser());

        toolBar.getComponent(4).setVisible(false);
        toolBar.getComponent(5).setVisible(false);
        toolBar.getComponent(6).setVisible(false);
        toolBar.getComponent(7).setVisible(false);
        toolBar.getComponent(8).setVisible(false);

        return toolBar;
    }

    private void hasieratuAldagaiak() {
        // this.mapaPanela = new MapaPanela(this);
        // this.mapaPanela.addPropertyChangeListener(this);
        deskonektatutaPanela = new DeskonektatutaPanela();
        negutegiInfoPanela = new NegutegiInfoPanela(this);
        negutegiInfoPanela.addPropertyChangeListener(this);
        datuHistorialPanela = new DatuHistorialPanela();
        informazioPanela = new InformazioPanela();

        try {
            mqtt = new MQTT(this);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        negutegi = new ArrayList<>();
        negutegi = NegutegiKudeaketa.jasoNegutegiak(negutegi);
        menuBarra = new MenuBarra(this, mqtt);
        menuBarra.addPropertyChangeListener(this);
        tabPanela = new JTabbedPane();
        tabPanela.addChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propietatea = evt.getPropertyName();

        if (propietatea.equals(MenuBarra.P_KONEKTATU)) {
            infoZabalikDago = false;
            historialZabalikDago = false;
            tabPanela.removeAll();
            mapaPanela = new MapaPanela(this); // hau ez bada egiten filtroa apurtu egiten da
            mapaPanela.addPropertyChangeListener(this); // deskonektatu eta konektatzerakoan
            tabPanela.addTab(" MAPA ", mapaPanela.sortuMapaPanela());
        }
        if (propietatea.equals(MenuBarra.P_DESKONEKTATU)) {
            tabPanela.removeAll();
            tabPanela.addTab(" KONEKTATU ", deskonektatutaPanela.sortuDeskonektatutaPanela());
        }
        if (propietatea.equals(MapaPanela.P_MAPA_NEGUTEGI_INFO)) {
            if (tabPanela.getTabCount() == 1)
                tabPanela.addTab(" NEGUTEGIA ", negutegiInfoPanela.negutegiInfoPZentrala());
        }
        if (propietatea.equals(MapaPanela.P_MAPA_NEGUTEGI_INFO_EXIT)) {
            if (tabPanela.getTabCount() > 1) {
                infoZabalikDago = false;
                historialZabalikDago = false;
                for (int i = 0; i < tabPanela.getTabCount(); i++) {
                    tabPanela.remove(1);
                }
            }
        }
        if (propietatea.equals(MapaKudeatzailea.P_MAPA_NEGUTEGI_INFO_CLICK)) {
            tabPanela.setSelectedIndex(1);
        }
        if (propietatea.equals(NegutegiInfoPanela.P_HISTORIAL_PANELA)) {
            if (!historialZabalikDago) {
                historialZabalikDago = true;
                // tabPanela.addTab(" HISTORIALA ", datuHistorialPanela.historialPanelaSortu());
                tabPanela.insertTab(" HISTORIALA ", null, datuHistorialPanela.historialPanelaSortu(), null, 2);
                tabPanela.setSelectedIndex(2);
            }
        }
        if (propietatea.equals(NegutegiInfoPanela.P_INFORMAZIO_PANELA)) {
            if (!infoZabalikDago) {
                infoZabalikDago = true;
                tabPanela.addTab(" INFORMAZIOA ", informazioPanela.sortuInformazioPanela());
                tabPanela.setSelectedIndex(tabPanela.getTabCount() - 1);
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (tabPanela.getSelectedIndex() != 0 || !mqtt.isKonektatutaDago()) {
            toolBar.getComponent(4).setVisible(false);
            toolBar.getComponent(5).setVisible(false);
            toolBar.getComponent(6).setVisible(false);
        } else {
            toolBar.getComponent(4).setVisible(true);
            toolBar.getComponent(5).setVisible(true);
            toolBar.getComponent(6).setVisible(true);
        }
        if (historialZabalikDago) {
            if (tabPanela.getSelectedIndex() != 2) {
                toolBar.getComponent(7).setVisible(false);
                toolBar.getComponent(8).setVisible(false);
            } else {
                toolBar.getComponent(7).setVisible(true);
                toolBar.getComponent(8).setVisible(true);
            }
        }
    }
}

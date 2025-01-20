package growtech.ui.panelak;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import growtech.mqtt.MQTTDatuak;
import growtech.ui.ItxuraPrintzipala;
import growtech.ui.adaptadore.MapaJLAdaptadorea;
import growtech.ui.adaptadore.negutegiInfoPanelAdaptadore;
import growtech.ui.ktrl.MapaKtrl;
import growtech.ui.modeloak.MapaPanelaKudeatzailea;
import growtech.util.filtro.FiltroSelektore;
import growtech.util.filtro.NegutegiFSFactory;
import growtech.util.negutegiKudeaketa.Negutegia;
import growtech.util.userKudeaketa.ErabiltzaileKudeaketa;
import growtech.util.userKudeaketa.ErabiltzaileMota;
import growtech.util.userKudeaketa.Erabiltzailea;
import lombok.Getter;

@Getter
public class MapaPanela implements PropertyChangeListener {
    private ItxuraPrintzipala itxura;
    private MapaKtrl mapaKtrl;
    private MapaPanelaKudeatzailea mapaKudeatzailea;
    private JList<Negutegia> negutegiJL;
    private JPanel mapaJPanel;
    public static JXMapViewer mapa;
    private GeoPosition hasierakoPosizioa;
    private JButton botoia;
    private List<JComboBox<String>> aukerenZerrenda;
    private Negutegia[] bistaratzekoak;
    private JButton mapsBotoia;
    private JList<Erabiltzailea> erabiltzaileJL;
    private JButton joanBotoia;
    private PropertyChangeSupport aldaketak;
    private JLabel tenperaturaLabel;
    private JLabel hezetasunLabel;
    public final static String P_MAPA_NEGUTEGI_INFO = "NEGUTEGIINFO";
    public final static String P_MAPA_NEGUTEGI_INFO_EXIT = "EXIT";
    public static Negutegia AUKERATUTAKO_NEGUTEGIA;

    public MapaPanela(ItxuraPrintzipala itxura) {
        this.itxura = itxura;
        this.mapaKudeatzailea = new MapaPanelaKudeatzailea(itxura, this);
        this.mapaKudeatzailea.addPropertyChangeListener(this);
        this.mapaKtrl = new MapaKtrl(this, mapaKudeatzailea, itxura);
        aukerenZerrenda = new ArrayList<>();
        aldaketak = new PropertyChangeSupport(itxura);
        hasieratuTenpHezeBalioak();
    }

    public JSplitPane sortuMapaPanela() {
        JSplitPane panela = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                true, sortuEzkerrekoPanela(), sortuEskumakoPanela());

        panela.setDividerLocation(300);
        panela.setDividerSize(10);

        return panela;
    }

    private Component sortuEzkerrekoPanela() {
        JSplitPane panela = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                true, sortuAtzeraFiltroPanelak(), sortuNegutegiJLPanela());

        panela.setDividerLocation(50);
        panela.setDividerSize(0);
        panela.setEnabled(false);

        return panela;
    }

    private Component sortuAtzeraFiltroPanelak() {
        JPanel panela = new JPanel(new BorderLayout());
        JPanel filtroPanela = new JPanel(new GridLayout(1, 2));
        panela.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        botoia = new JButton(new FlatSVGIcon("svg/GeziaAtzera.svg", 25, 25));
        botoia.setActionCommand("Botoia");
        botoia.addActionListener(mapaKtrl);
        botoia.setEnabled(false);

        filtroPanela.add(sortuOpzioPanela(NegutegiFSFactory.getFiltroLurralde("Denak"), "LURRALDEA"));
        filtroPanela.add(sortuOpzioPanela(NegutegiFSFactory.getFiltroHerria("Denak"), "HERRIA"));

        panela.add(botoia, BorderLayout.WEST);
        panela.add(filtroPanela, BorderLayout.CENTER);

        return panela;
    }

    private Component sortuOpzioPanela(FiltroSelektore<Negutegia, String> selektore, String izena) {
        JPanel panela = new JPanel(new BorderLayout());
        JComboBox<String> comboBox = new JComboBox<>(mapaKudeatzailea.jasoAukerak(selektore, izena));
        comboBox.setSelectedIndex(0);
        comboBox.addItemListener(mapaKtrl);
        comboBox.setActionCommand(izena);
        aukerenZerrenda.add(comboBox);
        panela.add(comboBox);
        return panela;
    }

    private Component sortuNegutegiJLPanela() {
        JScrollPane panela = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        negutegiJL = new JList<>();
        negutegiJL.setCellRenderer(new MapaJLAdaptadorea());
        negutegiJL.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        negutegiJL.addListSelectionListener(mapaKtrl);
        negutegiJLAktualizatu();
        panela.setViewportView(negutegiJL);

        return panela;
    }

    public void negutegiJLAktualizatu() {
        bistaratzekoak = sortuBistaratzekoak();
        negutegiJL.setListData(bistaratzekoak);
    }

    public Negutegia[] sortuBistaratzekoak() {
        List<Negutegia> bistaratzekoakList = itxura.getNegutegi();

        for (int i = 0; i < aukerenZerrenda.size(); i++) {
            JComboBox<String> opcion = aukerenZerrenda.get(i);
            String balioa = (String) opcion.getSelectedItem();
            if (!(balioa.equals("LURRALDEA") || balioa.equals("HERRIA"))) {
                switch (i) {
                    case 0:
                        bistaratzekoakList = mapaKudeatzailea.filtratu(NegutegiFSFactory.getFiltroLurralde(balioa));
                        break;
                    case 1:
                        bistaratzekoakList = mapaKudeatzailea.filtratu(NegutegiFSFactory.getFiltroHerria(balioa),
                                bistaratzekoakList);
                        break;
                }
            }
        }
        return (bistaratzekoakList.toArray(new Negutegia[0]));
    }

    private Component sortuEskumakoPanela() {
        mapaJPanel = new JPanel(new BorderLayout());

        mapa = new JXMapViewer();
        OSMTileFactoryInfo mapaMota = new OSMTileFactoryInfo();
        mapa.setTileFactory(new org.jxmapviewer.viewer.DefaultTileFactory(mapaMota));
        org.jxmapviewer.input.PanMouseInputListener saguListener = new org.jxmapviewer.input.PanMouseInputListener(
                mapa);
        mapa.addMouseListener(saguListener);
        mapa.addMouseMotionListener(saguListener);
        mapa.addMouseWheelListener(new org.jxmapviewer.input.ZoomMouseWheelListenerCenter(mapa));
        hasierakoPosizioa = new GeoPosition(43.1000, -2.4500);
        mapa.setZoom(9);
        mapa.setAddressLocation(hasierakoPosizioa);

        waypointakMarraztu();

        mapaJPanel.add(mapa);

        return mapaJPanel;
    }

    public void waypointakMarraztu() {
        GeoPosition posizioa;
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        List<Waypoint> waypoint = new ArrayList<>();
        for (Negutegia negutegi : bistaratzekoak) {
            posizioa = negutegi.getPosizioa();
            waypoint.add(new DefaultWaypoint(posizioa));
        }
        Set<Waypoint> waypointSet = new HashSet<>(waypoint);
        waypointPainter.setWaypoints(waypointSet);

        mapa.setOverlayPainter(waypointPainter);
    }

    public void mapaHanditu(Negutegia negutegia) {
        mapa.setZoom(3);
        mapa.setAddressLocation(negutegia.getPosizioa());
    }

    public void mapaPanelaTxikituta() {
        mapa.setZoom(9);
        mapa.setAddressLocation(hasierakoPosizioa);
    }

    public void mapaPanelaHandituta(Negutegia negutegia) {
        JSplitPane panela = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                true, mapa, sortuNegutegiInformazioaPanela(negutegia));
        panela.setDividerLocation(550);
        panela.setDividerSize(10);
        // panela.setEnabled(false);

        mapaJPanel.removeAll();
        mapaJPanel.revalidate();
        mapaJPanel.repaint();

        mapaJPanel.add(panela);
        mapaHanditu(negutegia);
    }

    private Component sortuNegutegiInformazioaPanela(Negutegia negutegia) {
        JSplitPane panela = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true,
                negutegiInformazioaPanelaEzkerra(negutegia), negutegiInformazioaPanelaEskuma(negutegia));

        panela.setDividerLocation(700);
        panela.setDividerSize(10);

        return panela;
    }

    private Component negutegiInformazioaPanelaEzkerra(Negutegia negutegia) {
        JPanel panela = new JPanel(new GridLayout(1, 2));
        panela.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel ezkerPanela = new JPanel(new GridLayout(3, 1));
        JPanel eskumaPanela = new JPanel(new GridLayout(3, 1));

        JLabel herriaLabel = new JLabel(negutegia.getHerria());
        herriaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        herriaLabel.setForeground(Color.BLACK);
        JLabel lurraldeLabel = new JLabel(negutegia.getLurraldea());
        lurraldeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        lurraldeLabel.setForeground(Color.BLACK);
        JLabel partzelaLabel = new JLabel("Partzela kopurua " + String.valueOf(negutegia.getPartzelaKop()));
        partzelaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        partzelaLabel.setForeground(Color.BLACK);

        ezkerPanela.add(herriaLabel);
        ezkerPanela.add(lurraldeLabel);
        ezkerPanela.add(partzelaLabel);

        // tenperaturaLabel = new JLabel("Tenperatura " + MQTTDatuak.AZKEN_TENPERATURA +
        // " ºC");
        tenperaturaLabel.setFont(new Font("Arial", Font.BOLD, 18));
        tenperaturaLabel.setForeground(Color.BLACK);
        tenperaturaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        // hezetasunLabel = new JLabel("Hezetasuna " + MQTTDatuak.AZKEN_HEZETASUNA + "
        // %");
        hezetasunLabel.setFont(new Font("Arial", Font.BOLD, 18));
        hezetasunLabel.setForeground(Color.BLACK);
        hezetasunLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JPanel biBotoiPanela = new JPanel(new GridLayout(1, 2, 10, 0));
        biBotoiPanela.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        joanBotoia = new JButton(new FlatSVGIcon("svg/termometro.svg", 25, 25));
        joanBotoia.setToolTipText("Negutegia ikusi");
        joanBotoia.setActionCommand("enter");
        joanBotoia.addActionListener(mapaKtrl);
        mapsBotoia = new JButton(new FlatSVGIcon("svg/maps.svg", 40, 40));
        mapsBotoia.setToolTipText("Nola joan ikusi");
        mapsBotoia.setActionCommand("maps");
        mapsBotoia.addActionListener(mapaKtrl);

        biBotoiPanela.add(joanBotoia);
        biBotoiPanela.add(mapsBotoia);

        eskumaPanela.add(tenperaturaLabel);
        eskumaPanela.add(hezetasunLabel);
        eskumaPanela.add(biBotoiPanela);

        panela.add(ezkerPanela);
        panela.add(eskumaPanela);

        return panela;
    }

    private Component negutegiInformazioaPanelaEskuma(Negutegia negutegia) {
        JScrollPane jlPanela = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        erabiltzaileJL = new JList<>();
        erabiltzaileJL.setCellRenderer(new negutegiInfoPanelAdaptadore());
        erabiltzaileJL.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        erabiltzaileJL.addListSelectionListener(mapaKtrl);
        erabiltzaileJL.setListData(sortuBistaratzekoakErabiltzaileak(negutegia));
        jlPanela.setViewportView(erabiltzaileJL);

        return jlPanela;
    }

    // TODO Filtroa hobetu
    private Erabiltzailea[] sortuBistaratzekoakErabiltzaileak(Negutegia negutegia) {
        List<Erabiltzailea> erabiltzaileak = new ArrayList<>();
        ErabiltzaileKudeaketa erabiltzaileKudeaketa = new ErabiltzaileKudeaketa();
        erabiltzaileak = erabiltzaileKudeaketa.erabiltzaileakIrakurri();
        List<Erabiltzailea> bistaratzekoErabiltzaile = new ArrayList<>();

        for (Erabiltzailea erabiltzaile : erabiltzaileak) {
            if (erabiltzaile.getMota() == ErabiltzaileMota.USER) {
                List<Integer> erabiltzaileNegutegi = erabiltzaile.getNegutegiak();
                for (Integer i : erabiltzaileNegutegi) {
                    if (i == negutegia.getId()) {
                        bistaratzekoErabiltzaile.add(erabiltzaile);
                    }
                }
            }
        }

        return (bistaratzekoErabiltzaile.toArray(new Erabiltzailea[0]));
    }

    public void mapaPanelaPred() {
        mapaJPanel.removeAll();
        mapaJPanel.revalidate();
        mapaJPanel.repaint();

        mapaJPanel.add(mapa);
        mapaPanelaTxikituta();
    }

    public void zoomHanditu() {
        mapa.setZoom(mapa.getZoom() - 1);
    }

    public void zoomTxikitu() {
        mapa.setZoom(mapa.getZoom() + 1);
    }

    private void hasieratuTenpHezeBalioak() {
        LocalDate data = LocalDate.now();
        String pathTenp = MQTTDatuak.FITXERO_PATH + data + " Tenperatura.txt";
        String pathHeze = MQTTDatuak.FITXERO_PATH + data + " Hezetasuna.txt";

        tenperaturaLabel = new JLabel("Tenperatura " + String.format("%.1f", MQTTDatuak.AZKEN_TENPERATURA) + " ºC");
        hezetasunLabel = new JLabel("Hezetasuna " + String.format("%.1f", MQTTDatuak.AZKEN_HEZETASUNA) + " %");

        try (BufferedReader br = new BufferedReader(new FileReader(pathTenp))) {
            String datua;
            String azkenDatua = null;
            while ((datua = br.readLine()) != null) {
                azkenDatua = datua;
            }
            if (azkenDatua != null) {
                double balioa = Double.parseDouble(azkenDatua.trim());
                MQTTDatuak.AZKEN_TENPERATURA = Double.parseDouble(azkenDatua);
                tenperaturaLabel.setText("Tenperatura " + String.format("%.1f", balioa) + " ºC");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        try (BufferedReader br = new BufferedReader(new FileReader(pathHeze))) {
            String datua;
            String azkenDatua = null;
            while ((datua = br.readLine()) != null) {
                azkenDatua = datua;
            }
            if (azkenDatua != null) {
                double balioa = Double.parseDouble(azkenDatua.trim());
                MQTTDatuak.AZKEN_HEZETASUNA = Double.parseDouble(azkenDatua);
                hezetasunLabel.setText("Hezetasuna " + String.format("%.1f", balioa) + " %");
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        aldaketak.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        aldaketak.removePropertyChangeListener(listener);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propietatea = evt.getPropertyName();

        if (propietatea.equals(MapaPanelaKudeatzailea.P_MAPA_HANDITUTA)) {
            // lehenengo bi linea hauek negutegi pestaña aktualizatzeko
            mapaPanelaPred();
            aldaketak.firePropertyChange(P_MAPA_NEGUTEGI_INFO_EXIT, -1, null);

            Negutegia negutegia = negutegiJL.getSelectedValue();
            AUKERATUTAKO_NEGUTEGIA = negutegia;
            mapaPanelaHandituta(negutegia);
            aldaketak.firePropertyChange(P_MAPA_NEGUTEGI_INFO, -1, null);
        }
        if (propietatea.equals(MapaPanelaKudeatzailea.P_MAPA_NORMAL)) {
            mapaPanelaPred();
            aldaketak.firePropertyChange(P_MAPA_NEGUTEGI_INFO_EXIT, -1, null);
        }
        if (propietatea.equals(MapaPanelaKudeatzailea.P_TENP_HEZE_AKTUALIZATU)) {
            if (tenperaturaLabel != null && hezetasunLabel != null) {
                tenperaturaLabel.setText("Tenperatura " + String.format("%.1f", MQTTDatuak.AZKEN_TENPERATURA) + " ºC");
                hezetasunLabel.setText("Hezetasuna " + String.format("%.1f", MQTTDatuak.AZKEN_HEZETASUNA) + " %");
            }
        }
    }
}

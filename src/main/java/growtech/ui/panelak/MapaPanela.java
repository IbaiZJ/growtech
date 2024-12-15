package growtech.ui.panelak;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import growtech.ui.ItxuraPrintzipala;
import growtech.ui.Negutegia;
import growtech.ui.ktrl.MapaKtrl;
import growtech.ui.modeloak.MapaKudeatzailea;
import growtech.util.filtro.FiltroSelektore;
import growtech.util.filtro.NegutegiFSFactory;
import lombok.Getter;


public class MapaPanela {
    private ItxuraPrintzipala itxura;
    private MapaKtrl mapaKtrl;
    private MapaKudeatzailea mapaKudeatzailea;
    private @Getter JList<Negutegia> negutegiJL;

    private @Getter JPanel mapaJPanel;
    private @Getter JXMapViewer mapa;
    private @Getter GeoPosition hasierakoPosizioa;
    private @Getter JButton botoia;
    private List<JComboBox<String>> aukerenZerrenda;
    private Negutegia [] bistaratzekoak;

    public MapaPanela(ItxuraPrintzipala itxura) {
        this.itxura = itxura;
        this.mapaKudeatzailea = new MapaKudeatzailea(this, itxura);
        this.mapaKtrl = new MapaKtrl(this, mapaKudeatzailea);
        aukerenZerrenda = new ArrayList<>();
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
        // negutegiJL.setCellRenderer(new MapaJLAdaptadorea());
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
			if (!(balioa.equals("LURRALDEA")  || balioa.equals("HERRIA"))) {
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
        // TileFactoryInfo tileFactoryInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        // TileFactoryInfo tileFactoryInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
        // TileFactoryInfo tileFactoryInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
        // mapa.setTileFactory(new org.jxmapviewer.viewer.DefaultTileFactory(tileFactoryInfo));
        mapa.setTileFactory(new org.jxmapviewer.viewer.DefaultTileFactory(mapaMota));
        org.jxmapviewer.input.PanMouseInputListener saguListener = new org.jxmapviewer.input.PanMouseInputListener(mapa);
        mapa.addMouseListener(saguListener);
        mapa.addMouseMotionListener(saguListener);
        mapa.addMouseWheelListener(new org.jxmapviewer.input.ZoomMouseWheelListenerCenter(mapa));
        hasierakoPosizioa = new GeoPosition(43.1000, -2.4500);
        mapa.setZoom(9); // hasierako zoom = 9
        mapa.setAddressLocation(hasierakoPosizioa);

        waypointakMarraztu();
        
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
        mapaJPanel.add(mapa);
    }
    
}




/*
private Component sortuEskumakoPanela() {
    JLayeredPane layeredPane = new JLayeredPane();
    // layeredPane.setPreferredSize(new Dimension(200,200));
    String[] opciones = {"Opción 1", "Opción 2", "Opción 3"};
    JComboBox<String> comboBox = new JComboBox<>(opciones);
    comboBox.setBounds(10, 10, 150, 30);
    mapaJPanel = new JPanel(new BorderLayout());
    mapaJPanel.setBounds(0, 0, 800, 600);
    
    mapa = new JXMapViewer();
    OSMTileFactoryInfo mapaMota = new OSMTileFactoryInfo();
    // TileFactoryInfo tileFactoryInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
    // TileFactoryInfo tileFactoryInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
    // TileFactoryInfo tileFactoryInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
    // mapa.setTileFactory(new org.jxmapviewer.viewer.DefaultTileFactory(tileFactoryInfo));
    mapa.setTileFactory(new org.jxmapviewer.viewer.DefaultTileFactory(mapaMota));
    org.jxmapviewer.input.PanMouseInputListener saguListener = new org.jxmapviewer.input.PanMouseInputListener(mapa);
    mapa.addMouseListener(saguListener);
    mapa.addMouseMotionListener(saguListener);
    mapa.addMouseWheelListener(new org.jxmapviewer.input.ZoomMouseWheelListenerCenter(mapa));
    hasierakoPosizioa = new GeoPosition(43.1000, -2.4500);
    mapa.setZoom(9); // hasierako zoom = 9
    mapa.setAddressLocation(hasierakoPosizioa);

    waypointakMarraztu();

    layeredPane.add(mapaJPanel, Integer.valueOf(0)); // Fondo (nivel inferior)
    layeredPane.add(comboBox, Integer.valueOf(1));
    
    return layeredPane;
}*/
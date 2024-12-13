package growtech.ui.panelak;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import growtech.ui.ItxuraPrintzipala;
import growtech.ui.Negutegia;
import growtech.ui.ktrl.MapaKtrl;
import growtech.ui.kudeatzaileak.MapaKudeatzailea;
import lombok.Getter;
import lombok.Setter;


public class MapaPanela {
    private ItxuraPrintzipala itxura;
    private MapaKtrl mapaKtrl;
    private MapaKudeatzailea mapaKudeatzailea;
    private @Getter JList<Negutegia> negutegiJL;

    private @Getter @Setter JPanel mapaJPanel;
    private @Getter @Setter JXMapViewer mapa;
    private @Getter GeoPosition hasierakoPosizioa;
    
    public MapaPanela(ItxuraPrintzipala itxura) {
        this.itxura = itxura;
        this.mapaKudeatzailea = new MapaKudeatzailea(this);
        this.mapaKtrl = new MapaKtrl(this, mapaKudeatzailea);
        sortuMapaPanela();
    }

    public JSplitPane sortuMapaPanela() {
        JSplitPane panela = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
        true, sortuEzkerrekoPanela(), sortuEskumakoPanela());

        panela.setDividerLocation(300);
        panela.setDividerSize(10);

        return panela;
    }

    private Component sortuEzkerrekoPanela() {
        JScrollPane panela = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        negutegiJL = new JList<>();
        // negutegiJL.setCellRenderer(new MapaJLAdaptadorea());
        negutegiJL.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        negutegiJL.addListSelectionListener(mapaKtrl);
        negutegiJL.setListData(itxura.getNegutegi().toArray(new Negutegia[0]));
        panela.setViewportView(negutegiJL);

        return panela;
    }

    private Component sortuEskumakoPanela() {
        mapaJPanel = new JPanel(new BorderLayout());
        
        mapa = new JXMapViewer();
        OSMTileFactoryInfo mapaMota = new OSMTileFactoryInfo();
        // TileFactoryInfo tileFactoryInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        // TileFactoryInfo tileFactoryInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
        // TileFactoryInfo tileFactoryInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
        mapa.setTileFactory(new org.jxmapviewer.viewer.DefaultTileFactory(mapaMota));
        org.jxmapviewer.input.PanMouseInputListener saguListener = new org.jxmapviewer.input.PanMouseInputListener(mapa);
        mapa.addMouseListener(saguListener);
        mapa.addMouseMotionListener(saguListener);
        mapa.addMouseWheelListener(new org.jxmapviewer.input.ZoomMouseWheelListenerCenter(mapa));
        hasierakoPosizioa = new GeoPosition(43.1000, -2.4500);
        mapa.setZoom(9); // hasierako zoom = 9
        mapa.setAddressLocation(hasierakoPosizioa);

        // Puntuak
        GeoPosition posizioa;
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        List<Waypoint> waypoint = new ArrayList<>();
        for (Negutegia negutegi : itxura.getNegutegi()) {
            posizioa = negutegi.getPosizioa();
            waypoint.add(new DefaultWaypoint(posizioa));
        }
        Set<Waypoint> waypointSet = new HashSet<>(waypoint);
        waypointPainter.setWaypoints(waypointSet);

        mapa.setOverlayPainter(waypointPainter);
        mapaJPanel.add(mapa);
        
        return mapaJPanel;
    }
    
}

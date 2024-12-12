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


public class MapaPanela {
    ItxuraPrintzipala itxura;
    JList<Negutegia> negutegiJL;
    
    public MapaPanela(ItxuraPrintzipala itxura) {
        this.itxura = itxura;
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
        // negutegiJL.addListSelectionListener(this);
        negutegiJL.setListData(itxura.getNegutegi().toArray(new Negutegia[0]));
        panela.setViewportView(negutegiJL);

        return panela;
    }

    private Component sortuEskumakoPanela() {
        JPanel panela = new JPanel(new BorderLayout());
        
        JXMapViewer mapViewer = new JXMapViewer();
        OSMTileFactoryInfo tileFactoryInfo = new OSMTileFactoryInfo();
        // TileFactoryInfo tileFactoryInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
        // TileFactoryInfo tileFactoryInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
        // TileFactoryInfo tileFactoryInfo = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.SATELLITE);
        mapViewer.setTileFactory(new org.jxmapviewer.viewer.DefaultTileFactory(tileFactoryInfo));
        org.jxmapviewer.input.PanMouseInputListener panMouseListener = new org.jxmapviewer.input.PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(panMouseListener);
        mapViewer.addMouseMotionListener(panMouseListener);
        mapViewer.addMouseWheelListener(new org.jxmapviewer.input.ZoomMouseWheelListenerCenter(mapViewer));
        GeoPosition startPosition = new GeoPosition(43.1000, -2.4500);
        mapViewer.setZoom(9);
        mapViewer.setAddressLocation(startPosition);

        // Puntuak
        GeoPosition position;
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        List<Waypoint> waypoint = new ArrayList<>();
        for (Negutegia negutegi : itxura.getNegutegi()) {
            position = negutegi.getPosizioa();
            waypoint.add(new DefaultWaypoint(position));
        }
        Set<Waypoint> waypointSet = new HashSet<>(waypoint);
        waypointPainter.setWaypoints(waypointSet);

        mapViewer.setOverlayPainter(waypointPainter);
        panela.add(mapViewer);
        
        return panela;
    }
    
}

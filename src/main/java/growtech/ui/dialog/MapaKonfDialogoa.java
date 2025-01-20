package growtech.ui.dialog;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.VirtualEarthTileFactoryInfo;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

import growtech.ui.ItxuraPrintzipala;
import growtech.ui.panelak.MapaPanela;

public class MapaKonfDialogoa extends JDialog implements ActionListener, ItemListener {
    private ItxuraPrintzipala itxuraPrintzipala;
    private JXMapViewer mapa;
    private GeoPosition hasierakoPosizioa;
    private JComboBox<String> mapaAukeraketa;
    private static String mapaMotaIzena = "OSM";

    public MapaKonfDialogoa(ItxuraPrintzipala itxuraPrintzipala) {
        super(itxuraPrintzipala, "Mapa Konfigurazioa", true);
        this.setSize(350, 450);
        ImageIcon icon = new ImageIcon(getClass().getResource("/img/GrowTech.png"));
        this.setIconImage(icon.getImage());
        this.setLocationRelativeTo(null);
        this.setContentPane(sortuDialogoPanela());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.itxuraPrintzipala = itxuraPrintzipala;
    }

    private Container sortuDialogoPanela() {
        JPanel panela = new JPanel(new BorderLayout());
        panela.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel barnePanela = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // 1/3 okupatzeko
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        barnePanela.add(aukeraketaPanela(), gbc);

        // 2/3 okupatzeko
        gbc.gridy = 1;
        gbc.weighty = 3;
        barnePanela.add(mapaPreikusi(), gbc);

        JPanel botoiPanela = new JPanel();
        JButton gordeBotoia = new JButton("Gorde");
        gordeBotoia.addActionListener(this);
        botoiPanela.add(gordeBotoia);

        panela.add(barnePanela, BorderLayout.CENTER);
        panela.add(botoiPanela, BorderLayout.SOUTH);

        return panela;
    }

    private Component aukeraketaPanela() {
        JPanel panela = new JPanel(new GridLayout(2, 1));

        JLabel mapaMota = new JLabel("Mapa Mota");
        String[] mapaMotaIzenak = { "OSM", "Virtual Earth", "Virtual Earth Hybrid", "Virtual Earth Satellite" };
        mapaAukeraketa = new JComboBox<>(mapaMotaIzenak);
        mapaAukeraketa.addItemListener(this);

        mapaMota.setFont(new Font("Arial", Font.BOLD, 30));
        mapaMota.setHorizontalAlignment(SwingConstants.CENTER);

        panela.add(mapaMota);
        panela.add(mapaAukeraketa);

        return panela;
    }

    private Component mapaPreikusi() {
        JPanel panela = new JPanel(new BorderLayout());

        mapa = new JXMapViewer();
        OSMTileFactoryInfo mapaMota = new OSMTileFactoryInfo();
        mapa.setTileFactory(new org.jxmapviewer.viewer.DefaultTileFactory(mapaMota));
        org.jxmapviewer.input.PanMouseInputListener saguListener = new org.jxmapviewer.input.PanMouseInputListener(
                mapa);
        mapa.addMouseListener(saguListener);
        mapa.addMouseMotionListener(saguListener);
        mapa.addMouseWheelListener(new org.jxmapviewer.input.ZoomMouseWheelListenerCenter(mapa));
        hasierakoPosizioa = MapaPanela.mapa.getCenterPosition();
        mapa.setZoom(MapaPanela.mapa.getZoom());
        mapa.setAddressLocation(hasierakoPosizioa);

        panela.add(mapa);

        return panela;
    }

    private TileFactoryInfo tileFactoryJaso() {
        TileFactoryInfo mapaMota;
        switch ((String) mapaAukeraketa.getSelectedItem()) {
            case "OSM":
                mapaMota = new OSMTileFactoryInfo();
                break;
            case "Virtual Earth":
                mapaMota = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.MAP);
                break;
            case "Virtual Earth Hybrid":
                mapaMota = new VirtualEarthTileFactoryInfo(VirtualEarthTileFactoryInfo.HYBRID);
                break;
            case "Virtual Earth Satellite":
                mapaMota = new VirtualEarthTileFactoryInfo(
                        VirtualEarthTileFactoryInfo.SATELLITE);
                break;
            default:
                mapaMota = new OSMTileFactoryInfo();
                break;
        }
        return mapaMota;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (MapaPanela.mapa != null) {
            if (mapaAukeraketa.getSelectedItem().equals(mapaMotaIzena)) {
                this.dispose();
            } else { // Erroreak ez emateko zoom-a eta posizioa berriz jarri
                int oraingoZoom = MapaPanela.mapa.getZoom();
                GeoPosition oraingoPos = MapaPanela.mapa.getAddressLocation();
                MapaPanela.mapa.setTileFactory(new org.jxmapviewer.viewer.DefaultTileFactory(tileFactoryJaso()));
                MapaPanela.mapa.setZoom(oraingoZoom);
                MapaPanela.mapa.setAddressLocation(oraingoPos);
                mapaMotaIzena = (String) mapaAukeraketa.getSelectedItem();
                this.dispose();
            }
        } else {
            JOptionPane.showMessageDialog(
                    itxuraPrintzipala,
                    "Errore bat egon da\nmapa mota aldatzerakoan",
                    "Errorea",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        // Erroreak ez emateko zoom-a eta posizioa berriz jarri
        int oraingoZoom = mapa.getZoom();
        GeoPosition oraingoPos = mapa.getAddressLocation();
        mapa.setTileFactory(new org.jxmapviewer.viewer.DefaultTileFactory(tileFactoryJaso()));
        mapa.setZoom(oraingoZoom);
        mapa.setAddressLocation(oraingoPos);
    }

}

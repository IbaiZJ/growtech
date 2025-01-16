package growtech.ui.panelak;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import growtech.mqtt.MQTTDatuak;
import growtech.ui.ItxuraPrintzipala;
import growtech.ui.ktrl.NegutegiInfoKtrl;
import growtech.ui.modeloak.NegutegiInfoKudeatzailea;
import growtech.util.Grafikoa;

public class NegutegiInfoPanela implements PropertyChangeListener {
    private NegutegiInfoKtrl negutegiInfoKtrl;
    private NegutegiInfoKudeatzailea negutegiInfoKudeatzailea;

    private JLabel tenperatura;
    private JLabel hezetasuna;

    private JButton onOffBotoia;
    private JSlider haizagailuaSlider;
    private JLabel haizagailuaSVG;

    public NegutegiInfoPanela(ItxuraPrintzipala itxuraPrintzipala) {
        negutegiInfoKudeatzailea = new NegutegiInfoKudeatzailea(this, itxuraPrintzipala);
        negutegiInfoKudeatzailea.addPropertyChangeListener(this);
        negutegiInfoKtrl = new NegutegiInfoKtrl(negutegiInfoKudeatzailea);
        hasieratuTenpHezeBalioak();
    }

    public Component negutegiInfoPZentrala() {
        JSplitPane panela = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                true, sortuEzkerrekoPanela(), sortuEskumakoPanela());

        panela.setDividerLocation(500);
        panela.setDividerSize(10);

        return panela;
    }

    private Component sortuEzkerrekoPanela() {
        JSplitPane panela = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                true, sortuInfoTenperaturaHezetasunPanela(), sortuHaizagailuaPanela());

        panela.setDividerLocation(500);
        panela.setDividerSize(10);

        return panela;
    }

    private Component sortuInfoTenperaturaHezetasunPanela() {
        JSplitPane panela = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                true, sortuInfoPanela(), sortuTenperaturaHezetasunPanela());

        panela.setDividerLocation(120);
        panela.setDividerSize(10);

        return panela;
    }

    private Component sortuInfoPanela() {
        JPanel panela = new JPanel(new GridLayout(3, 1));
        JLabel herria = new JLabel("Herria: " + MapaPanela.AUKERATUTAKO_NEGUTEGIA.getHerria());
        JLabel lurraldea = new JLabel("Lurraldea: " + MapaPanela.AUKERATUTAKO_NEGUTEGIA.getLurraldea());
        JLabel partzelaKop = new JLabel(
                "Partzela kopurua: " + String.valueOf(MapaPanela.AUKERATUTAKO_NEGUTEGIA.getPartzelaKop()));

        herria.setFont(new Font("Arial", Font.BOLD, 18));
        herria.setForeground(Color.BLACK);
        herria.setHorizontalAlignment(SwingConstants.CENTER);
        lurraldea.setFont(new Font("Arial", Font.BOLD, 18));
        lurraldea.setForeground(Color.BLACK);
        lurraldea.setHorizontalAlignment(SwingConstants.CENTER);
        partzelaKop.setFont(new Font("Arial", Font.BOLD, 18));
        partzelaKop.setForeground(Color.BLACK);
        partzelaKop.setHorizontalAlignment(SwingConstants.CENTER);

        panela.add(herria);
        panela.add(lurraldea);
        panela.add(partzelaKop);

        return panela;
    }

    private Component sortuTenperaturaHezetasunPanela() {
        JPanel panela = new JPanel(new GridLayout(1, 2, 5, 5));
        panela.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JPanel ezkerPanela = new JPanel(new GridLayout(4, 1));
        JPanel eskumaPanela = new JPanel(new GridLayout(4, 1));

        // tenperatura = new JLabel(String.valueOf(MQTTDatuak.AZKEN_TENPERATURA) + "ºC");
        tenperatura.setFont(new Font("Arial", Font.BOLD, 50));
        tenperatura.setForeground(Color.BLACK);
        tenperatura.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel tenperaturaTestu = new JLabel("Tenperatura");
        tenperaturaTestu.setFont(new Font("Arial", Font.BOLD, 30));
        tenperaturaTestu.setForeground(Color.BLACK);
        tenperaturaTestu.setHorizontalAlignment(SwingConstants.CENTER);
        JSlider tenperaturaSlider = new JSlider();
        JLabel tenperaturaIrudi = new JLabel(new FlatSVGIcon("svg/temperature.svg", 100, 100));
        ezkerPanela.add(tenperaturaTestu);
        ezkerPanela.add(tenperatura);
        ezkerPanela.add(tenperaturaIrudi);
        ezkerPanela.add(tenperaturaSlider);

        // hezetasuna = new JLabel(String.valueOf(MQTTDatuak.AZKEN_HEZETASUNA) + "%");
        hezetasuna.setFont(new Font("Arial", Font.BOLD, 50));
        hezetasuna.setForeground(Color.BLACK);
        hezetasuna.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel hezetasunaTestu = new JLabel("Hezetasuna");
        hezetasunaTestu.setFont(new Font("Arial", Font.BOLD, 30));
        hezetasunaTestu.setForeground(Color.BLACK);
        hezetasunaTestu.setHorizontalAlignment(SwingConstants.CENTER);
        JSlider hezetasunSlider = new JSlider();
        JLabel hezetasunIrudi = new JLabel(new FlatSVGIcon("svg/humidity.svg", 100, 100));
        eskumaPanela.add(hezetasunaTestu);
        eskumaPanela.add(hezetasuna);
        eskumaPanela.add(hezetasunIrudi);
        eskumaPanela.add(hezetasunSlider);

        // JXDatePicker kaixo = new JXDatePicker();

        panela.add(ezkerPanela);
        panela.add(eskumaPanela);

        return panela;
    }

    private Component sortuHaizagailuaPanela() {
        JPanel panela = new JPanel(new GridLayout());
        JPanel ezkerPanela = new JPanel(new GridLayout(2, 1));
        JPanel eskumaPanela = new JPanel(new GridLayout(2, 1, 20, 20));
        JPanel eskumaPanelaInformazioaHistoriala = new JPanel(new GridLayout(1, 2, 20, 20));

        haizagailuaSVG = new JLabel(new FlatSVGIcon("svg/ventilatorOn.svg", 100, 100));
        haizagailuaSlider = new JSlider();

        ezkerPanela.add(haizagailuaSVG);
        ezkerPanela.add(haizagailuaSlider);

        onOffBotoia = new JButton(new FlatSVGIcon("svg/on.svg", 40, 40));
        onOffBotoia.addActionListener(negutegiInfoKtrl);
        onOffBotoia.setActionCommand("onOffBotoia");
        JButton historialaBotoia = new JButton(new FlatSVGIcon("svg/Historiala.svg", 40, 40)/* + "Historiala" */);
        historialaBotoia.setToolTipText("Historiala ikusi");
        historialaBotoia.addActionListener(negutegiInfoKtrl);
        historialaBotoia.setActionCommand("historialaBotoia");
        JButton informazioBotoia = new JButton(new FlatSVGIcon("svg/info.svg", 40, 40));
        informazioBotoia.setToolTipText("Informazioa");
        informazioBotoia.addActionListener(negutegiInfoKtrl);
        informazioBotoia.setActionCommand("informazioBotoia");

        eskumaPanela.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 20));
        eskumaPanela.add(onOffBotoia);
        eskumaPanelaInformazioaHistoriala.add(historialaBotoia);
        eskumaPanelaInformazioaHistoriala.add(informazioBotoia);
        eskumaPanela.add(eskumaPanelaInformazioaHistoriala);

        panela.add(ezkerPanela);
        panela.add(eskumaPanela);

        return panela;
    }

    private Component sortuEskumakoPanela() {
        JPanel panela = new JPanel(new BorderLayout());
        panela.add(Grafikoa.sortuTenpHezGrafikoa());
        return panela;
    }

    private void hasieratuTenpHezeBalioak() {
        LocalDate data = LocalDate.now();
        String pathTenp = MQTTDatuak.FITXERO_PATH + data + " Tenperatura.txt";
        String pathHeze = MQTTDatuak.FITXERO_PATH + data + " Hezetasuna.txt";

        tenperatura = new JLabel(String.valueOf(MQTTDatuak.AZKEN_TENPERATURA) + "ºC");
        hezetasuna = new JLabel(String.valueOf(MQTTDatuak.AZKEN_HEZETASUNA) + "%");

        try (BufferedReader br = new BufferedReader(new FileReader(pathTenp))) {
            String datua;
            String azkenDatua = null;
            while ((datua = br.readLine()) != null) {
                azkenDatua = datua;
            }
            if (azkenDatua != null) {
                double balioa = Double.parseDouble(azkenDatua.trim());
                MQTTDatuak.AZKEN_TENPERATURA = Double.parseDouble(azkenDatua);
                tenperatura.setText(String.format(Locale.FRANCE, "%.1fºC", balioa));
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
                hezetasuna.setText(String.format(Locale.FRANCE, "%.1f%%", balioa));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propietatea = evt.getPropertyName();

        if (propietatea.equals(NegutegiInfoKudeatzailea.P_ONOFF_BOTOIA)) {
            boolean newValue = (boolean) evt.getNewValue();
            haizagailuaSlider.setEnabled(newValue);
            if (newValue)
                haizagailuaSVG.setIcon(new FlatSVGIcon("svg/ventilatorOn.svg", 100, 100));
            else
                haizagailuaSVG.setIcon(new FlatSVGIcon("svg/ventilatorOff.svg", 100, 100));
            // haizagailuaSVG.revalidate();
            // haizagailuaSVG.repaint();
        }
        if (propietatea.equals(NegutegiInfoKudeatzailea.P_TENP_HEZE_AKTUALIZATU)) {
            tenperatura.setText(String.format(Locale.FRANCE, "%.1fºC", MQTTDatuak.AZKEN_TENPERATURA));
            hezetasuna.setText(String.format(Locale.FRANCE, "%.1f%%", MQTTDatuak.AZKEN_HEZETASUNA));
        }
    }
}

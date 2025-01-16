package growtech.ui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import growtech.mqtt.MQTT;
import growtech.mqtt.dialog.KonektatuDialogoa;
import growtech.ui.dialog.AboutDialogoa;
import growtech.ui.theme.TemaKudeatzailea;
import lombok.Getter;

public class MenuBarra {
    PropertyChangeSupport aldaketak;
    private @Getter NireAkzioa konektatu, deskonektatu, konekzioKonf;
    private NireAkzioa irten;
    private NireAkzioa argia, iluna, koloreGlobala;
    private NireAkzioa about;
    private @Getter NireAkzioa user;
    private ItxuraPrintzipala itxura;
    private MQTT mqtt;

    private JCheckBoxMenuItem argi;
    private JCheckBoxMenuItem ilun;

    public final static String P_KONEKTATU = "KONEKTATU";
    public final static String P_DESKONEKTATU = "DESKONEKTATU";

    public MenuBarra(ItxuraPrintzipala itxura, MQTT mqtt) {
        aldaketak = new PropertyChangeSupport(itxura);
        hasieratuAkzioak();
        argi = new JCheckBoxMenuItem();
        ilun = new JCheckBoxMenuItem();
        this.itxura = itxura;
        this.mqtt = mqtt;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        aldaketak.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        aldaketak.removePropertyChangeListener(listener);
    }

    private void hasieratuAkzioak() {
        konektatu = new NireAkzioa("Konektatu", new FlatSVGIcon("svg/Konektatu.svg", 40, 40), "Konektatu",
                KeyEvent.VK_1);
        deskonektatu = new NireAkzioa("Deskonektatu", new FlatSVGIcon("svg/Deskonektatu.svg", 40, 40), "Deskonektatu",
                KeyEvent.VK_2);
        konekzioKonf = new NireAkzioa("Konekzio Konfigurazioa", new FlatSVGIcon("svg/KonekzioKonf.svg", 40, 40),
                "Konekzio Konfigurazioa", KeyEvent.VK_3);
        irten = new NireAkzioa("Irten", null, null, KeyEvent.VK_2);
        deskonektatu.setEnabled(false);

        argia = new NireAkzioa("Argia", null, null, KeyEvent.VK_1);
        iluna = new NireAkzioa("Iluna", null, null, KeyEvent.VK_2);
        koloreGlobala = new NireAkzioa("Kolore Globala", null, null, KeyEvent.VK_3);

        about = new NireAkzioa("About", null, null, KeyEvent.VK_1);

        user = new NireAkzioa("User", new FlatSVGIcon("svg/User.svg", 40, 40), "Usuarioa", KeyEvent.VK_9);
    }

    public JMenuBar sortuMenuBar() {
        JMenuBar barra = new JMenuBar();

        barra.add(sortuArtxiboMenua());
        barra.add(sortuItxuraMenua());
        barra.add(sortuLaguntzaMenua());
        barra.add(Box.createHorizontalGlue());

        return barra;
    }

    public JMenu sortuArtxiboMenua() {
        JMenu artxiboMenua = new JMenu("Artxibo");

        artxiboMenua.setMnemonic(KeyEvent.VK_F1);
        artxiboMenua.add(sortuKonekzioMenua());
        // artxiboMenua.add(user);
        artxiboMenua.addSeparator();
        artxiboMenua.add(irten);

        return artxiboMenua;
    }

    public JMenu sortuKonekzioMenua() {
        JMenu konekzioMenua = new JMenu("Konekzio");

        konekzioMenua.setMnemonic(KeyEvent.VK_F2);
        konekzioMenua.add(konektatu);
        konekzioMenua.add(deskonektatu);
        konekzioMenua.add(konekzioKonf);

        return konekzioMenua;
    }

    public JMenu sortuItxuraMenua() {
        JMenu itxuraMenua = new JMenu("Itxura");

        itxuraMenua.setMnemonic(KeyEvent.VK_F2);
        argi.setAction(argia);
        argi.setSelected(true);
        itxuraMenua.add(argi);
        ilun.setAction(iluna);
        itxuraMenua.add(ilun);
        itxuraMenua.addSeparator();
        itxuraMenua.add(koloreGlobala);

        return itxuraMenua;
    }

    public JMenu sortuLaguntzaMenua() {
        JMenu laguntzaMenua = new JMenu("Laguntza");

        // laguntzaMenua.addSeparator();
        laguntzaMenua.setMnemonic(KeyEvent.VK_F3);
        laguntzaMenua.add(about);

        return laguntzaMenua;
    }

    public void konektatuAkzioaEman() {
        new KonektatuDialogoa(itxura, mqtt);
        konektatu.setEnabled(false);
        deskonektatu.setEnabled(true);

        aldaketak.firePropertyChange(P_KONEKTATU, -1, "konektatu");
    }

    public void deskonektatuAkzioaEman() {
        try {
            mqtt.disconnect();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(itxura, "Errore bat egon da\ndeskonektatzerakoan",
                    "Errorea", JOptionPane.ERROR_MESSAGE);
        }
        konektatu.setEnabled(true);
        deskonektatu.setEnabled(false);

        aldaketak.firePropertyChange(P_DESKONEKTATU, -1, "deskonektatu");
    }

    private class NireAkzioa extends AbstractAction {
        String testua;

        public NireAkzioa(String texto, Icon imagen, String descrip, Integer nemonic) {
            super(texto, imagen);

            this.testua = texto;
            this.putValue(Action.SHORT_DESCRIPTION, descrip);
            this.putValue(Action.MNEMONIC_KEY, nemonic);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (testua.equals("Konektatu")) {
                konektatuAkzioaEman();
            }
            if (testua.equals("Deskonektatu")) {
                deskonektatuAkzioaEman();
            }
            if (testua.equals("Konekzio Konfigurazioa")) {
                /*try {
                    mqtt.publish(MQTT.TOPIC_MOTOREA, "15");
                } catch (MqttException e1) {
                    e1.printStackTrace();
                }*/
            }
            if (testua.equals("Irten")) {
                try {
                    if (mqtt.isKonektatutaDago())
                        mqtt.disconnect();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                itxura.dispose();
            }

            if (testua.equals("Argia")) {
                TemaKudeatzailea.ezarriArgiaTema();
                SwingUtilities.updateComponentTreeUI(itxura);
                argi.setSelected(true);
                ilun.setSelected(false);
            }
            if (testua.equals("Iluna")) {
                TemaKudeatzailea.ezarriIlunaTema();
                SwingUtilities.updateComponentTreeUI(itxura);
                argi.setSelected(false);
                ilun.setSelected(true);
            }
            if (testua.equals("Kolore Globala")) {
                Color kolore = JColorChooser.showDialog(itxura, "Aukeratu Kolorea", null);
                TemaKudeatzailea.aktualizatuKoloreGlobala("#" + Integer.toHexString(kolore.getRGB()).substring(2));
            }

            if (testua.equals("About")) {
                new AboutDialogoa(itxura);
            }

            if (testua.equals("User")) {
                JOptionPane.showMessageDialog(
                        itxura,
                        "Izena Ur\nAbizena Berraondo\nErabiltzailea UrBerraondo",
                        "Erabiltzailea",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

}

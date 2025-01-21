package growtech.ui.modeloak;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import org.eclipse.paho.client.mqttv3.MqttException;

import growtech.mqtt.MQTT;
import growtech.ui.panelak.NegutegiInfoPanela;

public class NegutegiInfoKudeatzailea {
    private static PropertyChangeSupport aldaketak;
    public final static String P_ONOFF_BOTOIA = "PONOFFBOTOIA";
    public final static String P_TENP_HEZE_AKTUALIZATU = "PTENPHEZEAKTUALIZATU";
    public final static String P_HISTORIAL_PANELA = "P_HISTORIAL_PANELA";
    public final static String P_INFORMAZIO_PANELA = "P_INFORMAZIO_PANELA";
    private static boolean botoiBalorea = true;

    public NegutegiInfoKudeatzailea(NegutegiInfoPanela negutegiInfoPanela) {
        aldaketak = new PropertyChangeSupport(negutegiInfoPanela);
    }

    public void onOffBotoiaExekutatu() {
        aldaketak.firePropertyChange(P_ONOFF_BOTOIA, null, botoiBalorea);
        if (botoiBalorea)
            botoiBalorea = false;
        else
            botoiBalorea = true;
    }

    public static void negutegiTenpHezeAktualizatu() {
        if (aldaketak != null)
            aldaketak.firePropertyChange(P_TENP_HEZE_AKTUALIZATU, null, null);
    }

    public void haizagailuSliderKudeatu(int balioa) {
        // System.out.println(balioa);
        // balioa 0 - 100
        try {
            MQTT.publish(MQTT.TOPIC_MOTOREA, String.valueOf(balioa));
        } catch (MqttException e) {
            System.err.println("Errorea Motore datua bidaltzerakoan");
            e.printStackTrace();
        }
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        aldaketak.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        aldaketak.removePropertyChangeListener(listener);
    }

    public void sortuHistorialTab() {
        aldaketak.firePropertyChange(P_HISTORIAL_PANELA, null, null);
    }

    public void sortuInformazioTab() {
        aldaketak.firePropertyChange(P_INFORMAZIO_PANELA, null, null);
    }

}

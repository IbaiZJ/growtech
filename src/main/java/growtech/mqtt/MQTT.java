package growtech.mqtt;

import java.time.LocalDate;
import javax.swing.JOptionPane;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import growtech.ui.ItxuraPrintzipala;
import growtech.ui.modeloak.MapaKudeatzailea;
import growtech.ui.modeloak.NegutegiInfoKudeatzailea;
import growtech.util.Grafikoa;
import lombok.Getter;

public class MQTT implements MqttCallback {
    public static String BROKER = "tcp://localhost:1883"; // localhost 192.168.1.100
    public static final String CLENT_ID = "GrowTech";
    public static int QoS = 2;

    public static final String TOPIC_TENPERATURA = "Tenperatura";
    public static final String TOPIC_HEZETASUNA = "Hezetasuna";
    public static final String TOPIC_MOTOREA = "Motorea";
    private static MqttClient client;
    private MqttConnectOptions connOpts;

    private volatile double valueTemperature;
    private volatile double valueHezetasuna;
    private ItxuraPrintzipala itxuraPrintzipala;

    private @Getter boolean konektatutaDago;

    public MQTT(String broker, String clientId) throws MqttException {
        this.valueTemperature = 0.0;
        this.valueHezetasuna = 0.0;
        konektatutaDago = false;

        MemoryPersistence persistence = new MemoryPersistence();

        MQTT.client = new MqttClient(broker, clientId, persistence);
        connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        MQTT.client.setCallback(this);
    }

    public MQTT(ItxuraPrintzipala itxuraPrintzipala) throws MqttException {
        this(BROKER, CLENT_ID);
        this.itxuraPrintzipala = itxuraPrintzipala;
    }

    public void klienteraKonektatu() throws MqttSecurityException, MqttException {
        System.out.println("[MQTT] Connecting to broker: " + BROKER);
        MQTT.client.connect(connOpts);
        System.out.println("[MQTT] Connected");
    }

    public void klienteraSubskribatu() throws MqttException {
        System.out.println("[MQTT] Subscribe " + TOPIC_TENPERATURA);
        client.subscribe(TOPIC_TENPERATURA, QoS);
        System.out.println("[MQTT] Subscribe " + TOPIC_HEZETASUNA);
        client.subscribe(TOPIC_HEZETASUNA, QoS);
        System.out.println("[MQTT] Ready");
        konektatutaDago = true;
    }

    public void disconnect() throws MqttException {
        MQTT.client.disconnect();
        konektatutaDago = false;
        System.out.println("[MQTT] Disconnecting");
    }

    public static void publish(String topic, String content) throws MqttException {
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(QoS);
        client.publish(topic, message);
    }

    public double getTemperature() {
        return this.valueTemperature;
    }

    public double getHezetasuna() {
        return this.valueHezetasuna;
    }

    @Override
    public void connectionLost(Throwable cause) {
        System.err.println("Connection to MQTT broker lost!" + cause);
        itxuraPrintzipala.getMenuBarra().deskonektatuAkzioaEman();
        konektatutaDago = false;
        JOptionPane.showMessageDialog(itxuraPrintzipala, "MQTT zerbitzua jausi egin da.", "MQTT Errorea",
                JOptionPane.ERROR_MESSAGE);
        // itxuraPrintzipala.getMenuBarra().konektatuAkzioaEman();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // Called when a message has been delivered to the
        // server. The token passed in here is the same one
        // that was passed to or returned from the original call to publish.
        // This allows applications to perform asynchronous
        // delivery without blocking until delivery completes.
        //
        // If the connection to the server breaks before delivery has completed
        // delivery of a message will complete after the client has re-connected.
        // The getPendingTokens method will provide tokens for any messages
        // that are still to be delivered.

        // try {
        // System.out.println("Topic honetako mensajea bidali da: " +
        // Arrays.toString(token.getTopics()));
        // // System.out.println("Mensajea: " + new
        // String(token.getMessage().getPayload(), "UTF-8"));
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws MqttException {
        // Called when a message arrives from the server that matches any
        // subscription made by the client

        String content = new String(message.getPayload());

        switch (topic) {
            case TOPIC_TENPERATURA:
                this.valueTemperature = Double.parseDouble(content);
                MQTTDatuak.idatziJasotakoDatua(TOPIC_TENPERATURA, this.valueTemperature);
                System.out.println(TOPIC_TENPERATURA + String.valueOf(valueTemperature));
                break;
            case TOPIC_HEZETASUNA:
                this.valueHezetasuna = Double.parseDouble(content);
                MQTTDatuak.idatziJasotakoDatua(TOPIC_HEZETASUNA, this.valueHezetasuna);
                System.out.println(TOPIC_HEZETASUNA + String.valueOf(valueHezetasuna));
                break;
            case TOPIC_MOTOREA:

                break;
            default:
                break;
        }

        Grafikoa.grafikoDatasetEzarri(String.valueOf(LocalDate.now()));
        Grafikoa.grafikoDatasetHistorialEzarri(String.valueOf(LocalDate.now()));
        NegutegiInfoKudeatzailea.negutegiTenpHezeAktualizatu();
        MapaKudeatzailea.mapaTenpHezeAktualizatu();
    }
}

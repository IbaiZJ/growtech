package growtech.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import growtech.ui.ItxuraPrintzipala;
import growtech.util.Grafikoa;
import lombok.Getter;

public class MQTT implements MqttCallback {
    public static final String BROKER = "tcp://localhost:1883"; // localhost  192.168.1.100
    public static final String CLENT_ID = "TemperatureSimulator";
    public static final int QoS = 2;
    
    public static final String TOPIC_TENPERATURA = "Tenperatura";
    public static final String TOPIC_HEZETASUNA = "Hezetasuna";
    private final MqttClient client;
    private MqttConnectOptions connOpts;
    
    private ItxuraPrintzipala itxura;

    private volatile double valueTemperature;
    private volatile double valueHezetasuna;

    private @Getter boolean konektatutaDago;

    public MQTT(String broker, String clientId) throws MqttException {
        this.valueTemperature = 0.0;
        this.valueHezetasuna = 0.0;
        konektatutaDago = false;

        MemoryPersistence persistence = new MemoryPersistence();
        
        this.client = new MqttClient(broker, clientId, persistence);
        connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        this.client.setCallback(this);
        
        /*System.out.println("[MQTT] Connecting to broker: " + broker);
        this.client.connect(connOpts);
        System.out.println("[MQTT] Connected");
        System.out.println("[MQTT] Subscribe " + TOPIC_TEMPERATURE);
        client.subscribe(TOPIC_TEMPERATURE, QoS);
        System.out.println("[MQTT] Ready");*/
        
    }

    public MQTT(ItxuraPrintzipala itxura) throws MqttException {
        this(BROKER, CLENT_ID);
        this.itxura = itxura;
    }

    public void klienteraKonektatu() throws MqttSecurityException, MqttException {
        System.out.println("[MQTT] Connecting to broker: " + BROKER);
        this.client.connect(connOpts);
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
        this.client.disconnect();
        konektatutaDago = false;
    }

    void publish(String topic, String content) throws MqttException {
        MqttMessage message = new MqttMessage(content.getBytes());
        message.setQos(QoS);
        this.client.publish(topic, message);
    }

   

    public double getTemperature() {
        return this.valueTemperature;
    }

    public double getHezetasuna() {
        return this.valueHezetasuna;
    }
  

    @Override
    public void connectionLost(Throwable cause) {
        // Called when the connection to the server has been lost.
        // An application may choose to implement reconnection
        // logic at this point. This sample simply exits.
        System.err.println("Connection to MQTT broker lost!" + cause);
        System.exit(1);
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
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws MqttException {
        // Called when a message arrives from the server that matches any
        // subscription made by the client
    
        String content = new String(message.getPayload());

        switch(topic) {
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
            default:
                break;
        }

        Grafikoa.grafikoDatasetEzarri();
    }
}

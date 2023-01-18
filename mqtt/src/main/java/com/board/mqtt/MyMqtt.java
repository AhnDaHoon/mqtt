package com.board.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class MyMqtt implements MqttCallback {

    String serverUrl    = "tcp://192.168.0.92:1883";   // your IP
    String clientId     = "client_sub";
    String userId       = "username";
    char[] password     = "password".toCharArray();
    String topic        = "testTopic";
    int qos             = 2;
    MqttClient client;

    public MyMqtt()
    {
        try
        {
            MqttConnectOptions conOpts = new MqttConnectOptions();
            conOpts.setCleanSession(true);
//            conOpts.setUserName(userId);
//            conOpts.setPassword(password);
            conOpts.setKeepAliveInterval(90);

            client = new MqttClient(serverUrl, clientId, new MemoryPersistence());
            client.setCallback(this);

            // connect
            client.connect(conOpts);

            // subscribe
            client.subscribe(topic, qos);

            System.out.println("connected..");
        }
        catch(MqttException me)
        {
            me.printStackTrace();
        }
    }


    @Override
    public void connectionLost(Throwable arg0) {
        System.out.println(arg0);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken arg0) {
        System.out.println(arg0);
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("topic   : " + topic);
        System.out.println("message : " + message);
    }




    public static void main(String[] args) {

        new MyMqtt();

    }
}

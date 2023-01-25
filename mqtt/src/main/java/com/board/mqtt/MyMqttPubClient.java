package com.board.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MyMqttPubClient {
    String serverUrl    = "tcp://ip:port";   // IP port
    String clientId     = "pub_client"; // 구분하기 위한 아이디
    int qos = 2;

    // MQTT 통신에서 Publisher와 Subscriber의 역할을 하기 위한 기능을 가지고 있는 객체
    private MqttClient pubClient;

    public MyMqttPubClient() {
        try {
            this.pubClient = new MqttClient(serverUrl, clientId);
            this.pubClient.connect();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    // 메시지 전송을 위한 메소드
    public boolean send(String topic, String msg){
        try {
            // broker로 전송할 메시지를 생성 - MqttMessage
            MqttMessage message = new MqttMessage();
            message.setPayload(msg.getBytes()); // 실제 broker로 전송할 메시지
            message.setQos(this.qos);
            this.pubClient.publish(topic, message);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    // 종료를 위한 메서드
    public void close(){
        try {
            if(this.pubClient != null){
                this.pubClient.disconnect();
                this.pubClient.close();
            }
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }



}

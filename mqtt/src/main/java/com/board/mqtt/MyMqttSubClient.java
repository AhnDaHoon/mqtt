package com.board.mqtt;

import com.board.Entity.MqttData;
import com.board.repository.MqttDataRepository;
import com.board.service.MqttService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MyMqttSubClient implements MqttCallback {

    @Autowired
    private MqttDataRepository mqttDataRepository;




    private MqttClient subClient;
    private MqttConnectOptions mqttConnectOptions;

    public MyMqttSubClient init(String serverUrl, String clientId) {
        try {
            this.mqttConnectOptions = new MqttConnectOptions();
            this.mqttConnectOptions.setCleanSession(true);
            this.mqttConnectOptions.setKeepAliveInterval(200);
            // broker에 subscribe하기 위한 클라이언트 객체 생성
            this.subClient = new MqttClient(serverUrl, clientId);

            // 클라이언트 객체에 MqttCallback을 등록 - 구독 신청 후 적절한 시점에 처리하고 싶은 기능을 구현하고
            // 메소드가 자동으로 그 시점에 호출되도록 할 수 있다.
            this.subClient.setCallback(this);
            this.subClient.connect(this.mqttConnectOptions);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    // 커넥션이 종료되면 호출 - 통신오류로 연결이 끊어지는 경우 호출
    @Override
    public void connectionLost(Throwable cause) {
        try {
            System.out.println("disconnect");
            subClient.disconnect();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    // 메시지의 전송의 완료되면 호출
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    // 메시지가 도착하면 호출
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        JSONParser parser = new JSONParser();
        String msg = new String(message.getPayload());
//        JSONObject jsonObject = (JSONObject) parser.parse(msg);
        System.out.println("====================== 메시지 도착");
//        System.out.println("topic: " + topic + " id: " +message.getId()  +" message: " + msg);

        MqttService mqttService = new MqttService();
        mqttService.save(msg);

    }

    // 구독신청 - 구독을 여러개 신청할 수 있어서 List로 받는다.
    public boolean subscribe(List<String> topics){
        boolean result = true;
        try {
             if(topics != null || !topics.isEmpty()){
                 for (String topic : topics) {
                     subClient.subscribe(topic, 2);
                 }
             }
        } catch (MqttException e) {
            result = false;
            throw new RuntimeException(e);
        }
        return result;
    }

}

package com.board.service;

import com.board.Entity.MqttData;
import com.board.repository.MqttDataRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MqttService {

    @Autowired
    private MqttDataRepository mqttDataRepository;

    public void save(String msg){
        try {
            // (String)json -> map
            ObjectMapper mapper = new ObjectMapper();
            Map<String, ArrayList> jsoMapData = mapper.readValue(msg, Map.class);


            // map -> List -> save
            ModelMapper modelMapper = new ModelMapper();
            List<MqttData> mqttDataList = new ArrayList<>();
            for (Object mapData : jsoMapData.get("data")) {
                Map listData = (HashMap) mapData;

                // map에 있는 데이터들을 MqttData객체의 데이터로 변환하고 list에 저장한다.
                mqttDataList.add(modelMapper.map(listData, MqttData.class));
            }

            System.out.println("mqttDataList = " + mqttDataList);

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("mqtt");

            EntityManager em = emf.createEntityManager();

            EntityTransaction tx = em.getTransaction(); // 트랜잭션 생성
            tx.begin(); // 트랜잭션 시작

            try {
                for (MqttData mqttData : mqttDataList) {
                    em.persist(mqttData);
                }


                tx.commit();
                System.out.println("성공");
            }catch (Exception e){
                System.out.println("실패");
                tx.rollback();
                e.printStackTrace();
            } finally {
                em.close();
            }
//
        }catch (IOException e){
            System.out.println("json -> map 변환 에러");
            e.printStackTrace();
        }
    }

    public void save2(MqttData mqttData){
        System.out.println("mqttData = " + mqttData);
        mqttDataRepository.save(mqttData);
    }



}

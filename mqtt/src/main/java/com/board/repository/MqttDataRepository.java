package com.board.repository;

import com.board.Entity.MqttData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MqttDataRepository extends JpaRepository<MqttData, Long> {
}

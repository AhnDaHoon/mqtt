package com.board.Entity;

import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Setter
@Table(name = "mqtt_data")
public class MqttData {

    @Id
    @GeneratedValue
    @Column(name = "mqtt_seq")
    private Long mqttSeq;

    @Column(name = "CO2")
    private Integer co2;

    @Column(name = "TVOC")
    private Integer tvoc;

    @Column(name = "Temperature")
    private Float temperature;

    @Column(name = "Humidity")
    private Float humidity;

    @Column(name = "Pressure")
    private Integer pressure;

    @Column(name = "HTS_temperature")
    private Float htsTemperature;

    @Column(name = "HTS_humidity")
    private Float htsHumidity;

    @Column(name = "LPS_pressure")
    private Float lpsPressure;

    @Column(name = "Proximity")
    private Integer proximity;

    @Column(name = "Acceleration")
    private Integer acceleration;

    @Column(name = "Gyroscope")
    private Integer gyroscope;

    @Column(name = "Magneticfield")
    private Integer magneticfield;

    @Column(name = "Differencial_Pressure")
    private Integer differencialPressure;

    @Column(name = "Altitude")
    private Float altitude;

    @Column(name = "GasResistance")
    private Float gasResistance;



}

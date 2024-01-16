package com.example.monitoringandcommunicationservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Entity
@Table
public class Measurement {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(name="measurement_id")
    private Long measurementId;

    private Long deviceId;

    private Timestamp timestamp;

    private double hourlyConsumption;

    public Measurement() {
    }

    public Measurement(Long measurementId, Long deviceId, Timestamp timestamp, double hourlyConsumption) {
        this.measurementId = measurementId;
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.hourlyConsumption = hourlyConsumption;
    }
}

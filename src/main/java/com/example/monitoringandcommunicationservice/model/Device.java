package com.example.monitoringandcommunicationservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "devices")
public class Device {
    @Id
    @Column(name="device_id")
    private Long deviceId;

    @Column(name = "max_consumption", nullable = false, columnDefinition = "float default 0.0")
    private double maxConsumption;
}

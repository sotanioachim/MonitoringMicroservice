package com.example.monitoringandcommunicationservice.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class MeasurementDTO {
    private Timestamp timestamp;
    private Long device_id;
    private double consumption;
}

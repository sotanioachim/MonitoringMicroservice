package com.example.monitoringandcommunicationservice.repositories;

import com.example.monitoringandcommunicationservice.model.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement,Long> {
}

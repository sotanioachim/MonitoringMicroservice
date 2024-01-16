package com.example.monitoringandcommunicationservice.repositories;

import com.example.monitoringandcommunicationservice.model.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends JpaRepository<Device,Long> {
}

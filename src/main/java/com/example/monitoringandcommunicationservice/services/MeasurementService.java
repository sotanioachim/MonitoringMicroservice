package com.example.monitoringandcommunicationservice.services;

import com.example.monitoringandcommunicationservice.model.Device;
import com.example.monitoringandcommunicationservice.model.Measurement;
import com.example.monitoringandcommunicationservice.repositories.DeviceRepository;
import com.example.monitoringandcommunicationservice.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository){
        this.measurementRepository = measurementRepository;
    }

    public List<Measurement> getMeasurements(){
        return measurementRepository.findAll();
    }

    public List<Measurement> getMeasurementsForDate(Date date){
        List<Measurement> allList = measurementRepository.findAll();

        return allList.stream()
                .filter(measurement -> isSameDay(measurement.getTimestamp(), date))
                .collect(Collectors.toList());
    }

    private boolean isSameDay(Date date1, Date date2) {
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate1.equals(localDate2);
    }

    public Measurement getMeasurementById(Long measurementId){
        return measurementRepository.findById(measurementId).get();
    }

    public void deleteMeasurement(Long measurementId){
        measurementRepository.deleteById(measurementId);
    }

    public Measurement createMeasurement(Measurement measurement){
        return measurementRepository.save(measurement);
    }
}

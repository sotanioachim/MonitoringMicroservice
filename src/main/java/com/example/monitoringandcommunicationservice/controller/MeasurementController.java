package com.example.monitoringandcommunicationservice.controller;

import com.example.monitoringandcommunicationservice.model.Measurement;
import com.example.monitoringandcommunicationservice.services.MeasurementService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/measurement")
@CrossOrigin("*")
public class MeasurementController {

    private final MeasurementService measurementService;

    @Autowired
    public MeasurementController(MeasurementService measurementService){
        this.measurementService = measurementService;
    }

    @GetMapping
    public ResponseEntity<List<Measurement>> getMeasurements(){
        return new ResponseEntity<>(measurementService.getMeasurements(), HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<List<Measurement>> getMeasurementsForDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date){
        return new ResponseEntity<>(measurementService.getMeasurementsForDate(date), HttpStatus.OK);
    }
}

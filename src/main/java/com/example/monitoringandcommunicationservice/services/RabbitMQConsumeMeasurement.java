package com.example.monitoringandcommunicationservice.services;

import com.example.monitoringandcommunicationservice.controller.MessageController;
import com.example.monitoringandcommunicationservice.dto.MeasurementDTO;
import com.example.monitoringandcommunicationservice.model.Measurement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RabbitMQConsumeMeasurement {
    private final MeasurementService measurementService;
    private final WSService wsService;
    private final DeviceService deviceService;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumeMeasurement.class);
    private final Map<Long, List<Double>> deviceConsumptionMap = new HashMap<>();

    @Autowired
    public RabbitMQConsumeMeasurement(MeasurementService measurementService,DeviceService deviceService, WSService wsService){
        this.measurementService = measurementService;
        this.deviceService = deviceService;
        this.wsService = wsService;
    }
    @RabbitListener(queues = {"${rabbitmq.queue.sensor}"})
    public void consume(MeasurementDTO measurementDTO){
        Long deviceId =  measurementDTO.getDevice_id();
        double consumption = measurementDTO.getConsumption();
        deviceConsumptionMap.computeIfAbsent(deviceId, k -> new ArrayList<>()).add(consumption);
    }

    @Scheduled(fixedRate = 60000) // 1 minute = 60,000 milliseconds
    public void displayConsumptionMessages() throws Exception {
        for (Map.Entry<Long, List<Double>> entry : deviceConsumptionMap.entrySet()) {
            Long deviceId = entry.getKey();
            List<Double> consumptionList = entry.getValue();

            double averageConsumption = calculateAverage(consumptionList);
            LOGGER.info(String.format("Device %s consumed: %.2f this minute!", deviceId, averageConsumption));

            double maxConsumption = deviceService.getDeviceById(deviceId).getMaxConsumption();
            if(maxConsumption < averageConsumption){
                LOGGER.info(String.format("Device %s consumed TO MUCH!", deviceId ));
                wsService.notifyFrontend("Device exceeded consumption!");
            }
            measurementService.createMeasurement(new Measurement(0L,deviceId,new Timestamp(System.currentTimeMillis()),averageConsumption));
        }
        this.deviceConsumptionMap.clear();
    }

    private double calculateAverage(List<Double> consumptionList) {
        if (consumptionList.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Double consumption : consumptionList) {
            sum += consumption;
        }

        return sum / consumptionList.size();
    }
}

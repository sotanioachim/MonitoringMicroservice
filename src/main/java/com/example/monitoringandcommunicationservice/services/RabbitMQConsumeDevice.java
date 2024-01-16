package com.example.monitoringandcommunicationservice.services;

import com.example.monitoringandcommunicationservice.dto.MeasurementDTO;
import com.example.monitoringandcommunicationservice.model.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class RabbitMQConsumeDevice {
    private final DeviceService deviceService;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumeMeasurement.class);

    public RabbitMQConsumeDevice(DeviceService deviceService){this.deviceService = deviceService;}
    @RabbitListener(queues = {"${rabbitmq.queue.device}"})
    public void consume(Device device){
        LOGGER.info(String.format("Message recieved -> %s", device.toString()));
        try {
            Device findDevice = deviceService.getDeviceById(device.getDeviceId());
            deviceService.updateDevice(device);
        } catch (Exception e) {
            deviceService.createDevice(device);
        }
    }
}

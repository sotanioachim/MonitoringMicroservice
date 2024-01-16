package com.example.monitoringandcommunicationservice.services;

import com.example.monitoringandcommunicationservice.model.Device;
import com.example.monitoringandcommunicationservice.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository){
        this.deviceRepository = deviceRepository;
    }

    public List<Device> getDevices(){
        return deviceRepository.findAll();
    }

    public Device getDeviceById(Long deviceId){
        return deviceRepository.findById(deviceId).get();
    }

    public void deleteDevice(Long deviceId){
        deviceRepository.deleteById(deviceId);
    }

    public Device updateDevice(Device updateDevice){
        Device device = deviceRepository.findById(updateDevice.getDeviceId()).get();
        device.setMaxConsumption(updateDevice.getMaxConsumption());
        deviceRepository.save(device);
        return device;
    }

    public Device createDevice(Device device){
        return deviceRepository.save(device);
    }
}

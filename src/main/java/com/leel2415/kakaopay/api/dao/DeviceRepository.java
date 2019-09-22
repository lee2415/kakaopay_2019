package com.leel2415.kakaopay.api.dao;

import com.leel2415.kakaopay.api.entity.Device;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DeviceRepository extends CrudRepository<Device, String> {
    public Device findByDeviceId(String deviceId);
    public List<Device> findAll();
}

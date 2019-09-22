package com.leel2415.kakaopay;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.leel2415.kakaopay.api.dao.AccessRepository;
import com.leel2415.kakaopay.api.dao.AccessRepositorySupport;
import com.leel2415.kakaopay.api.dao.DeviceRepository;
import com.leel2415.kakaopay.api.entity.Access;
import com.leel2415.kakaopay.api.entity.Device;
import com.leel2415.kakaopay.api.service.ApiService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ApiTest {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private ApiService apiService;

    @Autowired
    private AccessRepositorySupport accessRepositorySupport;

    @Test
    @Transactional
    public void deviceInsertTest() {
        Device device = Device.builder()
                .deviceId("DIS1234")
                .deviceName("스마트폰")
                .build();

        log.debug(device.toString());
        deviceRepository.save(device);

        assertThat(deviceRepository.findByDeviceId("DIS1234").getDeviceName(), is("스마트폰"));
    }

    @Test
    @Transactional
    public void accessInsertTest() {
        Device device = Device.builder()
                .deviceId("DIS1234")
                .deviceName("스마트폰")
                .build();

        log.debug(device.toString());
        deviceRepository.save(device);

        Access access = Access.builder()
                .year("2011")
                .deviceId("DIS1234")
                .rate("26.3")
                .accessRate("52.9")
                .device(device)
                .build();

        log.debug(access.toString());
        accessRepository.save(access);
        log.debug(accessRepository.findAll().toString());
    }

    @Test
    @Transactional
    public void dataLoadTest() throws IOException {
        apiService.save();
        assertThat(deviceRepository.findAll().size(), is(5));
        assertThat(accessRepository.findAll().size(), is(40));
    }

    @Test
    @Transactional
    public void getDeviceList() {
        Map resultMap = apiService.getDeviceList();

        log.debug(resultMap.toString());

        List<Device> deviceList = (List) resultMap.get("devices");
        log.debug(deviceList.toString());

        assertThat(deviceList.size(), is(5));
    }

    @Test
    @Transactional
    public void getMaxDeviceList() {
        log.debug(accessRepositorySupport.findByMaxRateDevice().toString());
    }

    @Test
    @Transactional
    public void getMaxDevice() {
        log.debug(accessRepositorySupport.findByMaxYearDevice("2018").toString());
    }

    @Test
    @Transactional
    public void findMyMaxDeviceYear() {
        log.debug(accessRepositorySupport.findMyMaxDeviceYear("DIS0001").toString());
    }
}


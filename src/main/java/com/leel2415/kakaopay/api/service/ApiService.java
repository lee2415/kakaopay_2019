package com.leel2415.kakaopay.api.service;

import com.leel2415.kakaopay.api.dao.AccessRepository;
import com.leel2415.kakaopay.api.dao.AccessRepositorySupport;
import com.leel2415.kakaopay.api.dao.DeviceRepository;
import com.leel2415.kakaopay.api.entity.Access;
import com.leel2415.kakaopay.api.entity.Device;
import com.leel2415.kakaopay.api.util.ObjectConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ApiService {

    private final static String DATA_FILE_NAME = "data.csv";

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private AccessRepository accessRepository;

    @Autowired
    private AccessRepositorySupport accessRepositorySupport;

    public void save() throws IOException {
        // csv에서 읽어온 내용을 Entity구조에 맞게 변경
        Map resultMap = ObjectConvertUtil.convertMappingVo(readCsv());

        // DB에 저장
        deviceRepository.saveAll((List<Device>)resultMap.get("deviceList"));
        accessRepository.saveAll((List<Access>)resultMap.get("accessList"));
    }

    private List<CSVRecord> readCsv() throws IOException {
        // 파일을 읽어와 저장
        ClassPathResource cpr = new ClassPathResource((DATA_FILE_NAME));
        BufferedReader in = new BufferedReader(new InputStreamReader(cpr.getInputStream()));
        CSVParser parser = CSVFormat.EXCEL.parse(in);

        // 읽어온 내용을 List로 만들어 return
        List<CSVRecord> resultList = parser.getRecords();
        return resultList;
    }

    public Map getDeviceList(){
        Map resultMap = new HashMap();
        resultMap.put("devices", deviceRepository.findAll());
        return resultMap;
    }

    public Map getMaxDeviceList(){
        Map resultMap = new HashMap();
        resultMap.put("devices", accessRepositorySupport.findByMaxRateDevice());
        return resultMap;
    }

    public Map getTopDevice(String year){
        Map resultMap = new HashMap();
        resultMap.put("result", accessRepositorySupport.findByMaxYearDevice(year));
        return resultMap;
    }

    public Map getTopYear(String deviceId){
        Map resultMap = new HashMap();
        resultMap.put("result", accessRepositorySupport.findMyMaxDeviceYear(deviceId));
        return resultMap;
    }

}

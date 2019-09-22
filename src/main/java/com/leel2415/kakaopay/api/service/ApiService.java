package com.leel2415.kakaopay.api.service;

import com.leel2415.kakaopay.api.dao.AccessRepository;
import com.leel2415.kakaopay.api.dao.AccessRepositorySupport;
import com.leel2415.kakaopay.api.dao.DeviceRepository;
import com.leel2415.kakaopay.api.entity.Access;
import com.leel2415.kakaopay.api.entity.Device;
import com.leel2415.kakaopay.api.util.LinearRegression;
import com.leel2415.kakaopay.api.util.ObjectConvertUtil;
import com.leel2415.kakaopay.common.exception.BizException;
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
import java.util.ArrayList;
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

    @Autowired
    private LinearRegression linearRegression;

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

    public Map gerPrediectYear(String deviceId) {
        // 기기 ID 기반으로 정보 조회.
        List<Access> list = accessRepository.findByDeviceId(deviceId);
        if (list.size() < 0) {
            throw new BizException("입력하신 기기에 대한 정보가 없습니다.");
        }
        log.debug(list.toString());

        List<Integer> x = new ArrayList<>();
        List<Double> y = new ArrayList<>();

        for (Access access : list) {
            try {
                x.add(Integer.parseInt(access.getYear()));
                y.add(Double.parseDouble(access.getRate()));
            } catch (NumberFormatException e) {
                y.add(0.0);
            }
        }
        double result = linearRegression.predictForValue(x, y, 2019);
        log.debug("예측 결과 : {}", result);
        Map resultMap = list.get(0).getDevice();
        resultMap.put("year", "2019");
        resultMap.put("rate", Math.round((result) * 10) / 10.0);

        return resultMap;
    }

}

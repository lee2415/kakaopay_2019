package com.leel2415.kakaopay.api.util;

import com.leel2415.kakaopay.api.entity.Access;
import com.leel2415.kakaopay.api.entity.Device;
import com.leel2415.kakaopay.common.exception.BizException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ObjectConvertUtil {

    /**
     * CSV에서 읽어온 데이터를 Entity구조에 맞게 변경하는 Method
     * @param records
     * @return
     */
    public static Map convertMappingVo(List<CSVRecord> records){
        Map resultMap = new HashMap();
        List<Device> deviceList = new ArrayList<>();
        List<Access> accessList = new ArrayList<>();

        if(records.size() > 0){
            boolean deviceFlag = true;
            for(CSVRecord record : records){
                // exceld의 첫행의 경우 device 정보를 넣는데 사용한다.
                if(deviceFlag){
                    for(int i=2;i<record.size();i++){
                        Device device = Device.builder()
                                .deviceId("DIS000" + (i -1))
                                .deviceName(record.get(i))
                                .build();
                        log.debug(device.toString());
                        deviceList.add(device);
                    }
                    deviceFlag = false;
                } else {
                    int idx = 2;
                    for(Device device : deviceList) {
                        Access access = Access.builder()
                                .year(record.get(0))
                                .accessRate(record.get(1))
                                .deviceId(device.getDeviceId())
                                .rate(record.get(idx++))
                                .device(device)
                                .build();
                        accessList.add(access);
                    }
                }
            }
        } else {
            throw new BizException("CSV 파일 load에 실패하였습니다.");
        }

        log.debug(deviceList.toString());
        log.debug(accessList.toString());

        resultMap.put("deviceList", deviceList);
        resultMap.put("accessList", accessList);

        return resultMap;
    }
}

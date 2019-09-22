package com.leel2415.kakaopay.api.web;

import com.leel2415.kakaopay.api.entity.Device;
import com.leel2415.kakaopay.api.service.ApiService;
import com.leel2415.kakaopay.common.ResponseBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class ApiController {

    @Autowired
    private ApiService apiService;

    /**
     * CSV 파일을 읽어와 DB에 저장.
     * @return
     * @throws IOException
     */
    @PostMapping("/save")
    public ResponseEntity<Object> save() throws IOException {
        apiService.save();
        return ResponseBase.ok();
    }

    /**
     * 인터넷뱅킹 서비스 접속 기기 목록을 출력하는 API 를 개발
     * @return
     */
    @GetMapping("/device/list")
    public ResponseEntity<Map> getDeviceList() {
        return ResponseBase.ok(apiService.getDeviceList());
    }

    /**
     * 각 년도별로 인터넷뱅킹을 가장 많이 이용하는 접속기기를 출력하는 API 를 개발
     * @return
     */
    @GetMapping("/device/list/max")
    public ResponseEntity<Map> getMaxDeviceList() {
        return ResponseBase.ok(apiService.getMaxDeviceList());
    }

    /**
     * 특정 년도를 입력받아 그 해에 인터넷뱅킹에 가장 많이 접속하는 기기 이름을 출력
     * @return
     */
    @GetMapping("/device/top/{year}")
    public ResponseEntity<Map> getTopDevice(@PathVariable("year") String year){
        return ResponseBase.ok(apiService.getTopDevice(year));
    }


    /**
     * 디바이스 아이디를 입력받아 인터넷뱅킹에 접속 비율이 가장 많은 해를 출력
     */
    @GetMapping("/rate/top/{deviceId}")
    public ResponseEntity<Map> getTopYear(@PathVariable("deviceId") String deviceId){
        return ResponseBase.ok(apiService.getTopYear(deviceId));
    }
}

package com.leel2415.kakaopay;

import com.leel2415.kakaopay.api.service.ApiService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
@Slf4j
public class TestApplication implements CommandLineRunner {

    @Autowired
    private ApiService apiService;

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Override
    public void run(String... args) throws IOException {
        log.debug("CSV 자동 로딩");
        apiService.save();
    }



}

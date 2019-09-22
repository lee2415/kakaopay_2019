package com.leel2415.kakaopay.api.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class AccessId implements Serializable {
    private String year;
    private String deviceId;


    public AccessId(){

    }

    public AccessId(String year, String deviceId){
        this.year = year;
        this.deviceId = deviceId;
    }
}

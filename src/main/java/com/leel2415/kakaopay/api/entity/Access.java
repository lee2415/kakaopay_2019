package com.leel2415.kakaopay.api.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@IdClass(AccessId.class)
public class Access {

    @Id
    private String year;

    @Id
    @JsonIgnore
    private String deviceId;
    private String rate;

    @JsonIgnore
    private String accessRate;

    @OneToOne
    @JoinColumn(name = "deviceId", insertable = false, updatable = false)
    private Device device;

    @JsonAnyGetter
    public Map<String, String> getDevice(){
        Map resultMap = new HashMap();
        resultMap.put("device_id", device.getDeviceId());
        resultMap.put("device_name", device.getDeviceName());
        return resultMap;
    }

}

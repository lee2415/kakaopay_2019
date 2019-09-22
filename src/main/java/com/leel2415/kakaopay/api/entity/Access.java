package com.leel2415.kakaopay.api.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
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
    @JsonView(View.ExceptId.class)
    private String year;

    @Id
    private String deviceId;

    @JsonView(View.ExceptId.class)
    private String rate;

    @JsonIgnore
    private String accessRate;

    @OneToOne
    @JoinColumn(name = "deviceId", insertable = false, updatable = false)
    private Device device;

    @JsonView(View.ExceptId.class)
    @JsonAnyGetter
    public Map<String, String> getDevice() {
        Map resultMap = new HashMap();
        resultMap.put("device_name", device.getDeviceName());
        return resultMap;
    }
}
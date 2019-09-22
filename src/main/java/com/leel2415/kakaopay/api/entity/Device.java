package com.leel2415.kakaopay.api.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Device {
    @Id
    @JsonProperty("device_id")
    private String deviceId;
    @JsonProperty("device_name")
    private String deviceName;
}

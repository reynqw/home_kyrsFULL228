package com.smarthome.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserDeviceId implements Serializable {
    private Integer userId;
    private Integer deviceId;

    public UserDeviceId() {}
    public UserDeviceId(Integer userId, Integer deviceId) {
        this.userId = userId;
        this.deviceId = deviceId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDeviceId that = (UserDeviceId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(deviceId, that.deviceId);
    }
    @Override
    public int hashCode() {
        return Objects.hash(userId, deviceId);
    }
} 
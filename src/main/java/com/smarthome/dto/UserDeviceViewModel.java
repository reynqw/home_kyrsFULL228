package com.smarthome.dto;

import com.smarthome.entity.UserDevice;
import com.smarthome.entity.DeviceTypeMeta;

public class UserDeviceViewModel {
    private final UserDevice userDevice;
    private final DeviceTypeMeta meta;

    public UserDeviceViewModel(UserDevice userDevice, DeviceTypeMeta meta) {
        this.userDevice = userDevice;
        this.meta = meta;
    }

    public UserDevice getUserDevice() { return userDevice; }
    public DeviceTypeMeta getMeta() { return meta; }
} 
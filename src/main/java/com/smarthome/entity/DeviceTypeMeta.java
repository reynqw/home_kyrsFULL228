package com.smarthome.entity;

// перечисление для описания мета-типа устройства и его допустимых значений или диапазона
public enum DeviceTypeMeta {
    // сенсор движения (например, датчик движения)
    MOTION_SENSOR(new String[]{"motion", "движение", "датчик движения", "движ", "сенсор движения"}, new String[]{"Движение", "Нет движения"}),
    // выключатель света
    LIGHT_SWITCH(new String[]{"light", "свет", "реле света", "реле", "переключатель света", "выключатель"}, new String[]{"Вкл", "Выкл"}),
    // выключатель отопления
    HEATER_SWITCH(new String[]{"heater", "отопление", "обогрев", "переключатель", "выключатель", "выключатель отопления", "переключатель отопления"}, new String[]{"Вкл", "Выкл"}),
    // датчик влажности (диапазон от 0 до 100)
    HUMIDITY_SENSOR(new String[]{"humidity", "влажность", "датчик влажности"}, 0, 100),
    // датчик температуры (диапазон от -20 до 40)
    TEMPERATURE_SENSOR(new String[]{"temperature", "температура", "датчик температуры"}, -20, 40);

    private final String[] types;
    private final String[] allowedValues;
    private final Integer minValue;
    private final Integer maxValue;

    // For enum values
    DeviceTypeMeta(String[] types, String[] allowedValues) {
        this.types = types;
        this.allowedValues = allowedValues;
        this.minValue = null;
        this.maxValue = null;
    }

    // For range values
    DeviceTypeMeta(String[] types, Integer minValue, Integer maxValue) {
        this.types = types;
        this.allowedValues = null;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public String[] getTypes() { return types; }
    public String[] getAllowedValues() { return allowedValues; }
    public Integer getMinValue() { return minValue; }
    public Integer getMaxValue() { return maxValue; }

    public static DeviceTypeMeta fromType(String type) {
        for (DeviceTypeMeta meta : values()) {
            for (String t : meta.types) {
                if (t.equalsIgnoreCase(type)) return meta;
            }
        }
        return null;
    }
} 
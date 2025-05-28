package com.smarthome.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

// конвертер для преобразования значения оператора между базой данных и enum Operator
@Converter(autoApply = true)
public class OperatorConverter implements AttributeConverter<Operator, String> {
    // преобразует enum Operator в строку для хранения в базе данных
    @Override
    public String convertToDatabaseColumn(Operator operator) {
        if (operator == null) return null;
        return operator.getSymbol();
    }

    // преобразует строку из базы данных обратно в enum Operator
    @Override
    public Operator convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        return Operator.fromSymbol(dbData);
    }
} 
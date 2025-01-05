package com.yassine.jaa.mapper;

import com.yassine.jaa.model.TransactionTypes;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TransactionTypesConverter implements AttributeConverter<TransactionTypes, String> {

    @Override
    public String convertToDatabaseColumn(TransactionTypes type) {
        if (type == null) {
            return null;
        }
        return type.toString();
    }

    @Override
    public TransactionTypes convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return TransactionTypes.fromString(dbData);
    }
}
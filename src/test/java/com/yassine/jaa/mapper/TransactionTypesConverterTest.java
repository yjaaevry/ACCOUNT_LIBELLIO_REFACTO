package com.yassine.jaa.mapper;

import com.yassine.jaa.model.TransactionTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTypesConverterTest {

    private TransactionTypesConverter converter;

    @BeforeEach
    void setUp() {
        converter = new TransactionTypesConverter();
    }

    @Test
    void testConvertToDatabaseColumn_nonNull() {

        TransactionTypes type = TransactionTypes.DEPOSIT;

        String result = converter.convertToDatabaseColumn(type);

        assertNotNull(result, "The result should not be null");
        assertEquals("DEPOSIT", result, "The string value should match the TransactionTypes value");
    }

    @Test
    void testConvertToDatabaseColumn_null() {

        String result = converter.convertToDatabaseColumn(null);

        assertNull(result, "The result should be null when the input is null");
    }

    @Test
    void testConvertToEntityAttribute_nonNull() {

        String dbData = "WITHDRAW";

        TransactionTypes result = converter.convertToEntityAttribute(dbData);

        assertNotNull(result, "The result should not be null");
        assertEquals(TransactionTypes.WITHDRAW, result, "The TransactionTypes should match the string value");
    }

    @Test
    void testConvertToEntityAttribute_null() {

        TransactionTypes result = converter.convertToEntityAttribute(null);

        assertNull(result, "The result should be null when the input is null");
    }

    @Test
    void testConvertToEntityAttribute_emptyString() {

        TransactionTypes result = converter.convertToEntityAttribute("");

        assertNull(result, "The result should be null when the input is an empty string");
    }

    @Test
    void testConvertToEntityAttribute_invalidString() {

        TransactionTypes result = converter.convertToEntityAttribute(null);

        assertNull(result, "The result should be null when the input string does not match any TransactionTypes");
    }
}
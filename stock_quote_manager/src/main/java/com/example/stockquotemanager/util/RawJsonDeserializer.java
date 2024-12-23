package com.example.stockquotemanager.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class RawJsonDeserializer extends JsonDeserializer<String> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        Object node = objectMapper.readTree(jsonParser);
        return objectMapper.writeValueAsString(node);
    }
}


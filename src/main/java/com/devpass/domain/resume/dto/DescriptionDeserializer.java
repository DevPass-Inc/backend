package com.devpass.domain.resume.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DescriptionDeserializer extends JsonDeserializer<List<String>> {
    @Override
    public List<String> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        if (p.getCurrentToken() == JsonToken.START_ARRAY) {
            return ctxt.readValue(p, ctxt.getTypeFactory().constructCollectionType(List.class, String.class));
        } else if (p.getCurrentToken() == JsonToken.VALUE_STRING) {
            String text = p.getText();
            List<String> list = new ArrayList<>();
            list.add(text);
            return list;
        } else {
            return new ArrayList<>();
        }
    }
}

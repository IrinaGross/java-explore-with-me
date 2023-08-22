package ru.practicum.configuration;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

class StringTrimDeserializer extends StdDeserializer<String> {
    protected StringTrimDeserializer(Class<?> vc) {
        super(vc);
    }

    @SuppressWarnings({"RedundantThrows", "DuplicateThrows"})
    @Override
    public String deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        var text = jsonParser.getText();
        return text != null ? text.trim() : null;
    }
}

package ru.practicum.configuration;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.format.DateTimeFormatter;

import static ru.practicum.Const.DATE_PATTERN;

@Configuration
public class JacksonConfiguration {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        var formatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
        return builder -> {
            builder.serializers(new LocalDateTimeSerializer(formatter));
            builder.deserializers(new LocalDateTimeDeserializer(formatter));
        };
    }

    @Bean
    public com.fasterxml.jackson.databind.Module stringTrimModule() {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new StringTrimDeserializer(String.class));
        return module;
    }
}

package com.company.storeapi.core.serialize;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The type Serialization configuration.
 */
@Configuration
public class SerializationConfiguration {

    /**
     * Serialization object mapper.
     *
     * @return the object mapper
     */
    @Bean
    public ObjectMapper serialization(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);

        return mapper;
    }

    /**
     * Local date time serializer bean local date time serializer.
     *
     * @return the local date time serializer
     */
    @Bean
    public LocalDateTimeSerializer localDateTimeSerializerBean() {
        return new LocalDateTimeSerializer();
    }

    /**
     * The type Local date time serializer.
     */
    static class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime>{

        @Override
        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
    }


}

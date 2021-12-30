package com.zzj.rule.engine.api.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author : zhuhongying@bytedance.com
 * @since : 2020/6/21
 */
@Slf4j
public class JSONUtils {
    public static final ObjectMapper DEFAULT_OBJECT_MAPPER = initObjectMapper();

    private static ObjectMapper initObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.registerModule(getJavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    private static JavaTimeModule getJavaTimeModule() {
        JavaTimeModule javaTimeModule = new JavaTimeModule();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dateTimeFormatter));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));

        javaTimeModule.addSerializer(Date.class, new DateSerializer(false, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addDeserializer(Date.class, new DateDeserializer(DateDeserializer.instance, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), "yyyy-MM-dd HH:mm:ss"));
        return javaTimeModule;
    }

    @SneakyThrows
    public static String toJSONString(Object value) {
        try {
            return DEFAULT_OBJECT_MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException err) {
//            log.error("Failed to convert the object to json string!");
            throw new IllegalArgumentException("Failed to convert the object" + value.toString() + "to json string", err);
        }
    }

    @SneakyThrows
    public static <T> T parseObject(String jsonString, Class<T> tClass) {
        try {
            return DEFAULT_OBJECT_MAPPER.readValue(jsonString, tClass);
        } catch (IOException err) {
//            log.error("Failed to parse text with error ", err);
            throw new IllegalArgumentException("Error parsing \'" + jsonString + "\' with error", err);
        }
    }

    public static byte[] toByteArray(Object value) {
        try {
            return DEFAULT_OBJECT_MAPPER.writeValueAsBytes(value);
        } catch (JsonProcessingException err) {
//            log.error("Failed to convert the object to json string!");
            throw new IllegalArgumentException("Failed to convert the object to json string", err);
        }
    }

    @SneakyThrows
    public static Map<String, Object> deepCopy(Object object) {
        String jsonString = toJSONString(object);
        return DEFAULT_OBJECT_MAPPER.readValue(jsonString, Map.class);
    }

    public static <T> List<T> parseList(String text, Class<T> elementType) {
        try {
            JavaType listType = DEFAULT_OBJECT_MAPPER.getTypeFactory().constructParametricType(ArrayList.class, new Class[]{elementType});
            return (List)DEFAULT_OBJECT_MAPPER.readValue(text, listType);
        } catch (IOException var4) {
//            log.error("Failed to parse text to list with error ", var4);
            throw new IllegalArgumentException("Error parsing '" + text + "' to list with error", var4);
        }
    }

}

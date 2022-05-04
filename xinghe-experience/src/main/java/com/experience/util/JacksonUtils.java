package com.experience.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author ghluo
 * @date 2019/11/28 14:02
 */
public final class JacksonUtils {
    /**
     * 默认日期时间格式
     */
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        OBJECT_MAPPER = new ObjectMapper();

        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT));
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        OBJECT_MAPPER.setSerializationInclusion(Include.NON_NULL);
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

        simpleModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE));
        simpleModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ISO_LOCAL_TIME));
        simpleModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DEFAULT_DATE_TIME_FORMAT)));
        simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE));
        simpleModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ISO_LOCAL_TIME));

        OBJECT_MAPPER.registerModule(simpleModule);
    }

    public static String toJson(Object obj) {
        if (obj == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    public static <T> T toObject(byte[] bytes, Class<T> cl) {
        if (bytes == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(bytes, cl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(String json, Class<T> cl) {
        if (json == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, cl);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(String json, TypeReference<T> resultType) {
        if (json == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.readValue(json, resultType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> List<T> toList(String json, Class<T> cl) {
        if (json == null) {
            return null;
        }
        JavaType javaType = getCollectionType(List.class, cl);
        try {
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObjectWithCollectionType(String json, Class<?> collectionClass, Class<?>... elementClasses) {
        if (json == null) {
            return null;
        }
        JavaType javaType = getCollectionType(collectionClass, elementClasses);
        try {
            return OBJECT_MAPPER.readValue(json, javaType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 比较传入的json是否完全一致
     *
     * @param json  json
     * @param json2 要对比的json
     */
    public static boolean jsonEquals(String json, String json2) {
        try {
            final JsonNode jsonNode = OBJECT_MAPPER.readTree(json);
            final JsonNode jsonNode2 = OBJECT_MAPPER.readTree(json2);
            return jsonNode.equals(jsonNode2);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static JavaType getCollectionType(Class<?> collectionClass, Class<?>... elementClasses) {
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    /**
     * 判断一个字符串是不是JSON
     *
     * @param content
     * @return
     */
    public static boolean isJson(String content) {
        if (!StringUtils.hasText(content)) {
            return false;
        }

        try {
            OBJECT_MAPPER.readTree(content);
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * 对象深拷贝
     *
     * @param object 要拷贝的对象
     * @param cl     对象类型
     * @param <T>    泛型
     * @return 拷贝结果
     */
    public static <T> T deepCopy(T object, Class<T> cl) {
        final String json = toJson(object);
        return toObject(json, cl);
    }

    /**
     * 列表拷贝
     *
     * @param list
     * @param cl
     * @param <T>
     * @return
     */
    public static <T> List<T> listCopy(List<T> list, Class<T> cl) {
        final String json = toJson(list);
        return toList(json, cl);
    }

    public static void main(String[] args) {
        System.out.println(JacksonUtils.toJson("123"));
    }

    public static boolean isNotJson(String str) {
        return !isJson(str);
    }
}

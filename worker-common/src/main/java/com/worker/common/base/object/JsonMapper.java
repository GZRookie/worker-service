package com.worker.common.base.object;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Json转换类
 *  @author
 * @date: 2023/11/2 23:15
 */
@Slf4j
public class JsonMapper {
    public final static ObjectMapper JSON_MAPPER = new ObjectMapper();

    private JsonMapper() {
    }

    /**
     * 配置该objectMapper在反序列化时，忽略目标对象没有的属性。
     * 凡是使用该objectMapper反序列化时，都会拥有该特性。
     */
    static {
        JSON_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true);
        JSON_MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    /****
     * 对象转json
     * @param obj
     * @return
     */
    public static String writeValueAsString(Object obj) {
        try {
            return JSON_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("对象转json异常", e);
        }
        return null;
    }

    public static <T> T readValue(String content, TypeReference<T> valueTypeRef) {
        try {
            return JSON_MAPPER.readValue(content, valueTypeRef);
        } catch (JsonProcessingException e) {
            log.error("对象转json异常", e);
        }
        return null;
    }

    public static <T> T readValue(String content, Class<T> tClass) {
        try {
            return JSON_MAPPER.readValue(content, tClass);
        } catch (JsonProcessingException e) {
            log.error("对象转json异常", e);
        }
        return null;
    }

    /****
     * json转 json tree
     * @param json
     * @return
     */
    public static JsonNode readTree(String json) {
        try {
            return JSON_MAPPER.readTree(json);
        } catch (IOException e) {
            log.error("json转 jsonTree 异常", e);
        }
        return null;
    }
}

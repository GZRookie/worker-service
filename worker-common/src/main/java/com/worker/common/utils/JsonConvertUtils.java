package com.worker.common.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.*;

/**
 * json工具类
 *
 *  @author
 * @date: 2023/11/2 21:32
 */
public class JsonConvertUtils {

    /**
     * json字符串转为列表
     */
    public static List<String> JsonToList(String json) {
        if(StringUtils.isEmpty(json)) {
            return Collections.emptyList();
        }

        List<String> stringList = new ArrayList<>();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(json);

            Iterator<JsonNode> elements = jsonNode.elements();
            while (elements.hasNext()) {
                JsonNode element = elements.next();
                stringList.add(element.asText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringList;
    }

    /**
     * 列表转为json字符串
     */
    public static String convertListToJson(List<String> stringList) {
        if(CollectionUtils.isEmpty(stringList)) {
            return null;
        }

        try {
            Map<String, String> jsonMap = new HashMap<>();
            for (int i = 0; i < stringList.size(); i++) {
                jsonMap.put(String.valueOf(i + 1), stringList.get(i));
            }

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(jsonMap);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}

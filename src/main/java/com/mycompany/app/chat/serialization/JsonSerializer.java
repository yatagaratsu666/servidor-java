/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.app.chat.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonSerializer {

    private final ObjectMapper mapper;

    public JsonSerializer() {
        this.mapper = JacksonProvider.getMapper();
    }

    public String serialize(Object object) throws JsonProcessingException {
        return mapper.writeValueAsString(object);
    }

    public <T> T deserialize(String json, Class<T> clazz)
            throws JsonProcessingException {
        return mapper.readValue(json, clazz);
    }

    public JsonNode readTree(String json) throws JsonProcessingException {
        return mapper.readTree(json);
    }
}
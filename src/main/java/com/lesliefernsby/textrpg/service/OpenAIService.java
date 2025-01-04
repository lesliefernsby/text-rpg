package com.lesliefernsby.textrpg.service;

import java.util.Map;

import org.json.JSONArray;

public interface OpenAIService {
    String getResponseAsString(JSONArray messages, String model, Integer maxTokens, Double temperature, Double topP);

    Map<String, Object> getResponseAsJson(JSONArray messages, String model, Integer maxTokens, Double temperature, Double topP);
}

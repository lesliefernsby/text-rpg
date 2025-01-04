package com.lesliefernsby.textrpg.service.impl;

import com.lesliefernsby.textrpg.service.OpenAIService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;

@Service
public class OpenAIServiceImpl implements OpenAIService {

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${openai.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public OpenAIServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public String getResponseAsString(JSONArray messages, String model, Integer maxTokens, Double temperature, Double topP) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", model != null ? model : "gpt-3.5-turbo");
        requestBody.put("messages", messages);
        requestBody.put("max_tokens", maxTokens != null ? maxTokens : 100);
        if (temperature != null) requestBody.put("temperature", temperature);
        if (topP != null) requestBody.put("top_p", topP);

        HttpEntity<String> request = new HttpEntity<>(requestBody.toString(), headers);
        ResponseEntity<String> response = restTemplate.exchange(
            apiUrl,
            HttpMethod.POST,
            request,
            String.class
        );

        JSONObject jsonResponse = new JSONObject(response.getBody());
        JSONArray choices = jsonResponse.getJSONArray("choices");
        String content = choices.getJSONObject(0).getJSONObject("message").getString("content");

        return content;
    }

    @Override
    public Map<String, Object> getResponseAsJson(JSONArray messages, String model, Integer maxTokens, Double temperature, Double topP) {
        String content = getResponseAsString(messages, model, maxTokens, temperature, topP);
        return new JSONObject(content).toMap();
    }
}

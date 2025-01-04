package com.lesliefernsby.textrpg.service.impl;

import com.lesliefernsby.textrpg.service.ActionAssessmentService;
import com.lesliefernsby.textrpg.service.OpenAIService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class ActionAssessmentServiceImpl implements ActionAssessmentService {

    @Value("${game.attributes-skills.path}")
    private Resource attributesSkillsResource;

    @Value("${game.prompt-template.path}")
    private Resource promptTemplateResource;

    private final OpenAIService openAIService;
    private final ObjectMapper objectMapper;

    public ActionAssessmentServiceImpl(OpenAIService openAIService, ObjectMapper objectMapper) {
        this.openAIService = openAIService;
        this.objectMapper = objectMapper;
    }

    @Override
    public Map<String, Object> assessAction(String userAction) {
        try {
            Map<String, List<String>> attributesSkills = objectMapper.readValue(
                attributesSkillsResource.getInputStream(), Map.class
            );
            List<String> attributes = attributesSkills.get("attributes");
            List<String> skills = attributesSkills.get("skills");

            String promptTemplate = StreamUtils.copyToString(
                promptTemplateResource.getInputStream(), StandardCharsets.UTF_8
            );

            JSONArray messages = new JSONArray();
            
            String systemPrompt = promptTemplate
                .replace("{attributes}", String.join(", ", attributes))
                .replace("{skills}", String.join(", ", skills));

            messages.put(new JSONObject().put("role", "system").put("content", systemPrompt));
            messages.put(new JSONObject().put("role", "user").put("content", "The player wants to perform the following action:\n\nAction: " + userAction));

            Map<String, Object> response = openAIService.getResponseAsJson(
                messages, "gpt-3.5-turbo", 200, 0.1, null
            );

            return response;
        } catch (Exception e) {
            throw new RuntimeException("Failed to assess action", e);
        }
    }
}

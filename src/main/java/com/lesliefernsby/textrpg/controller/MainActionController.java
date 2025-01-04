package com.lesliefernsby.textrpg.controller;

import com.lesliefernsby.textrpg.service.ActionAssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/game")
public class MainActionController {

    private final ActionAssessmentService actionAssessmentService;

    @Autowired
    public MainActionController(ActionAssessmentService actionAssessmentService) {
        this.actionAssessmentService = actionAssessmentService;
    }

    @PostMapping("/action")
    public Map<String, Object> handlePlayerAction(@RequestBody Map<String, String> request) {
        String action = request.get("action");
        if (action == null || action.trim().isEmpty()) {
            throw new IllegalArgumentException("Action must be provided");
        }

        // Call the ActionAssessmentService to get the action assessment (for now, return this as the result)
        return actionAssessmentService.assessAction(action.trim());
    }
}

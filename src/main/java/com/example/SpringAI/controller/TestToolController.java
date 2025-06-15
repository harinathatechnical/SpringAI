package com.example.SpringAI.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TestToolController {

    private final ChatClient chatClient;

    public TestToolController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/mongo/{message}")
    public ResponseEntity<?> listOfDataBase(@PathVariable String message) {

        String systemPrompt = """
You are a MongoDB assistant. Only respond to queries that involve MongoDB operations such as listing databases, querying collections, or executing JSON-based queries.
If the user asks a general question, respond with: "This assistant only supports MongoDB tool-based queries."
""";

        Prompt prompt = new Prompt(systemPrompt + "\n\n" + "Please return the result as a valid JSON array without any markdown formatting or code blocks." + message);
        ChatResponse response = chatClient.prompt(prompt).call().chatResponse();
        String text = response.getResult().getOutput().getText();

        try {
            ObjectMapper mapper = new ObjectMapper();

            // Clean up any Markdown or code block formatting
            String cleaned = text.replaceAll("(?s)```json|```", "").trim();

            // Try parsing as a List
            if (cleaned.startsWith("[")) {
                List<Map<String, Object>> list = mapper.readValue(cleaned, List.class);
                return ResponseEntity.ok(list);
            } else if (cleaned.startsWith("{")) {
                Map<String, Object> map = mapper.readValue(cleaned, Map.class);
                return ResponseEntity.ok(map);
            } else {
                return ResponseEntity.ok(Map.of("message", cleaned));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Failed to parse response", "details", e.getMessage()));
        }
    }
}
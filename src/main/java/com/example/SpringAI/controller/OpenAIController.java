package com.example.SpringAI.controller;

import com.example.SpringAI.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/openai")
@CrossOrigin("*")
@RequiredArgsConstructor
public class OpenAIController {

    private final ChatService chatService;

    @GetMapping("/{message}")
    public Mono<String> getOpenAIResponse(@PathVariable("message") String m) {
        return chatService.chat(m)
                .flatMap(chatService::chat);
    }
}

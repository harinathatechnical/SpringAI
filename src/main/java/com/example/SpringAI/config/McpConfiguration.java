package com.example.SpringAI.config;

import com.example.SpringAI.service.MongoServiceClient;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfiguration {

/*    @Bean
    public ToolCallbackProvider mongoTools(MongoServiceClient mongoServiceClient) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(mongoServiceClient)
                .build();
    }*/

    @Bean
    public ChatClient chatClient(ChatModel chatModel, MongoServiceClient mongoServiceClient) {
        return  ChatClient.builder(chatModel)
                .defaultTools(mongoServiceClient)
                .build();
    }

}
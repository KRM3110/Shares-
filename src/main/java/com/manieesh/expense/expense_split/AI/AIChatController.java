package com.manieesh.expense.expense_split.AI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/ai")
public class AIChatController {
    private final LLMService llmService;

    @Autowired
    public AIChatController(LLMService llmService) {
        this.llmService = llmService;
    }

    @PostMapping("/chat")
    public String chat(@RequestBody Map<String, String> body) {
        String prompt = body.get("prompt");
        return llmService.chatWithLLM(prompt);
    }
}

package com.manieesh.expense.expense_split.Controller;

import com.manieesh.expense.expense_split.Model.Message;
import com.manieesh.expense.expense_split.Model.MessageStatus;
import com.manieesh.expense.expense_split.Service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/groups/{groupId}/messages")
public class MessageController {

    public final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService){
        this.messageService=messageService;
    }

    @GetMapping
    public List<Message> getMessageByGroup(@PathVariable UUID groupId){
        return messageService.getMessagesByGroup(groupId);
    }

    @PostMapping
    public Message sendMessage(
            @PathVariable UUID groupId,
            @RequestParam UUID userId,
            @RequestParam String content){
        return messageService.sendMessage(groupId,userId,content);
    }

}

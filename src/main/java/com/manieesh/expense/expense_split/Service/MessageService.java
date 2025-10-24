package com.manieesh.expense.expense_split.Service;

import com.manieesh.expense.expense_split.Model.Groups;
import com.manieesh.expense.expense_split.Model.Message;
import com.manieesh.expense.expense_split.Model.User;
import com.manieesh.expense.expense_split.Repository.GroupRepository;
import com.manieesh.expense.expense_split.Repository.MessageRepository;
import com.manieesh.expense.expense_split.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.manieesh.expense.expense_split.Model.MessageStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository,
                          GroupRepository groupRepository,
                          UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }
    public Message sendMessage(UUID groupId, UUID userId, String content) {
        // logic will go here
        Optional<Groups> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isEmpty()) {
            throw new IllegalStateException("Group not found");
        }
        Groups group = groupOptional.get();
        Optional<User> userOptional=userRepository.findById(userId);
        if (userOptional.isEmpty()){
            throw new IllegalStateException("User not found");
        }
        User user=userOptional.get();
        if(!group.getMembers().contains(user)) {
            throw  new IllegalStateException("User not in this group");
        }
        Message message = Message.builder()
                .content(content)
                .group(group)
                .sender(user)
                .isRead(MessageStatus.UNREAD)
                .createdAt(LocalDateTime.now())
                .build();
        messageRepository.save(message);


        return message;
    }

    public List<Message> getMessagesByGroup(UUID groupId) {
        // logic will go here
        Optional<Groups> groupOptional = groupRepository.findById(groupId);
        if (groupOptional.isEmpty()) {
            throw new IllegalStateException("Group not found");
        }
        Groups group = groupOptional.get();
        messageRepository.findByGroupGroupIdOrderByCreatedAtAsc(groupId);

        return messageRepository.findByGroupGroupIdOrderByCreatedAtAsc(groupId);
    }


}

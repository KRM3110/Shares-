package com.manieesh.expense.expense_split.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "Messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID messageId;
    private String content;
    @ManyToOne
    @JoinColumn(name="sender" , referencedColumnName = "userId")
    private User sender;
    @ManyToOne
    @JoinColumn(name="group_id" , referencedColumnName = "groupId")
    private Groups group;
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private MessageStatus isRead;
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

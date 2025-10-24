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
@Table(name= "expense_share")
public class ExpenseShare {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID shareId;
    @ManyToOne
    @JoinColumn(name = "expense" , referencedColumnName = "expenseId")
    private Expense expense;
    @ManyToOne
    @JoinColumn(name = "user_id" , referencedColumnName = "userId")
    private User user;

    private double shareAmount;
    private double percentage;

    @Enumerated(EnumType.STRING)
    private SplitType split;
    private Boolean isSettled ;
    private LocalDateTime createdAt;
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

package com.manieesh.expense.expense_split.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID expenseId;
    private String title;
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "group_id" , referencedColumnName = "groupId")
    private Groups group;
    @ManyToOne
    @JoinColumn(name = "paidBy" , referencedColumnName = "userId")
    private User paidBy;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "expense" , cascade = CascadeType.ALL , orphanRemoval = true)
    private Set<ExpenseShare> shares;
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

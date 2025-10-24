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
@Table(name = "payments")
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID paymentId;

    private double amount;
    @Enumerated(EnumType.STRING)
    private PaymentType paymentMethod;

    @ManyToOne
    @JoinColumn(name = "payer" , referencedColumnName = "userId")
    private User payer;

    @ManyToOne
    @JoinColumn(name = "expense" , referencedColumnName = "expenseId")
    private Expense expense;
    @ManyToOne
    @JoinColumn(name = "receiver", referencedColumnName = "userId")
    private User receiver;

    private Boolean isConfirmed;
    private LocalDateTime createdAt;
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
}

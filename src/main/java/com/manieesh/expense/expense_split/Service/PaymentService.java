package com.manieesh.expense.expense_split.Service;

import com.manieesh.expense.expense_split.Model.Groups;
import com.manieesh.expense.expense_split.Model.PaymentType;
import com.manieesh.expense.expense_split.Model.Payments;
import com.manieesh.expense.expense_split.Model.User;
import com.manieesh.expense.expense_split.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaymentService {
    public final GroupRepository groupRepository;
    public final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseShareRepository expenseShareRepository;
    public  final PaymentRepository paymentRepository;

    @Autowired
    public PaymentService(ExpenseRepository expenseRepository,
                               ExpenseShareRepository expenseShareRepository,
                               GroupRepository groupRepository,
                               UserRepository userRepository,
                               PaymentRepository paymentRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseShareRepository = expenseShareRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.paymentRepository = paymentRepository;
    }

    public Payments addPayment(UUID payerId, UUID receiverId, UUID groupId,
                               UUID expenseId, Double amount, PaymentType paymentType){
        User payer = userRepository.findById(payerId)
                .orElseThrow(() -> new IllegalStateException("Payer not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalStateException("Receiver not found"));

        Groups group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalStateException("Group not found"));

        if (!group.getMembers().contains(payer) || !group.getMembers().contains(receiver)) {
            throw new IllegalStateException("Both payer and receiver must belong to this group");
        }

        Payments payment = new Payments();
        payment.setAmount(amount);
        payment.setPayer(payer);
        payment.setReceiver(receiver);
        payment.setPaymentMethod(paymentType);
        payment.setIsConfirmed(false);
        payment.setExpense(expenseRepository.findByExpenseId(expenseId)
                .orElseThrow(() -> new IllegalStateException("Expense not found")));

        return paymentRepository.save(payment);

    }
    public Payments confirmPayment(UUID paymentId) {
        Payments payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalStateException("Payment not found"));
        payment.setIsConfirmed(true);
        return paymentRepository.save(payment);
    }

    // 3️⃣ Get all payments in a group
    public List<Payments> getPaymentsByGroup(UUID groupId) {
        return paymentRepository.findByExpenseGroupGroupId(groupId);
    }
}

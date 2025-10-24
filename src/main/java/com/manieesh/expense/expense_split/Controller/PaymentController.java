package com.manieesh.expense.expense_split.Controller;

import com.manieesh.expense.expense_split.Model.PaymentType;
import com.manieesh.expense.expense_split.Model.Payments;
import com.manieesh.expense.expense_split.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments")
    public Payments addPayment(@RequestParam UUID payerId,
                               @RequestParam UUID receiverId,
                               @RequestParam UUID groupId,
                               @RequestParam UUID expenseId,
                               @RequestParam Double amount,
                               @RequestParam PaymentType paymentType) {
        return paymentService.addPayment(payerId, receiverId, groupId, expenseId, amount, paymentType);
    }

    @PatchMapping("/payments/{paymentId}/confirm")
    public Payments confirmPayment(@PathVariable UUID paymentId) {
        return paymentService.confirmPayment(paymentId);
    }

    @GetMapping("/groups/{groupId}/payments")
    public List<Payments> getPaymentsByGroup(@PathVariable UUID groupId) {
        return paymentService.getPaymentsByGroup(groupId);
    }
}


package com.manieesh.expense.expense_split.Service;

import com.manieesh.expense.expense_split.Model.Groups;
import com.manieesh.expense.expense_split.Model.User;
import com.manieesh.expense.expense_split.Repository.ExpenseRepository;
import com.manieesh.expense.expense_split.Repository.ExpenseShareRepository;
import com.manieesh.expense.expense_split.Repository.GroupRepository;
import com.manieesh.expense.expense_split.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class SummaryService {
        private final GroupRepository groupRepository;
        private final ExpenseRepository expenseRepository;
        private final ExpenseShareRepository expenseShareRepository;
        private final PaymentRepository paymentRepository;

        @Autowired
        public SummaryService(GroupRepository groupRepository,
                              ExpenseRepository expenseRepository,
                              ExpenseShareRepository expenseShareRepository,
                              PaymentRepository paymentRepository) {
            this.groupRepository = groupRepository;
            this.expenseRepository = expenseRepository;
            this.expenseShareRepository = expenseShareRepository;
            this.paymentRepository = paymentRepository;
        }

        public Map<String, Double> calculateNetBalances(UUID groupId) {
            Groups group=groupRepository.findById(groupId)
                    .orElseThrow(() -> new IllegalStateException("Group not found"));
            Map<User, Double> balances = new HashMap<>();
            for (User member : group.getMembers()) {
                balances.put(member, 0.0);
            }
            expenseRepository.findByGroupGroupId(groupId)
                    .forEach(expense -> {
                        User payer = expense.getPaidBy();
                        balances.put(payer, balances.get(payer) + expense.getAmount());
                    });
            expenseShareRepository.findAll().stream()
                    .filter(share -> share.getExpense().getGroup().getGroupId().equals(groupId))
                    .forEach(share -> {
                        User user = share.getUser();
                        balances.put(user, balances.get(user) - share.getShareAmount());
                    });

            paymentRepository.findByExpenseGroupGroupId(groupId)
                    .forEach(payment -> {
                        User payer = payment.getPayer();
                        User receiver = payment.getReceiver();
                        double amount = payment.getAmount();

                        balances.put(payer, balances.get(payer) - amount);
                        balances.put(receiver, balances.get(receiver) + amount);
                    });
            Map<String, Double> finalBalances = new HashMap<>();
            for (Map.Entry<User, Double> entry : balances.entrySet()) {
                finalBalances.put(entry.getKey().getName(), entry.getValue());
            }
            return finalBalances;
        }
}

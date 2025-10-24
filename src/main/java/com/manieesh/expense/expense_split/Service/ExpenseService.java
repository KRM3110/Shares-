package com.manieesh.expense.expense_split.Service;


import com.manieesh.expense.expense_split.Model.*;
import com.manieesh.expense.expense_split.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExpenseService {
    public final GroupRepository groupRepository;
    public final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseShareRepository expenseShareRepository;
    public final ExpenseShareService expenseShareService;

    @Autowired
    public ExpenseService(ExpenseRepository expenseRepository,
                          ExpenseShareRepository expenseShareRepository,
                          GroupRepository groupRepository,
                          UserRepository userRepository,
                          ExpenseShareService expenseShareService) {
        this.expenseRepository = expenseRepository;
        this.expenseShareRepository = expenseShareRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.expenseShareService = expenseShareService;
    }

    public Expense addExpense(UUID groupId, UUID paidByUserId, String title, Double amount, SplitType splitType, Map<UUID, Double> inputData) {
        Groups group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalStateException("Group not found"));

        User user = userRepository.findById(paidByUserId)
                .orElseThrow(() -> new IllegalStateException("User not found"));

        if (!group.getMembers().contains(user)) {
            throw new IllegalStateException("User not in this group");
        }
        Expense expense = new Expense();
        expense.setTitle(title);
        expense.setAmount(amount);
        expense.setGroup(group);
        expense.setPaidBy(user);
        expenseRepository.save(expense);
        if (inputData == null) {
            inputData = new HashMap<>();
        }
        Set<ExpenseShare> shares = expenseShareService.calculateShares(expense, splitType, inputData);
        expense.setShares(shares);
        return expense;



    }

    public List<Expense> getExpensesByGroup(UUID groupId) {
        return expenseRepository.findByGroupGroupId(groupId);
    }
}


package com.manieesh.expense.expense_split.Service;

import com.manieesh.expense.expense_split.Model.*;
import com.manieesh.expense.expense_split.Repository.ExpenseRepository;
import com.manieesh.expense.expense_split.Repository.ExpenseShareRepository;
import com.manieesh.expense.expense_split.Repository.GroupRepository;
import com.manieesh.expense.expense_split.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ExpenseShareService {
    public final GroupRepository groupRepository;
    public final UserRepository userRepository;
    private final ExpenseRepository expenseRepository;
    private final ExpenseShareRepository expenseShareRepository;

    @Autowired
    public ExpenseShareService(ExpenseRepository expenseRepository,
                               ExpenseShareRepository expenseShareRepository,
                               GroupRepository groupRepository,
                               UserRepository userRepository) {
        this.expenseRepository = expenseRepository;
        this.expenseShareRepository = expenseShareRepository;
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    public Set<ExpenseShare> calculateShares(Expense expense, SplitType splitType, Map<UUID, Double> inputData) {
        Set<ExpenseShare> shares = new HashSet<>();
        if (splitType == SplitType.Exact) {
            double total = 0;
            for (Double share : inputData.values()) {
                total += share;

            }
            if (total != expense.getAmount()) {
                throw new IllegalStateException("Invalid split: total share doesn't match expense amount");
            }

            for (Map.Entry<UUID, Double> entry : inputData.entrySet()) {
                UUID userId = entry.getKey();
                Double shareAmount = entry.getValue();
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalStateException("User not found"));
                ExpenseShare expenseShare = new ExpenseShare();
                expenseShare.setExpense(expense);
                expenseShare.setUser(user);
                expenseShare.setShareAmount(shareAmount);
                expenseShare.setIsSettled(false);
                expenseShare.setSplit(splitType);
                expenseShareRepository.save(expenseShare);
                shares.add(expenseShare);
            }

        } else if (splitType == SplitType.Percentage) {
            double total = 0;
            for (Double share : inputData.values()) {
                total += share;
            }
            if (total != 100.00) {
                throw new IllegalStateException("Invalid split: total share doesn't match expense amount");
            }

            for (Map.Entry<UUID, Double> entry : inputData.entrySet()) {
                UUID userId = entry.getKey();
                Double percent = entry.getValue();
                User user = userRepository.findById(userId)
                        .orElseThrow(() -> new IllegalStateException("User not found"));
                ExpenseShare expenseShare = new ExpenseShare();
                double shareAmount = expense.getAmount() * (percent / 100);
                expenseShare.setExpense(expense);
                expenseShare.setUser(user);
                expenseShare.setShareAmount(shareAmount);
                expenseShare.setIsSettled(false);
                expenseShare.setSplit(splitType);
                expenseShareRepository.save(expenseShare);
                shares.add(expenseShare);

            }
        } else {
            Groups group = expense.getGroup();
            expenseRepository.save(expense);
            Set<User> members = group.getMembers();
            int noOfMembers = group.getMembers().size();
            double equalShare = expense.getAmount() / noOfMembers;
            for (User member : members) {
                ExpenseShare expenseShare = new ExpenseShare();
                expenseShare.setExpense(expense);
                expenseShare.setSplit(splitType);
                expenseShare.setShareAmount(equalShare);
                expenseShare.setIsSettled(false);
                expenseShare.setUser(member);

                expenseShareRepository.save(expenseShare);
                shares.add(expenseShare);
            }
            expense.setShares(shares);
        }


        return shares;
    }

    public ExpenseShare updateShareStatus(UUID shareId, boolean isSettled){
        ExpenseShare share = expenseShareRepository.findById(shareId)
                .orElseThrow(() -> new IllegalStateException("Share not found"));
        share.setIsSettled(isSettled);
        return expenseShareRepository.save(share);
    }

    public List<ExpenseShare> getSharesByExpense(UUID expenseId){
        return expenseShareRepository.findByExpenseExpenseId(expenseId);
    }
}

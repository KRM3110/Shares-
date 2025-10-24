package com.manieesh.expense.expense_split.Repository;


import com.manieesh.expense.expense_split.Model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {
    List<Expense> findByGroupGroupId(UUID groupId);
    Optional<Expense> findByExpenseId(UUID expenseId);
}

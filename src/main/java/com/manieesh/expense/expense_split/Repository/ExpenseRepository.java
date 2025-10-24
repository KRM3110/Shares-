package com.manieesh.expense.expense_split.Repository;


import com.manieesh.expense.expense_split.Model.Expense;
import com.manieesh.expense.expense_split.Model.ExpenseShare;
import com.manieesh.expense.expense_split.Model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Groups, UUID> {
    public List<Expense> findByGroupGroupId(UUID groupId);
    Optional<Expense> findByExpenseId(UUID expenseId);

    void save(Expense expense);
}

package com.manieesh.expense.expense_split.Repository;

import com.manieesh.expense.expense_split.Model.ExpenseShare;
import com.manieesh.expense.expense_split.Model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseShareRepository extends JpaRepository<ExpenseShare, UUID> {
    public ExpenseShare save(ExpenseShare expenseShare);
    public List<ExpenseShare> findByExpenseExpenseId(UUID expenseId);
    public Optional<ExpenseShare> findById(UUID shareId);

}

package com.manieesh.expense.expense_split.Repository;

import com.manieesh.expense.expense_split.Model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaymentRepository extends JpaRepository<Payments, UUID> {
    List<Payments> findByExpenseGroupGroupId(UUID groupId);
}

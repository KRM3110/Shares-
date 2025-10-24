package com.manieesh.expense.expense_split.Repository;

import com.manieesh.expense.expense_split.Model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface GroupRepository extends JpaRepository<Groups, UUID>  {
    Optional<Groups> findByGroupId(UUID groupId);


}


package com.manieesh.expense.expense_split.Controller;


import com.manieesh.expense.expense_split.Model.Expense;
import com.manieesh.expense.expense_split.Model.SplitType;
import com.manieesh.expense.expense_split.Service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/groups/{groupId}/expenses")
public class ExpenseController {
    public final ExpenseService expenseService;

    @Autowired
    public  ExpenseController(ExpenseService expenseService)
    {
        this.expenseService=expenseService;
    }

    @PostMapping
    public Expense addExpense(@PathVariable UUID groupId,
                              @RequestParam UUID paidByUserId,
                              @RequestParam String title,
                              @RequestParam Double amount,
                              @RequestParam SplitType splitType,
                              @RequestBody(required = false) Map<UUID, Double> inputData){
        return expenseService.addExpense(groupId,paidByUserId,title,amount,splitType,inputData);
    }

    @GetMapping
    public List<Expense> getExpensesByGroup(@PathVariable UUID groupId){
        return expenseService.getExpensesByGroup(groupId);
    }

}

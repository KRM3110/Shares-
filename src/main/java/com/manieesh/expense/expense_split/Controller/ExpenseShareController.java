package com.manieesh.expense.expense_split.Controller;

import com.manieesh.expense.expense_split.Model.Expense;
import com.manieesh.expense.expense_split.Model.ExpenseShare;
import com.manieesh.expense.expense_split.Model.SplitType;
import com.manieesh.expense.expense_split.Service.ExpenseShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ExpenseShareController {
    public final ExpenseShareService expenseShareService;
    @Autowired
    public ExpenseShareController(ExpenseShareService expenseShareService){
        this.expenseShareService=expenseShareService;
    }


    @PatchMapping("/shares/{shareId}/status")
    public ExpenseShare updateShareStatus(
            @PathVariable UUID shareId,
            @RequestParam boolean isSettled) {
        return expenseShareService.updateShareStatus(shareId, isSettled);
    }

    @GetMapping("/expenses/{expenseId}/shares")
    public List<ExpenseShare> getSharesByExpense(@PathVariable UUID expenseId){
        return expenseShareService.getSharesByExpense(expenseId);
    }

}

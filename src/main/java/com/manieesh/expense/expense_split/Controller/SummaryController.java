package com.manieesh.expense.expense_split.Controller;

import com.manieesh.expense.expense_split.Service.SummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/groups")
public class SummaryController {

        private final SummaryService summaryService;

        @Autowired
        public SummaryController(SummaryService summaryService) {
            this.summaryService = summaryService;
        }

        @GetMapping("/{groupId}/summary")
        public Map<String, Double> getSummary(@PathVariable UUID groupId) {
            return summaryService.calculateNetBalances(groupId);
        }
    }


package com.spark.toy.controller;

import com.spark.toy.dto.SubscriptionDto;
import com.spark.toy.service.SubscriptionService;
import com.spark.toy.service.SubscriptionStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/main")
@PreAuthorize("hasAnyAuthority('ROLE_USER')")
@RequiredArgsConstructor
public class ViewMainController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionStatisticsService subscriptionStatisticsService;

    @GetMapping
    public String mainView(Model model) {
        List<SubscriptionDto> subscriptionDtos = subscriptionService.getSubscriptionsWithIsProceeded(false);
        var statistics = subscriptionStatisticsService.findTodayStatistics();

        var successRate = (double)statistics.getSucceeded()/(double)statistics.getTotalCount() * 100;

        model.addAttribute("totalCount", statistics.getTotalCount());
        model.addAttribute("completed", statistics.getSucceeded());
        model.addAttribute("successRate", Math.round(successRate) + "%");
        model.addAttribute("notProceeded", statistics.getNotProceeded());
        model.addAttribute("subscriptions", subscriptionDtos);
        return "index";
    }

}

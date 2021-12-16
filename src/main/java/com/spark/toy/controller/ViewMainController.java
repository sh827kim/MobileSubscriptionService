package com.spark.toy.controller;

import com.spark.toy.dto.SubscriptionDto;
import com.spark.toy.service.SubscriptionService;
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

    @GetMapping
    public String mainView(Model model) {
        List<SubscriptionDto> subscriptionDtos = subscriptionService.getSubscriptionsWithIsProceeded(false);

        model.addAttribute("subscriptions", subscriptionDtos);
        return "index";
    }

}

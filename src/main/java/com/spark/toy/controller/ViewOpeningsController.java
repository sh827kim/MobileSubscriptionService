package com.spark.toy.controller;

import com.spark.toy.dto.SubscriptionDto;
import com.spark.toy.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/openings")
@PreAuthorize("hasAnyAuthority('ROLE_USER')")
@RequiredArgsConstructor
@Slf4j
public class ViewOpeningsController {

    private final SubscriptionService subscriptionService;

    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @GetMapping
    public String openingsView(Model model) {
        List<SubscriptionDto> subscriptionDtos = subscriptionService.getSubscriptions();
        subscriptionDtos.forEach(dto -> log.info(dto.toString()));
        model.addAttribute("openingList", subscriptionDtos);

        return "openings";
    }
}

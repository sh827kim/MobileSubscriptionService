package com.spark.toy.controller;

import com.spark.toy.dto.CustomerDto;
import com.spark.toy.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/customers")
@PreAuthorize("hasAnyAuthority('ROLE_USER')")
@RequiredArgsConstructor
public class ViewCustomerController {

    private final CustomerService customerService;

    @GetMapping
    public String customerView(Model model) {

        List<CustomerDto> customerDtos = customerService.getCustomers();

        model.addAttribute("customerList", customerDtos);
        return "customers";
    }
}

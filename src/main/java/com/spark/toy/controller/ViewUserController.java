package com.spark.toy.controller;

import com.spark.toy.dto.EmployeeDto;
import com.spark.toy.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class ViewUserController {

    private final EmployeeService employeeService;

    @GetMapping
    public String userView(Model model) {

        List<EmployeeDto> employeeDtos = employeeService.getEmployees();

        model.addAttribute("userList", employeeDtos);

        return "users";
    }
}

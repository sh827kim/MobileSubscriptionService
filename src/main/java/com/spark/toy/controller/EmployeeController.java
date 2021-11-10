package com.spark.toy.controller;

import com.spark.toy.dto.EmployeeDto;
import com.spark.toy.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    private static final Integer DEFAULT_PAGE_SIZE = 10;

    @GetMapping("/all")
    public ResponseEntity<List<EmployeeDto>> getEmployees(@RequestParam int pageNo, @RequestParam(required = false) Integer pageSize) {
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;

        List<EmployeeDto> employeeDtoList = employeeService.getEmployeeWithPageNumber(pageNo, pageSize);

        return ResponseEntity.ok(employeeDtoList);
    }

    @GetMapping
    public ResponseEntity<EmployeeDto> getEmployeeByAccount(@RequestParam String account) {
        EmployeeDto employeeDto = employeeService.getEmployeeByAccount(account);

        return ResponseEntity.ok(employeeDto);
    }

    @PostMapping
    public ResponseEntity<EmployeeDto> createEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto employeeDtoResponse = employeeService.createEmployee(employeeDto);

        return ResponseEntity.ok(employeeDtoResponse);
    }

    @PutMapping
    public ResponseEntity<EmployeeDto> updateEmployee(@RequestBody EmployeeDto employeeDto) {
        EmployeeDto employeeDtoResponse = employeeService.updateEmployee(employeeDto);

        return ResponseEntity.ok(employeeDtoResponse);
    }

    @DeleteMapping
    public ResponseEntity deleteEmployeeByAccount(@RequestParam String account) {
        employeeService.deleteEmployeeByAccount(account);

        return ResponseEntity.ok().build();
    }
}

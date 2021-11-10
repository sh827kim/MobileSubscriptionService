package com.spark.toy.controller;

import com.spark.toy.dto.CustomerDto;
import com.spark.toy.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    private static final Integer DEFAULT_PAGE_SIZE =10;

    @GetMapping("/all")
    public ResponseEntity<List<CustomerDto>> getCustomers(@RequestParam int pageNo, @RequestParam(required = false) Integer pageSize) {
        pageSize = pageSize==null ? DEFAULT_PAGE_SIZE : pageSize;

        List<CustomerDto> customerDtoList = customerService.getCustomersWithPageNumber(pageNo, pageSize);

        return ResponseEntity.ok(customerDtoList);
    }

    @GetMapping
    public ResponseEntity<CustomerDto> getCustomerByAccount(@RequestParam String account) {
        CustomerDto customerDto = customerService.getCustomerByAccount(account);

        return ResponseEntity.ok(customerDto);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto customerDtoResponse = customerService.createCustomer(customerDto);

        return  ResponseEntity.ok(customerDtoResponse);
    }

    @PutMapping
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerDto customerDto) {
        CustomerDto customerDtoResponse = customerService.updateCustomer(customerDto);

        return ResponseEntity.ok(customerDtoResponse);
    }

    @DeleteMapping
    public ResponseEntity deleteCustomerByAccount(@RequestParam String account) {

        customerService.deleteCustomerByAccount(account);

        return ResponseEntity.ok().build();
    }
}

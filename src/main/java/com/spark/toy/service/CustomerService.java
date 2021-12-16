package com.spark.toy.service;

import com.spark.toy.domain.Customer;
import com.spark.toy.dto.CustomerDto;
import com.spark.toy.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<CustomerDto> getCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return entityListToDtoList(customers);
    }

    public List<CustomerDto> getCustomersWithPageNumber(int pageNo, int pageSize) {
        Page<Customer> pagedCustomers = customerRepository.findAll(PageRequest.of(pageNo, pageSize));
        return entityListToDtoList(pagedCustomers.toList());
    }

    public CustomerDto getCustomerByAccount(String account) {
        Customer customer = customerRepository.findByAccount(account).orElse(null);

        return customer!=null ? CustomerDto.toDto(customer): null;
    }

    public CustomerDto createCustomer(CustomerDto customerDto) {
        Customer customer = CustomerDto.toEntity(customerDto);
        customer = customerRepository.save(customer);

        return CustomerDto.toDto(customer);
    }



    public CustomerDto updateCustomer(CustomerDto customerDto) {
        Long customerId = customerRepository.findByAccount(customerDto.getAccount()).orElseThrow(RuntimeException::new).getId();
        Customer customer = CustomerDto.toEntity(customerDto);
        customer.setId(customerId);
        customer = customerRepository.save(customer);

        return CustomerDto.toDto(customer);
    }

    public void deleteCustomerByAccount(String account) {
        log.info("account : {}", account);
        customerRepository.deleteByAccount(account);
    }


    private List<CustomerDto> entityListToDtoList(List<Customer> customers) {
        return customers.stream().map(CustomerDto::toDto).collect(Collectors.toList());
    }
}

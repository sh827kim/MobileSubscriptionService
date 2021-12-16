package com.spark.toy.dto;

import com.spark.toy.domain.Customer;
import com.spark.toy.dto.base.UserBaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto extends UserBaseDto {

    private String birth;

    private List<SubscriptionDto> subscriptions = new ArrayList<>();

    public static CustomerDto toDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setCreatedAt(customer.getCreatedAt());
        customerDto.setUpdatedAt(customer.getUpdatedAt());
        customerDto.setAccount(customer.getAccount());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setPassword(customer.getPassword());
        customerDto.setBirth(customer.getBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        customerDto.setSubscriptions(customer.getSubscriptions().stream().map(sub -> SubscriptionDto.toDto(sub)).collect(Collectors.toList()));
        return customerDto;
    }

    public static Customer toEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setCreatedAt(customerDto.getCreatedAt());
        customer.setUpdatedAt(customerDto.getUpdatedAt());
        customer.setAccount(customerDto.getAccount());
        customer.setName(customerDto.getName());
        customer.setBirth(LocalDate.parse(customerDto.getBirth(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setPassword(customerDto.getPassword());
        customer.setSubscriptions(customerDto.getSubscriptions().stream().map(sub -> SubscriptionDto.toEntity(sub)).collect(Collectors.toList()));

        return customer;
    }
}

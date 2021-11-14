package com.spark.toy.dto;

import com.spark.toy.domain.Customer;
import com.spark.toy.dto.base.UserBaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto extends UserBaseDto {

    private LocalDate birth;

    public static CustomerDto toDto(Customer customer) {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setName(customer.getName());
        customerDto.setCreatedAt(customer.getCreatedAt());
        customerDto.setUpdatedAt(customer.getUpdatedAt());
        customerDto.setAccount(customer.getAccount());
        customerDto.setPhoneNumber(customer.getPhoneNumber());
        customerDto.setPassword(customer.getPassword());
        customerDto.setBirth(customer.getBirth());
        return customerDto;
    }

    public static Customer toEntity(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setCreatedAt(customerDto.getCreatedAt());
        customer.setUpdatedAt(customerDto.getUpdatedAt());
        customer.setAccount(customerDto.getAccount());
        customer.setName(customerDto.getName());
        customer.setBirth(customerDto.getBirth());
        customer.setPhoneNumber(customerDto.getPhoneNumber());
        customer.setPassword(customerDto.getPassword());

        return customer;
    }
}

package com.spark.toy.dto;

import com.spark.toy.domain.Employee;
import com.spark.toy.domain.enums.Role;
import com.spark.toy.dto.base.UserBaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto extends UserBaseDto {

    private Role role;

    @Email
    private String email;

    public static EmployeeDto toDto(Employee employee) {
        EmployeeDto employeeDto = new EmployeeDto(employee.getRole(), employee.getEmail());
        employeeDto.setAccount(employee.getAccount());
        employeeDto.setName(employee.getName());
        employeeDto.setPassword(employee.getPassword());
        employeeDto.setPhoneNumber(employee.getPhoneNumber());
        employeeDto.setCreatedAt(employee.getCreatedAt());
        employeeDto.setUpdatedAt(employee.getUpdatedAt());

        return employeeDto;
    }

    public static Employee toEntity(EmployeeDto employeeDto) {
        Employee employee = new Employee();
        employee.setRole(employeeDto.getRole());
        employee.setEmail(employeeDto.getEmail());
        employee.setAccount(employeeDto.getAccount());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setPassword(employeeDto.getPassword());
        employee.setCreatedAt(employeeDto.getCreatedAt());
        employee.setUpdatedAt(employeeDto.getUpdatedAt());
        employee.setName(employeeDto.getName());

        return employee;
    }
}

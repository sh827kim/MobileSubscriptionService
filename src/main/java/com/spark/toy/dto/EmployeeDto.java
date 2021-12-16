package com.spark.toy.dto;

import com.spark.toy.domain.Employee;
import com.spark.toy.domain.EmployeeAuthority;
import com.spark.toy.dto.base.UserBaseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.Email;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto extends UserBaseDto {

    private String role;

    @Email
    private String email;

    public static EmployeeDto toDto(Employee employee) {

        EmployeeDto employeeDto = new EmployeeDto(employee.getAuthorities().stream().findFirst().map(EmployeeAuthority::getAuthority).orElse("ROLE_USER"), employee.getEmail());
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
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        employee.setEmail(employeeDto.getEmail());
        employee.setAccount(employeeDto.getAccount());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setPassword(encoder.encode(employeeDto.getPassword()));
        employee.setCreatedAt(employeeDto.getCreatedAt());
        employee.setUpdatedAt(employeeDto.getUpdatedAt());
        employee.setName(employeeDto.getName());

        return employee;
    }
}

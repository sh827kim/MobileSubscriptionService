package com.spark.toy.service;

import com.spark.toy.domain.Employee;
import com.spark.toy.dto.EmployeeDto;
import com.spark.toy.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    public List<EmployeeDto> getEmployeeWithPageNumber(int pageNo, int pageSize) {
        Page<Employee> pagedEmployees = employeeRepository.findAll(PageRequest.of(pageNo, pageSize));

        return entityListToDtoList(pagedEmployees.toList());
    }

    public EmployeeDto getEmployeeByAccount(String account) {
        Employee employee = employeeRepository.findByAccount(account).orElseThrow(RuntimeException::new);

        return EmployeeDto.toDto(employee);
    }

    public EmployeeDto createEmployee(EmployeeDto employeeDto) {
        Employee employee = EmployeeDto.toEntity(employeeDto);
        employee = employeeRepository.save(employee);

        return EmployeeDto.toDto(employee);
    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        Long employeeId = employeeRepository.findByAccount(employeeDto.getAccount()).orElseThrow(RuntimeException::new).getId();
        Employee employee = EmployeeDto.toEntity(employeeDto);
        employee.setId(employeeId);
        employee = employeeRepository.save(employee);

        return EmployeeDto.toDto(employee);
    }

    public void deleteEmployeeByAccount(String account) {
        Employee employee = employeeRepository.findByAccount(account).orElseThrow(RuntimeException::new);
        employeeRepository.delete(employee);
    }

    private List<EmployeeDto> entityListToDtoList(List<Employee> employees) {
        return employees.stream().map(EmployeeDto::toDto).collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employeeRepository.findByAccount(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}

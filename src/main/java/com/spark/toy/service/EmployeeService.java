package com.spark.toy.service;

import com.spark.toy.domain.Employee;
import com.spark.toy.domain.EmployeeAuthority;
import com.spark.toy.dto.EmployeeDto;
import com.spark.toy.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class EmployeeService implements UserDetailsService {
    private final EmployeeRepository employeeRepository;

    public List<EmployeeDto> getEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return entityListToDtoList(employees);
    }
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
        Long employeeId = employeeRepository.findByAccount(employeeDto.getAccount()).orElseGet(Employee::new).getId();
        employee.setId(employeeId);
        employee = employeeRepository.save(employee);
        employee = employeeRepository.save(setAuthority(employee, employeeDto.getRole()));

        return EmployeeDto.toDto(employee);
    }

    public EmployeeDto updateEmployee(EmployeeDto employeeDto) {
        Employee found = employeeRepository.findByAccount(employeeDto.getAccount()).orElseThrow(RuntimeException::new);
        Employee employee = EmployeeDto.toEntity(employeeDto);
        if(employeeDto.getPassword()==null || employeeDto.getPassword().equals("")) {
            employee.setPassword(found.getPassword());
        }
        employee.setId(found.getId());
        log.info("employee : {}", employee);
        employee = employeeRepository.save(setAuthority(employee, employeeDto.getRole()));

        return EmployeeDto.toDto(employee);
    }

    private Employee setAuthority(Employee employee, String role) {
        Set<EmployeeAuthority> authorities = new HashSet<>();
        EmployeeAuthority employeeAuthority = new EmployeeAuthority();
        employeeAuthority.setAuthority(role);
        employeeAuthority.setEmployeeId(employee.getId());

        authorities.add(employeeAuthority);
        employee.setAuthorities(authorities);

        return employee;
    }

    public void deleteEmployeeByAccount(String account) {
        Employee employee = employeeRepository.findByAccount(account).orElseThrow(RuntimeException::new);
        log.info("employee : {}", employee);
        employee.setAuthorities(null);
        employee = employeeRepository.save(employee);
        employeeRepository.delete(employee);
    }

    private List<EmployeeDto> entityListToDtoList(List<Employee> employees) {
        return employees.stream().map(EmployeeDto::toDto).toList();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Authentication userName : {}", username);
        return employeeRepository.findByAccount(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}

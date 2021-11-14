package com.spark.toy.repository;

import com.spark.toy.domain.Employee;
import com.spark.toy.domain.EmployeeAuthority;
import com.spark.toy.domain.enums.Role;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.history.Revisions;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(EmployeeRepositoryTest.class);

    private static String TEST_ACCOUNT = "spark@gmail.com";
    private static Role TEST_ROLE = Role.ROLE_ADMIN;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Order(1)
    void createTest() {
        Set<EmployeeAuthority> authorities = new HashSet<>();
        EmployeeAuthority authority = new EmployeeAuthority();
        authority.setAuthority(TEST_ROLE.name());
        authorities.add(authority);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Employee employee = new Employee();
        employee.setAccount(TEST_ACCOUNT);
        employee.setEmail("spark@gmail.com");
        employee.setName("Spark Kim");
        employee.setAuthorities(authorities);
        employee.setPhoneNumber("010-2345-6789");
        employee.setPassword(encoder.encode("!new1234"));
        log.info("before create : {}", employee);

        Employee employee1 = employeeRepository.save(employee);


        log.info("after create : {}", employee1);

        Assertions.assertEquals(employee.getAccount(), employee1.getAccount());
    }

    @Test
    @Order(2)
    void readTest() {
        Employee employee = employeeRepository.findByAccount(TEST_ACCOUNT).orElse(null);

        Assertions.assertNotNull(employee);
        Assertions.assertEquals(employee.getAccount(), TEST_ACCOUNT);
        log.info("read : {}", employee);
    }

    @Test
    @Order(3)
    void updateAndRevisionTest() {
        Employee employee = employeeRepository.findByAccount(TEST_ACCOUNT).orElseThrow(RuntimeException::new);
        log.info("before update : {}" ,employee);

        int beforeSize = employee.getAuthorities().size();
        EmployeeAuthority authority = new EmployeeAuthority();
        authority.setAuthority(Role.ROLE_SYSADMIN.name());
        Set<EmployeeAuthority> authorities = new HashSet<>(employee.getAuthorities());
        authorities.add(authority);
        employee.setAuthorities(authorities);

        Employee employee1 = employeeRepository.save(employee);

        log.info("after update : {}" ,employee1);

        Assertions.assertNotEquals(beforeSize, employee1.getAuthorities().size());

        Revisions<Long, Employee> revisions = employeeRepository.findRevisions(employee1.getId());

        revisions.forEach(revision ->
            log.info("after update revision: {}, no : {}" , revision.getEntity(), revision.getRevisionNumber().orElseThrow(RuntimeException::new))
        );
    }

    @Test
    @Order(4)
    void deleteTest() {
        Employee employee = employeeRepository.findByAccount(TEST_ACCOUNT).orElseThrow(RuntimeException::new);
        log.info("delete : {}", employee);

        employeeRepository.delete(employee);

        Assertions.assertFalse(employeeRepository.findByAccount(TEST_ACCOUNT).isPresent());
    }
}
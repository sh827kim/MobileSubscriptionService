package com.spark.toy.repository;

import com.spark.toy.domain.Employee;
import com.spark.toy.domain.enums.Role;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.history.Revisions;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeRepositoryTest {

    private static final Logger log = LoggerFactory.getLogger(EmployeeRepositoryTest.class);

    private static String TEST_ACCOUNT = "spark805";
    private static Role TEST_ROLE = Role.ADMIN;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    @Order(1)
    void createTest() {
        Employee employee = new Employee();
        employee.setAccount("spark805");
        employee.setEmail("spark@gmail.com");
        employee.setRole(Role.ADMIN);
        employee.setPhoneNumber("010-2345-6789");
        employee.setPassword("!new1234");
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

        employee.setRole(Role.SYSADMIN);

        Employee employee1 = employeeRepository.save(employee);

        log.info("after update : {}" ,employee1);

        Assertions.assertNotEquals(employee.getRole(), TEST_ROLE);

        Revisions<Long, Employee> revisions = employeeRepository.findRevisions(employee1.getId());

        revisions.forEach(revision -> {
            log.info("after update revision: {}, no : {}" , revision.getEntity(), revision.getRevisionNumber().orElseThrow(RuntimeException::new));
        });
    }

    @Test
    @Order(4)
    void revisionTest() {

    }

    @Test
    @Order(5)
    void deleteTest() {
        Employee employee = employeeRepository.findByAccount(TEST_ACCOUNT).orElseThrow(RuntimeException::new);
        log.info("delete : {}", employee);

        employeeRepository.delete(employee);

        Assertions.assertEquals(employeeRepository.findByAccount(TEST_ACCOUNT).isPresent(), false);
    }
}
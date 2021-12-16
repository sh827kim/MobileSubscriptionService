package com.spark.toy.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false, exclude = {"employee"})
@IdClass(EmployeeAuthority.class)
public class EmployeeAuthority implements GrantedAuthority {

    @ToString.Exclude
    @Id
    @Column(name = "employee_id")
    private Long employeeId;

    @Id
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}

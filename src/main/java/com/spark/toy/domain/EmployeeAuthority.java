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
public class EmployeeAuthority implements GrantedAuthority {

    @ManyToOne
    @ToString.Exclude
    private Employee employee;

    @Id
    private String authority;

    @Override
    public String getAuthority() {
        return authority;
    }
}

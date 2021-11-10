package com.spark.toy.domain;

import com.spark.toy.domain.base.UserBaseEntity;
import com.spark.toy.domain.enums.Role;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Audited
public class Employee extends UserBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Enumerated(value = EnumType.STRING)
    private Role role;
}

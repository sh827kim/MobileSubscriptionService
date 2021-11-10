package com.spark.toy.domain.base;

import com.spark.toy.domain.base.BaseEntity;
import lombok.Data;
import org.hibernate.envers.Audited;

import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
@Audited
public class UserBaseEntity extends BaseEntity {
    private String account;

    private String password;

    private String name;

    private String phoneNumber;
}

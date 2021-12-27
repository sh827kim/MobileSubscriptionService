package com.spark.toy.domain;

import com.spark.toy.domain.base.BaseEntity;
import lombok.*;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SubscriptionStatistics extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @UniqueElements
    private LocalDate date;

    private Integer totalCount;

    private Integer notProceeded;

    private Integer succeeded;

    private Integer failed;
}

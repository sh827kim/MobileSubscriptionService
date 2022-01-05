package com.spark.toy.domain;

import com.spark.toy.domain.base.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
public class SubscriptionStatistics extends BaseEntity {
    @Id
    private LocalDate date;

    private Integer totalCount;

    private Integer notProceeded;

    private Integer succeeded;

    private Integer failed;
}

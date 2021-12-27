package com.spark.toy.domain;

import com.spark.toy.domain.base.BaseEntity;
import com.spark.toy.domain.enums.SubscriptionCode;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SubscriptionRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "subscription_id")
    @ToString.Exclude
    private Subscription subscription;

    @Enumerated(value = EnumType.STRING)
    private SubscriptionCode requestSubscriptionCode;

    private Boolean isProceeded;

    private LocalDateTime requestedAt;

    private LocalDateTime proceededAt;
}

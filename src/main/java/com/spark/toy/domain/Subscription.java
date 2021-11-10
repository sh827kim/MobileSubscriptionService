package com.spark.toy.domain;

import com.spark.toy.domain.base.BaseEntity;
import com.spark.toy.domain.enums.DeviceType;
import com.spark.toy.domain.enums.SubscriptionCode;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Audited
public class Subscription extends BaseEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String usimNumber;

    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private DeviceType deviceType;

    @Enumerated(value = EnumType.STRING)
    private SubscriptionCode subscriptionCode;

    private LocalDateTime subscribedAt;

    private LocalDateTime expiredAt;

    @OneToOne(mappedBy = "subscription")
    @ToString.Exclude
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private SubscriptionRequest subscriptionRequest;

    @ManyToOne
    @ToString.Exclude
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Customer customer;
}

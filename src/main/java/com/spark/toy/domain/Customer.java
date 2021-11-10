package com.spark.toy.domain;

import com.spark.toy.domain.base.UserBaseEntity;
import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Customer extends UserBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Audited
    private Long id;

    @Audited
    private LocalDate birth;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "customer_id")
    @ToString.Exclude
    private List<Subscription> subscriptions = new ArrayList<>();


    public void addCustomerSubsciptions(Subscription... subscription) {
        Collections.addAll(this.subscriptions, subscription);
    }


}

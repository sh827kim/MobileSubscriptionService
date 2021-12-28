package com.spark.toy.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.spark.toy.domain.Subscription;
import com.spark.toy.domain.enums.DeviceType;
import com.spark.toy.domain.enums.SubscriptionCode;
import com.spark.toy.dto.base.BaseDto;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionDto extends BaseDto {

    private UUID id;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. 01x-xxx(x)-xxxx")
    private String phoneNumber;

    private DeviceType deviceType;

    private SubscriptionRequestDto subscriptionRequestDto;

    private SubscriptionCode subscriptionCode;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime subscribedAt;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime expiredAt;

    @NotNull
    private String customerAccount;

    public static  SubscriptionDto toDto(Subscription subscription) {
        SubscriptionDto subscriptionDto = SubscriptionDto.builder()
                .id(subscription.getId())
                .subscriptionCode(subscription.getSubscriptionCode())
                .deviceType(subscription.getDeviceType())
                .phoneNumber(subscription.getPhoneNumber())
                .subscribedAt(subscription.getSubscribedAt())
                .expiredAt(subscription.getExpiredAt())
                .subscriptionRequestDto(SubscriptionRequestDto.toDto(subscription.getSubscriptionRequest()))
                .build();
        subscriptionDto.setCreatedAt(subscription.getCreatedAt());
        subscriptionDto.setUpdatedAt(subscription.getUpdatedAt());

        return subscriptionDto;
    }

    public static Subscription toEntity(SubscriptionDto subscriptionDto) {
        Subscription subscription = new Subscription();
        subscription.setId(subscriptionDto.getId());
        subscription.setSubscriptionCode(subscriptionDto.getSubscriptionCode());
        subscription.setDeviceType(subscriptionDto.getDeviceType());
        subscription.setPhoneNumber(subscriptionDto.getPhoneNumber());
        subscription.setSubscribedAt(subscriptionDto.getSubscribedAt());
        subscription.setUpdatedAt(subscriptionDto.getUpdatedAt());
        subscription.setCreatedAt(subscriptionDto.getCreatedAt());
        subscription.setExpiredAt(subscriptionDto.getExpiredAt());
        subscription.setSubscriptionRequest(SubscriptionRequestDto.toEntity(subscriptionDto.getSubscriptionRequestDto()));

        return subscription;
    }
}

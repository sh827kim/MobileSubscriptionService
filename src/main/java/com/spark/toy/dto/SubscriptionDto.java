package com.spark.toy.dto;

import com.spark.toy.domain.Subscription;
import com.spark.toy.domain.enums.DeviceType;
import com.spark.toy.domain.enums.SubscriptionCode;
import com.spark.toy.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionDto extends BaseDto {
    private String usimNumber;

    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "핸드폰 번호의 양식과 맞지 않습니다. 01x-xxx(x)-xxxx")
    private String phoneNumber;

    private DeviceType deviceType;

    private SubscriptionCode subscriptionCode;

    private LocalDateTime subscribedAt;

    private LocalDateTime expiredAt;

    public static  SubscriptionDto toDto(Subscription subscription) {
        SubscriptionDto subscriptionDto = SubscriptionDto.builder()
                .subscriptionCode(subscription.getSubscriptionCode())
                .deviceType(subscription.getDeviceType())
                .usimNumber(subscription.getUsimNumber())
                .phoneNumber(subscription.getPhoneNumber())
                .subscribedAt(subscription.getSubscribedAt())
                .expiredAt(subscription.getExpiredAt())
                .build();
        subscriptionDto.setCreatedAt(subscription.getCreatedAt());
        subscriptionDto.setUpdatedAt(subscription.getUpdatedAt());

        return subscriptionDto;
    }

    public static Subscription toEntity(SubscriptionDto subscriptionDto) {
        Subscription subscription = new Subscription();
        subscription.setUsimNumber(subscriptionDto.getUsimNumber());
        subscription.setSubscriptionCode(subscriptionDto.getSubscriptionCode());
        subscription.setDeviceType(subscriptionDto.getDeviceType());
        subscription.setPhoneNumber(subscriptionDto.getPhoneNumber());
        subscription.setSubscribedAt(subscriptionDto.getSubscribedAt());
        subscription.setUpdatedAt(subscriptionDto.getUpdatedAt());
        subscription.setCreatedAt(subscriptionDto.getCreatedAt());
        subscription.setExpiredAt(subscriptionDto.getExpiredAt());

        return subscription;
    }
}

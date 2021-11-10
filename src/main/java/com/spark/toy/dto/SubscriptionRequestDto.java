package com.spark.toy.dto;

import com.spark.toy.domain.SubscriptionRequest;
import com.spark.toy.domain.enums.SubscriptionCode;
import com.spark.toy.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscriptionRequestDto extends BaseDto {
    private SubscriptionDto subscriptionDto;

    private SubscriptionCode requestCode;

    private Boolean isProceeded;

    private LocalDateTime requestedAt;

    private LocalDateTime proceededAt;

    public static SubscriptionRequestDto toDto(SubscriptionRequest subscriptionRequest) {
        SubscriptionRequestDto subscriptionRequestDto = SubscriptionRequestDto.builder()
                .subscriptionDto(SubscriptionDto.toDto(subscriptionRequest.getSubscription()))
                .requestCode(subscriptionRequest.getRequestSubscriptionCode())
                .isProceeded(subscriptionRequest.getIsProceeded())
                .requestedAt(subscriptionRequest.getRequestedAt())
                .proceededAt(subscriptionRequest.getProceededAt())
                .build();
        subscriptionRequestDto.setCreatedAt(subscriptionRequest.getCreatedAt());
        subscriptionRequestDto.setUpdatedAt(subscriptionRequest.getUpdatedAt());

        return subscriptionRequestDto;
    }

    public static SubscriptionRequest toEntity(SubscriptionRequestDto subscriptionRequestDto) {
        SubscriptionRequest subscriptionRequest = new SubscriptionRequest();
        subscriptionRequest.setRequestSubscriptionCode(subscriptionRequestDto.getRequestCode());
        subscriptionRequest.setRequestedAt(subscriptionRequestDto.getRequestedAt());
        subscriptionRequest.setIsProceeded(subscriptionRequestDto.getIsProceeded());
        subscriptionRequest.setProceededAt(subscriptionRequestDto.getProceededAt());
        subscriptionRequest.setCreatedAt(subscriptionRequestDto.getCreatedAt());
        subscriptionRequest.setUpdatedAt(subscriptionRequestDto.getUpdatedAt());
        subscriptionRequest.setSubscription(SubscriptionDto.toEntity(subscriptionRequestDto.getSubscriptionDto()));

        return subscriptionRequest;
    }
}

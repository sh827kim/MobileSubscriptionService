package com.spark.toy.dto;

import com.spark.toy.dto.base.BaseDto;
import lombok.*;

@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionStatisticsDto extends BaseDto {

    private Integer totalCount;

    private Integer notProceeded;

    private Integer succeeded;

    private Integer failed;

}

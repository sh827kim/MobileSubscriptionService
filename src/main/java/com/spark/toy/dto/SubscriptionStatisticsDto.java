package com.spark.toy.dto;

import com.spark.toy.dto.base.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

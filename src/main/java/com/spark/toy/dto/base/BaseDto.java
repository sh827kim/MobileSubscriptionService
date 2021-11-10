package com.spark.toy.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDto {

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

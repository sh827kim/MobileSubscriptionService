package com.spark.toy.dto.base;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseDto {

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime createdAt;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDateTime updatedAt;
}

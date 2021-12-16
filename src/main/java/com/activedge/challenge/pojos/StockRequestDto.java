package com.activedge.challenge.pojos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockRequestDto {

    private Long id;

    @NotNull(message = "name cannot be null")
    @NotBlank(message = "name cannot be empty")
    private String name;

    @NotNull(message = "amount cannot be null")
    private Double amount;
}

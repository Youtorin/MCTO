package com.yangdonglin.mcto.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDto {

    private String id;

    private Integer status;

    private BigDecimal price;

    private String payType;
}

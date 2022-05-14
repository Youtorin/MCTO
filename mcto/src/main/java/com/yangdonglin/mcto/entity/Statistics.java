package com.yangdonglin.mcto.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

@Data
public class Statistics {

    private BigDecimal totalSales;

    private String orderByCate;

    private Integer totalComment;

    private Integer totalView;

    private Integer totalUser;

    private String foodAndCount;

    private Integer totalCate;
}

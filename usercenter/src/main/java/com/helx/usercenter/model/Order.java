package com.helx.usercenter.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Order {

//    @TableField("order_id")
    int orderId;
    BigDecimal price;
//    @TableField("user_id")
    Long userId;
    String status;
}

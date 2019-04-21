package com.su.dto;

import lombok.Data;

/**
 * 类似购物车的类：用于封装前端传入的数据
 */
@Data
public class CartDTO {
    /** 商品id */
    private String productId;

    /** 商品数量 */
    private Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}

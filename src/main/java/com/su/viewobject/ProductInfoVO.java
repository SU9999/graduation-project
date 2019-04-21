package com.su.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品详情的View Object对象
 * 封装ProductInfo中与前端交互需要传输的属性
 */
@Data
public class ProductInfoVO {

    /** 商品id */
    @JsonProperty("id")
    private String productId;

    /** 商品名称 */
    @JsonProperty("name")
    private String productName;

    /** 商品价格 */
    @JsonProperty("price")
    private BigDecimal productPrice;

    /** 商品描述 */
    @JsonProperty("description")
    private String productDescription;

    /** 商品图标 */
    @JsonProperty("icon")
    private String productIcon;
}

package com.su.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商品详情的View Object对象
 * 封装ProductInfo中与前端交互需要传输的属性
 */
@Data
public class ProductInfoVO implements Serializable {

    private static final long serialVersionUID = 4393627761982166561L;

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

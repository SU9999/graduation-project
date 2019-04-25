package com.su.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品信息表单验证相关类
 */
@Data
public class ProductInfoForm {

    /** 商品id */
    private String productId;

    /** 名字 */
    private String productName;

    /** 价格 */
    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;

    /** 描述信息 */
    private String productDescription;

    /** 图片url */
    private String productIcon;

    /** 类目编号 */
    private Integer categoryType;
}

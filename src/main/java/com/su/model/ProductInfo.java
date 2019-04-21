package com.su.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * 商品表：实体类
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    /** 商品id */
    @Id
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

    /** 商品状态：0正常；1下架*/
    private Integer productStatus;

    /** 类目编号 */
    private Integer categoryType;
}

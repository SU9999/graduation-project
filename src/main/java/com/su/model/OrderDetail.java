package com.su.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单详情表order_detail：实体类
 */
@Entity
@Data
@DynamicUpdate
public class OrderDetail {

    /** 详情表id */
    @Id
    private String detailId;

    /** 订单主表id */
    private String orderId;

    /** 商品id */
    private String productId;

    /** 商品名称 */
    private String productName;

    /** 商品价格 */
    private BigDecimal productPrice;

    /** 商品数量 */
    private Integer productQuantity;

    /** 商品图片 */
    private String productIcon;

    /** 创建时间*/
    private Date createTime;
    /** 更新时间*/
    private Date updateTime;
}

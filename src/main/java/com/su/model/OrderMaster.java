package com.su.model;

import com.su.enums.OrderStatusEnum;
import com.su.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单主表order_master：实体类
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    /** 订单id */
    @Id
    private String orderId;

    /** 买家姓名 */
    private String buyerName;

    /** 买家电话 */
    private String buyerPhone;

    /** 买家地址 */
    private String buyerAddress;

    /** 买家微信openid */
    private String buyerOpenid;

    /** 订单总额 */
    private BigDecimal orderAmount;

    /** 订单状态：默认状态为新订单，0新订单；1完结订单；2取消订单 */
    private int orderStatus = OrderStatusEnum.NEW.getCode();

    /** 订单支付状态：默认为未支付，0未支付，1已支付 */
    private int payStatus = PayStatusEnum.WAIT.getCode();

    /** 订单创建时间 */
    private Date createTime;

    /** 订单更新时间 */
    private Date updateTime;

}

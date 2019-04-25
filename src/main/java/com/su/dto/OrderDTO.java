package com.su.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.su.enums.OrderStatusEnum;
import com.su.enums.PayStatusEnum;
import com.su.model.OrderDetail;
import com.su.util.EnumUtil;
import com.su.util.serializer.Date2LongSerializer;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 订单主表order_master：实体类的数据传输对象
 * 主要用于DAO层和Service层的数据交换
 * @JsonInclude(JsonInclude.Include.NON_NULL) 当属性值为null时，不序列化到json格式中
 */
@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    /** 订单id */
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
    private int orderStatus;

    /** 订单支付状态：默认为未支付，0未支付，1已支付 */
    private int payStatus;

    /** 订单创建时间: 使用自定义解析器将Date类型解析成long类型 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 订单更新时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /** 该订单包含的订单详情表列表 */
    private List<OrderDetail> orderDetailList;

    /**
     * 添加如下两个字段，用于将状态信息（integer）转化为对应的枚举
     */
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getStatusEnum(orderStatus, OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getStatusEnum(payStatus, PayStatusEnum.class);
    }
}

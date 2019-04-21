package com.su.form;

import com.su.dto.CartDTO;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * com.su.form包下都是表单验证相关的类
 * 用于进行订单表单的验证和传输
 */
@Data
public class OrderForm {
    /** 买家姓名 */
    @NotEmpty(message = "姓名必填")
    private String name;

    /** 买家手机号 */
    @NotEmpty(message = "买家手机号必填")
    private String phone;

    /** 买家地址 */
    @NotEmpty(message = "买家地址必填")
    private String address;

    /** 买家微信openid */
    @NotEmpty(message = "买家微信openid必填")
    private String openid;

    /** 订单购物车列表 */
    @NotEmpty(message = "购物车不能为空")
    private String items;
}

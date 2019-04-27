package com.su.form;

import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

/**
 * 用户注册Form表单验证
 */
@Data
public class SellerInfoForm {
    /** 卖家id */
    private String sellerId;

    /** 用户名 */
    @NotEmpty(message = "用户名不能为空")
    private String username;

    /** 密码 */
    @NotEmpty(message = "密码不能为空")
    private String password;

    /** 确认密码 */
    @NotEmpty(message = "密码不能为空")
    private String rePassword;

    /** 注册的邮箱 */
    @NotEmpty(message = "邮箱不能为空")
    private String email;
}

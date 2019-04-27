package com.su.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.exception.DataException;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 卖家信息表：用于进行卖家的登录验证
 */
@Entity
@Data
@DynamicUpdate
public class SellerInfo {

    /** 卖家id */
    @Id
    private String sellerId;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 注册的邮箱 */
    private String email;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;
}

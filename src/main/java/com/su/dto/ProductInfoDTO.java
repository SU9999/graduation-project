package com.su.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.su.enums.ProductStatusEnum;
import com.su.util.EnumUtil;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInfoDTO {
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

    /** 商品状态：0正常；1下架*/
    private Integer productStatus;

    /** 类目编号 */
    private Integer categoryType;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    /** 类目名称 */
    private String categoryName;

    @JsonIgnore
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getStatusEnum(this.productStatus, ProductStatusEnum.class);
    }
}

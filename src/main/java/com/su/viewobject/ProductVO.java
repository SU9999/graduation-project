package com.su.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品类进行前后端交互的View Object对象
 * 即封装成json格式数据时的外层格式对象
 */
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 6701791470133212281L;
    /** 类目名称 */
    @JsonProperty("name")
    private String categoryName;

    /** 类目编号 */
    @JsonProperty("type")
    private Integer categoryType;

    /** 该类目下的商品 */
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;
}

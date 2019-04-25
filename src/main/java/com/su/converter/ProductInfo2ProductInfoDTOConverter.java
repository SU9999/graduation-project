package com.su.converter;

import com.su.dto.ProductInfoDTO;
import com.su.model.ProductInfo;
import org.springframework.beans.BeanUtils;

public class ProductInfo2ProductInfoDTOConverter {
    public static ProductInfoDTO convert(ProductInfo productInfo){
        ProductInfoDTO productInfoDTO = new ProductInfoDTO();
        BeanUtils.copyProperties(productInfo, productInfoDTO);
        return productInfoDTO;
    }
}

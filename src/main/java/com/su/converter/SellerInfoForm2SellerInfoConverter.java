package com.su.converter;

import com.su.form.SellerInfoForm;
import com.su.model.SellerInfo;
import org.springframework.beans.BeanUtils;

public class SellerInfoForm2SellerInfoConverter {

    public static SellerInfo convert(SellerInfoForm sellerInfoForm){
        SellerInfo sellerInfo = new SellerInfo();
        BeanUtils.copyProperties(sellerInfoForm, sellerInfo);
        return sellerInfo;
    }
}

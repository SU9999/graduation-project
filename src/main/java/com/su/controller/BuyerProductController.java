package com.su.controller;

import com.su.model.ProductCategory;
import com.su.model.ProductInfo;
import com.su.service.CategoryService;
import com.su.service.ProductInfoService;
import com.su.util.ResultVOUtil;
import com.su.viewobject.ProductInfoVO;
import com.su.viewobject.ProductVO;
import com.su.viewobject.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 买家端，商品相关控制器
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    @Cacheable(cacheNames = "product", key = "123", unless = "#result.getCode() != 0")
    public ResultVO list(){
        /* 查询所有上架的商品 */
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        /* 查询所有需要的类目（一次性查询）：即在架商品存在的类目 */
        //获取到在架商品所包含的所有类目的类目编号
        Set<Integer> categoryTypeSet = productInfoList.stream()
                .map(productInfo -> productInfo.getCategoryType())
                .collect(Collectors.toSet());
        List<ProductCategory> categoryList = categoryService.findByCategoryTypeIn(categoryTypeSet);

        /* 将查询的数据拼接成返回对象ResultVO */
        // 定义返回的对象
        List<ProductVO> productVOList = new ArrayList<>();

        // 遍历类目信息：categoryList
        for (ProductCategory category : categoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(category.getCategoryName());
            productVO.setCategoryType(category.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            // 遍历所有上架商品：productInfoList
            for (ProductInfo productInfo: productInfoList){
                // 根据类目编号进行判断
                if (productInfo.getCategoryType() == category.getCategoryType()){
                    ProductInfoVO productInfoVO = new ProductInfoVO();

                    // 复制属性
                    BeanUtils.copyProperties(productInfo, productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);

            // 将拼接的ProductVO对象存放到数组中
            productVOList.add(productVO);
        }

        /** 拼接返回的数据并返回 */
        return ResultVOUtil.success(productVOList);
    }
}

package com.su.controller;

import com.su.converter.ProductInfo2ProductInfoDTOConverter;
import com.su.dto.ProductInfoDTO;
import com.su.exception.SellException;
import com.su.form.ProductInfoForm;
import com.su.model.ProductCategory;
import com.su.model.ProductInfo;
import com.su.service.CategoryService;
import com.su.service.ProductInfoService;
import com.su.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 卖家商品相关的Controller
 */
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private CategoryService categoryService;

    /**
     * 商品列表
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(request);

        // 获取所有的page对象
        List<ProductInfoDTO> productInfoDTOList = new ArrayList<>();
        for (ProductInfo productInfo : productInfoPage.getContent()) {
            ProductInfoDTO productInfoDTO = ProductInfo2ProductInfoDTOConverter.convert(productInfo);
            ProductCategory productCategory = categoryService.findByCategoryType(productInfo.getCategoryType());
            productInfoDTO.setCategoryName(productCategory.getCategoryName());
            productInfoDTOList.add(productInfoDTO);
        }
        PageImpl<ProductInfoDTO> productInfoDTOPage = new PageImpl<>(productInfoDTOList,
                request, productInfoPage.getTotalElements());

        map.put("productInfoPage", productInfoDTOPage);
        // 将page传回给前端：用于处理分页
        map.put("currentPage", page);
        map.put("size", size);

        return new ModelAndView("product/list", map);
    }

    /**
     * 下架商品
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                                Map<String, Object> map) {
        try {
            productInfoService.offSale(productId);
        } catch (Exception e) {
            log.error("【卖家下架商品】下架商品失败，productId={}，msg={}", productId, e.getMessage());
            // 跳转到错误页面
            map.put("msg", e.getMessage());  // 错误信息
            map.put("url", "/sell/seller/product/list");     // 跳转页面
            return new ModelAndView("common/error", map);
        }

        // 下架成功：重定向到list页面
        return new ModelAndView("redirect:/seller/product/list");
    }

    /**
     * 上架商品
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String, Object> map) {
        try {
            productInfoService.onSale(productId);
        } catch (Exception e) {
            log.error("【卖家上架商品】上架商品失败，productId={}，msg={}", productId, e.getMessage());
            // 跳转到错误页面
            map.put("msg", e.getMessage());  // 错误信息
            map.put("url", "/sell/seller/product/list");     // 跳转页面
            return new ModelAndView("common/error", map);
        }

        // 下架成功：重定向到list页面
        return new ModelAndView("redirect:/seller/product/list");
    }

    /**
     * 进入新增或修改商品信息的页面
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "productId", required = false) String productId,
                              Map<String, Object> map) {
        // 当传入productId时，表示修改商品信息，否则表示新增商品
        if (!StringUtils.isEmpty(productId)) {
            ProductInfo productInfo = productInfoService.findById(productId);
            map.put("productInfo", productInfo);
        }

        // 查询出所有的类目信息
        List<ProductCategory> productCategoryList = categoryService.findAll();
        map.put("categoryList", productCategoryList);
        return new ModelAndView("product/index", map);
    }

    /**
     * 保存卖家提交的商品信息：修改或者新增商品的通用方法
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductInfoForm productInfoForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {

        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            log.error("【卖家修改商品信息】修改商品信息失败，msg={}", errorMsg);
            // 跳转到错误页面
            map.put("msg", errorMsg);  // 错误信息
            map.put("url", "/sell/seller/product/index");     // 跳转页面
            return new ModelAndView("common/error", map);
        }

        // 首先查询商品
        ProductInfo productInfo = null;
        if (!StringUtils.isEmpty(productInfoForm.getProductId())) {
            // 修改
            productInfo = productInfoService.findById(productInfoForm.getProductId());
        } else {
            // 新增: 需要设置主键productId
            productInfo = new ProductInfo();
            productInfoForm.setProductId(KeyUtil.genUniqueKey());
        }
        BeanUtils.copyProperties(productInfoForm, productInfo);
        productInfoService.save(productInfo);

        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("common/success", map);
    }
}

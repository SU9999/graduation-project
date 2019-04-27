package com.su.controller;

import com.su.converter.ProductInfo2ProductInfoDTOConverter;
import com.su.dto.ProductInfoDTO;
import com.su.form.CategoryForm;
import com.su.model.ProductCategory;
import com.su.model.ProductInfo;
import com.su.service.CategoryService;
import com.su.service.ProductInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.concurrent.ExecutionException;

/**
 * 卖家对类目的操作Controller
 */
@Controller
@RequestMapping("/seller/category")
@Slf4j
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductInfoService productInfoService;

    /**
     * 查询类目列表
     *
     * @param map
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map) {
        List<ProductCategory> categoryList = new ArrayList<>();
        try {
            // 查询出所有的类目
            categoryList = categoryService.findAll();
        } catch (Exception e) {
            // 查询出错，返回一个空的列表数据
            e.printStackTrace();
        }
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    /**
     * 新增或者修改类目：
     */
    @GetMapping("/index")
    public ModelAndView index(@RequestParam(value = "categoryId", required = false) Integer categoryId,
                              Map<String, Object> map) {
        ProductCategory productCategory = null;
        if (!StringUtils.isEmpty(categoryId)) {
            try {
                // categoryId非空时，表示修改操作，从数据库中查询该类目
                productCategory = categoryService.findById(categoryId);
            } catch (Exception e) {
                // 发生异常时，忽略
                e.printStackTrace();
            }
        }

        map.put("category", productCategory);
        return new ModelAndView("category/index", map);
    }

    /**
     * 执行新增或修改类目的保存操作
     */
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm categoryForm,
                             BindingResult bindingResult,
                             Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            log.error("【卖家修改类目信息】修改类目信息失败，msg={}", errorMsg);
            // 跳转到错误页面
            map.put("msg", errorMsg);  // 错误信息
            map.put("url", "/sell/seller/category/index");     // 跳转页面
            return new ModelAndView("common/error", map);
        }

        Integer categoryType = categoryForm.getCategoryType();
        Integer categoryId = categoryForm.getCategoryId();
        // 从数据库中查询该类目，如果该类目存在，则需要同步更改该类目下的商品所对应的类目信息
        if (categoryId != null) {
            ProductCategory oldCategory = categoryService.findById(categoryId);

            if (oldCategory != null) {
                // 修改操作，此时同步修改其对应的商品的categoryType
                Integer oldCategoryType = oldCategory.getCategoryType();
                // 查询修改前的categoryType对应的所有的商品
                List<ProductInfo> productInfoList = productInfoService
                        .findByCategoryType(oldCategoryType);
                // 修改该类目下的所有商品的类目categoryType
                for (ProductInfo productInfo : productInfoList) {
                    productInfo.setCategoryType(categoryType);
                    productInfoService.save(productInfo);
                }
            }
        }

        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(categoryForm, productCategory);
        categoryService.save(productCategory);
        return new ModelAndView("redirect:/seller/category/list");
    }

    /**
     * 查询一个类目下的所有商品信息
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("categoryId") Integer categoryId,
                               Map<String, Object> map){
        //根据该类目id，查询出该类目的具体信息。
        ProductCategory category = null;
        try {
            category = categoryService.findById(categoryId);
            map.put("category", category);
        } catch (Exception e){
            log.error("【查询类目详情】类目不存在，categoryId={}", categoryId);
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/category/list");
            return new ModelAndView("common/error", map);
        }

        // 如果类目存在，则根据categoryType查询出该类目下所有的商品信息
        List<ProductInfoDTO> productInfoDTOList = new ArrayList<>();
        List<ProductInfo> productInfoList = productInfoService.findByCategoryType(category.getCategoryType());
        for (ProductInfo productInfo : productInfoList) {
            ProductInfoDTO productInfoDTO = ProductInfo2ProductInfoDTOConverter.convert(productInfo);
            productInfoDTO.setCategoryName(category.getCategoryName());
            productInfoDTOList.add(productInfoDTO);
        }
        map.put("productInfoList", productInfoDTOList);
        return new ModelAndView("category/detail", map);
    }
}

package com.su.service;

import com.su.model.ProductCategory;

import java.util.List;
import java.util.Set;

/**
 * 类目表的Service层接口：体现面向接口编程
 */
public interface CategoryService {

    ProductCategory findById(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(Set<Integer> categoryTypeList);

    ProductCategory save(ProductCategory category);

    ProductCategory findByCategoryType(Integer categoryType);
}

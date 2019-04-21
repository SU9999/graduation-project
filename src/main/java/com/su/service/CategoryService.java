package com.su.service;

import com.su.model.ProductCategory;

import java.util.List;
import java.util.Set;

/**
 * 类目表的Service层接口：体现面向接口编程
 */
public interface CategoryService {

    public ProductCategory findById(Integer categoryId);

    public List<ProductCategory> findAll();

    public List<ProductCategory> findByCategoryTypeIn(Set<Integer> categoryTypeList);

    public ProductCategory save(ProductCategory category);
}

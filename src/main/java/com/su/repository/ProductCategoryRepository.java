package com.su.repository;

import com.su.model.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 类目表 实体类的 DAO处理类：通过代理的方式执行相应操作
 * */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

    List<ProductCategory> findByCategoryTypeIn(Set<Integer> categoryTypeList);
}

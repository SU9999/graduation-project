package com.su.repository;

import com.su.model.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品表实体类的DAO层接口
 */
@Repository
public interface ProductInfoRepository extends JpaRepository<ProductInfo, String> {

    /** 根据商品状态productStatus进行查询*/
    List<ProductInfo> findByProductStatus(Integer productStatus);

    /** 根据商品的类目categoryType进行查询 */
    List<ProductInfo> findByCategoryType(Integer categoryType);
}

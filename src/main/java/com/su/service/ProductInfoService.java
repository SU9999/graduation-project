package com.su.service;

import com.su.dto.CartDTO;
import com.su.model.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 商品表的Service层接口
 */
public interface ProductInfoService {

    /** 根据商品id查询商品 */
    public ProductInfo findById(String productId);

    /** 买家（手机）端：查询所有在架的商品 */
    public List<ProductInfo> findUpAll();

    /** 卖家（PC）端：查询所有商品，采用分页的方式 */
    public Page<ProductInfo> findAll(Pageable pageable);

    /** 新增/修改商品 */
    public ProductInfo save(ProductInfo productInfo);

    //TODO
    /** 加库存 */
    void increaseStock(List<CartDTO> cartDTOList);

    /** 减库存 */
    void decreaseStock(List<CartDTO> cartDTOList);
}

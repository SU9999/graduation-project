package com.su.service.impl;

import com.su.dto.CartDTO;
import com.su.enums.ProductStatusEnum;
import com.su.enums.ResultEnum;
import com.su.exception.SellException;
import com.su.model.ProductInfo;
import com.su.repository.ProductInfoRepository;
import com.su.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import sun.management.Agent;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * 商品表实体类：Service层实现类
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
    public ProductInfo findById(String productId) {
        Optional<ProductInfo> optional = productInfoRepository.findById(productId);
        try {
            return optional.get();
        } catch (NoSuchElementException e){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            Optional<ProductInfo> optional = productInfoRepository.findById(cartDTO.getProductId());
            try {
                ProductInfo productInfo = optional.get();
                Integer rest = productInfo.getProductStock() + cartDTO.getProductQuantity();

                productInfo.setProductStock(rest);

                // 更新数据库
                productInfoRepository.save(productInfo);
            } catch (NoSuchElementException e) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
        }
    }

    /**
     * 添加事务特性
     */
    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        // TODO 存在超卖现象，项目优化时使用Redis分布式锁解决
        for (CartDTO cartDTO : cartDTOList) {
            Optional<ProductInfo> optional = productInfoRepository.findById(cartDTO.getProductId());
            try {
                ProductInfo productInfo = optional.get();
                Integer rest = productInfo.getProductStock() - cartDTO.getProductQuantity();
                if (rest < 0) {
                    throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
                }
                productInfo.setProductStock(rest);

                // 更新数据库
                productInfoRepository.save(productInfo);
            } catch (NoSuchElementException e) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
        }
    }
}

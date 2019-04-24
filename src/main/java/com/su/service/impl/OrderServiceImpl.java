package com.su.service.impl;

import com.su.converter.OrderMaster2OrderDTOConverter;
import com.su.dto.CartDTO;
import com.su.dto.OrderDTO;
import com.su.enums.OrderStatusEnum;
import com.su.enums.PayStatusEnum;
import com.su.enums.ResultEnum;
import com.su.exception.SellException;
import com.su.model.OrderDetail;
import com.su.model.OrderMaster;
import com.su.model.ProductInfo;
import com.su.repository.OrderDetailRepository;
import com.su.repository.OrderMasterRepository;
import com.su.service.OrderService;
import com.su.service.ProductInfoService;
import com.su.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 买家订单Service层实现类
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository detailRepository;

    @Autowired
    private OrderMasterRepository masterRepository;

    /**
     * 添加事务操作
     */
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        // 生成orderId
        String orderId = KeyUtil.genUniqueKey();

        /** step1: 查询商品详情：*/
        // 遍历订单详情列表，获取每一个订单详情表的商品信息
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {

            //查询每一个订单详情表中对应商品的信息
            ProductInfo productInfo = productInfoService.findById(orderDetail.getProductId());
            // 商品不存在，抛出异常
            if (null == productInfo) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            /** step2: 计算订单总金额 */
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            // 构造orderDetail对象的所有属性，并将订单详情入库
            BeanUtils.copyProperties(productInfo, orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            detailRepository.save(orderDetail);
        }
        /** step3: 写入订单数据库：order_master，order_detail*/
        // 定义订单主表对象
        OrderMaster orderMaster = new OrderMaster();
        //拷贝,设置属性
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        masterRepository.save(orderMaster);

        /** step4: 扣库存 */
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        // 返回orderDTO
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        // 根据buyerOpenid查询出该买家对应的订单：分页查询
        Page<OrderMaster> page = masterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        // 将查询的OrderMaster对象全部转化为OrderDTO对象
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(page.getContent());

        // 构造返回对象
        Page<OrderDTO> orderDTOPage = new PageImpl<>(orderDTOList, pageable, page.getTotalElements());
        return orderDTOPage;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        // 根据orderId查询出订单主表orderMaster
        Optional<OrderMaster> optional = masterRepository.findById(orderId);
        try {
            OrderMaster orderMaster = optional.get();

            // 查询出该订单的所有详情信息
            List<OrderDetail> orderDetailList = detailRepository.findByOrderId(orderId);
            if (orderDetailList.size() <= 0) {
                throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
            }

            // 定义返回的OrderDTO对象
            OrderDTO orderDTO = new OrderDTO();
            // 设置属性
            BeanUtils.copyProperties(orderMaster, orderDTO);
            orderDTO.setOrderDetailList(orderDetailList);

            return orderDTO;
        } catch (NoSuchElementException e) {
            log.error("该订单不存在，orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
    }

    /**
     * 由于需要修改数据库，因此添加事务操作
     */
    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        /** Step1：查询该订单，并判断该订单状态 */
        OrderMaster orderMaster = null;
        Optional<OrderMaster> optional = masterRepository.findById(orderDTO.getOrderId());
        try {
            orderMaster = optional.get();
            if (orderMaster.getOrderStatus() != OrderStatusEnum.NEW.getCode()) {
                log.error("【取消订单】 订单状态不正确，orderId={}, orderStatus={}",
                        orderMaster.getOrderId(), OrderStatusEnum.getEnumByCode(orderMaster.getOrderStatus()).getMsg());
                throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
            }
        } catch (NoSuchElementException e) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        /** Step2：修改订单状态 */
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster masterUpdate = masterRepository.save(orderMaster);
        if (masterUpdate == null) {
            log.error("【取消订单】更新订单状态失败，OrderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAILED);
        }

        /** Step3：返还库存 */
        if (orderDTO.getOrderDetailList().isEmpty()) {
            log.error("【取消订单】订单中没有订单详情列表信息，orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        // 获取购物车信息列表：cartDTOList
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);

        // TODO
        /** Step4：如果已支付，退款给买家 */
        if (orderMaster.getPayStatus() == PayStatusEnum.SUCCESS.getCode()) {
            // TODO
        }
        // 重新设置返回的数据
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        /** Step1: 判断订单状态 */
        OrderMaster orderMaster = null;
        Optional<OrderMaster> optional = masterRepository.findById(orderDTO.getOrderId());
        try {
            orderMaster = optional.get();
            if (orderMaster.getOrderStatus() != OrderStatusEnum.NEW.getCode()) {
                log.error("【完结订单】 订单状态不正确，orderId={}, orderStatus={}",
                        orderMaster.getOrderId(), OrderStatusEnum.getEnumByCode(orderMaster.getOrderStatus()).getMsg());
                throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
            }
        } catch (NoSuchElementException e) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        /** Step2: 修改订单状态 */
        orderMaster.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster masterUpdate = masterRepository.save(orderMaster);
        if (masterUpdate == null) {
            log.error("【完结订单】更新订单状态失败，OrderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAILED);
        }

        // 重新设置返回的数据
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO pay(OrderDTO orderDTO) {
        /** Step1: 判断订单状态 */
        OrderMaster orderMaster = null;
        Optional<OrderMaster> optional = masterRepository.findById(orderDTO.getOrderId());
        try {
            orderMaster = optional.get();
            if (orderMaster.getOrderStatus() != OrderStatusEnum.NEW.getCode()) {
                log.error("【支付订单】 订单状态不正确，orderId={}, orderStatus={}",
                        orderMaster.getOrderId(), OrderStatusEnum.getEnumByCode(orderMaster.getOrderStatus()).getMsg());
                throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
            }
            /** Step2: 判断支付状态 */
            if (orderMaster.getPayStatus() != PayStatusEnum.WAIT.getCode()) {
                log.error("【支付订单】 订单支付状态不正确，orderId={}, payStatus={}",
                        orderMaster.getOrderId(), PayStatusEnum.getEnumByCode(orderMaster.getPayStatus()).getMsg());
                throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
            }
        } catch (NoSuchElementException e) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        // TODO
        /** Step3: 支付 */

        /** Step4: 修改订单支付状态 */
        orderMaster.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster masterUpdate = masterRepository.save(orderMaster);
        if (masterUpdate == null) {
            log.error("【支付订单】更新支付状态失败，OrderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_PAY_FAILED);
        }

        // 重新设置返回的数据
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }
}

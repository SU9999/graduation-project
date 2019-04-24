package com.su.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.BestPayService;
import com.su.config.WeChatAccountConfig;
import com.su.dto.OrderDTO;
import com.su.enums.ResultEnum;
import com.su.exception.SellException;
import com.su.service.OrderService;
import com.su.service.PayService;
import com.su.util.JsonUtil;
import com.su.util.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * 微信支付和退款相关Service层实现类：
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "order";

    /**
     * 自动注入用于微信支付的第三方sdk的Service接口
     * */
    @Autowired
    private BestPayService bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse createPayment(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        /** 设置支付的相关参数 */
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderName(ORDER_NAME);
        // H5支付表示微信公众账号的方式进行支付
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        // 设置异步通知的url

        log.info("【微信支付】发起支付，request={}", JsonUtil.toJson(payRequest));
        // TODO 暂时无法接入微信支付，因此注释该方法
//        PayResponse payResponse = bestPayService.pay(payRequest);
        // TODO 暂时无法接入微信支付, 因此自己构造一个PayResponse对象返回
        PayResponse payResponse = buildPayResponse(payRequest);
        log.info("【微信支付】发起支付，response={}", JsonUtil.toJson(payResponse));

        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        /** 处理微信支付完成后的异步通知消息：返回一个PayResponse */
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】支付异步通知：PayResponse={}", JsonUtil.toJson(payResponse));
        /**
         * 在进行修改订单支付状态之前，必须完成以下一个操作：
         *  1.验证签名paySign（发起微信支付时，微信端生成的一个签名）：该操作由best-pay SDK完成
         *  2.验证支付的状态，即微信方对应的订单是否为预支付状态：该操作有best-pay SDK完成
         *  3.验证支付金额：需要我们自己实现业务逻辑（重点）
         *  4.支付人：根据具体业务逻辑判断支付人是否必须为本人：通过openid判断（该系统不进行判断）
         * */

        // 查询出该订单
        String orderId = payResponse.getOrderId();
        OrderDTO orderDTO = orderService.findOne(orderId);

        // 对支付金额进行校验, 判断支付金额和订单金额是否一致
        if (!MathUtil.equals(payResponse.getOrderAmount(), orderDTO.getOrderAmount().doubleValue())){
            log.error("【微信支付】支付异步通知：支付金额和订单金额不一致，支付金额={},订单金额={}",
                    payResponse.getOrderAmount(),
                    orderDTO.getOrderAmount().doubleValue());
            throw new SellException(ResultEnum.WECHAT_PAY_FAILED);
        }
        // 执行修改订单支付状态操作
        orderService.pay(orderDTO);
        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        /** 定义一个RefundRequest对象，用于申请退款 */
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        /** 发起退款申请 */
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】发起退款，response={}", JsonUtil.toJson(refundResponse));

        return refundResponse;
    }

    /**
     * 由于无法接入微信支付API，因此无法成功获取到PayResponse
     * 因此自己构造一个PayResponse对象返回
     * */
    @Autowired
    private WeChatAccountConfig accountConfig;

    private PayResponse buildPayResponse(PayRequest payRequest){
        PayResponse payResponse = new PayResponse();
        payResponse.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        payResponse.setAppId(accountConfig.getMpAppId());
        payResponse.setNonceStr(UUID.randomUUID().toString());
        payResponse.setPackAge("prepay_id=" + UUID.randomUUID().toString());
        payResponse.setSignType("MD5");
        payResponse.setPaySign(UUID.randomUUID().toString());

        return payResponse;
    }
}

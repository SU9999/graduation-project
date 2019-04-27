package com.su.service.impl;

import com.su.config.WeChatAccountConfig;
import com.su.constant.TemplateConstant;
import com.su.dto.OrderDTO;
import com.su.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信模板详细相关的Service
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    //TODO
    /**
     * 买家openid：这一模块应该从数据库中读取，由于无法获取微信内置浏览器中的Cookie，暂时固定设置为本人测试用户openid
     * */
    private final String BUYER_OPENID = "oHA4A0wZ7dQknJuhWIowM73uwW5w";

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WeChatAccountConfig accountConfig;

    @Override
    public void orderStatusMessage(OrderDTO orderDTO) {
        WxMpTemplateMessage wxMpTemplateMessage = new WxMpTemplateMessage();
        wxMpTemplateMessage.setTemplateId(accountConfig.getTemplateId().get(TemplateConstant.ORDER_STATUS));

        /**
         * 买家openid：这一模块应该从数据库中读取，由于无法获取微信内置浏览器中的Cookie，暂时固定设置为本人测试用户openid
         * */
//        wxMpTemplateMessage.setToUser(orderDTO.getBuyerOpenid());
        wxMpTemplateMessage.setToUser(BUYER_OPENID);

        List<WxMpTemplateData> data = new ArrayList<>();
        data.add(new WxMpTemplateData("first", "亲，请记得及时收货"));
        data.add(new WxMpTemplateData("name", "咸鱼在线"));
        data.add(new WxMpTemplateData("phone", "18890807009"));
        data.add(new WxMpTemplateData("orderId", orderDTO.getOrderId()));
        data.add(new WxMpTemplateData("orderStatus", orderDTO.getOrderStatusEnum().getMsg()));
        data.add(new WxMpTemplateData("orderAmount", "￥"+orderDTO.getOrderAmount()));
        data.add(new WxMpTemplateData("remark", "如果商品有问题，请联系客服！"));
        wxMpTemplateMessage.setData(data);

        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
        } catch (WxErrorException e) {
            log.error("【模板消息】发送模板消息失败，{}", e);
        }
    }
}

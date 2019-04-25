<!DOCTYPE html>
<html lang="en">

<head>
    <#include "../common/header.ftl">
</head>

<body>

<#--最外层div-->
<div id="wrapper" class="toggled">

    <#--边栏Slidbar-->
    <#include "../common/nav.ftl">

    <#--主要内容区域-->
    <div id="page-content-wrapper">
        <#--container-fluid表示流动样式-->
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-hover table-condensed table-bordered">
                        <thead>
                        <tr>
                            <th>订单id</th>
                            <th>姓名</th>
                            <th>手机号</th>
                            <th>地址</th>
                            <th>金额</th>
                            <th>订单状态</th>
                            <th>支付状态</th>
                            <th>创建时间</th>

                            <th colspan="2">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list orderPage.content as orderDTO>

                            <tr>
                            <td>${orderDTO.orderId}</td>
                            <td>${orderDTO.buyerName}</td>
                            <td>${orderDTO.buyerPhone}</td>
                            <td>${orderDTO.buyerAddress}</td>
                            <td>${orderDTO.orderAmount}</td>
                            <td>${orderDTO.orderStatusEnum.getMsg()}</td>
                            <td>${orderDTO.payStatusEnum.getMsg()}</td>
                            <td>${orderDTO.createTime}</td>
                            <td>
                        <a href="/sell/seller/order/detail?orderId=${orderDTO.orderId}">详情</a>
                            </td>
                            <td>
                            <#if orderDTO.orderStatus == 0>
                                <a href="/sell/seller/order/cancel?orderId=${orderDTO.orderId}">取消</a>
                            </#if>
                            </td>
                            </tr>
                        </#list>

                        </tbody>
                    </table>
                </div>

                <#--分页-->
                <div class="col-md-12 column">
                    <ul class="pagination pagination-sm pull-right">
                        <#if currentPage lte 1>
                            <li class="disabled">
                                <a href="#">上一页</a>
                            </li>
                        <#else>
                            <li>
                            <a href="/sell/seller/order/list?page=${currentPage-1}&size=${size}">上一页</a>
                            </li>
                        </#if>
                        <#--完成分页的功能，当页面多时，收起其他页面-->
                        <#list 1..orderPage.totalPages as index>
                            <#if currentPage == index>
                                <li class="disabled">
                                <a href="#">${index}</a>
                                </li>
                            <#elseif index gte currentPage && index-currentPage lt 9>
                                <li>
                                <a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a>
                                </li>
                            <#elseif index-currentPage lt 9 && orderPage.totalPages - index lt 10>
                                <li>
                                <a href="/sell/seller/order/list?page=${index}&size=${size}">${index}</a>
                                </li>
                            </#if>
                        </#list>
                        <#if orderPage.totalPages - currentPage gte 9>
                            <li>
                            <a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">...</a>
                            </li>
                        </#if>

                        <#if currentPage gte orderPage.totalPages>
                            <li class="disabled">
                                <a href="#">下一页</a>
                            </li>
                        <#else>
                            <li>
                            <a href="/sell/seller/order/list?page=${currentPage+1}&size=${size}">下一页</a>
                            </li>
                        </#if>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
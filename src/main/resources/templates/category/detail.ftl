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
                <div class="col-md-4 column">
                    <table class="table table-bordered table-condensed">
                        <thead>
                        <tr>
                            <th>类目名称</th>
                            <th>Type</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>${category.categoryName}</td>
                            <td>${category.categoryType}</td>
                        </tr>
                        </tbody>
                    </table>
                </div>

                <div class="col-md-12 column">
                    <table class="table table-hover table-condensed table-bordered">
                        <thead>
                        <tr>
                            <th>商品id</th>
                            <th>名称</th>
                            <th>图片</th>
                            <th>单价</th>
                            <th>库存</th>
                            <th>状态</th>
                            <th>描述</th>
                            <th>类目</th>
                            <th>创建时间</th>
                            <th>修改时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list productInfoList as productInfo>

                            <tr>
                            <td>${productInfo.productId}</td>
                            <td>${productInfo.productName}</td>
                            <td><img src="${productInfo.productIcon}" alt="${productInfo.productName}" width="50px"
                                                                                                       height="50px">
                            </td>
                            <td>${productInfo.productPrice}</td>
                            <td>${productInfo.productStock}</td>
                            <td>${productInfo.productStatusEnum.getMessage()}</td>
                            <td>${productInfo.productDescription}</td>
                            <td>${productInfo.categoryName}</td>
                            <td>${productInfo.createTime}</td>
                            <td>${productInfo.updateTime}</td>
                            </tr>
                        </#list>

                        </tbody>
                    </table>
                </div>

                <div class="col-md-12 column">
                    <a href="/sell/seller/category/index?categoryId=${category.categoryId}" type="button"
                       class="btn btn-lg btn-success">修改类目</a>
                </div>

            </div>
        </div>
    </div>
</div>

</body>
</html>
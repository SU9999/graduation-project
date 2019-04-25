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
                    <form role="form" action="/sell/seller/product/save" method="post">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="productName" type="text" class="form-control"
                                   value="${(productInfo.productName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>价格</label>
                            <input name="productPrice" type="text" class="form-control"
                                   value="${(productInfo.productPrice)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>库存</label>
                            <input name="productStock" type="number" class="form-control"
                                   value="${(productInfo.productStock)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input name="productDescription" type="text" class="form-control"
                                   value="${(productInfo.productDescription)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <img width="100px" height="100px" src="${(productInfo.productIcon)!''}" alt="">
                            <input name="productIcon" type="text" class="form-control"
                                   value="${(productInfo.productIcon)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                                <#list categoryList as category>
                                    <#if (productInfo.categoryType)?? && category.categoryType == productInfo.categoryType>
                                        <option value="${category.categoryType}" selected>${category.categoryName}</option>
                                    <#else>
                                        <option value="${category.categoryType}">${category.categoryName}</option>
                                    </#if>
                                </#list>
                            </select>
                        </div>

                        <#--添加productId，并设置为隐藏字段-->
                        <input name="productId" type="text" value="${(productInfo.productId)!''}" hidden>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
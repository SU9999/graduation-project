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
                    <form role="form" action="/sell/seller/category/save" method="post">
                        <div class="form-group">
                            <label>名称</label>
                            <input name="categoryName" type="text" class="form-control"
                                   value="${(category.categoryName)!''}"/>
                        </div>
                        <div class="form-group">
                            <label>Type</label>
                            <input name="categoryType" type="number" class="form-control"
                                   value="${(category.categoryType)!''}"/>
                        </div>
                        <#--<input type="text" name="oldCategoryType" value="${(category.categoryType)!''}" hidden>-->
                        <input type="text" name="categoryId" value="${(category.categoryId)!''}" hidden>
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

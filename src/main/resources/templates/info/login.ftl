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
                    <form class="form-horizontal" role="form" method="post" action="/sell/seller/login">
                        <div class="form-group">
                            <label class="col-sm-4 control-label">邮箱</label>
                            <div class="col-sm-8">
                                <input name="email" type="email" class="form-control" id="email" placeholder="请输入邮箱"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-4 control-label">密码</label>
                            <div class="col-sm-8">
                                <input name="password" type="password" class="form-control" id="inputPassword3" placeholder="请输入密码"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <a href="#">忘记密码？</a>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-4 col-sm-8">
                                <button type="submit" class="btn btn-default">登录</button>
                                <a href="/sell/seller/register-index" type="submit" class="btn btn-default pull-right">去注册</a>
                            </div>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
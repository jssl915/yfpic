<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>用户登录</title>
<link href="<c:url value="/static/css/login.css"/>" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<c:url value='/static/js/easyui1.4.1/jquery.min.js'/>"></script>
</head>
<body>
<form id="form1" method="post">
<div class="content">
	<div class="login-div">
    	<div class="login-title1">个性化相册</div>
        <div class="login-title2">后台管理系统</div>
    	<table class="login-table">
        	<tr><td class="left">用户名:</td><td><input type="text" id="userName" name="username" class="login-text"></td></tr>
            <tr><td class="left">密&nbsp;&nbsp;码:</td><td><input type="password" id="passWord" name="password" class="login-text"></td></tr>
        </table>
        <div id="loginBtn" class="login-btn"></div>
        <div id="errorMessages" class="errorMessages"></div>
   		<div class="login-wz">版权所有：鄂ICP备15005533号  </div>
	</div>
</div>
</form>
<script>
$(function(){
	$('#loginBtn').click(function(){
		var userName = $('#userName').val();
		var passWord = $('#passWord').val();
		if (userName.length == 0 && passWord.length == 0) {
			$('#errorMessages').html("请输入用户名与密码！");
			return false;
		}
		if (userName.length == 0) {
			$('#errorMessages').html("请输入用户名！");
			return false;
		}
		if (passWord.length == 0) {
			$('#errorMessages').html("请输入密码！");
			return false;
		}
		$.post("login", {"userName" : userName,"passWord" : passWord}, function(data) {
			if (data.success) {
				window.location.href = 'main';
			} else {
				$('#errorMessages').html(data.msg);
			}
		}, "json");
	});
	$(document).keydown(function(e) {
		//13等于回车键(Enter)键值,ctrlKey 等于 Ctrl
		if (e.which == 13) {
			$('#loginBtn').click();
		}
	});
});
</script>
</body>
</html>

<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ include file="taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>主页</title>
<link href="${css}/main.css" rel="stylesheet" type="text/css"/>
<link rel="stylesheet" href="${js}/scrollbar/jquery.mCustomScrollbar.min.css">
<script src="${js}/scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
<style>
.mCSB_scrollTools .mCSB_draggerContainer {right:-5px;}
</style>
</head>
<body class="easyui-layout">
<div id="north-panel" class="north-panel" data-options="region:'north',border:false" style="height:81px;">
	<div class="north-top">
    	<div class="top-left-logo"><div class="top-left-title">管理员：<span><shiro:principal/></span> 您好！欢迎您登录使用</div></div>
    	<div class="north-top-left">个性化相册后台管理系统</div>
        <div class="north-top-right">
        	<ul>
            	<li><div class="home-icon" onclick="showOmtabs()"></div></li>
                <li><div class="password-icon" onclick="changePasWord()"></div></li>
                <li><div class="logout-icon" onclick="logout()"></div></li>
            </ul>
        </div>
    </div>
</div>

<div data-options="region:'west'" title="<span class='menu-up'></span><span class='menu-down'></span>" style="width:179px;padding-left:3px;">
	<div id="westMenu" class="west-menu"></div>
</div>

<div id="center-panel" class="center-panel" data-options="region:'center'" style="padding:0 3px 0 5px;">
	<div id="eTab" class="easyui-tabs" data-options="border:false" style="width:100%;">
		<div title="我的主页">
			<iframe id="indexF" width='100%' style="border:0;background:url(${img}/bj.jpg) 50px 0;"></iframe>
		</div> 
	</div>
</div>
<div data-options="region:'south'" style="height:20px;"><div id="footer">Copyright &copy; 2014.</div></div>
<script>
$(function(){
	resize();
	loadMenuTree();
	$('.home-icon').tooltip({
		position: 'bottom',
		content: '<span style="color:#fff">主页</span>',
		onShow: function(){
			$(this).tooltip('tip').css({
				backgroundColor: '#666',
				borderColor: '#666'
			});
		}
	});
	$('.password-icon').tooltip({
		position: 'bottom',
		content: '<span style="color:#fff">修改密码</span>',
		onShow: function(){
			$(this).tooltip('tip').css({
				backgroundColor: '#666',
				borderColor: '#666'
			});
		}
	});
	$('.logout-icon').tooltip({
		position: 'bottom',
		content: '<span style="color:#fff">注销</span>',
		onShow: function(){
			$(this).tooltip('tip').css({
				backgroundColor: '#666',
				borderColor: '#666'
			});
		}
	});

});
function resize(){
 	$('#eTab').height($('#center-panel').height());	
	tabH = $('#eTab').height() - $(".tabs-wrap").height()-7;
	$('#indexF').height(tabH);	
}
function loadMenuTree(){
	$.post(ctx+"index/tree",
	   function(data){
			$('#westMenu').menuTree(JSON.parse(data));
			$('#westMenu').height(tabH);
			$('#westMenu').mCustomScrollbar({
				theme:"minimal-dark"
			});
	});
	$('.menu-up').click(function(){
		var $li = $(".west-menu-content").find('li .one-right-icon-minus');
		$li.attr('class','one-right-icon-add');
		$li.parent().next().slideUp();
	})
	$('.menu-down').click(function(){
		var $li = $(".west-menu-content").find('li .one-right-icon-add');
		$li.attr('class','one-right-icon-minus');
		$li.parent().next().slideDown();
	})
}

function openTab(menuId){
	$.post(ctx+"/system/prg/menu/getMenu", {"menuId":menuId},
	   function(msg){
			var url = ctx+msg.menuUrl;
			var text = msg.menuName;
			var opts = {
	            title : text, 
	            tabId :'menu'+menuId,
	            content : "<iframe id='"+menuId+"' frameBorder='no' width='100%' height='"+tabH+"' src='"+url+"' ></iframe>",
	            closable : true
	        };
			var tabs = $('#eTab');
			if(tabs.tabs('exists', text)){
				tabs.tabs('select', text);
				var tab = tabs.tabs('getSelected');  // 获取选择的面板
				tabs.tabs('update', {tab:tab,options:opts});
			}else{
				tabs.tabs('add', opts);
			}

	   }, "json");
}
function showOmtabs(){document.location.reload();} 
function logout(){
	window.location.href=ctx+'/security/logout';
}
function changePasWord(){
	$.dialog.open(ctx+'/index/password', {
		lock: true,
		width:600,
		height:220
	});
 }
 
function openChildTab(url,menuId,text){
	var opts = {
        title : text, 
        tabId :'menu'+menuId,
        content : "<iframe id='"+menuId+"' frameBorder='no' width='100%' height='"+tabH+"' src='"+url+"' ></iframe>",
        closable : true
    };
	var tabs = $('#eTab');
	if(tabs.tabs('exists', text)){
		tabs.tabs('select', text);
		var tab = tabs.tabs('getSelected');  // 获取选择的面板
		tabs.tabs('update', {tab:tab,options:opts});
	}else{
		tabs.tabs('add', opts);
	}
} 
</script>
</body>
</html>
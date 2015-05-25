<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/views/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
body{background:#e3e3e3;height:auto;}
#container{margin:10px 0 0 10px;}
#imgDiv{width:1000px;margin-left:20px;padding:0;}
.title{margin:10px 0 0 30px;}
#filelist{margin-left:30px;}
.imgUl li img{width:80px;height:60px;}
.imgUl li:hover{cursor:default}
.picCheckBox{position:absolute;left:-4px;top:-4px;}
</style>
<script type="text/javascript" src="${js}/plupload.full.min.js"></script>
</head>
<body>
<div class="title dialogPage">用户名:${pUser.PUserName}&nbsp;&nbsp;真实姓名:${pUser.PRealName}
	<button type="button" class="button" onclick="save()">保存图片</button>
</div>
<div id="imgDiv">
<ul class="imgUl">
	<c:forEach var="up" items="${userPicList}" varStatus="status">
		<li>
			<input type="checkbox" name="picId" value="${up.picId}" class="picCheckBox"></input>
			<img src="${picIp}${up.picUrl}">
		</li>
	</c:forEach>
</ul>
</div>
<script>
$(function(){
	var pluginPicListJson =JSON.parse('${pluginPicListJson}');
	$('input[name="picId"]').each(function(){ 
		for(var i=0;i<pluginPicListJson.length;i++){
			if($(this).val()==pluginPicListJson[i].picId){
				 $(this).attr("checked",true);
			}
		}
	}); 
	console.log(pluginPicListJson);
});
function save(){
	var aPicId = [];
	$('input[name="picId"]:checked').each(function(){ 
		aPicId.push($(this).val());
	}); 
	if(aPicId.length==0){
		$.messager.alert('提示:','请至少选择一张图片'); 
		return
	}
	$.ajax({
		url: '<c:url value="/pic_user_plugin_photo/insert"/>',
		data: {'userPluginId':'${userPluginId}','aPicId':aPicId.join(',')},
		type: "POST",
		success: function(data)	{
			var win=art.dialog.open.origin; 
			win.showTip(1);
			art.dialog.close();
		}
 	 });	
}
</script>
</body>
</html>
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
</style>
<script type="text/javascript" src="${js}/plupload.full.min.js"></script>
</head>
<body>
<div class="title dialogPage" >用户名:${pUser.PUserName}&nbsp;&nbsp;真实姓名:${pUser.PRealName} 
	<button type="button" class="button" onclick="save()">保存图片</button>
</div>
<div id="imgDiv"></div>
<script>
var obj = {id:'imgDiv',	aImg:[],aPic:[],rowNum:6};
$(function(){
	var picList = JSON.parse('${pluginPicListJson}');
	var picIp = '${picIp}';
	for(var i=0;i<picList.length;i++){
		obj.aImg.push(picIp+picList[i].picUrl);
		obj.aPic.push(picList[i].picId);
	}
	$.imgDrag(obj);
})

function save(){
	var aPicId = $.getImgIds();
 	$.ajax({
			url: '<c:url value="/pic_user_plugin_photo/update"/>',
			data: {'aPicId':aPicId.join(','),'userPluginId':'${userPluginId}'},
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
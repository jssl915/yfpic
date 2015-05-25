<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/views/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<form id="form1" action="update">
<div class="dialogPage">
	<div class="om-panel-header">新增</div>
	<div class="editDiv" style="height:250px;">
		<input type="hidden" id="pUserId" name="pUserId" value="${pUser.PUserId}">
		<table class="editTable">
		<tr>
			<td><span class="required">*</span>用户名称：</td>
			<td>${pUser.PUserName}</td>
		</tr>
		<tr>
			<td>组件名称：</td>
			<td><input id="pluginId" name="pluginId" type="combo"></td> 
			<td>排序</td>
			<td><input type="text" id="pluginOrder" name="pluginOrder" value="${userPlugin.pluginOrder}"></td>
		</tr>
	   </table>
	   <div class="editBtn" style="margin-top:130px;">
			<button type="submit" class="button">&nbsp;保存&nbsp;</button>
			<button type="button" class="button" onclick="javascript:art.dialog.close();">&nbsp;关闭&nbsp;</button>
		</div>
	</div>
</div>
</form>
</body>
<script type="text/javascript">
$(function(){
	//验证长度不超过32位，不能重命，不能为空
	$('#pluginOrder').validatebox({
		validType:['number','maxLength[8]']
	});
    $('#pluginId').combobox({  
    	data:JSON.parse('${pluginCombo}'),
    	panelHeight:'auto',
    	editable:false,
    	value:'${userPlugin.pluginId}'
    }); 
	$(":submit").click(function(){
		if(!$("#form1").form('validate')){return false;}
	});
});
</script>
</html>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/views/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<form id="form1" action="insert">
<div class="dialogPage">
	<div class="om-panel-header">新增</div>
	<div class="editDiv">
		<table class="editTable">
		<tr>
			<td><span class="required">*</span>组件名称：</td>
			<td><input type="text" id="pluginName" name="pluginName"></td>
			<td>排序：</td>
			<td><input type="text" id="pluginOrder" name="pluginOrder"></td>
		</tr>
		<tr>
			<td>组件图片：</td>
			<td><input type="text" id="pluginPic" name="pluginPic"></td>
		</tr>
		<tr>
			<td>组件描述：</td>
			<td colspan="3">
			<textarea name="pluginRemark" id="pluginRemark" cols="58" rows="3" maxlength="256"></textarea>
			</td>
		</tr>
	   </table>
	   <div class="editBtn">
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
	$('#pluginName').validatebox({
		required:true,
		validType:['isExist["checkPluginName","pluginName","用名户已经存在,请重新输入"]','maxLength[32]']
	});  
	$('#pluginOrder').validatebox({
		validType:['number','maxLength[8]']
	});
	$(":submit").click(function(){
		if(!$("#form1").form('validate')){return false;}
	});
});
</script>
</html>
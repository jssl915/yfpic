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
	<div class="om-panel-header">编辑</div>
	<div class="editDiv">
		<input type="hidden" id="pluginId" name="pluginId" value="${plugin.pluginId}">
		<table class="editTable">
		<tr>
			<td><span class="required">*</span>组件名称：</td>
			<td><input type="text" id="pluginName" name="pluginName" value="${plugin.pluginName}"></td>
		</tr>
		<tr>
			<td>排序：</td>
			<td><input type="text" id="pluginOrder" name="pluginOrder" value="${plugin.pluginOrder}"></td>
			<td>组件状态：</td>
			<td><input id="pStatus" name="pStatus" type="combo"></td> 
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
			<button id ="btnSubmit" type="button" class="button" >&nbsp;保存&nbsp;</button>
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
		validType:['isExist["checkPluginName","pluginName,pluginId","用名户已经存在,请重新输入"]','maxLength[32]']
	});  
	$('#pluginOrder').validatebox({
		validType:['number','maxLength[8]']
	});
    $('#pStatus').combobox({  
    	data:JSON.parse('${statusCombo}'),
    	panelHeight:'auto',
    	editable:false,
    	value:'${plugin.PStatus}'
    }); 
	$('#btnSubmit').click(function(){
		if($('#form1').form('validate')){
			$('#form1').submit();
		}
	})
});
</script>
</html>
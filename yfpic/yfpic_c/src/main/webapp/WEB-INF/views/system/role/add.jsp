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
			<td><span class="required">*</span>角色名称：</td>
			<td><input type="text" id="roleName" name="roleName"></td>
			<td>角色顺序：</td>
			<td><input type="text" id="roleOrder" name="roleOrder"></td>
		</tr>
		<tr>
			<td>角色描述：</td>
			<td colspan="3">
			<textarea name="roleDesc" id="roleDesc" cols="58" rows="3" maxlength="256"></textarea>
			</td>
		</tr>
	   </table>
	   <div class="editBtn">
			<button id ="btnSubmit" type="button" class="button">&nbsp;保存&nbsp;</button>
			<button type="button" class="button" onclick="javascript:art.dialog.close();">&nbsp;关闭&nbsp;</button>
		</div>
	</div>
</div>
</form>
</body>
<script type="text/javascript">
$(function(){
	$('#roleName').validatebox({
		required:true,
		validType:['isExist["checkRoleName","roleName"]','maxLength[32]']  
	}); 
	$('#roleOrder').validatebox({
		validType:['number','maxLength[8]']
	});
	$('#btnSubmit').click(function(){
		if($('#form1').form('validate')){
			$('#form1').submit();
		}
	})
});
</script>
</html>
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
		<input type="hidden" id="pUserId" name="pUserId" value="${pUser.PUserId}">
		<table class="editTable">
		<tr>
			<td><span class="required">*</span>登录名：</td>
			<td><input type="text" id="pUserName" name="pUserName" value="${pUser.PUserName}"></td>
			<td>真实姓名：</td>
			<td><input type="text" id="pRealName" name="pRealName" value="${pUser.PRealName}"></td>
		</tr>
		<tr>
			<td>手机号：</td>
			<td><input type="text" id="pMobile" name="pMobile" value="${pUser.PMobile}"></td>
			<td>邮箱地址：</td>
			<td><input type="text" id="pUserEmail" name="pUserEmail" value="${pUser.PUserEmail}"></td>
		</tr>
		<tr>
			<td>姓别：</td>
			<td><input id="pUserSex" name="pUserSex" type="combo"></td> 
			<td>用户状态：</td>
			<td><input id="pStatus" name="pStatus" type="combo"></td> 
		</tr>
		<tr>
			<td>排序：</td>
			<td><input type="text" id="pUserOrder" name="pUserOrder" value="${pUser.PUserOrder}"></td>
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
	$('#pUserName').validatebox({
		required:true,
		validType:['isExist["checkUserName","pUserName,pUserId"]','maxLength[32]']  
	}); 
	$('#pRealName').validatebox({required: true}); 
	$('#pUserOrder').validatebox({
		validType:['number','maxLength[8]']
	});
	$('#pUserEmail').validatebox({validType:'email'});
	$('#pMobile').validatebox({validType:'mobile'});
    $('#pUserSex').combobox({  
    	data:JSON.parse('${sexCombo}'),
    	panelHeight:'auto',
    	value:'${pUser.PUserSex}'
    }); 
    $('#pStatus').combobox({  
    	data:JSON.parse('${statusCombo}'),
    	panelHeight:'auto',
    	editable:false,
    	value:'${pUser.PStatus}'
    }); 
	$('#btnSubmit').click(function(){
		if($('#form1').form('validate')){
			$('#form1').submit();
		}
	})
});
</script>
</html>
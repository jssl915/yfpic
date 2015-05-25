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
			<td><span class="required">*</span>登录名：</td>
			<td><input type="text" id="pUserName" name="pUserName"></td>
			<td><span class="required">*</span>初始密码：</td>
			<td><input type="text" id="pUserPsd" name="pUserPsd" value="${initPassword}" readonly="readonly"></td>
		</tr>
		<tr>
			<td><span class="required">*</span>真实姓名：</td>
			<td><input type="text" id="pRealName" name="pRealName"></td>
			<td>手机号：</td>
			<td><input type="text" id="pMobile" name="pMobile"></td>
		</tr>
		<tr>
			<td>邮箱地址：</td>
			<td><input type="text" id="pUserEmail" name="pUserEmail"></td>
			<td>姓别：</td>
			<td><input id="pUserSex" name="pUserSex" type="combo"></td> 
		</tr>
		<tr>
			<td>排序：</td>
			<td><input type="text" id="pUserOrder" name="pUserOrder"></td>
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
	$('#pUserName').validatebox({
		required:true,
		validType:['isExist["checkUserName","pUserName","用名户已经存在,请重新输入"]','maxLength[32]']
	});  
	$('#pRealName').validatebox({required:true}); 
	$('#pUserOrder').validatebox({
		validType:['number','maxLength[8]']
	});
	$('#pUserEmail').validatebox({
		validType:'email',
		invalidMessage:'输入的邮箱格式不对'
	});
	$('#pMobile').validatebox({validType:'mobile'});
    $('#pUserSex').combobox({  
    	data:JSON.parse('${sexCombo}'),
    	panelHeight:'auto',
    	editable:false,
    	value:0
    }); 
	$(":submit").click(function(){
		if(!$("#form1").form('validate')){return false;}
	});
});
</script>
</html>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/views/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
body{background:#fff;height:auto;}
</style>
</head>
<body>
<form id="form1" action="insert">
<div class="dialogPage">
	<div class="om-panel-header" style="border-top:0;">新增</div>
	<div class="editDiv">
		<table class="editTable">
		<tr>
			<td><span class="required">*</span>登录名：</td>
			<td><input type="text" id="userName" name="userName"></td>
			<td><span class="required">*</span>初始密码：</td>
			<td><input type="text" id="userPwd" name="userPwd" value="${initPassword}" readonly="readonly"></td>
		</tr>
		<tr>
			<td><span class="required">*</span>真实姓名：</td>
			<td><input type="text" id="realName" name="realName"></td>
			<td>手机号：</td>
			<td><input type="text" id="mobile" name="mobile"></td>
		</tr>
		<tr>
			<td>邮箱地址：</td>
			<td><input type="text" id="email" name="email"></td>
			<td>姓别：</td>
			<td><input id="sex" name="sex" type="combo"></td> 
		</tr>
		<tr>
			<td>排序：</td>
			<td><input type="text" id="userOrder" name="userOrder"></td>
		</tr>
	   </table>
	   <div class="editBtn">
			<button id="save" type="button" class="button">&nbsp;保存&nbsp;</button>
			<button id="clear" type="button" class="button">&nbsp;清空&nbsp;</button>
			<button type="button" class="button" onclick="closeChildTab();">&nbsp;关闭&nbsp;</button>
		</div>
	</div>
</div>
</form>
</body>
<script type="text/javascript">
$(function(){
	//验证长度不超过32位，不能重命，不能为空
	$('#userName').validatebox({
		required:true,
		validType:['isExist["checkUserName","userName","用名户已经存在,请重新输入"]','maxLength[32]']
	}); 
	$('#realName').validatebox({required:true}); 
	$('#userOrder').validatebox({
		validType:['number','maxLength[8]']
	});
	$('#email').validatebox({
		validType:'email',
		invalidMessage:'输入的邮箱格式不对'
	});
	$('#mobile').validatebox({validType:'mobile'});
    $('#sex').combobox({  
    	data:JSON.parse('${sexCombo}'),
    	panelHeight:'auto',
    	editable:false
    }); 
	$("#save").click(function(){
		if(!$("#form1").form('validate')){return false;}
		 $.ajax({
             type: "POST",
             dataType: "json",
             url:'insert',
             data: $('#form1').serialize(),
             success: function (data) {
            	 if(data.success){
     				$.messager.show({ 
     					title:'温馨提示:', 
     					msg:'新增成功!', 
     					timeout:1500, 
     					showType:'slide'
     				});
     			} else {
     				$.messager.alert('提示:',data.msg,'warning'); 
     			}
             }
         });
	});
	$('#clear').click(function(){
		clearForm(document.forms[0]);
		$('#userPwd').val('${initPassword}');
	});
});
function closeChildTab(){
	parent.closeChildTab('用户新增');
}
</script>
</html>
<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/views/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
<div id="toolbar">
<form id="list" action="list">
<div id="search" class="easyui-panel" title="查询条件" data-options="fit:true,collapsible:true,border:0"> 
  <table class="searchTable">
	<tr>
		<td>用户名：</td>
		<td><input type="text" id="pUserName" name="pUserName"></td>
		<td>真实姓名：</td>
		<td><input type="text" id="pRealName" name="pRealName"></td>
		<td><button id="queryBtn" type="button" class="button">查询</button></td>
	</tr>
	<tr>
		<td>状态：</td>
		<td><input id="pStatus" name="pStatus" type="combo"></td>
		<td>创建时间：</td>
		<td><input id="createTimeStart" name="createTimeStart" type="datetime" class="easyui-datebox"></input>
			至 <input id="createTimeEnd" name="createTimeEnd"  type="datetime" class="easyui-datebox"/>
		</td>  
		<td><button id="clearBtn" type="button" class="button">清空</button></td>
	</tr>
   </table>
</div>
</form>
<div class="operate">
	<div class="om-panel-header">用户管理列表</div>
	<div class="icon">
		<ul>
    		<li><a href="#" onclick="showAdd('/pic_user/showAdd',600,250);"><span class="menu1"></span>添加</a></li>
    		<li><a href="#" onclick="showEdit('/pic_user/showEdit','pUserId',600,250);"><span class="menu13"></span>修改</a></li>
    		<li><a href="#" onclick="removeRow('pUserId');"><span class="menu11"></span>删除</a></li>
    		<li><a href="#" onclick="initPwd('pUserId');"><span class="menu9"></span>初始化密码</a></li>
		</ul>
	</div>
</div>
</div>
<table id="grid" data-options="fit:true,border:false"></table>
</body>
<script type="text/javascript">
$(function() {
	$('#grid').datagrid({   
	    url:'list', 
	    pageSize :10,
		pageList : [10, 20, 30, 40, 50, 100, 200, 300, 400, 500, 1000 ],
		striped : true,
		rownumbers : true,
		pagination : true,
		toolbar : '#toolbar',
	    columns : [[ {width : '50', field : 'ck',checkbox:true},
	                 {width : '100',title : '用户名',field : 'pUserName'},
	                 {width : '100',title : '真实姓名',field : 'pRealName'},
	                 {width : '50',title : '姓别',field : 'pUserSex',formatter:function(v,r){return JSON.parse('${sexMap}')[v]}},
	                 {width : '100',title : '状态',field : 'pStatus',formatter:function(v,r){return JSON.parse('${statusMap}')[v]}},
					 {width : '150',title : '创建时间',field : 'createTime'},
	                 {width : '150',title : '手机号码',field : 'pMobile'},
	                 {width : '50',title : '排序',field : 'pUserOrder'},
	                 {width : '80',title : '图片管理',field : 'pUserId',sortable:true,formatter:function(value,row){
	                	 return '<span class="operateBtn" onclick="picAdd('+row.pUserId+')">添加</span> | <span class="operateBtn" onclick="picEdit('+row.pUserId+')">编辑</span>';
	    			 }},
	                 {width : '80',title : '组件管理',field : 'action',sortable:true,formatter:function(value,row){
	                	 return '<a href="#" onclick="showPlugin('+row.pUserId+')"><img src="<c:url value='/static/images/childs.gif'/>"/></a>';
	    			 }}]
	    		]
	}); 
    $('#pStatus').combobox({  
    	data:JSON.parse('${statusCombo}'),
    	editable:false,
    	panelHeight:'auto'
    }); 
});

function initPwd(deleteId){
	var selections = $('#grid').datagrid('getSelections');
	if (selections.length == 0) {
		$.messager.alert('提示:','请至少选择一行记录'); 
		return false;
	}
	$.messager.confirm('提示:','确定对所选数据进行初始化密码(<span style="color:red">${initPassword}</span>)？',function(e){ 
		if(e){ 
		   var ids = [];
  		   for(var i=0;i<selections.length;i++){ids.push(selections[i][deleteId]);}
  		   $.post('initPwd',{"ids":ids.toString()}, function(data) {
  			 if(data.success){
  					$("#grid").datagrid('reload'); 	
  					$.messager.show({ 
  						title:'温馨提示:', 
  						msg:'初始化密码成功!', 
  						timeout:1500, 
  						showType:'slide'
  					}); 
  				} else {
  					$.messager.alert('提示:',data.msg,'warning'); 
  				}
  			}, 'json');
		}
	}); 	
}

function picAdd(pUserId){
	parent.openChildTab(ctx+"pic_user_photo/add?pUserId="+pUserId,'addUserPic','用户图片新增');
}
function picEdit(pUserId){
	parent.openChildTab(ctx+"pic_user_photo/edit?pUserId="+pUserId,'editUserPic','用户图片编辑');
}

function showPlugin(pUserId){
	parent.openChildTab(ctx+"pic_user_plugin/init?pUserId="+pUserId,'editUserPlugin','用户组件列表');
}
</script>
</html>
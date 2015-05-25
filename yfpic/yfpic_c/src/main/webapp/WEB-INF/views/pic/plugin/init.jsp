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
		<td>组件名称：</td>
		<td><input type="text" id="pluginName" name="pluginName"></td>
		<td>状态：</td>
		<td><input id="picStatus" name="picStatus" type="combo"></td>
		<td>创建时间：</td>
		<td><input id="createTimeStart" name="createTimeStart" type="datetime" class="easyui-datebox"></input>
			至 <input id="createTimeEnd" name="createTimeEnd"  type="datetime" class="easyui-datebox"/>
		</td> 
		<td><button id="queryBtn" type="button" class="button">查询</button></td> 
		<td><button id="clearBtn" type="button" class="button">清空</button></td>
	</tr>
   </table>
</div>
</form>
<div class="operate">
	<div class="om-panel-header">组件管理列表</div>
	<div class="icon">
		<ul>
    		<li><a href="#" onclick="showAdd('/pic_plugin/showAdd',600,300);"><span class="menu1"></span>添加</a></li>
    		<li><a href="#" onclick="showEdit('/pic_plugin/showEdit','pluginId',600,300);"><span class="menu13"></span>修改</a></li>
    		<li><a href="#" onclick="removeRow('pluginId');"><span class="menu11"></span>删除</a></li>
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
	                 {width : '150',title : '组件名称',field : 'pluginName'},
	                 {width : '100',title : '状态',field : 'picStatus',formatter:function(v,r){return JSON.parse('${statusMap}')[v]}},
					 {width : '150',title : '创建时间',field : 'createTime'},
	                 {width : '50',title : '排序',field : 'pluginOrder'},
	                 {width : '150',title : '备注',field : 'pluginRemark'}]
	    		]
	}); 
    $('#picStatus').combobox({  
    	data:JSON.parse('${statusCombo}'),
    	editable:false,
    	panelHeight:'auto'
    }); 
});
</script>
</html>
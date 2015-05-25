<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/views/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${js}/scrollbar/jquery.mCustomScrollbar.min.css">
<script src="${js}/scrollbar/jquery.mCustomScrollbar.concat.min.js"></script>
</head>
<body>
<div class="dialogPage">
	<div class="om-panel-header">绑定菜单</div>
	<div class="treeDiv" style="width:238px;border:1px solid #86a3c4;">
		<ul id="navtree"></ul>
	</div>
	<form id="form1" action="bindMenu">
	<div class="editBtn" style="position:absolute;bottom:0px;margin:10px auto;">
		<input type="hidden" name="roleId" value="${roleId}" />
		<input type="hidden" name="menuIds" id="menuIds"/>
		<button type="button" class="button" onclick="doSubmit()">保存</button>
		<button type="button" class="button" onclick="javascript:art.dialog.close();">关闭</button>
	</div>
	</form>
</div>
<script>
$(function(){
	var flag = false;
  	$(".treeDiv").css("height","420px"); 
    $('#navtree').tree({   
	    url:ctx+'/system/prg/role/loadResourceTree',
	    checkbox:true,
	    cascadeCheck: false,
	    onLoadSuccess:function(){
	    	var sRoleMenuJson = ${SRoleMenuJson};
	    	for(var i=0;i<sRoleMenuJson.length;i++){
	    		var n = $("#navtree").tree('find',sRoleMenuJson[i].menuId);
	            if(n){$("#navtree").tree('check',n.target)}
	    	}
	    	$('#navtree').height($('.treeDiv').height());
	    	$('#navtree').mCustomScrollbar({theme:"minimal-dark"});
	    	flag = true;
	    },       
	    onCheck: function (node, checked) {
	    	//初始化之后才生效
	    	if(flag){
	    		if(checked){
	    			 var parentNode = $("#navtree").tree('getParent', node.target);
	    			 //父节点不为空
	    			 if(parentNode!=null) {
	    				 //如果没选中就勾选，选中就不管
	    				 if(!parentNode.checked){
	    					 $("#navtree").tree('check', parentNode.target);
	    				 }
	    			 }
	    			 //子节点都没有选中的话，让其选中,如果有一个选中了则不管
	    			 var childNode = $("#navtree").tree('getChildren', node.target);
	    			 if (childNode.length > 0) {
	    				 var bFlag = true;
	                     for (var i = 0; i < childNode.length; i++) {
	                    	 if(childNode[i].checked){bFlag=false;break;}
	                     }
	                     if(bFlag){
	                    	 for (var i = 0; i < childNode.length; i++) {
	                    		 $("#navtree").tree('check', childNode[i].target);
		                     }
	                     }
	                 }
	    		}else{
	    			 var childNode = $("#navtree").tree('getChildren', node.target);
	                 if (childNode.length > 0) {
	                     for (var i = 0; i < childNode.length; i++) {
	                    	 if(childNode[i].checked){
		                         $("#navtree").tree('uncheck', childNode[i].target);
	                    	 }
	                     }
	                 }
	    		}
	    	}
        }
	}); 
});
function doSubmit(){
	var nodes = $('#navtree').tree('getChecked');
	var menuIds=[];
    for(var i=0;i <nodes.length; i++){
    	menuIds.push(nodes[i].id);
	}
	$("#menuIds").val(menuIds.toString());
	$("#form1").submit();
}
</script>
</body>
</html>
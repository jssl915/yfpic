$(function(){
	//查询条件展开收缩方法
	$('.easyui-panel').panel({   
	    onCollapse:function(){   
	    	$('#grid').datagrid('resize');
	    },
	    onExpand:function(){
	    	$('#grid').datagrid('resize');
	    },
	    onBeforeExpand:function(){
	    	$('#search').height('auto');
	    }
	}); 
	//查询按钮点击事件
	$('#queryBtn').click(function(){
		$('#grid').datagrid('load',getFormData(document.forms[0]));
	});
	//清空按钮点击事件
	$('#clearBtn').click(function(){
		clearForm(document.forms[0]);
	});	
});

//封装常用的工具方法，如搜索框数据格式化、清空,add edit remove showTip..等
(function(){
	var init = {
		//格式化搜索框中的数据
		getFormData:function(form){
			var obj = {};
			//文本框
			$("#"+form.id+" :input[type='text']").each(function() {
				obj[$(this).attr("name")] = $(this).val();
			});
			// 隐藏框
			$("#"+form.id+" :input[type='hidden']").each(function() {
				obj[$(this).attr("name")] = $(this).val();
			});
			//date
			$("#" + form.id + " :input[type='date']").each(function() {
				obj[$(this).attr("name")] = $(this).val();
			});
			// select下拉框
			$("#" + form.id + " select").each(function() {
				obj[$(this).attr("name")] = $(this).val();
			});
			return obj;
		},
		//清除搜索框中的数据，注意会清空隐藏域
		clearForm:function(form){
			for(var i=0;i<form.elements.length;i++){
				var field=form.elements[i];
				var fieldType=form.elements[i].type.toLowerCase();
				if(fieldType!="submit" && fieldType!="reset" && fieldType!="button"){  
					if(fieldType=="radio" || fieldType=="checkbox"){
						field.checked=false;
					}else if(fieldType=="select"){
						field.selected=false;
					}else{
						field.value="";
					}
				}
				var dataName = form.elements[i].name;
				if(dataName=='createTimeStart'||dataName=='createTimeEnd'||dataName=='updateTimeStart'||dataName=='updateTimeEnd'){
					field.value="";
				}
			}
		},
		list:{
			tip:function(){
				$("#grid").datagrid('reload');
				refleshTree&&($("#navtree").tree('reload'));//刷新菜单树，默认false不删除，只有菜单功能会刷新
				$.messager.show({ 
					title:'温馨提示:', 
					msg:'操作成功!', 
					timeout:1500, 
					showType:'slide'
				}); 	
			},
			add:function(url,w,h){
				$.dialog.open(ctx+url, {
					lock: true,
					width:w,
					height:h
				});
			},
			edit:function(url,updateId,w,h){
				var selections = $('#grid').datagrid('getSelections');
				if (selections.length != 1) {		
					$.messager.alert('提示:','只能选择一行记录!'); 
					return false;
				}
				var id = selections[0][updateId];
				$.dialog.open(ctx+url+'?'+updateId+'='+id, {
					lock: true,
					width:w,
					height:h
				});
			},
			remove:function(deleteId){
				var selections = $('#grid').datagrid('getSelections');
				if (selections.length == 0) {
					$.messager.alert('提示:','请至少选择一行记录'); 
					return false;
				}
				$.messager.confirm('提示:','你确认要删除吗?',function(e){ 
					if(e){ 
					   var ids = [];
			  		   for(var i=0;i<selections.length;i++){ids.push(selections[i][deleteId]);}
			  		   $.post('delete',{"ids":ids.toString()}, function(data) {
			  			   if(data.success){
			  					$("#grid").datagrid('reload');
			  					refleshTree&&($("#navtree").tree('reload'));
			  					$.messager.show({ 
			  						title:'温馨提示:', 
			  						msg:'删除数据成功!', 
			  						timeout:1500, 
			  						showType:'slide'
			  					}); 
			  				} else {
			  					$.messager.alert('提示:',data.msg,'warning'); 
			  				}
			  			}, 'json');
					}
				}); 
			},
			refreshGrid:function(id){
				$('#'+id).datagrid('reload');// 刷新当前页数据
				$.messager.show({ 
					title:'温馨提示:', 
					msg:'操作成功!', 
					timeout:1500, 
					showType:'slide'
				}); 	
			}
		}
	};
	//提供一些全局方法
	window.getFormData = init.getFormData;
	window.clearForm = init.clearForm;
	window.showTip = init.list.tip;
	window.showAdd = init.list.add;
	window.showEdit = init.list.edit;
	window.removeRow = init.list.remove;
	window.showAdd = init.list.add;
	window.refreshGrid = init.list.refreshGrid;
	
	window.refleshTree=false;
}(window));

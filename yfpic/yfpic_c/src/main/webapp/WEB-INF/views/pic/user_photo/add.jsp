<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/WEB-INF/views/taglibs.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<style>
body{background:#e3e3e3;height:auto;}
#container{margin:10px 0 0 10px;}
#imgDiv{width:1000px;margin-left:20px;padding:0;}
.title{margin:10px 0 0 30px;}
#filelist{margin-left:30px;}
</style>
<script type="text/javascript" src="${js}/plupload.full.min.js"></script>
</head>
<body>
<div class="title">用户名:${pUser.PUserName}&nbsp;&nbsp;真实姓名:${pUser.PRealName}</div>

<div id="filelist">Your browser doesn't have Flash, Silverlight or HTML5 support.</div>
<div id="container" class="dialogPage">
    <a id="pickfiles"><button id ="btnSubmit" type="button" class="button">选择文件</button></a> 
    <a id="uploadfiles"><button type="button" class="button" onclick="javascript:art.dialog.close();">全部上传</button></a>
    <a id="save" ><button type="button" class="button" onclick="save()">保存图片</button></a>
</div>
<pre id="console"></pre>
<div id="imgDiv"></div>
<script>
var obj = {id:'imgDiv',	aImg:[],rowNum:6};
$(function(){
	uploader.init();
	uploader.bind('UploadComplete',function(up,files){ 
		$('#filelist').hide();
		$.imgDrag(obj);
	});
})
var uploader = new plupload.Uploader({
	browse_button : 'pickfiles', 
	url : '${ctx}/pic_user_photo/uploadDo?pUserName=${pUser.PUserName}',
	filters : {
		max_file_size:'1mb',
		mime_types: [
			{title : "Image files", extensions : "jpg,gif,png"},
			{title : "Zip files", extensions : "zip"}
		]
	},
	init: {
		PostInit: function() {
			document.getElementById('filelist').innerHTML = '';
			document.getElementById('uploadfiles').onclick = function() {
				uploader.start();
				return false;
			};
		},
		FilesAdded: function(up, files) {
			$('#filelist').show();
			plupload.each(files, function(file) {
				document.getElementById('filelist').innerHTML += '<div id="' + file.id + '">' + file.name + ' (' + plupload.formatSize(file.size) + ') <b></b></div>';
			});
		},
		UploadProgress: function(up, file) {
			document.getElementById(file.id).getElementsByTagName('b')[0].innerHTML = '<span>' + file.percent + "%</span>";
		},
		FileUploaded:function(uploader,file,responseObject){
			var d = JSON.parse(responseObject.response);
	    	obj.aImg.push(d.fileUrl+'/'+d.fileName);
		},
		Error: function(up, err) {
			document.getElementById('console').innerHTML += "\nError #" + err.code + ": " + err.message;
		}
	}
});

function save(){
	var imgSrc = [];
	var oImgs = $('.imgUl').find('img');
	for(var i=0;i<oImgs.length;i++){
		imgSrc.push(oImgs[i].src.split('/pic_copy/')[1]);
	}
	if(imgSrc.length==0){
		$.messager.alert('提示:','请至少上传一张图片'); 
		return
	}
	$.ajax({
			url: '<c:url value="/pic_user_photo/insert"/>',
			data: {'picUserId':'${pUser.PUserId}','imgSrc':imgSrc.join(',')},
			type: "POST",
			success: function(data)	{
				var win=art.dialog.open.origin; 
				win.showTip(1);
				art.dialog.close();
				obj.img=[];
				setTimeout(function(){
					window.location.reload();
				},1500);
				
			}
	  });	
}
</script>
</body>
</html>
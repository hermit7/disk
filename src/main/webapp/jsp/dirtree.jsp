<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title></title>
<jsp:include page="/jsp/common/public.jsp"></jsp:include>
<style type="text/css">
</style>
</head>
<body>
	<form>
		<input type="hidden" id="curPath" name="${curPath}" value="${curPath}">
		<input type="hidden" id="originName" name="${originName}" value="${originName}">
		<input type="hidden" id="motion" name="${motion}" value="${motion}">
	</form>
	<div class="container">
		<div class="tree" style="padding: 5px; height: auto">
	    	<ul id="roleTree" class="easyui-tree"></ul>
		</div>
		<div>
			<input type="button" value="确认" onclick="affirm()">&nbsp;
			<input type="button" value="关闭" onclick="cancel()">
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			$('#roleTree').tree({
				url : '${basePath}/file/showDirTree.action'
			});
		});
		
		function affirm(){
			var node = $('#roleTree').tree('getSelected');
			if(node == undefined){
				alert("请选择一个目录");
			}
			var dst = node.attributes.url;
			var curPath = document.getElementById("curPath").value;
			var originName = document.getElementById("originName").value;
			var motion = document.getElementById("motion").value;
			//拿到跳转地址，然后执行move操作 或者移动操作
			$.ajax({
				url : "${basePath}/file/moveFile.action",
				type : "POST",
				data : {
					"curPath" : curPath,
					"originName" : originName,
					"motion" : motion,
					"dst": dst,
				},
				success:function(data){
					if (data) {
						$.messager.show({
							title : 'tip',
							msg : '操作成功',
							timeout : 1000,
							showType : 'slide'
						});
					}
					openFile(curPath);
				},
			});
			$('#dd', window.parent.document).dialog('close');
		}
		
		function cancel(){
			$('#dd', window.parent.document).dialog('close');
			
		}
		
		function openFile(path) {
			var url = "${basePath}/file/list.action?path=" + path;
			url = encodeURI(url);
			url = encodeURI(url);
			var tab = $('#tt', window.parent.parent.document).tabs('getSelected');
			tab.panel('refresh', url);
		}
		
	</script>
</body>
</html>
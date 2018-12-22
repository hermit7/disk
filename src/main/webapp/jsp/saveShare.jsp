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
</head>
<body>
	<input type="hidden" id="path" name="${path}" value="${path}">
	<div class="container">
		<div class="tree" style="padding: 5px; height: auto">
			<ul id="roleTree" class="easyui-tree"></ul>
		</div>
		<div>
			<input type="button" value="确认" onclick="affirm()">&nbsp; <input
				type="button" value="关闭" onclick="cancel()">
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			$('#roleTree').tree({
				url : '${basePath}/file/showDirTree.action'
			});
		});

		function affirm() {
			var node = $('#roleTree').tree('getSelected');
			if (node == undefined) {
				alert("请选择一个目录");
			}
			var dst = node.attributes.url;
			var path = $("#path").val();
			//拿到跳转地址，然后执行move操作 或者移动操作
			$.ajax({
				url : "${basePath}/share/saveShare.action",
				type : "POST",
				data : {
					"path" : path,
					"dst" : dst,
				},
				success : function(data) {
					if (data) {
						$.messager.show({
							title : 'tip',
							msg : '操作成功',
							timeout : 1000,
							showType : 'slide'
						});
					}
				},
			});
			$('#dd', window.parent.document).dialog('close'); 
		}

		function cancel() {
			$('#dd', window.parent.document).dialog('close'); 
		}
	</script>
</body>
</html>
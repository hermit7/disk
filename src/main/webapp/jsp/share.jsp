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
	<input type="hidden" id="path" value="${path}">
	<div class="container">
		<p>用户</p>
		<div class="combobox" style="padding: 5px; height: auto">
			<input id="cc" class="easyui-combobox">
		</div>
		<div>
			<input type="button" value="确认" onclick="addMemberShare()">&nbsp;
			<input type="button" value="关闭" onclick="cancel()">
		</div>
		<p>群</p>
		<div class="combobox" style="padding: 5px; height: auto">
			<input id="gg" class="easyui-combobox">
		</div>
		<div>
			<input type="button" value="确认" onclick="addGroupShare()">&nbsp;
			<input type="button" value="关闭" onclick="cancel()">
		</div>
	</div>
	<script type="text/javascript">
		$(function() {
			$('#cc').combobox({
				url : '${basePath}/relation/showFollows.action',
				valueField : 'id',
				textField : 'text'
			});

			$('#gg').combobox({
				url : '${basePath}/relation/showGroups.action',
				valueField : 'id',
				textField : 'text'
			});
		});

		function addMemberShare() {
			var receiverId = $('#cc').combobox('getValue');//下拉框的取Value方法
			var receiverName = $('#cc').combobox('getText');//下拉框的去Text方法 
			/* if (receiverId == "") {
				alert("请选择一个用户");
			} */
			var path = $("#path").val();
			console.log(receiverId);
			console.log(receiverName);
			console.log(path);
			//然后执行addShare操作
			$.ajax({
				url : "${basePath}/share/addMemberShare.action",
				type : "POST",
				data : {
					"path" : path,
					"receiverId" : receiverId,
					"receiverName" : receiverName
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
		
		function addGroupShare() {
			var groupNumber = $('#gg').combobox('getValue');//下拉框的取Value方法
			var groupName = $('#gg').combobox('getText');//下拉框的去Text方法 
			/* if (receiverId == "") {
				alert("请选择一个用户");
			} */
			var path = $("#path").val(); 
			console.log(groupNumber);
			console.log(groupName);
			console.log(path);
			//然后执行addShare操作
			$.ajax({
				url : "${basePath}/share/addGroupShare.action",
				type : "POST",
				data : {
					"path" : path,
					"groupNumber" : groupNumber,
					"groupName" : groupName
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
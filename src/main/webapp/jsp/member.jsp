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
	<input type="hidden" id="groupNumber" value="${groupNumber}">
	<input type="hidden" id="groupName" value="${groupName}">
	<input type="hidden" id="groupOwner" value="${groupOwner}">
	<div class="container">
		<p>用户</p>
		<div class="combobox" style="padding: 5px; height: auto">
			<input id="cc" class="easyui-combobox">
		</div>
		<div>
			<input type="button" value="确认" onclick="affirm()">&nbsp; <input
				type="button" value="关闭" onclick="cancel()">
		</div>
	</div>

	<script type="text/javascript">
		$(function() {
			$('#cc').combobox({
				url : '${basePath}/relation/showFollows.action',
				valueField : 'userId',
				textField : 'username',
			});
		});
	</script>

	<script type="text/javascript">
		function affirm() {
			var userId = $('#cc').combobox('getValue');//下拉框的取Value方法
			var username = $('#cc').combobox('getText');//下拉框的去Text方法 
			/* if (receiverId == "") {
				alert("请选择一个用户");
			} */
			var groupNumber = $("#groupNumber").val();
			var groupName = $("#groupName").val();
			var groupOwner = $("#groupOwner").val();
			console.log(username);
			console.log(groupNumber);
			console.log(groupName);
			$.ajax({
				url : "${basePath}/relation/addGroupMember.action",
				type : "POST",
				data : {
					"groupName" : groupName,
					"groupNumber" : groupNumber,
					"username" : username,
					"userId" : userId,
					"groupOwner" : groupOwner,
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
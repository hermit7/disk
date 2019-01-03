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
<jsp:include page="/jsp/common/public.jsp"></jsp:include>
<title>friend</title>
</head>
<body>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".upload").click(function() {
				$(".tip").fadeIn(200);
			});

			$(".sure").click(function() {
				$(".tip").fadeOut(100);
			});

			$(".cancel").click(function() {
				$(".tip").fadeOut(100);
			});

		});
	</script>

	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<%-- 此处放一个面包屑导航 --%>
			<li>
				<a href="javascript:void(0)" onclick="listAllUsers()">用户管理</a>
			</li>
			<%-- <c:forEach items="${breadlist}" var="bread">
				<li><a href="javascript:void(0)"
					onclick="openFile('${bread.folderPath}')">${bread.folderName}</a></li>
			</c:forEach> --%>
			<!-- <li><a href="javascript:void(0)" onclick="openFile('/')">列表</a></li> -->
		</ul>
	</div>
	<div class="tools">
		<ul class="toolbar">
			<li class="upload">
				<span><img src="${basePath }/images/upload_1.png" style="width: 30px; height: 28px" /></span>添加
			</li>
			<li class="share">
				<span><img src="${basePath }/images/share_1.png" style="width: 30px; height: 28px" /></span>禁用
			</li>
			<li class="delete">
				<span><img src="${basePath }/images/delete_1.png" style="width: 30px; height: 28px" /></span>删除
			</li>
			<li>
				<input type="text" style="height: 30px; border: none">
				<span><img src="${basePath }/images/search.png" style="width: 30px; height: 28px" /></span>
			</li>
		</ul>

	</div>
	<ul id="tt"></ul>
	<table id="tb" class="filetable">
		<thead>
			<tr>
				<th><input name="" type="checkbox" value="" /></th>
				<th>用户昵称</th>
				<th width="150px"></th>
				<th>用户类型</th>
				<th>已用大小</th>
				<th>账号状态</th>
				<th>容量</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userList}" var="user" varStatus="sta">
				<tr class="row">
					<td><input id="box" name="" type="checkbox" value="" /></td>
					<td width="300px"><img src="${basePath }/images/friend.png" /> <input type="text"
							value="${user.username}" readonly="readonly"></td>
					<td>
						<div class="action">
							<img src="${basePath }/images/ban.png" onclick="banUser('${user.userId}')" title="禁用">
							<img src="${basePath }/images/permit.png" onclick="permitUser('${user.userId}')" title="解禁">
								<img src="${basePath }/images/reduction.png" onclick="reduction('${user.userId}')"
								title="缩容"> <img src="${basePath }/images/dilatation.png" onclick="dilatation('${user.userId}')"
								title="扩容">
						</div>
					</td>
					<td><c:choose>
							<c:when test="${user.userType eq 0}">
								<input type="text" value="管理员" readonly="readonly">
							</c:when>
							<c:when test="${user.userType eq 1}">
								<input type="text" value="普通用户" readonly="readonly">
							</c:when>
							<c:when test="${user.userType eq 2}">
								<input type="text" value="禁用" readonly="readonly">
							</c:when>
						</c:choose></td>
					<td><input class="fileinput" type="text" value="${user.usedSpace}" readOnly="readonly"></td>
					<td><c:choose>
							<c:when test="${user.status eq 0}">
								<input type="text" value="正常" readonly="readonly">
							</c:when>
							<c:when test="${user.status eq 1}">
								<input type="text" value="禁用" readonly="readonly">
							</c:when>
						</c:choose></td>
					<td><input type="text" value="${user.spaceTimes}倍" readOnly="readonly"></td>
				</tr>

			</c:forEach>
		</tbody>
	</table>

	<div class="tip">
		<div class="tiptop">
			<span>找用户</span>
		</div>
		<form id="fileForm" method="POST">
			<div class="tipinfo">
				<div class="tipright">
					<!-- <p>找好友</p> -->
					<input id="searchUser" type="text" name="username" style="height: 30px; position: center"
						placeholder="请输入用户名或其ID">
					<br>
					<input id="followId" type="hidden">
					<br> <span id="useralert"></span>
				</div>
			</div>
			<div class="tipbtn">
				<input name="" type="button" class="sure" onclick="follow()" value="关注" />
				&nbsp;
				<input name="" type="button" class="cancel" value="取消" />
			</div>
		</form>
	</div>

	<script type="text/javascript">
		$(function() {
			$("#searchUser").on("blur", function() {
				var username = $("#searchUser").val();
				console.log(username);
				$.ajax({
					url : "${basePath}/user/existUser.action",
					type : "POST",
					data : {
						"username" : username
					},
					success : function(data) {
						if (null != data && "" != data) {
							$("#followId").val(data.userId);
							console.log(data);
							$("#useralert").html("用户存在");
						} else {
							$("#useralert").html("用户不存在，请输入其他用户名");
						}
					}
				});
			});
		});
	</script>

	<script type="text/javascript">
		function listAllUsers() {
			var url = "${basePath}/system/userManage.action";
			var tab = $('#tt', window.parent.document).tabs('getSelected');
			tab.panel('refresh', url);
		}

		/**
			禁用
		 */
		function banUser(userId) {
			var url = "${basePath}/system/banUser.action";
			$.ajax({
				url : url,
				type : "POST",
				data : {
					"userId" : userId,
				},
				success : function(data) {
					$.messager.show({
						title : 'tip',
						msg : '禁用成功',
						timeout : 1000,
						showType : 'slide'
					});
					listAllUsers();
				}
			});
		}

		/**
			解禁
		 */
		function permitUser(userId) {
			var url = "${basePath}/system/permitUser.action";
			$.ajax({
				url : url,
				type : "POST",
				data : {
					"userId" : userId,
				},
				success : function(data) {
					$.messager.show({
						title : 'tip',
						msg : '解禁成功',
						timeout : 1000,
						showType : 'slide'
					});
					listAllUsers();
				}
			});
		}
		/**
			扩容
		 */
		function dilatation(userId) {
			var url = "${basePath}/system/dilatation.action";
			$.ajax({
				url : url,
				type : "POST",
				data : {
					"userId" : userId,
				},
				success : function(data) {
					$.messager.show({
						title : 'tip',
						msg : '扩容成功',
						timeout : 1000,
						showType : 'slide'
					});
					listAllUsers();
				}
			});
		}
		/**
			缩容
		 */
		function reduction(userId) {
			var url = "${basePath}/system/reduction.action";
			$.ajax({
				url : url,
				type : "POST",
				data : {
					"userId" : userId,
				},
				success : function(data) {
					$.messager.show({
						title : 'tip',
						msg : '缩容成功',
						timeout : 1000,
						showType : 'slide'
					});
					listAllUsers();
				}
			});
		}

		/**
			重命名
		 */
		function remarkFriend(obj) {
			var tr = $(obj).parents(".row");
			var input = $(tr).find(".fileinput");
			var hidden = $(tr).find(".hidden");
			var friendId = hidden.val();
			console.log(friendId);
			input.removeAttr("readonly").focus();
			input.on("blur", function() {
				var remark = input.val();
				console.log(remark);
				$.ajax({
					url : "${basePath}/relation/remark.action",
					type : "POST",
					data : {
						"friendId" : friendId,
						"remark" : remark,
					},
					success : function(data) {
						if (data) {
							$.messager.show({
								title : 'tip',
								msg : '修改成功',
								timeout : 1000,
								showType : 'slide'
							});
						}
						listFriends();
					}
				});
			})
		}

		function deleteFriend(obj) {
			$.messager.confirm('确认', '您确认想要删除此好友？', function(r) {
				var tr = $(obj).parents(".row");
				var hidden = $(tr).find(".hidden");
				var friendId = hidden.val();
				console.log(friendId);
				if (r) {
					$.ajax({
						url : "${basePath}/relation/delete.action",
						type : "POST",
						data : {
							"friendId" : friendId
						},
						success : function(data) {
							if (data) {
								$.messager.show({
									title : 'tip',
									msg : '删除成功',
									timeout : 1000,
									showType : 'slide'
								});
							}
							listFriends();
						}
					});
				}
			});
		}
	</script>
</body>
</html>

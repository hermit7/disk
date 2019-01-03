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
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<%-- 此处放一个面包屑导航 --%>
			<li><a href="javascript:void(0)" onclick="listGroup()">我的群组</a></li>
			<li><a href="javascript:void(0)"
				onclick="listMember('${groupNumber}')">${groupName}</a></li>
		</ul>
	</div>
	<div class="tools">
		<ul class="toolbar">
			<li class="upload"><span><img
					src="${basePath }/images/remove.png"
					style="width: 30px; height: 28px" /></span>移除</li>
		</ul>

	</div>
	<ul id="tt"></ul>
	<table id="tb" class="filetable">
		<thead>
			<tr>
				<th width="5px"><input name="" type="checkbox" value="" /></th>
				<th>用户昵称</th>
				<th width="200px"></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${memberList}" var="member" varStatus="sta">
				<tr class="row">
					<td><input id="box" name="" type="checkbox" value="" /></td>
					<td width="300px"><img src="${basePath }/images/friend.png" />
						<input type="text" value="${member.username}" readonly="readonly"></td>
					<td>
						<div class="action">
							<c:if test="${member.username ne user.username}">
								<img src="${basePath }/images/remark.png"
									onclick="followUser('${member.userId}', '${member.username}')" style="width: 20px; height: 25px"
									title="关注">
							</c:if>
						</div>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<script type="text/javascript">
		function listGroup() {
			var url = "${basePath}/relation/group.action";
			var tab = $('#tt', window.parent.document).tabs('getSelected');
			tab.panel('refresh', url);
		}

		/**
		 * 列出该群的所有成员
		 */
		function listMember(groupNumber) {
			var url = "${basePath}/relation/listMember.action?groupNumber="
					+ groupNumber;
			var tab = $('#tt', window.parent.document).tabs('getSelected');
			tab.panel('refresh', url);
		}

		/**
			关注用户
		 */
		function followUser(friendId, friendName) {
			var url = "${basePath}/relation/follow.action";
			console.log(friendName);
			console.log(friendId);
			$.ajax({
				url : url,
				type : "POST",
				data : {
					"friendId" : friendId,
					"friendName" : friendName
				},
				success : function(data) {
					$.messager.show({
						title : 'tip',
						msg : '关注成功',
						timeout : 1000,
						showType : 'slide'
					});
					listFriends();
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

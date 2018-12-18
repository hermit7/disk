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
<title>无标题文档</title>
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

			$(".newfolder").click(function() {
				$(".tip2").fadeIn(200);
			});

			$(".sure2").click(function() {
				$(".tip2").fadeOut(100);
			});

			$(".cancel2").click(function() {
				$(".tip2").fadeOut(100);
			});
		});
	</script>

	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<%-- 此处放一个面包屑导航 --%>
			<li><a href="javascript:void(0)" onclick="listGroup()">我的群组</a></li>
			<%-- <c:forEach items="${breadlist}" var="bread">
				<li><a href="javascript:void(0)"
					onclick="listMember()">${bread.folderName}</a></li>
			</c:forEach> --%>
		</ul>
	</div>
	<div class="tools">
		<ul class="toolbar">
			<li class="upload"><span><img
					src="${basePath }/images/upload_1.png"
					style="width: 30px; height: 28px" /></span>加群</li>
			<li class="newfolder"><span><img
					src="${basePath }/images/new_file.png"
					style="width: 30px; height: 28px" /></span>创建群组</li>
		</ul>

	</div>
	<ul id="tt"></ul>
	<table id="tb" class="filetable">
		<thead>
			<tr>
				<th width="5px"><input name="" type="checkbox" value="" /></th>
				<th>群名称</th>
				<th>群号</th>
				<th width="200px"></th>
				<th></th>
				<th>创建者</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${groupList}" var="group" varStatus="sta">
				<tr class="row">
					<td><input id="box" name="" type="checkbox" value="" /></td>
					<td width="200px"><img src="${basePath }/images/group.png" />
						<input class="fileinput" type="text" value="${group.groupName}"
						readOnly="readonly"></td>
					<td><input type="text" value="${group.groupNumber}"
						readOnly="readonly"></td>
					<td>
						<div class="action">
							<c:choose>
								<c:when test="${user.username eq group.owner}">
									<img src="${basePath }/images/modify.png"
										onclick="renameGroup(this, '${group.groupNumber}')"
										style="width: 20px; height: 25px" title="修改群名">
									<img src="${basePath }/images/dismiss.png"
										onclick="dismissGroup('${group.groupNumber}')"
										style="width: 20px; height: 25px" title="解散">
								</c:when>
								<c:otherwise>
									<img src="${basePath }/images/quit.png"
										onclick="quitGroup('${group.groupNumber}')"
										style="width: 20px; height: 25px" title="退出该群">
								</c:otherwise>
							</c:choose>
							<img src="${basePath }/images/invite.png"
								onclick="inviteFriend('${group.groupNumber}','${group.groupName }','${ group.owner}')"
								style="width: 20px; height: 25px" title="邀请">
						</div>
					</td>
					<td><a href="javascript:void(0)" onclick="listMember('${group.groupNumber}')">成员</a>&nbsp; | &nbsp;<a href="javascript:void(0)" onclick="listShare('${group.groupNumber}')">文件</a></td>
					<td class="tdlast">${group.owner}</td>
				</tr>

			</c:forEach>
		</tbody>
	</table>

	<div id="dd"></div>

	<div class="tip">
		<div class="tiptop">
			<span>找群</span>
		</div>
		<form id="fileForm" method="POST">
			<div class="tipinfo">
				<div class="tipright">
					<!-- <p>找好友</p> -->
					<input id="searchUser" type="text" name="groupname"
						style="height: 30px; position: center" placeholder="请输入群名称或ID"><br>
					<input id="followId" type="hidden"><br> <span
						id="useralert"></span>
				</div>
			</div>
			<div class="tipbtn">
				<input name="" type="button" class="sure" onclick="joinGroup()"
					value="加入" />&nbsp; <input name="" type="button" class="cancel"
					value="取消" />
			</div>
		</form>
	</div>

	<div class="tip2">
		<div class="tiptop2">
			<span>新建群</span>
		</div>
		<form id="folderForm" method="POST">
			<div class="tipinfo2">
				<div class="tipright2">
					<input type="text" id="newGroup" name="groupname"
						style="height: 30px; position: center" placeholder="请输入群名称"><br>
				</div>
			</div>
			<div class="tipbtn2">
				<input name="" type="button" class="sure2" onclick="mkgroup()"
					value="确认" />&nbsp; <input name="" type="button" class="cancel2"
					value="取消" />
			</div>
		</form>
	</div>

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
			列出该群所有的共享文件
		 */
		function listShare(groupNumber) {
			var url = "${basePath}/share/listGroupShare.action?groupNumber="
					+ groupNumber;
			var tab = $('#tt', window.parent.document).tabs('getSelected');
			tab.panel('refresh', url);
		}

		function inviteFriend(groupNumber, groupName, owner) {
			var url = "${basePath}/relation/member.action?groupNumber="
					+ groupNumber + "&groupName=" + groupName + "&groupOwner="
					+ owner;
			url = encodeURI(url);
			url = encodeURI(url);
			$('#dd').dialog({
				title : '请选择用户',
				width : 400,
				height : 400,
				closed : false,
				cache : false,
				href : url, //传到后台，返回一个jsp页面带着数据返回   传递过去当前这个文件的路径
				modal : true
			});
		}

		/**
			重命名
		 */
		function renameGroup(obj, groupNumber) {
			var tr = $(obj).parents(".row");
			var input = $(tr).find(".fileinput");
			input.removeAttr("readonly").focus();
			input.on("blur", function() {
				var destName = input.val();
				$.ajax({
					url : "${basePath}/relation/renameGroup.action",
					type : "POST",
					data : {
						"groupNumber" : groupNumber,
						"destName" : destName,
					},
					success : function(data) {
						if (data) {
							$.messager.show({
								title : 'tip',
								msg : '修改成功',
								timeout : 2000,
								showType : 'slide'
							});
						}
						listGroup();
					}
				});
			})
		}

		/**
			新建群
		 */
		function mkgroup() {
			var groupName = $("#newGroup").val();
			$.ajax({
				url : "${basePath}/relation/createGroup.action",
				type : "POST",
				data : {
					"groupName" : groupName
				},
				success : function(data) {
					if (data) {
						$.messager.show({
							title : 'tip',
							msg : '创群成功',
							timeout : 1000,
							showType : 'slide'
						});
						listGroup();
					}
				}
			});
		}

		function dismissGroup(groupNumber) {
			$.messager.confirm('确认', '您确认想要解散此群？', function(r) {
				if (r) {
					$.ajax({
						url : "${basePath}/relation/dismissGroup.action",
						type : "POST",
						data : {
							"groupNumber" : groupNumber
						},
						success : function(data) {
							if (data) {
								$.messager.show({
									title : 'tip',
									msg : '解散成功',
									timeout : 1000,
									showType : 'slide'
								});
							}
							listGroup();
						}
					});
				}
			});
		}

		function quitGroup(groupNumber) {
			$.messager.confirm('确认', '您确认想要退出此群？', function(r) {
				if (r) {
					$.ajax({
						url : "${basePath}/relation/quitGroup.action",
						type : "POST",
						data : {
							"groupNumber" : groupNumber
						},
						success : function(data) {
							if (data) {
								$.messager.show({
									title : 'tip',
									msg : '退出成功',
									timeout : 1000,
									showType : 'slide'
								});
							}
							listGroup();
						}
					});
				}
			});
		}
	</script>
</body>
</html>

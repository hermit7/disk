<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>智慧网盘</title>
<jsp:include page="${basePath}/jsp/common/public.jsp"></jsp:include>
<meta charset="UTF-8">
</head>
<body class="easyui-layout">
	<div data-options="region:'north',split:true,minHeight:95,maxHeight:95"
		style="height: 95px;">
		<div class="header"
			style="background: url(${basePath}/images/topbg.gif) repeat-x">
			<div class="topleft">
				<a href="#"><img src="${basePath}/images/logo.png" title="系统首页" /></a>
			</div>

			<div class="topright">
				<ul>
					<li><a href="/login.html">退出</a></li>
				</ul>

				<div class="user">
					<span>${user.username} </span> <i>消息</i><b>0</b>
				</div>
			</div>
		</div>
	</div>
	<div data-options="region:'west',split:true,minWidth:200,maxWidth:200"
		style="width: 200px;">
		<div id="aa" class="easyui-accordion" data-options="fit:true"
			style="width: 190px;">
			<div title="我的网盘"
				data-options="iconCls:'icon-save',border:false,collapsible:false"
				style="padding: 10px">
				<a id="btn1" href="#" class="easyui-linkbutton"
					onclick="openFileUI(this)"
					data-options="iconCls:'icon-blank',plain:true">全部文件</a><br> <a
					id="btn4" href="#" class="easyui-linkbutton"
					onclick="openFriendUI(this)"
					data-options="iconCls:'icon-blank',plain:true">我的关注</a><br> <a
					id="btn5" href="#" class="easyui-linkbutton"
					onclick="openGroupUI(this)"
					data-options="iconCls:'icon-blank',plain:true">共享群组</a><br> <a
					id="btn5" href="#" class="easyui-linkbutton"
					onclick="openReceivedShareUI(this)"
					data-options="iconCls:'icon-blank',plain:true">收获分享</a><br> <a
					id="btn5" href="#" class="easyui-linkbutton"
					onclick="openProvidedShareUI(this)"
					data-options="iconCls:'icon-blank',plain:true">我的分享</a><br>
				<!-- <a
					id="btn2" href="#" class="easyui-linkbutton"
					onclick="openPage(this)"
					data-options="iconCls:'icon-blank',plain:true">种子</a><br> <a
					id="btn3" href="#" class="easyui-linkbutton"
					onclick="openPage(this)"
					data-options="iconCls:'icon-blank',plain:true">文档</a><br> -->
			</div>
			<!-- <div title="分享" data-options="iconCls:'icon-save',border:false"
				style="padding: 10px">
				<a id="btn4" href="#" class="easyui-linkbutton"
					onclick="openFriendUI(this)"
					data-options="iconCls:'icon-blank',plain:true">我的关注</a><br> <a
					id="btn5" href="#" class="easyui-linkbutton"
					onclick="openGroupUI(this)"
					data-options="iconCls:'icon-blank',plain:true">共享群组</a><br> <a
					id="btn5" href="#" class="easyui-linkbutton"
					onclick="openReceivedShareUI(this)"
					data-options="iconCls:'icon-blank',plain:true">收获分享</a><br> <a
					id="btn5" href="#" class="easyui-linkbutton"
					onclick="openProvidedShareUI(this)"
					data-options="iconCls:'icon-blank',plain:true">我的分享</a><br>
			</div> -->
		</div>
	</div>
	<div data-options="region:'center'"
		style="padding: 5px; background: #eee;">
		<div id="tt" class="easyui-tabs" data-options="fit:true,border:false"
			style="width: 500px; height: 250px;">
			<div title="主页" style="padding: 20px;">欢迎使用智慧网盘</div>
		</div>

	</div>

	<script type="text/javascript">
		function openFileUI(btn) {
			if ($('#tt').tabs('exists', btn.text)) {
				var tab = $('#tt').tabs("select", btn.text);
			} else {
				$('#tt').tabs("add", {
					title : btn.text,
					href : '${basePath}/file/list.action',
					closable : true,
					selected : true,
				});
			}
		}

		function openFriendUI(btn) {
			if ($('#tt').tabs('exists', btn.text)) {
				$('#tt').tabs("select", btn.text);
			} else {
				$('#tt').tabs("add", {
					title : btn.text,
					href : '${basePath}/relation/friend.action',
					closable : true,
					selected : true,
				});
			}
		}

		function openGroupUI(btn) {
			if ($('#tt').tabs('exists', btn.text)) {
				$('#tt').tabs("select", btn.text);
			} else {
				$('#tt').tabs("add", {
					title : btn.text,
					href : '${basePath}/relation/group.action',
					closable : true,
					selected : true,
				});
			}
		}

		//好友分享
		function openReceivedShareUI(btn) {
			if ($('#tt').tabs('exists', btn.text)) {
				$('#tt').tabs("select", btn.text);
			} else {
				$('#tt').tabs("add", {
					title : btn.text,
					href : '${basePath}/share/receive.action',
					closable : true,
					selected : true,
				});
			}
		}

		//我的分享
		function openProvidedShareUI(btn) {
			if ($('#tt').tabs('exists', btn.text)) {
				$('#tt').tabs("select", btn.text);
			} else {
				$('#tt').tabs("add", {
					title : btn.text,
					href : '${basePath}/share/provide.action',
					closable : true,
					selected : true,
				});
			}
		}
	</script>
</body>

</html>
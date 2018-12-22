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
	<div class="place">
		<span>位置：</span>
		<ul class="placeul">
			<%-- 此处放一个面包屑导航 --%>
			<li><a href="javascript:void(0)" onclick="listShare()">收获分享</a></li>
			<c:forEach items="${breadlist}" var="bread">
				<li><a href="javascript:void(0)"
					onclick="openFile('${bread.folderPath}')">${bread.folderName}</a></li>
			</c:forEach>
		</ul>
	</div>
	<div class="tools">
		<ul class="toolbar">
			<li class="download" onclick="downloadFiles()"><span><img
					src="${basePath }/images/download_1.png"
					style="width: 30px; height: 28px" /></span>下载</li>
			<li class="share" onclick="shareFiles()"><span><img
					src="${basePath }/images/share_1.png"
					style="width: 30px; height: 28px" /></span>分享</li>
			<li class="delete"><span><img
					src="${basePath }/images/delete_1.png"
					style="width: 30px; height: 28px" /></span>删除</li>
			<li><input type="text" style="height: 30px; border: none"><span><img
					src="${basePath }/images/search.png"
					style="width: 30px; height: 28px" /></span></li>
		</ul>

	</div>

	<table id="tb" class="filetable">
		<thead>
			<tr>
				<th width="5px"><input name="" type="checkbox" value="" /></th>
				<th>文件名</th>
				<td width="200px"></td>
				<th>分享日期</th>
				<th>大小</th>
				<th>分享人</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${shareList}" var="share" varStatus="sta">
				<tr class="row">
					<td><input id="box" name="" type="checkbox" value="" /></td>
					<td width="300px"><c:choose>
							<c:when test="${share.fileType=='d'}">
								<a href="javascript:void(0)"
									ondblclick="enterFolder('${share.filePath}')"> <img
									src="${basePath }/images/f01.png" /> <input class="fileinput"
									type="text" value="${share.fileName}" readOnly="readonly">
								</a>
							</c:when>
							<c:when test="${share.fileType=='p'}">
								<img src="${basePath }/images/f07.png" />
								<input class="fileinput" type="text" value="${share.fileName}"
									readOnly="readonly">
							</c:when>
							<c:when test="${share.fileType=='v'}">
								<img src="${basePath }/images/f10.png" />
								<input class="fileinput" type="text" value="${share.fileName}"
									readOnly="readonly">
							</c:when>
							<c:when test="${share.fileType=='z'}">
								<img src="${basePath }/images/f02.png" />
								<input class="fileinput" type="text" value="${share.fileName}"
									readOnly="readonly">
							</c:when>
							<c:when test="${share.fileType=='t'}">
								<img src="${basePath }/images/f03.png" />
								<input class="fileinput" type="text" value="${share.fileName}"
									readOnly="readonly">
							</c:when>
							<c:when test="${share.fileType=='m'}">
								<img src="${basePath }/images/f08.png" />
								<input class="fileinput" type="text" value="${share.fileName}"
									readOnly="readonly">
							</c:when>
							<c:when test="${share.fileType=='o'}">
								<img src="${basePath }/images/f09.png" />
								<input class="fileinput" type="text" style="width: 300px;"
									value="${share.fileName}" readOnly="readonly">
							</c:when>
						</c:choose></td>
					<td>
						<div class="action">
							<img src="${basePath }/images/delete.png"
								onclick="deleteShare('${share.providerId}','${share.shareId}')"
								style="width: 20px; height: 25px" title="删除"> <img
								src="${basePath }/images/share.png"
								onclick="shareShare('${share.filePath}')"
								style="width: 20px; height: 25px" title="分享"> <img
								src="${basePath }/images/download.png"
								onclick="downloadShare('${share.filePath}')"
								style="width: 20px; height: 25px" title="下载"> <img
								src="${basePath }/images/save.png"
								onclick="saveShare('${share.filePath}')"
								style="width: 20px; height: 25px" title="保存">
						</div>
					</td>
					<td>${share.shareTime }</td>
					<td>${share.fileSize }</td>
					<td class="tdlast">${share.providerName }</td>
				</tr>

			</c:forEach>
		</tbody>
	</table>

	<ul id="tt"></ul>

	<div id="dd"></div>

	<script type="text/javascript">
		function listShare() {
			var url = "${basePath}/share/receive.action";
			var tab = $('#tt', window.parent.document).tabs('getSelected');
			tab.panel('refresh', url);
		}

		function enterFolder(path) {
			console.log(path);
			var url = "${basePath}/file/enterFoder.action?path=" + path;
			var tab = $('#tt', window.parent.document).tabs('getSelected');
			tab.panel('refresh', url);
		}

		/**
		下载文件
		 */
		function downloadShare(path) {
			$.ajax({
				url : "${basePath}/file/download.action",
				type : "POST",
				data : {
					"path" : path
				},
				success : function(data) {
					if (data) {
						$.messager.show({
							title : 'tip',
							msg : '下载成功',
							timeout : 1000,
							showType : 'slide'
						});
					}
				},
				error : function(err) {
					$.messager.show({
						title : 'tip',
						msg : '下载失败，联系管理员',
						timeout : 1000,
						showType : 'slide'
					});
				}
			});
		}

		function downloadFiles() {
			alert("请选择多个文件");
		}

		function shareFiles() {
			alert("分享多个文件");
		}

		function shareShare(path) {
			var url = '${basePath}/relation/shareUI.action?path='
					+ encodeURIComponent(path);
			url = encodeURI(url);
			url = encodeURI(url);
			console.log(path);
			$('#dd').dialog({
				title : '请选择好友或群组',
				width : 400,
				height : 400,
				closed : false,
				cache : false,
				href : url, //传到后台，返回一个jsp页面带着数据返回   传递过去当前这个文件的路径
				modal : true
			});
		}

		function deleteShare(userId, shareId) {
			console.log(userId);
			console.log(shareId);
			$.messager.confirm('确认', '您确认想要删除此分享？', function(r) {
				if (r) {
					$.ajax({
						url : "${basePath}/share/delete.action",
						type : "POST",
						data : {
							"userId" : userId,
							"shareId" : shareId,
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
							listShare();
						}
					});
				}
			});
		}

		function saveShare(path) {
			var url = '${basePath}/share/dirTree.action?path='
					+ encodeURIComponent(path);
			url = encodeURI(url);
			url = encodeURI(url);
			$('#dd').dialog({
				title : '保存到我的网盘',
				width : 400,
				height : 400,
				closed : false,
				cache : false,
				href : url, //传到后台，返回一个jsp页面带着数据返回   传递过去当前这个文件的路径
				modal : true
			});
		}
	</script>
</body>
</html>

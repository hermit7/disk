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
			<li><a href="javascript:void(0)" onclick="listShare()">我的分享</a></li>
		</ul>
	</div>
	<div class="tools">
		<ul class="toolbar">
			<li class="delete"><span><img
					src="${basePath }/images/delete_1.png"
					style="width: 30px; height: 28px" /></span>删除</li>
			<li><input type="text" style="height: 30px; border: none"><span><img
					src="${basePath }/images/search.png"
					style="width: 30px; height: 28px" /></span></li>
		</ul>
	</div>
	<ul id="tt"></ul>
	<table id="tb" class="filetable">
		<thead>
			<tr>
				<th><input name="" type="checkbox" value="" /></th>
				<th>文件名</th>
				<th width="200px"></th>
				<th>分享日期</th>
				<th>大小</th>
				<th>分享对象</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${shareList}" var="share" varStatus="sta">
				<tr class="row">
					<td><input id="box" name="" type="checkbox" value="" /></td>
					<td width="300px"><c:choose>
							<c:when test="${share.fileType=='d'}">
								<img src="${basePath }/images/f01.png" />
								<input class="fileinput" type="text" value="${share.fileName}"
									readOnly="readonly">
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
								onclick="deleteShare('${share.shareId}')"
								title="取消分享">
						</div>
					</td>
					<td>${share.shareTime }</td>
					<td class="tdlast">${share.fileSize }</td>
					<td>${share.receiverName }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<script type="text/javascript">
		function listShare() {
			var url = "${basePath}/share/provide.action";
			var tab = $('#tt', window.parent.document).tabs('getSelected');
			tab.panel('refresh', url);
		}

		function deleteShare(shareId) {
			$.messager.confirm('确认', '您确认想要取消此分享？', function(r) {
				if (r) {
					$.ajax({
						url : "${basePath}/share/delete.action",
						type : "POST",
						data : {
							"shareId" : shareId
						},
						success : function(data) {
							if (data) {
								$.messager.show({
									title : 'tip',
									msg : '取消成功',
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
	</script>
</body>
</html>

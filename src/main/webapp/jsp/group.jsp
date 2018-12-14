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
			<li><a href="javascript:void(0)" onclick="openFile('/')">我的群组</a></li>
			<c:forEach items="${breadlist}" var="bread">
				<li><a href="javascript:void(0)"
					onclick="openFile('${bread.folderPath}')">${bread.folderName}</a></li>
			</c:forEach>
			<!-- <li><a href="javascript:void(0)" onclick="openFile('/')">列表</a></li> -->
		</ul>
	</div>
	<div class="tools">
		<ul class="toolbar">
			<li class="upload"><span><img
					src="${basePath }/images/upload_1.png"
					style="width: 30px; height: 28px" /></span>找群</li>
			<li class="download"><span><img
					src="${basePath }/images/download_1.png"
					style="width: 30px; height: 28px" /></span>下载</li>
			<li class="share"><span><img
					src="${basePath }/images/share_1.png"
					style="width: 30px; height: 28px" /></span>分享</li>
			<li class="delete"><span><img
					src="${basePath }/images/delete_1.png"
					style="width: 30px; height: 28px" /></span>删除</li>
			<li class="newfolder"><span><img
					src="${basePath }/images/new_file.png"
					style="width: 30px; height: 28px" /></span>新建群组</li>
		</ul>

	</div>
	<ul id="tt"></ul>
	<table id="tb" class="filetable">
		<thead>
			<tr>
				<th width="5px"><input name="" type="checkbox" value="" /></th>
				<th>文件名</th>
				<td width="200px"></td>
				<th>修改日期</th>
				<th>大小</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${fileList}" var="file" varStatus="sta">
				<tr class="row">
					<td><input id="box" name="" type="checkbox" value="" /></td>
					<td width="500px"><c:choose>
							<c:when test="${file.type=='d'}">
								<a href="javascript:void(0)"
									ondblclick="openFile('${file.path}')"> <img
									src="${basePath }/images/f01.png" /> <input class="fileinput"
									type="text" value="${file.name}" readOnly="readonly">
								</a>
							</c:when>
							<c:when test="${file.type=='p'}">
								<img src="${basePath }/images/f07.png" />
								<input class="fileinput" type="text" value="${file.name}"
									readOnly="readonly">
							</c:when>
							<c:when test="${file.type=='v'}">
								<img src="${basePath }/images/f10.png" />
								<input class="fileinput" type="text" value="${file.name}"
									readOnly="readonly">
							</c:when>
							<c:when test="${file.type=='z'}">
								<img src="${basePath }/images/f02.png" />
								<input class="fileinput" type="text" value="${file.name}"
									readOnly="readonly">
							</c:when>
							<c:when test="${file.type=='t'}">
								<img src="${basePath }/images/f03.png" />
								<input class="fileinput" type="text" value="${file.name}"
									readOnly="readonly">
							</c:when>
							<c:when test="${file.type=='m'}">
								<img src="${basePath }/images/f08.png" />
								<input class="fileinput" type="text" value="${file.name}"
									readOnly="readonly">
							</c:when>
							<c:when test="${file.type=='o'}">
								<img src="${basePath }/images/f09.png" />
								<input class="fileinput" type="text" style="width: 300px;"
									value="${file.name}" readOnly="readonly">
							</c:when>
						</c:choose></td>
					<td>
						<div class="action">
							<img src="${basePath }/images/modify.png"
								onclick="modifyFile(this)" style="width: 20px; height: 25px"
								title="修改"> <img src="${basePath }/images/delete.png"
								onclick="deleteFile('${file.path}')"
								style="width: 20px; height: 25px" title="删除"> <img
								src="${basePath }/images/share.png"
								style="width: 20px; height: 25px" title="分享"> <img
								src="${basePath }/images/download.png"
								onclick="downloadFile('${file.path}')"
								style="width: 20px; height: 25px" title="下载"> <img
								src="${basePath }/images/more.png" onclick="moveFile(this)"
								style="width: 20px; height: 25px" title="更多">
						</div>
					</td>
					<td>${file.time }</td>
					<td class="tdlast">${file.size }</td>
				</tr>

			</c:forEach>
		</tbody>
	</table>
	<div class="tip">
		<div class="tiptop">
			<span>上传文件</span>
		</div>
		<form id="fileForm" method="POST">
			<div class="tipinfo">
				<div class="tipright">
					<p>请选择需要上传的文件</p>
					<input type="file" name="filename"><br>
				</div>
			</div>
			<div class="tipbtn">
				<input name="" type="button" class="sure" onclick="upload()"
					value="上传" />&nbsp; <input name="" type="button" class="cancel"
					value="取消" />
			</div>
		</form>
	</div>

	<div class="tip2">
		<div class="tiptop2">
			<span>新建文件夹</span>
		</div>
		<form id="folderForm" method="POST">
			<div class="tipinfo2">
				<div class="tipright2">
					<input type="text" name="folder"
						style="height: 30px; position: center" placeholder="请输入文件夹名"><br>
				</div>
			</div>
			<div class="tipbtn2">
				<input name="" type="button" class="sure2" onclick="mkdir()"
					value="确认" />&nbsp; <input name="" type="button" class="cancel2"
					value="取消" />
			</div>
		</form>
	</div>
	
	
	<div id="mm" class="easyui-menu" data-options="onClick:menuHandler" style="width: 80px;">
		<div data-options="name:'copy'">复制到</div>
		<div data-options="name:'move'">移动到</div>
	</div>
	
	<script type="text/javascript">
		function openFile(path) {
			var url = "${basePath}/file/list.action?path=" + path;
			url = encodeURI(url);
			url = encodeURI(url);
			var tab = $('#tt', window.parent.document).tabs('getSelected');
			tab.panel('refresh', url);
		}

		/**
			上传文件
		 */
		var curPath = '${currentPath}';
		function upload() {
			var url = "${basePath}/file/upload.action?curPath=" + curPath;
			url = encodeURI(url);
			url = encodeURI(url);
			var formData = new FormData($('#fileForm')[0]);
			$.ajax({
				url : url,
				type : "POST",
				data : formData,
				async : false,
				cache : false,
				contentType : false,
				processData : false,
				success : function(data) {
					$.messager.show({
						title : 'tip',
						msg : '文件上传成功',
						timeout : 2000,
						showType : 'slide'
					});
					openFile(curPath);
				},
				error : function(err) {
					$.messager.show({
						title : 'tip',
						msg : '文件上传失败，请重试',
						timeout : 1000,
						showType : 'slide'
					});
				}
			});
		}

		/**
			重命名
		 */
		function modifyFile(obj) {
			var tr = $(obj).parents(".row");
			var input = $(tr).find(".fileinput");
			var originName = input.val();
			//console.log(originName);
			input.removeAttr("readonly").focus();
			input.on("blur", function() {
				var destName = input.val();
				//console.log(destName);
				$.ajax({
					url : "${basePath}/file/modify.action",
					type : "POST",
					data : {
						"curPath" : curPath,
						"originName" : originName,
						"destName" : destName
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
						openFile(curPath);
					}
				});
			})
		}

		/**
			新建文件夹
		 */
		function mkdir() {
			var folder = $("input[name='folder']").val();
			$.ajax({
				url : "${basePath}/file/mkdir.action",
				type : "POST",
				data : {
					"curPath" : curPath,
					"folder" : folder
				},
				success : function(data) {
					if (data) {
						$.messager.show({
							title : 'tip',
							msg : '新建文件夹成功',
							timeout : 2000,
							showType : 'slide'
						});
						openFile(curPath);
					}
				}
			});
		}

		/**
		下载文件
		 */
		function downloadFile(path) {
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
							timeout : 2000,
							showType : 'slide'
						});
					}
				},
				error : function(err) {
					$.messager.show({
						title : 'tip',
						msg : '下载失败，联系管理员',
						timeout : 2000,
						showType : 'slide'
					});
				}
			});
		}

		function deleteFile(path) {
			$.messager.confirm('确认', '您确认想要删除此文件？', function(r) {
				if (r) {
					$.ajax({
						url : "${basePath}/file/delete.action",
						type : "POST",
						data : {
							"path" : path
						},
						success : function(data) {
							if (data) {
								$.messager.show({
									title : 'tip',
									msg : '删除成功',
									timeout : 2000,
									showType : 'slide'
								});
							}
							openFile(curPath);
						}
					});
				}
			});
		}

		function moveFile(obj) {
			$('#mm').menu('show', {
				left : getAbsoluteLeft(obj),
				top : getAbsoluteTop(obj)
			});
		}
		
		function menuHandler(item){
			alert(item.name)
		}

		
		//获取控件左绝对位置
		function getAbsoluteLeft(o) {
			/* 	o = document.getElementById(objectId) */
			oLeft = o.offsetLeft
			while (o.offsetParent != null) {
				oParent = o.offsetParent
				oLeft += oParent.offsetLeft
				o = oParent
			}
			return oLeft
		}

		//获取控件上绝对位置
		function getAbsoluteTop(o) {
			/* o = document.getElementById(objectId); */
			oTop = o.offsetTop;
			while (o.offsetParent != null) {
				oParent = o.offsetParent
				oTop += oParent.offsetTop // Add parent top position
				o = oParent
			}
			return oTop + 25
		}
	</script>
</body>
</html>

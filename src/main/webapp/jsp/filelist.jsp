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

			/* $(".sure").click(function() {
				$(".tip").fadeOut(100);
			}); */

			function fadeOut() {
				$(".tip").fadeOut(100);
			}

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
			<li>
				<a href="javascript:void(0)" onclick="openFile('/')">我的网盘</a>
			</li>
			<c:forEach items="${breadlist}" var="bread">
				<li>
					<a href="javascript:void(0)" onclick="openFile('${bread.folderPath}')">${bread.folderName}</a>
				</li>
			</c:forEach>
			<!-- <li><a href="javascript:void(0)" onclick="openFile('/')">列表</a></li> -->
		</ul>
		<div id="p" style="width: 400px; float: right; margin: 8px 2px 2px"></div>
	</div>
	<div class="tools">
		<ul class="toolbar">
			<li class="upload">
				<span><img src="${basePath }/images/upload_1.png" style="width: 30px; height: 28px" /></span>上传
			</li>
			<li class="download" onclick="downloadFiles()">
				<span><img src="${basePath }/images/download_1.png" style="width: 30px; height: 28px" /></span>下载
			</li>
			<li class="share" onclick="shareFiles()">
				<span><img src="${basePath }/images/share_1.png" style="width: 30px; height: 28px" /></span>分享
			</li>
			<li class="delete">
				<span><img src="${basePath }/images/delete_1.png" style="width: 30px; height: 28px" /></span>删除
			</li>
			<li class="newfolder">
				<span><img src="${basePath }/images/new_file.png" style="width: 30px; height: 28px" /></span>新建文件夹
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
				<th>文件名</th>
				<th width="200px"></th>
				<th>修改日期</th>
				<th>大小</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${fileList}" var="file" varStatus="sta">
				<tr class="row">
					<td><input id="box" type="checkbox" /></td>
					<td width="500px"><c:choose>
							<c:when test="${file.type=='d'}">
								<a href="javascript:void(0)" ondblclick="openFile('${file.path}')">
									<img src="${basePath }/images/f01.png" />
									<input class="fileinput" type="text" value="${file.name}" readonly="readonly">
								</a>
							</c:when>
							<c:when test="${file.type=='p'}">
								<img src="${basePath }/images/f07.png" />
								<input class="fileinput" type="text" value="${file.name}" readOnly="readonly">
							</c:when>
							<c:when test="${file.type=='v'}">
								<img src="${basePath }/images/f10.png" />
								<input class="fileinput" type="text" value="${file.name}" readOnly="readonly">
							</c:when>
							<c:when test="${file.type=='z'}">
								<img src="${basePath }/images/f02.png" />
								<input class="fileinput" type="text" value="${file.name}" readOnly="readonly">
							</c:when>
							<c:when test="${file.type=='t'}">
								<img src="${basePath }/images/f03.png" />
								<input class="fileinput" type="text" value="${file.name}" readOnly="readonly">
							</c:when>
							<c:when test="${file.type=='m'}">
								<img src="${basePath }/images/f08.png" />
								<input class="fileinput" type="text" value="${file.name}" readOnly="readonly">
							</c:when>
							<c:when test="${file.type=='o'}">
								<img src="${basePath }/images/f09.png" />
								<input class="fileinput" type="text" style="width: 300px;" value="${file.name}"
									readOnly="readonly">
							</c:when>
						</c:choose></td>
					<td>
						<div class="action">
							<img src="${basePath }/images/modify.png" onclick="modifyFile(this)" title="修改"> <img
								src="${basePath }/images/delete.png" onclick="deleteFile('${file.path}')" title="删除">
							<img src="${basePath }/images/share.png" onclick="shareFile('${file.path}')" title="分享">
							<img src="${basePath }/images/download.png" onclick="downloadFile('${file.path}')" title="下载">
							<img src="${basePath }/images/more.png" onclick="moveFile(this)" title="更多">
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
		<form id="fileForm" action="./uploadServlet" method="POST" enctype="multipart/form-data">
			<div class="tipinfo">
				<div class="tipright">
					<p>请选择需要上传的文件</p>
					<input id="uploadFile" type="file" name="filename" onchange="doValidate()">
					<br>
				</div>
				<div id="progressBar">
					<div id="progress"></div>
				</div>
				<span id="fileTip"></span><span id="proInfo">上传进度：0%</span>
			</div>
			<div class="tipbtn">
				<input name="" class="sure" type="button" onclick="upload()" value="上传" />
				&nbsp;
				<input name="" type="button" class="cancel" value="取消" />
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
					<input type="text" name="folder" style="height: 30px; position: center" placeholder="请输入文件夹名">
					<br>
				</div>
			</div>
			<div class="tipbtn2">
				<input name="" type="button" class="sure2" onclick="mkdir()" value="确认" />
				&nbsp;
				<input name="" type="button" class="cancel2" value="取消" />
			</div>
		</form>
	</div>

	<div id="dd"></div>

	<div id="mm" class="easyui-menu" data-options="onClick:menuHandler" style="width: 80px;">
		<div data-options="name:'copy'">复制到</div>
		<div data-options="name:'move'">移动到</div>
	</div>

	<script type="text/javascript">
		$(function() {
			$('#p').progressbar({
				width : 195,
				value : '${user.value}',
				text : '${user.text}',
			});

		});

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
		/* function upload() {
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
						timeout : 1000,
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
		} */

		function doValidate() {
			var filename = $("#uploadFile").val();
			if (filename == "") {
				$("#fileTip").css({
					"color" : "red"
				});
				$('#fileTip').html("未选择");
				return false;
			} else {
				$("#fileTip").css({
					"color" : "green"
				});
				$('#fileTip').html("已选择");
				return true;
			}
		}

		function upload() {
			//非空判断
			if (!doValidate()) {
				return false;
			}
			var form = new FormData($('#fileForm')[0]);
			url = '${basePath}/file/uploadFile.action';
			form.append("curPath", curPath);
			var xhr = new XMLHttpRequest();
			xhr.open("post", url, true);
			xhr.onload = function() {
				openFile(curPath);
			}
			xhr.upload.addEventListener("progress", progressFunction, false);
			xhr.send(form);
		}

		function progressFunction(evt) {
			var progressBar = $("#progressBar");
			if (evt.lengthComputable) {
				var completePercent = Math.round(evt.loaded / evt.total * 100);
				$("#proInfo").text("上传进度：" + completePercent + "%");
				if (completePercent == 100) {
					$(".tip").fadeOut(1000);
				}
			}
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
								timeout : 1000,
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
							timeout : 1000,
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
									timeout : 1000,
									showType : 'slide'
								});
							}
							openFile(curPath);
						}
					});
				}
			});
		}
		var originName;
		function moveFile(obj) {
			var tr = $(obj).parents(".row");
			var input = $(tr).find(".fileinput");
			originName = input.val();
			$('#mm').menu('show', {
				left : getAbsoluteLeft(obj),
				top : getAbsoluteTop(obj)
			});
		}

		function shareFile(path) {
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

		function menuHandler(item) {
			var url = '${basePath}/file/dirTree.action?curPath=' + curPath
					+ '&originName=' + originName + '&motion=' + item.name;
			url = encodeURI(url);
			url = encodeURI(url);
			$('#dd').dialog({
				title : '请选择目录',
				width : 400,
				height : 400,
				closed : false,
				cache : false,
				href : url, //传到后台，返回一个jsp页面带着数据返回   传递过去当前这个文件的路径
				modal : true
			});
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

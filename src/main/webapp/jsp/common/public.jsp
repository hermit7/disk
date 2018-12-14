<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<link rel="stylesheet" href="${basePath}/easyui/themes/default/easyui.css">
<link rel="stylesheet" href="${basePath}/easyui/themes/icon.css">
<link href="${basePath}/css/style.css" rel="stylesheet" type="text/css" />
<script src="${basePath}/easyui/jquery.min.js"></script>
<script src="${basePath}/easyui/jquery.easyui.min.js"></script>

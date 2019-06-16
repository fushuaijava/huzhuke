<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no,minimal-ui">
<title>互助客</title>
<link rel="shortcut icon"
	href="${pageContext.request.contextPath}/static/images/favicon.ico" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/htm/iconfont/iconfont.css" />
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/static/css/main.css" />
</head>

<body>
	<div class="payconfirm">
		<div class="headerBl"></div>
		<div class="secheader pr">
			<a href="hisInvite.html"><i class="iconfont">&#xe629;</i></a> <span>支付确认</span>
		</div>
		<c:choose>
			<c:when test="${SESSIONUSER==null}">
    您还没有登录请先<a
					href="${pageContext.request.contextPath}/static/htm/loginIn.html">登录</a>，然后再进行其他操作。
    
    </c:when>
			<c:otherwise>
				<div class="con">
					<div class="condition">
						<i class="iconfont colPi">&#xe61c;</i> <i class="iconfont colYe">&#xe61d;</i>
						<i class="iconfont colGr">&#xe61e;</i> <i class="iconfont colCa">&#xe61f;</i>
					</div>

					<div class="goldNeed">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr class="tr1">
								<td width="50%" class="pr">您的金币数<span class="pa"></span></td>
								<td width="50%">本次所需金币数</td>
							</tr>
							<tr>
								<td>${SESSIONUSER.coin}</td>
								<td>${result.guestorder.totalPay}</td>
							</tr>
							<tr>
								<td><a class="liBtn" href="/zuoke/static/htm/recharge.html">去充值</a></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="submitPay">
					<input type="hidden" id="guestorderId" value="${result.guestorder.id}">
					<button id="submitbtn" class="blueBtn">提交</button>
				</div>
			</c:otherwise>
		</c:choose>
	</div>
</body>
<script type="text/javascript" data-main="${pageContext.request.contextPath}/static/js/my/guestorder/payconfirm2" src="${pageContext.request.contextPath}/static/js/require.js"></script>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>首页</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
</head>

<body>
	<%=basePath%>
	这里是订单提交页面
	<table align="center" border="1">
		<tr>
			<td>商户id</td>
			<td><input type="text" name="merchantId" ></td>
		</tr>
		<tr>
			<td>订单编号</td>
			<td><input type="text" name="orderNo" ></td>
		</tr>
		<tr>
			<td>签名</td>
			<td><input type="text" name="sign" ></td>
		</tr>
		<tr>
			<td>参数</td>
			<td><input type="text" name="param" ></td>
		</tr>
		<tr>
			<td colspan="4"><form action="commitOrder">
					<button type="submit">提交</button>
				</form></td>
		</tr>
	</table>
</body>
</html>

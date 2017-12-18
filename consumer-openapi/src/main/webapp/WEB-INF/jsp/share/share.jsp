<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分享 - ${name}</title>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
<meta name="format-detection" content="telephone=no,email=no,adress=no">
<link href="<%=request.getContextPath() %>/res/share/css/share.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath() %>/res/jquery-1.12.4.min.js"></script>
<!--[if lt IE 9]>
<script src="<%=request.getContextPath() %>/res/share/js/html5.js"></script>
<![endif]-->
<script src="<%=request.getContextPath() %>/res/share/js/blocksit.min.js"></script>
<script>
$(document).ready(function() {

	$(window).load( function() {
		$('#container').BlocksIt({
			numOfCol: 2,
			offsetX: 0,
			offsetY: 0
		});
	});

});
</script>
</head>
<body>

<div class="jingqu_pic">
  <div class="fudongceng">
    <div class="jingqu_title">${name}</div>
    <div class="yinyingceng"></div>
  </div>
  <div class="jingqu_pic_img">
    <img src="${pic}" alt=""/>
  </div>
</div>
<div class="wrap border_b">
  <div class="jingqu_jianjie">${introduce}</div>
</div>
<div class="wrap border_t mt10">
  <div class="jingqu_list">
    <p>景区地址<p>
    <span>地址：${address}</span>
  </div>
</div>
<div class="wrap border_t">
  <div class="jingqu_list">
    <p>开放时间<p>
    <span>开放时间：${openTime}</span>
  </div>
</div>
<div class="wrap border_t border_b">
  <div class="jingqu_list">
    <p>票价<p>
    <span>单次票：${fares}元</span>
  </div>
</div>
<div class="wrap border_t mt10">
  <div class="jingqu_list">
    <p>推荐景点<p>
  </div>
  <div class="tuijianjingdian">
    <div class="tuijianjingdian_img">
      <div id="container">
      	<c:forEach var="scenicSpot" items="${scenicSpots}">
      	<div><img src="${scenicSpot.pic}"></div>
      	</c:forEach>
      </div>      
    </div>
  </div>
</div>
<div class="fenxiang_down_height"><!--用于浮动层在最底部的高度--></div>
<div class="fenxiang_down">
  <div class="fenxiang_down_wrap">
  <div class="fenxiang_down_cont">
    <div class="fenxiang_down_logo"><img src="<%=request.getContextPath() %>/res/share/images/appdown_logo.png" width="40"/></div>
    <div class="fenxiang_down_txt">南京旅游年卡<br>下载APP客户端旅游更便捷！</div>
    <div class="fenxiang_down_anniu"><a href="<%=request.getContextPath() %>/lyk/otherBusssesController/lykAPP.html" onClick="onclick()">立即下载</a></div>
  </div>
  </div>
</div>
</body>
</html>
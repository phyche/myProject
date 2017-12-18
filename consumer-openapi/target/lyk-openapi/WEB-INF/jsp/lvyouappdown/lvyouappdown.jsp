<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>APP下载</title>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type" />
<meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1">
<meta name="format-detection" content="telephone=no,email=no,adress=no">
<link href="${pageContext.request.contextPath}/res/webview/css/appdown.css" rel="stylesheet" type="text/css">
<script src="<%=request.getContextPath() %>/res/jquery-1.12.4.min.js"></script>
<script src="<%=request.getContextPath() %>/res/lvyouappdown/js/download.js"></script>
<%--<script type="text/javascript">
function onclickapp(){ 
//判断是微信浏览器
var ua = navigator.userAgent.toLowerCase();
    var boolean = null;
    if(ua.match(/MicroMessenger/i)=="micromessenger") {
	alert("请点击右上角按钮，选择使用【在浏览器打开】");
        boolean = false;
    } else {
        boolean = true;
    }
    if(boolean){
        window.location.href = $(this).attr('downlink');
    }
} 
</script>--%>
</head>
<body>
<div class="wrap">
  <div class="appdown_logo">
    <img src="<%=request.getContextPath() %>/res/lvyouappdown/images/appdown_logo.png" width="25%">
  </div>
  <div class="app_title">南京旅游年卡</div>
  <div class="cont_text">
    <p><img src="<%=request.getContextPath() %>/res/lvyouappdown/images/lvyou_ico01.png" width="10">&nbsp;南京市电子旅游年卡选择多、景点全。</p>
    <p><img src="<%=request.getContextPath() %>/res/lvyouappdown/images/lvyou_ico01.png" width="10">&nbsp;涵盖39个景区，有效期自购买日起一年内有效。</p>
	<p><img src="<%=request.getContextPath() %>/res/lvyouappdown/images/lvyou_ico01.png" width="10">&nbsp;游客凭本人年卡，在有效期内可不限次游览涵盖的景区。</p>
  </div>
  <div class="anniu">
     <div class="android"><a href="#" onClick="onclickapp('${androidLink}')">Android 下载</a></div>
     <div class="iphone"><a href="#" onClick="onclickapp('${iOSLink}')">iPhone 下载</a></div>
  </div>
</div>
<div class="wxtip" id="JweixinTip" style="background: rgba(0,0,0,0.8); text-align: center; position: fixed; left:0; top: 0; width: 100%; height: 100%; z-index: 998; display: none;">
    <span class="wxtip-icon" style="width: 102px; height: 117px; background: url(${pageContext.request.contextPath}/resources/img/weixin-tip.png) no-repeat; display: block; position: absolute; right: 20px; top: 20px;"></span>
    <p class="wxtip-txt" style="margin-top: 107px; color: #fff; font-size: 32px; line-height: 1.5;">点击右上角<br/>选择在浏览器中打开</p>
</div>
</body>
</html>
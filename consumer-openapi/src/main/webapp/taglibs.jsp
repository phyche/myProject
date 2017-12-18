<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="res" value="${ctx}/resources2"/>
<c:set var="img" value="${res}/images"/>
<c:set var="js"  value="${res}/js"/>
<c:set var="css" value="${res}/css"/>
<c:set var="sourceVer" value="?v0.0.4" />
<script type="text/javascript">
    var fhpt_ctx = '${ctx}';
    var fhpt_res = '${res}';
    var sourceVer = '${sourceVer}';
</script>

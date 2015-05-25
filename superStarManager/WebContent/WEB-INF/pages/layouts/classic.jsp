<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<title><tiles:getAsString name="title"/></title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/script/easyUi/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/script/easyUi/themes/icon.css">
	<script type="text/javascript" src="<%=request.getContextPath() %>/script/easyUi/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/script/easyUi/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/script/easyUi/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/script/uploadPreview.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/script/myLoad.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/script/easyUi/datagrid-detailview.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/script/jquery.validate.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/script/messages_zh.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/script/myValidate.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/script/util.js"></script>
	
	
</head>
<script type="text/javascript">
	var server_path = "<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/" %>";
</script>
<body class="easyui-layout">
	<div region="north" style="height:80px; background: #f5f4f2;"  >
		<tiles:insertAttribute name="header" />
	</div>
	<div region="west" split="true" style="width:220px; background: #f5f4f2;" title="导航菜单">
		<tiles:insertAttribute name="sidebar" />
	</div>
	<div region="center" style="height: auto;">
				<tiles:insertAttribute name="content" />
	</div>
	<div region="east" style="width:0px;display: none;"> 
	</div>
</body>
</html>

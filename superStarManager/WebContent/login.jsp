<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>门店管理系统</title>
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/script/easyUi/themes/default/easyui.css" />
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/script/easyUi/themes/icon.css" />
	<script type="text/javascript" src="<%=request.getContextPath() %>/script/easyUi/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/script/easyUi/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath() %>/script/easyUi/locale/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript">

		function submitForm(){
			$('#userLogin').form('submit', {
				url:'userLogin.action',
				onSubmit: function(){
					if($('#user_code').val()==""){
						$.messager.alert('提示','用户名不能为空', 'info');
						return false ; 
					}
					if($('#user_pwd').val()=="" ){
						$.messager.alert('提示','密码不能为空', 'info');
						return false ; 
					}
					return true ; 
				},
			    success: function(data){
			    	var data = eval('(' + data + ')'); 
    		        if (data.success){
				        window.location.href="index.action";
                    } else {
                    	$.messager.alert('提示',data.message, 'info');
                    }
			    }
			});
		}
		function clearForm(){
		    $('#userLogin').form('clear');
		}
		function tijiao(){
			if (event.keyCode==13){
				$('#loginBtn').click();
			}
		}
	</script>
	<style>
		.center {position:absolute;top:50%;left:50%;margin:-100px 0 0 -100px;}
	</style>
</head>
	
<body class="easyui-layout" onkeydown="tijiao()">
	<div id="login" class='center'>
		<form id="userLogin"  method="post">
			<table>
				<tr>
					<td>用户名：</td>
					<td><input type="text" id="user_code" name="user.user_code"/></td>
				</tr>
				<tr>
					<td>密码：</td>
					<td><input type="password" id="user_pwd" name="user.user_pwd"/></td>
				</tr>
				<tr>
					<td><a href="javascript:void(0)" class="easyui-linkbutton" id="loginBtn" onclick="submitForm()">登录</a></td>
					<td><a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">重置</a></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>

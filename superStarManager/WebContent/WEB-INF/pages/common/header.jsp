<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	function showUserInfo(){
		$('#userInfoWin').dialog('open');
		$('#s_user_id').val($('#user_id').val());
	}
	function closeUserWin(){
		$('#userInfoWin').dialog('close');
		$('#updateUserInfo').form('clear');
	}
	function validateForm(){
		var result = false ; 
		result = $("#updateUserInfo").validate({  
	    	rules: {
	        	"oldPwd": {
					required: true,
					maxlength: 16,
					remote:{
						type:"POST",
						url:"checkUserPwd.action",
						async: false,
						data:{
							'user.user_id' :$('#user_id').val()
						}
					} 
				},
				"user.user_pwd": {
					required: true,
					maxlength: 16
					
				},
				"s_pwd2": {
					required: true,
					equalTo:'#s_pwd1'
				}
			},
			messages: {
				"oldPwd": {
					required: "密码不能为空",
					maxlength: "用户密码长度不能超过16",
					remote:"用户密码不正确"
				},
				"user.user_pwd": {
					required: "密码不能为空",
					maxlength: "用户密码长度不能超过16"
					
				},
				's_pwd2': {
					required: "密码不能为空",
					equalTo:'密码输入不一致'
				}
			},
			 /* 重写错误显示消息方法,以alert方式弹出错误消息 */  
	        showErrors: function(errorMap, errorList) {  
				var msg = "" ;
	        	var obj = errorList[0];
	            if(obj!=null){
	           	
	            	msg = obj.message;
	            }
	            if(msg!=""){
		            $.messager.alert('提示',msg, 'info');	
	            	
	            }
	        },
	        /* 失去焦点时不验证 */    
	        onfocusout: false,  
	        onkeyup: false,
	        onclick: false
	    }).form();
		return result ;
	}
	function updateUserInfo(){
		$('#updateUserInfo').form('submit', {
			url : 'updatePwd.action',
			onSubmit : function() {
				return validateForm();
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$.messager.alert('edit', data.message, 'info');
					closeWin();
				}else {
                	$.messager.alert('Warning',data.message,'info');
                }
			}
		});
	}
</script>
	<div style="margin: 10px;">
		<b><i style="font-size: 30px;">门店管理平台</i></b>
	</div>
	<div style="float: right ">
		<s:hidden id="user_id" value="%{#session['key.session.current.user'].user_id}" />
		<button type="submit" id="user_name" onclick="showUserInfo()"><s:property value="%{#session['key.session.current.user'].user_name}"/></button>
		<a href="<s:url namespace="/" action="logout" />">退出</a>
	</div>
<div id="userInfoWin" class="easyui-dialog" style="padding:5px;width:500px;height:300px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true" 
			modal="true"
			buttons="#userInfo-buttons">
		<form id="updateUserInfo" method="post">
			<table>
				<tr>
					<td>
						<s:hidden id="s_user_id" name="user.user_id"></s:hidden>
					</td>
				</tr>
				<tr>
					<td>原密码</td>
					<td>
						<input type="text" id="old_pwd" name="oldPwd" />
					</td>
				</tr>
				<tr>
					<td>新密码</td>
					<td><input type="password" id="s_pwd1" name="user.user_pwd" /></td>
				</tr>
				<tr>
					<td>再次输入新密码</td>
					<td><input type="password" id="s_pwd2" name="s_pwd2"  /></td>
				</tr>
			</table>
		</form>
</div>
<div id="userInfo-buttons">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="updateUserInfo()">保存</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeUserWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>

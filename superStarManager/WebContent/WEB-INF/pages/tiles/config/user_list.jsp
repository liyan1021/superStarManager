<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
var valid ;
$(function(){
	$('#tt').datagrid({
		title:'人员管理',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    url:'userList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
	   	sortName : "create_time",
	   	sortOrder: "asc",
		columns:[[
			{field:'ck',checkbox:true},
			{field:'opt',title:'',align:'center',
				formatter:function(value,row,index){
					return '<a href="#" class="editcls" onclick="editUser('+index+')"></a>';
				}
			},
			{field:'user_id',title:'用户 ID',width:60,hidden:true},
			{field:'user_name',title:'用户姓名',width:150},
			{field:'user_code',title:'用户帐号',width:150},
			{field:'tel_num',title:'电话',width:150},
			{field:'create_time',title:'创建时间',width:150,
				formatter: function(value,row,index){
					if (row.create_time){
						return row.create_time.substring(0,19);
					}else{
						return "" ;
					}
				}
			},
			{field:'org_code',title:'门店CODE',width:60,hidden:true},
			{field:'org_name',title:'门店名称',width:100},
			{field:'offi_name',title:'职务',width:150},
			
			{field:'role_id',title:'角色',width:150,
				formatter: function(value,row,index){
					if (row.role_id==1){
						return "曲库管理员";
					} else if(row.role_id==2) {
						return "网络管理员";
					}else if(row.role_id==3) {
						return "设备管理员";
					}else if(row.role_id==4) {
						return "管理员";
					}
			}}
			
		]],
		onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
	    }
	});
});
function newUser(){
	$('#win').dialog('open').dialog('setTitle','新增用户');
}
function editUser(index){
	$('#tt').datagrid('selectRow',index);// 关键在这里  
    var row = $('#tt').datagrid('getSelected');  
   	if (row){  
		$('#win').dialog('open').dialog('setTitle','编辑用户');
        $('#userId').val(row.user_id);
        $('#userName').val(row.user_name);
        $('#userCode').val(row.user_code);
        //$('#roleId').val(row.role_id);
        $('#roleId').combobox('setValue',row.role_id);
        $('#orgName').val(row.org_name);
        $('#offiName').val(row.offi_name);
        $('#telNum').val(row.tel_num);
    }
}
function validateForm(){
	var result =  $("#saveUser").validate({  
    	rules: {
        	"user.user_name": {
				required: true,
				maxlength: 20
			},
			"user.user_code": {
				required: true,
				maxlength: 20
				/* remote:{
					type:"POST",
					async: false,
					url:"checkUserCode.action"
				}  */
			}
			
		},
		messages: {
			"user.user_name": {
				required: "用户名称不能为空",
				maxlength: "用户姓名长度不能超过20"
			},
			"user.user_code": {
				required: "用户帐号不能为空",
				maxlength: "用户帐号长度不能超过20"
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
	if(result){
		if($('#roleId').combobox('getValue')=='0'){
			 $.messager.alert('提示','请选择角色', 'info');
			 return false ; 
		}
		var sign = false ;
		$.ajax({
			async: false,
			url:'checkUserCode.action',
			type : 'post',
			data:{
				'user.user_id':$('#userId').val(),
				'user.user_code':$('#userCode').val()
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data) {
					 $.messager.alert('提示','用户账号已存在，请重新输入', 'info');
				} else {
					sign =  true ;
				}
			}
		});
		return sign ; 
	}
	return result ; 
}
function saveUser(){
	if(!$('#userId').val()){
		//新增
		
		$('#saveUser').form('submit', {
		    url:'saveUser.action',
		    onSubmit: function(){
		       //校验 return flase ;
		    	return validateForm(); 
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$.messager.alert('提示', data.message, 'info');
					$('#saveUser').form('reset');
					$('#userId').val("");
					$('#win').dialog('close');
					$('#tt').datagrid({url:'userList.action',queryParams:"",pageNumber:1});
				}else {
                	$.messager.alert('警告',data.message,'info');
                }
			}
		});
		} else {
			//修改
			$('#saveUser').form('submit', {
				url : 'editUser.action',
				onSubmit : function() {
					return validateForm(); 
				},
				success : function(data) {
					var data = eval('(' + data + ')'); // change the JSON string to javascript object
					if (data.success) {
						$.messager.alert('提示', data.message, 'info');
						$('#userId').val("");
						$('#saveUser').form('reset');
						$('#win').dialog('close');
						$('#tt').datagrid({url:'userList.action',queryParams:"",pageNumber:1});
					}else {
	                	$.messager.alert('警告',data.message,'info');
	                }
				}
			});
		}

	}
	function removeUser() {
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要删除的记录!", 'info');
			return;
		} else {
			$.messager.confirm('删除记录', '确定要删除选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].user_id + ",";
					}
				}
				$.ajax({
					url : 'removeUser.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'userList.action',queryParams:"",pageNumber:1});
							$('#tt').datagrid('clearSelections');
							$.messager.alert('提示', "成功删除" + pers + "条记录",
									'info');
						} else {
							$.messager.alert('警告', "删除失败!", 'info');
						}
					}
				});
			});
		}
	}
	var param;
	function searchUser() {
		var gridparam = {
			"user.user_name" : $('#s_userName').val(),
			"user.org_name" : $('#s_orgName').val(),
			"user.offi_name" : $('#s_offiName').val(),
			"user.user_code" : $('#s_userCode').val(),
			"user.role_id" :  $('#s_roleId').combobox('getValue'),
			"user.tel_num" : $('#s_telNum').val(),
			"start_time" : $("#start_time").datebox("getValue"),  
			"end_time" : $("#end_time").datebox("getValue") 
		};
		param = gridparam;
		$('#tt').datagrid({
			url : 'searchUserList.action',
			queryParams : gridparam,
			pageNumber:1
		});
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	function openSerchWin() {
		$('#searchDialog').dialog('open');
	}
	function closeWin() {
		$('#userId').val("");
		$('#saveUser').form('reset');
		$('#win').dialog('close');
	}
	function closeSearchWin() {
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
</script>
<table id="tt">
</table>
<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeUser()">删除</a>
</div>
<div id="win" class="easyui-dialog" style="padding:5px;width:500px;height:300px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="saveUser" method="post">
		<table>
			<tr>
				<td>用户姓名 <font color="red">*</font></td>
				<td>
					<input type="hidden" id="userId" name="user.user_id"  />
	        		<input type="text"  id="userName" name="user.user_name"   />
	        	</td>
				<td>用户帐号 <font color="red">*</font></td>
				<td><input type="text" id="userCode" name="user.user_code"  /></td>
			</tr>
			<tr>
				<td>电话</td>
				<td><input type="text" id="telNum" name="user.tel_num" /></td>
				<td>职务</td>
				<td><input type="text" id="offiName" name="user.offi_name" /></td>
			</tr>
			<tr>
				<td>角色 <font color="red">*</font></td>
				<td>
					<select id='roleId' name="user.role_id" class="easyui-combobox" panelHeight = 'auto' >
			        	<option value='0'>--请选择--</option>
			        	<option value='1'>曲库管理员</option>
			        	<option value='2'>网络管理员</option>
			        	<option value='3'>设备管理员</option>
			        	<option value='4'>管理员</option>
	        		</select>
				</td>
				<td>门店</td>
					<td>
					<input id="orgCode" name="user.org_code"  style="width:150px;" class="easyui-combobox" 
							data-options="
									url:'getStoreInfoList.action',
									method:'post',
									valueField:'store_code', 
									textField:'store_name',
									panelHeight:'auto',
									onSelect:function(){
												var test = $('#orgCode').combobox('getText');
												$('#orgName').val(test);
								}"
						/>
						<input type="hidden"  id= "orgName" name="user.org_name">
					</td>
			</tr>
			
		</table>
	</form>

</div>
<div id="dlg-buttons" >
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveUser()">保存</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>

<div id="searchDialog" class="easyui-dialog" style="padding:5px;width:500px;height:300px;"
			title="查询" iconCls="icon-ok"
			closed="true" 
			modal="true"
			buttons="#search-buttons">
		<form id="searchForm" method="post">
			<table>
				<tr>
					<td>用户姓名</td>
					<td><input type="text" id="s_userName" name="user.user_name" /></td>
					<td>用户帐号</td>
					<td><input type="text" id="s_userCode" name="user.user_code" /></td>
				</tr>
				<tr>
					<td>电话</td>
					<td><input type="text" id="s_telNum" name="user.tel_num" /></td>
					<td>职务</td>
					<td><input type="text" id="s_offiName" name="user.offi_name" /></td>
				</tr>
				<tr>
					<td>角色</td>
					<td>
						<select id='s_roleId' name="user.role_id"  class="easyui-combobox" panelHeight ='auto' >
				        	<option value='0'>--请选择--</option>
				        	<option value='1'>曲库管理员</option>
				        	<option value='2'>网络管理员</option>
				        	<option value='3'>设备管理员</option>
				        	<option value='4'>管理员</option>
				        </select>
	        		</td>
					<td>门店</td>
					<td><input type="text" id="s_orgName" name="user.org_name" /></td>
				</tr>
				<tr>
					<td>创建时间</td>
					<td><input class="easyui-datebox" id="start_time" name="start_time" /></td>
		        	<td>至</td>
		        	<td><input class="easyui-datebox" id="end_time" name="end_time"/></td>
	        	</tr>
			</table>
		</form>
</div>
<div id="search-buttons">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchUser()">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
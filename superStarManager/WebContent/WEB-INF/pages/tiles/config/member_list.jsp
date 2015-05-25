<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'会员管理',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    url:'memberList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		columns:[[
			{field:'ck',checkbox:true},
			{field:'opt',title:'',align:'center',
				formatter:function(value,row,index){
					return '<a href="#" class="editcls" onclick="editMember('+index+')"></a>';
				}
			},
			{field:'member_id',title:'会员 ID',width:60,hidden:true},
			{field:'member_name',title:'会员姓名',width:150},
			{field:'member_code',title:'会员帐号',width:150},
			{field:'member_tel',title:'会员电话',width:150}
		]],
		onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
	    }
	});
});
function newMember(){
	$('#win').dialog('open').dialog('setTitle','新增会员');
}
function editMember(index){
	$('#tt').datagrid('selectRow',index);// 关键在这里  
    var row = $('#tt').datagrid('getSelected');  
   	if (row){  
		$('#win').dialog('open').dialog('setTitle','编辑会员');
        $('#memberId').val(row.member_id);
        $('#memberName').val(row.member_name);
        $('#memberCode').val(row.member_code);
        $('#memberTel').val(row.member_tel);
    }
}
function saveMember(){
	if(!$('#memberId').val()){
		//新增
		$('#saveMember').form('submit', {
		    url:'saveMembers.action',
		    onSubmit: function(){
		       //校验 return flase ;
		    	return $(this).form('validate');
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$.messager.alert('提示', data.message, 'info');
					$('#saveMember').form('reset');
					$('#memberId').val("");
					$('#win').dialog('close');
					$('#tt').datagrid('load');
				}else {
                	$.messager.alert('警告',data.message,'info');
                }
			}
		});
		} else {
			//修改
			$('#saveMember').form('submit', {
				url : 'editMembers.action',
				onSubmit : function() {
					return $(this).form('validate');
				},
				success : function(data) {
					var data = eval('(' + data + ')'); // change the JSON string to javascript object
					if (data.success) {
						$.messager.alert('提示', data.message, 'info');
						$('#memberId').val("");
						$('#saveMember').form('reset');
						$('#win').dialog('close');
						$('#tt').datagrid('load');
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
						ids += rows[i].member_id + ",";
					}
				}
				$.ajax({
					url : 'removeMembers.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid('load');
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
			"members.member_name" : $('#s_memberName').val(),
			"members.member_code" : $('#s_memberCode').val(),
			"members.member_tel" : $('#s_memberTel').val()
		};
		param = gridparam;
		$('#tt').datagrid({
			url : 'searchMember.action',
			queryParams : gridparam
		});
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	function openSerchWin() {
		$('#searchDialog').dialog('open');
	}
	function closeWin() {
		$('#memberId').val("");
		$('#saveMember').form('reset');
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
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newMember()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeUser()">删除</a>
</div>
<div id="win" class="easyui-dialog" style="padding:5px;width:500px;height:300px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="saveMember" method="post">
		<table>
			<tr>
				<td>会员姓名</td>
				<td>
					<input type="hidden" id="memberId" name="members.member_id"  />
	        		<input type="text"  id="memberName" name="members.member_name"  class="easyui-validatebox" required="true" />
	        	</td>
				<td>会员帐号</td>
				<td><input type="text" id="memberCode" name="members.member_code"  class="easyui-validatebox" required="true" /></td>
			</tr>
			<tr>
				<td>电话</td>
				<td><input type="text" id="memberTel" name="members.member_tel" /></td>
			</tr>
			
		</table>
	</form>

</div>
<div id="dlg-buttons" >
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveMember()">保存</a>
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
					<td><input type="text" id="s_memberName" name="members.member_name" /></td>
					<td>用户帐号</td>
					<td><input type="text" id="s_memberCode" name="members.member_code" /></td>
				</tr>
				<tr>
					<td>电话</td>
					<td><input type="text" id="s_memberTel" name="members.member_tel" /></td>
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
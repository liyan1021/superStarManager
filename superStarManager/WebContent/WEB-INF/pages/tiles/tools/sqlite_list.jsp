<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function() {
		$('#tt').datagrid({
			title : 'SQLite版本管理',
			iconCls : 'icon-edit',
			rownumbers : true,
			height : 'auto',
			fit:true ,
			pagination : true,
			fitColumns : false,
			url : 'sqliteInfoList.action',
			method : 'post',
			toolbar : "#toolbar",
			sortName : "create_time",
		    sortOrder : "desc",
			columns : [ [
			{
				field : 'sqlite_id',
				checkbox : true
			},
			{
				field : 'sqlite_version',
				title : 'sqlite版本号',
				width : 100
			},
			{
				field : 'sqlite_path',
				title : '文件路径',
				width : 100
			},
			{
				field : 'create_time',
				title : '生成时间',
				width : 60,
				formatter: function(value,row,index){
					if (row.create_time){
						return row.create_time.substring(0,19);
					}else{
						return "" ;
					}
				}
				
			},
			{field:'state',title:'发布状态',width:150,
				formatter: function(value,row,index){
					if (row.state == 1){
						return "已发布" ;
					}else if(row.state == 0){
						return "未发布" ;
					}
					else if(row.state==2){
						return "预发布" ;
					}
						return row.is_activity ;
				}}
		 ] ]
		});
	});
	function searchSqliteInfo() {
		var gridparam = {
			"sqliteInfo.sqlite_version" : $("#s_sqliteVersion").val(),
			"start_time" : $("#start_time").datebox("getValue"),
			"end_time" :$("#end_time").datebox("getValue")
			
		};
		$('#tt').datagrid({
			url : 'searchSqliteInfo.action',
			queryParams : gridparam,
			pageNumber:1
		});

		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	function removeSqliteInfo() {
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要删除的记录!", 'info');
			return;
		} else {
			$.messager.confirm('删除记录', '确定要删除选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].sqlite_id + ",";
					}
				$.ajax({
					url : 'removeSqliteInfo.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'sqliteInfoList.action',queryParams:"",pageNumber:1});
							$('#tt').datagrid('clearSelections');
							$.messager.alert('提示', "成功删除" + pers + "条记录",
									'info');
						} else {
							$.messager.alert('警告', "删除失败!", 'info');
						}
					}
				});
				}
			});
		}
	}
	function importSqlite(){
		$.messager.confirm('生成文件', '确定要生成吗?', function(result) {
		  if(result){
			$.ajax({
				url : 'generateSqlite.action',
				type : 'post',
				success : function(data) {
					var data = eval('(' + data + ')'); // change the JSON string to javascript object
					if (data.success) {
						$('#tt').datagrid({url:'sqliteInfoList.action',queryParams:"",pageNumber:1});
						$('#tt').datagrid('clearSelections');
						$.messager.alert('提示', "文件生成成功",'info');
					} else {
						$.messager.alert('警告', "生成失败!", 'info');
					}
				}
			});
			}
		});
	}
	function saveSqliteInfo(){
		$('#saveSqliteInfo').form('submit', {
		    url:'saveSqliteInfo.action',
		    onSubmit: function(){
		       //校验 return flase ;
		    	return $(this).form('validate');
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$.messager.alert('提示', data.message, 'info');
					closeWin();
					$('#tt').datagrid({url:'sqliteInfoList.action',queryParams:"",pageNumber:1});
				}else {
                	$.messager.alert('警告',data.message,'info');
                }
			}
		});
	}
	function pushSqlite(){
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
		$.messager.alert('警告', "请选择要发布的记录!", 'info');
		return;
	} else if (rows.length > 1) {
		$.messager.alert('警告', "一次只能发布一条记录，请从新选择!", 'info');
		return;
	 } else
		 {
		    if (rows[0].state == 1) {
			$.messager.alert('警告', "已发布的不能再次发布，请重新选择!", 'info');
			return;
		     }
			$.messager.confirm('发布记录', '确定要发布选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].sqlite_id + ",";
					}
				$.ajax({
					url : 'pushSqlite.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'sqliteInfoList.action',queryParams:"",pageNumber:1});
							$('#tt').datagrid('clearSelections');
							$.messager.alert('提示', "成功发布" + pers + "条记录",
									'info');
						} else {
							$.messager.alert('警告', "发布失败!", 'info');
						}
					}
				});
				}
			});
		}
	}
	function selectRoom() {
	var rows = $("#tt").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert('警告', "请选择要预发布的记录!", 'info');
		return;
	} else if (rows.length > 1) {
		$.messager.alert('警告', "一次只能预发布一条记录，请重新选择!", 'info');
	} else {
		if (rows[0].state == 2) {
			$.messager.alert('警告', "已预发布的不能再次预发布，请重新选择!", 'info');
			return;
		} else if (rows[0].state == 1) {
			$.messager.alert('警告', "已发布的不能预发布，请重新选择!", 'info');
			return;
		}
		$('#roomGrid').datagrid( {
			rownumbers : true,
			height : 'auto',
			fit : true,
			singleSelect : true,
			pagination : true,
			url : 'getRoomNum1.action',
			queryParams : "",
			method : 'post',
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'room_no',
				title : '包房编号',
				width : 150
			}, {
				field : 'oid',
				title : 'ID',
				width : 60,
				hidden : true
			}, ] ]
		});
		$('#roomWin').dialog('open');
	}
}

function pushSqliteReady() {
	var ids = "";
	var rows = $("#roomGrid").datagrid("getSelections");
	var pushrows = $("#tt").datagrid("getSelections");
if (rows.length == 0) {
			$.messager.alert('警告', "请选择包房编号!", 'info');
			return;
		} 
		else
		{
			var roomId=rows[0].oid;
			var roomNum=rows[0].room_no;
			for ( var i = 0; i < pushrows.length; i++) {
						ids += pushrows[i].sqlite_id + ",";
			}
					
				$.ajax({
					url : 'pushSqliteReady.action',
					data : {
						'ids' : ids,
						'room_id' : roomId,
			            'room_num' : roomNum,
					},
					type : 'post',
					success : function(data) {
			        var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = pushrows.length;
							$('#tt').datagrid({url:'sqliteInfoList.action',queryParams:"",pageNumber:1});
							$('#tt').datagrid('clearSelections');
							$('#roomWin').dialog('close');
							$.messager.alert('提示', "成功预发布" + pers + "条记录",
									'info');
						} else {
							$.messager.alert('警告', "预发布失败!", 'info');
						}
					}
				});
			}
		}
	
	function closeWin(){
		$('#saveSqliteInfo').form('reset');
		$('#win').dialog('close');
	}
	function openSerchWin() {
		$('#searchDialog').dialog('open');
	}
	function closeSearchWin() {
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	function closeRoomWin() {
	$('#roomWin').dialog('close');
}
</script>
<table id="tt">
</table>
<div id="toolbar">
	<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增</a> -->
	<a href="#"	class="easyui-linkbutton" iconCls="icon-print"  plain="true" onclick="importSqlite()">生成文件</a> 
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a> 
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeSqliteInfo()">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="selectRoom()">预发布</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="pushSqlite()">发布</a>
	</div>
<div id="searchDialog" class="easyui-dialog"
	style="padding: 5px; width: 600px; height: 400px;" title="查询"
	iconCls="icon-ok" closed="true" modal="true" buttons="#search-buttons">
	<form id="searchForm" method="post">
		<table>
			<tr>
				<td>SQLite版本</td>
				<td><input type="text" id="s_sqliteVersion"  /></td>
			</tr>
			<tr>
				<td>提交时间</td>
				<td><input type="text" id="start_time" class="easyui-datebox"  /></td>
				<td>至</td>
				<td><input type="text" id="end_time"  class="easyui-datebox" /></td>
			</tr>
		</table>
		
	</form>
</div>
<div id="search-buttons">
	<table cellpadding="0" cellspacing="0" style="width: 100%">
		<tr>
			<td style="text-align: right"><a href="#"
				class="easyui-linkbutton" iconCls="icon-search"
				onclick="searchSqliteInfo()">查询</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a></td>
		</tr>
	</table>
</div>
<div id="roomWin" class="easyui-dialog" title="My Dialog"
	iconCls="icon-ok" closed="true" modal="true"
	style="width: 700px; height: 400px;" buttons="#music-buttons"
	toolbar='#music-toolbar'>
	<table id="roomGrid">
	</table>
</div>
<div id="music-buttons">
	<table cellpadding="0" cellspacing="0" style="width: 100%">
		<tr>
			<td style="text-align: right">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search"
					onclick="pushSqliteReady()">确定</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
					onclick="closeRoomWin()">关闭</a>
			</td>
		</tr>
	</table>
</div>

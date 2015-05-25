<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function() {
		$('#tt').datagrid({
			title : '包房设备监控',
			iconCls : 'icon-edit',
			rownumbers : true,
			height : 'auto',
			fit:true ,
			pagination : true,
			fitColumns : false,
			sortName : "room",
		   	sortOrder: "asc",
			url : 'deviceMonitorList.action',
			method : 'post',
			toolbar : "#toolbar",
			columns : [ [
			{
				field : 'device_id',
				title:'终端号'
			},
			{
				field : 'device_name',
				title : '终端名称',
				width : 200
			},
			{
				field : 'net_state',
				title : '网络状态',
				width : 100,
				formatter: function(value,row,index){
					if (row.net_state == 1){
						return "正常" ;
					}else if(row.net_state == 2){
						return "异常" ;
					}
						return row.net_state ;
				}
			},
			{
				field : 'device_state',
				title : '设备状态',
				width : 60,
				formatter: function(value,row,index){
					if (row.device_state == 1){
						return "正常" ;
					}else if(row.device_state == 2){
						return "异常" ;
					}
						return row.device_state ;
				}
			},
			{
				field : 'room_state',
				title : '开关房状态',
				width : 60,
				formatter: function(value,row,index){
					if (row.room_state == 1){
						return "开房" ;
					}else if(row.room_state == 2){
						return "关房" ;
					}
						return row.room_state ;
				}
			},
			{
				field : 'store',
				title : '门店',
				width : 60
			},
			{
				field : 'room',
				title : '包房',
				width : 60
			}
		 ] ]
		});
	});
	function searchDeviceMonitor() {
		var gridparam = {
			"deviceMonitor.device_id" : $("#s_device_id").val(),
			"deviceMonitor.device_name" : $("#s_device_name").val(),
			"deviceMonitor.net_state" : $('#s_net_state').combobox('getValue'),
			"deviceMonitor.device_state" :$('#s_device_state').combobox('getValue'),
			"deviceMonitor.room_state" :$('#s_room_state').combobox('getValue'),
			"deviceMonitor.room" :$('#s_room').val()
			
		};
		$('#tt').datagrid({
			url : 'searchDeviceMonitor.action',
			queryParams : gridparam,
			pageNumber:1
		});

		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	function openRoom(){
		$('#roomDialog').dialog('open');
		$("#roomState").val("1");//开房状态
		$('#roomTable').datagrid({
			rownumbers : true,
			height : 'auto',
			fit:true ,
			pagination : true,
			fitColumns : false,
			singleSelect:true,
			url : 'openRoomList.action',
			method : 'post',
			columns : [ [
				{
					field : 'ck',
					checkbox : true
				},
				{
					field : 'room_no',
					title:'包房号'
				}
		 	] ]
		});
	}
	function closeRoom(){
		$('#roomDialog').dialog('open');
		$("#roomState").val("2");//关房状态
		$('#roomTable').datagrid({
			rownumbers : true,
			height : 'auto',
			fit:true ,
			pagination : true,
			fitColumns : false,
			singleSelect:true,
			url : 'openRoomList.action',
			method : 'post',
			columns : [ [
				{
					field : 'ck',
					checkbox : true
				},
				{
					field : 'room_no',
					title:'包房号'
				}
		 	] ]
		});
	}
	function closeRoomDialog(){
		$('#roomDialog').dialog('close');
	}
	function setRoom(){
		var rows = $("#roomTable").datagrid("getSelections");
		var ids = "";
		var roomState = $("#roomState").val();
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要修改的记录!", 'info');
			return;
		} else {
			$.messager.confirm('删除记录', '确定要修改选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].room_no + ",";
					}
				}
				$.ajax({
					url : 'setRoomState.action',
					data : {
						'ids' : ids,
						'roomState':roomState
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#roomDialog').dialog('close');
							$.messager.alert('提示', "成功修改" + pers + "条记录",
									'info');
						} else {
							$.messager.alert('警告', "修改失败!", 'info');
						}
					}
				});
			});
		}
	}
	function openSerchWin() {
		$('#searchDialog').dialog('open');
	}
	function closeSearchWin() {
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
</script>
<table id="tt">
</table>
<div id="toolbar">
	<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增</a> -->
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openRoom()">开房</a>  
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="closeRoom()">关房</a>  
	</div>
<div id="searchDialog" class="easyui-dialog"
	style="width: 600px; height: 400px;" title="查询"
	iconCls="icon-ok" closed="true" modal="true" buttons="#search-buttons">
	<form id="searchForm" method="post">
		<table>
			<tr>
				<td>终端号</td>
				<td><input type="text" id="s_device_id"  /></td>
				<td>终端名称</td>
				<td><input type="text" id="s_device_name"  /></td>
			</tr>
			<tr>
				<td>网络状态</td>
				<td>
					<select id='s_net_state' class="easyui-combobox" panelHeight = 'auto'>
			        	<option value='0'>--请选择--</option>
			        	<option value='1'>正常</option>
			        	<option value='2'>异常</option>
	        		</select>				
				</td>
				<td>设备状态</td>
				<td>
					<select id='s_device_state' class="easyui-combobox" panelHeight = 'auto'>
			        	<option value='0'>--请选择--</option>
			        	<option value='1'>正常</option>
			        	<option value='2'>异常</option>
	        		</select>	
				</td>
			</tr>
			<tr>
				<td>开关房状态</td>
				<td>
					<select id='s_room_state' class="easyui-combobox" panelHeight = 'auto'>
			        	<option value='0'>--请选择--</option>
			        	<option value='1'>正常</option>
			        	<option value='2'>异常</option>
	        		</select>	
				</td>
				<td>包房</td>
				<td><input type="text" id="s_room" /></td>
			</tr>
		</table>
		
	</form>
</div>
<div id="search-buttons">
	<table cellpadding="0" cellspacing="0" style="width: 100%">
		<tr>
			<td style="text-align: right"><a href="#"
				class="easyui-linkbutton" iconCls="icon-search"
				onclick="searchDeviceMonitor()">查询</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a></td>
		</tr>
	</table>
</div>
<div id="roomDialog" class="easyui-dialog"
	style="padding: 5px; width: 600px; height: 400px;" title="包房信息"
	iconCls="icon-ok" closed="true" modal="true" buttons="#search-buttons">
	<table id="roomTable"></table>
	<input type="hidden" id="roomState">
</div>
<div id="search-buttons">
	<table cellpadding="0" cellspacing="0" style="width: 100%">
		<tr>
			<td style="text-align: right"><a href="#"
				class="easyui-linkbutton" iconCls="icon-search"
				onclick="setRoom()">确定</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="closeRoomDialog()">关闭</a></td>
		</tr>
	</table>
</div>
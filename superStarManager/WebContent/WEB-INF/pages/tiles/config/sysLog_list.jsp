<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'系统日志',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    singleSelect:true,
	    url:'sysLogList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		columns:[[
			{field:'sys_log_id',title:'系统日志ID',width:60,hidden:true},
			{field:'device_id',title:'设备ID',width:60,hidden:true},
			{field:'device_name',title:'设备名称',width:200}, 
			{field:'run_state',title:'运行状态',width:100},
			{field:'rec_time',title:'记录时间',width:200},
			{field:'store_code',title:'门店编号',width:100},
			{field:'room_code',title:'包房编号',width:100}
		]]
	});
});
var param ;
function searchLog(){
	var gridparam = { 
		"sysLog.device_name": $('#s_deviceName').val(), 
		"sysLog.run_state": $('#s_runState').val(),
		"start_time" :  $("#start_time").datebox("getValue"),
		"end_time" :  $("#end_time").datebox("getValue"),
		"store_code":$('#s_store').val(),
		"room_code":$('#s_room').val()
	} ;
	param = gridparam ;
	$('#tt').datagrid('load',gridparam);
	$('#searchForm').form('reset');
	$('#searchDialog').dialog('close');
}
function post(URL, PARAMS) {        
    var temp = document.createElement("form");        
    temp.action = URL;        
    temp.method = "post";        
    temp.style.display = "none";        
    for (var x in PARAMS) {        
        var opt = document.createElement("input");        
        opt.name = x;        
        opt.value = PARAMS[x];        
        alert(opt.name);        
        temp.appendChild(opt);        
    }        
    document.body.appendChild(temp);        
    temp.submit();        
    return temp;        
}        
       
function exportLog(){
	post('exportSysLog.action',param); 
}
function openSerchWin(){
	$('#searchDialog').dialog('open');
}
function closeSearchWin(){
	$('#searchForm').form('reset');
	$('#searchDialog').dialog('close');
}
</script>
<table id="tt">
</table>
<div id="toolbar">
	<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增</a> -->
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="exportLog()">导出</a>
</div>
<div id="searchDialog" class="easyui-dialog" style="padding:5px;width:600px;height:200px;"
			title="查询" iconCls="icon-ok"
			closed="true" 
			modal="true"
			buttons="#search-buttons">
		<form id="searchForm" method="post">
		<table>
			<tr>
				<td>设备名称</td>
				<td><input class="easyui-validatebox" type="text" id="s_deviceName" /></td>
				<td>运行状态</td>
				<td>
					<select id='s_runState' style="width:180px;">
			        	<option value='0'>--请选择--</option>
			        	<option value='1'>增加</option>
			        	<option value='2'>删除</option>
			        	<option value='3'>修改</option>
			        	<option value='4'>查询</option>
		        	</select>
	        	</td>
			</tr>
			<tr>
				<td>记录时间</td>
				<td><input class="easyui-datebox" type="text" id="start_time" name="start_time" /></td>
				<td>至</td>
				<td><input class="easyui-datebox" type="text" id="end_time" name="end_time" /></td>
			</tr>
			<tr>
				<td>门店</td>
				<td> <input class="easyui-validatebox" type="text" id="s_store" /></td>
				<td>包房</td>
				<td> <input class="easyui-validatebox" type="text" id="s_room"/></td>
			</tr>
		</table>
		</form>
</div>
<div id="search-buttons">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchLog()">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
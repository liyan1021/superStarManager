<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'操作日志',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
		style: "height: 500px;",
	    pagination : true,
	    singleSelect:true,
	    url:'operationList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		columns:[[

			{field:'op_log_id',title:'id',width:60,hidden:true},
			{field:'user_id',title:'操作人ID',width:60,hidden:true},
			{field:'user_name',title:'操作人',width:100}, 
			{field:'op_origin',title:'操作来源',width:100},
			{field:'op_func_id',title:'操作功能ID',width:60,hidden:true},
			{field:'op_func',title:'操作功能',width:200},
			{field:'op_type',title:'操作类型',width:100,
				formatter: function(value,row,index){
					if (row.op_type==1){
						return "增加";
					} else if(row.op_type==2) {
						return "删除";
					}else if(row.op_type==3) {
						return "修改";
					}else if(row.op_type==4) {
						return "查询";
					}else if(row.op_type==5) {
						return "登录";
					}else if(row.op_type==6) {
						return "导出";
					}else if(row.op_type==7) {
						return "导入";
					}
				}
			},
			{field:'op_time',title:'操作时间',width:150},
			{field:'store_code',title:'门店',width:200},
			{field:'room_code',title:'包房',width:200}
			
		]]
	});
});
var param ;
function searchLog(){
	var gridparam = { 
		"opt.user_name" : $('#s_userName').val(), 
		"opt.op_origin" : $('#s_opOrigin').val(),
		"opt.op_func" : $('#s_opFunc').val(),
		"opt.op_type" : $('#s_opType').val(),
		"start_time" :  $("#start_time").datebox("getValue"),
		"end_time" :  $("#end_time").datebox("getValue"),
		"opt.store_code" : $('#s_store').val(),
		"opt.room_code" : $('#s_room').val()
	} ;
	param = gridparam ;
/* 	$.post('searchOptLogList.action',gridparam,function(json){
		$('#tt').datagrid('loadData', {   
	        "total": json.total,   
	        "rows": json.rows   
		});   
        $('#searchForm').form('reset');
		$('#searchDialog').dialog('close'); 
	}); */
	$('#tt').datagrid({url:'searchOptLogList.action',queryParams:gridparam,pageNumber:1});
	//var p = $('#tt').datagrid('getPager');
	

	$('#searchForm').form('reset');
	$('#searchDialog').dialog('close');
	//$('#tt').datagrid('load',gridparam);
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
        temp.appendChild(opt);        
    }        
    document.body.appendChild(temp);        
    temp.submit();        
    return temp;        
}        
       
function exportLog(){
	if($('#tt').datagrid('getRows').length == 0 ){
		$.messager.alert('提示','查询为空，无法导出', 'info');		
	}else{
		post('exportOptLog.action',param);	
	}
	 
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
<div id="searchDialog" class="easyui-dialog" style="padding:5px;width:700px;height:300px;"
			title="查询" iconCls="icon-ok"
			closed="true" 
			modal="true"
			buttons="#search-buttons">
		<form id="searchForm" method="post">
		<table>
			<tr>
				<td>操作人:</td>
				<td> <input class="easyui-validatebox" type="text" id="s_userName"  /></td>
				<td>操作来源:</td>
				<td><input class="easyui-validatebox" type="text" id="s_opOrigin"  /></td>
			</tr>
			<tr>
				<td>操作功能:</td>
				<td><input class="easyui-validatebox" type="text" id="s_opFunc"  /></td>
				<td>操作类型:</td>
				<td>
					<select id='s_opType' style="width:150px;">
			        	<option value='0'>--请选择--</option>
			        	<option value='1'>增加</option>
			        	<option value='2'>删除</option>
			        	<option value='3'>修改</option>
			        	<option value='4'>查询</option>
			        	<option value='5'>登录</option>
			        	<option value='6'>导出</option>
			        	<option value='7'>导入</option>
		       		</select>
	       		</td>
			</tr>
			<tr>
				<td>操作时间:</td>
				<td><input class="easyui-datebox" type="text" id="start_time" name="start_time" /></td>
	        	<td>至</td>
	        	<td><input class="easyui-datebox" type="text" id="end_time" name="end_time" /></td>
			</tr>
			<tr>
				<td>门店:</td>
				<td><input class="easyui-validatebox" type="text" id="s_store"/></td>
				<td>包房:</td>
				<td> <input class="easyui-validatebox" type="text" id="s_room" /></td>
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
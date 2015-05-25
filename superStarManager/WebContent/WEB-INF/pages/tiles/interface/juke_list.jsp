<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'点唱记录',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    url:'jukeList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		columns:[[
		          
			{field:'music_name',title:'歌曲名称',width:150},
			{field:'start_time',title:'播放开始时间',width:150},
			{field:'end_time',title:'播放结束时间',width:150},
			{field:'play_plan',title:'播放进度',width:150},
			{field:'member_no',title:'会员名称',width:150},
			{field:'company',title:'所属公司',width:150},
			{field:'store',title:'门店',width:150},
			{field:'room',title:'包房',width:150}
			
			
		]],
		onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
	    }
	});
});
function searchJuke() {
	var gridparam = {
		"jukeRank.music_name" : $('#s_music_name').val(),
		"jukeRank.start_time" : $('#s_start_time').datebox("getValue"),
		"jukeRank.end_time" : $('#s_end_time').datebox("getValue"),
		"jukeRank.member_no" : $('#s_member_no').val(),
		"jukeRank.company" : $('#s_company').val(),
		"jukeRank.store" : $('#s_store').val(),
		"jukeRank.room" : $('#s_room').val()
	};
	$('#tt').datagrid({
		url : 'searchJuke.action',
		queryParams : gridparam
	});
	$('#searchForm').form('reset');
	$('#searchDialog').dialog('close');
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
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
</div>

<div id="searchDialog" class="easyui-dialog" style="padding:5px;width:500px;height:300px;"
			title="查询" iconCls="icon-ok"
			closed="true" 
			modal="true"
			buttons="#search-buttons">
		<form id="searchForm" method="post">
			<table>
				<tr>
					<td>歌曲名称</td>
					<td><input type="text" id="s_music_name" /></td>
				</tr>
				<tr>
					<td>播放开始时间</td>
					<td><input type="text" class="easyui-datebox"  id="s_start_time"/></td>
					<td>播放结束时间</td>
					<td><input type="text" class="easyui-datebox"  id="s_end_time" /></td>
				</tr>
				<tr>
					<td>
						会员名称
					</td>
					<td>
						<input type="text" id="s_member_no" />
					</td>
					<td>
						公司名称
					</td>
					<td>
						<input type="text" id="s_company" />
					</td>
					
				</tr>
				<tr>
					<td>
						门店
					</td>
					<td>
						<input type="text" id="s_store" />
					</td>
					<td>
						包房
					</td>
					<td>
						<input type="text" id="s_room" />
					</td>
				</tr>
			</table>
		</form>
</div>
<div id="search-buttons">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchJuke()">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
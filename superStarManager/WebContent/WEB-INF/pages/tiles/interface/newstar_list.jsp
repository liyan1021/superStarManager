<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'新星推荐',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    url:'newStarList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		columns:[[
			{field:'ck',checkbox:true},
			
			{
				field : 'star_name',
				title : '歌星名字',
				width : 150
			},
			{
				field : 'star_type',
				title : '性别',
				width : 60,
				formatter: function(value,row,index){
					if (row.star_type==1){
						return "男";
					} else if(row.star_type==2) {
						return "女";
					}else if(row.star_type==3) {
						return "组合";
					}
					return row.star_type ;
				}
			},
			{
				field : 'area',
				title : '歌星归属区域',
				width : 150
			},
			{
				field : 'introduce_time',
				title : '推荐日期',
				width : 150
			},
			{
				field : 'state',
				title : '发布状态',
				width : 150,
				formatter: function(value,row,index){
					if (row.state == 1){
						return "已发布" ;
					}else if(row.state == 0){
						return "未发布" ;
					}
						return row.state ;
				}
			}
	] ],
	onLoadSuccess : function(data) {
		$('.editcls').linkbutton({
			plain : true,
			iconCls : 'icon-edit'
		});
	}
	});
});
	function newSinger() {
		//获取非新星	
		$('#singerGrid').datagrid({
			rownumbers:true ,
			height : 'auto',
			fit:true ,
			singleSelect:false, //多选
		    pagination : true,
		    url:'unNewStarList.action',
		    method: 'post',
			columns:[[
				{field:'ck',checkbox:true},
				{field : 'star_id',title : '歌星ID',width : 60,hidden:true},
				{field : 'star_name',title : '歌星名字',width : 60},
				{field : 'other_name',title : '别名',width : 60},
				{field : 'spell_first_letter_abbreviation',title : '歌星名的拼音字头',width : 60},
				{field : 'star_type',title : '性别',width : 60 },
				{field : 'area',title : '歌星归属区域',width : 60}
				
			]]
		});
		$('#win').dialog('open').dialog('setTitle', '新增新星');
		
	}
	function saveSinger() {
			//获取选中歌星 将新星字段更改为‘是’
		var rows = $("#singerGrid").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要更改的歌星!", 'info');
			return;
		} else {
			$.messager.confirm('更改记录', '确定要更改选中的歌星吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].star_id + ",";
					}
				
				$.ajax({
					url : 'setNewStar.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'musicRankingList.action',queryParams:"",pageNumber:1});
							$('#tt').datagrid('clearSelections');
							$('#win').dialog('close');
							$.messager.alert('提示', "成功更改" + pers + "条记录",
									'info');
						} else {
							$.messager.alert('警告', "更改失败!", 'info');
						}
					}
				});
				}
			});
		}

	}
	function removeSinger() {
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要删除的记录!", 'info');
			return;
		} else {
			$.messager.confirm('删除记录', '确定要删除选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].introduce_id + ",";
					}
				
				$.ajax({
					url : 'removeNewStar.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'musicRankingList.action',queryParams:"",pageNumber:1});
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
	function pushSinger(){
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要发布的记录!", 'info');
			return;
		} else {
		      var i=0;
		     for (i; i < rows.length; i++){  //检查是否已发布，已发布的不能再次发布
		        if( rows[i].state==1){
		        $.messager.alert('警告', "已发布的不能再次发布，请重新选择!", 'info');
		           return;
		           }
		      }
		  if(i==rows.length){		
			$.messager.confirm('发布记录', '确定要发布选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].introduce_id + ",";
					}
				
				$.ajax({
					url : 'pushNewStar.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'musicRankingList.action',queryParams:"",pageNumber:1});
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
	function searchSinger() {
		var gridparam = { 
				"mainStar.singer.star_name" : $('#s_starName').val(),
				"mainStar.singer.star_type" : $('#s_starType').combobox('getValue'),
				"mainStar.singer.area" : $('#s_area').combobox('getValue'),
		} ;
		$('#tt').datagrid({url:'searchNewStar.action',queryParams:gridparam});
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	
	function openSerchWin() {
		$('#searchDialog').dialog('open');
	}
	function closeWin() {
		$('#star_id').val("");
		$('#saveSinger').form('reset');
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
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newSinger()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeSinger()">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="pushSinger()">发布</a>
</div>
<div id="win" class="easyui-dialog" style="width:600px;height:400px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<table id="singerGrid">
	</table>
</div>
<div id="dlg-buttons" >
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveSinger()">保存</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>

<div id="searchDialog" class="easyui-dialog" style="padding:5px;width:600px;height:400px;"
			title="查询" iconCls="icon-ok"
			closed="true" 
			modal="true"
			buttons="#search-buttons">
		<form id="searchForm" method="post">
			<table>
				<tr>
					<td>歌星名字</td>
					<td><input type="text" id="s_starName"/></td>
					<td>歌星性别</td>
					<td>
						<select class="easyui-combobox" id="s_starType" 
							panelHeight="auto" style="width:150px;"
						>
						<option>--请选择--</option>
						<option value='1'>男</option>
						<option value='2'>女</option>
						<option value='3'>组合</option>
					</select>
					</td>
				</tr>
				
				<tr>
					<td>歌星归属地址</td>
					<td>
						<input class="easyui-combobox" id="s_area"
										data-options="
												url:'getDictList.action?type=歌星',
												method:'post',
												valueField:'dict_value',
												textField:'dict_value',
												panelHeight:'auto'
										" />
					</td> 
				</tr>
			</table>
		</form>
</div>
<div id="search-buttons">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchSinger()">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
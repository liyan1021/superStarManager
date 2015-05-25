<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function () {
	    $('#tt').datagrid({
	    	title:'新歌排行', 
			iconCls:'icon-edit',
			rownumbers:true ,
			height : 'auto',
			fit:true ,
		    pagination : true,
		    url:'newMusicRankingList.action',
		    method: 'post',
		   	toolbar: "#toolbar",
			sortName : "music_song_number",
		   	sortOrder: "desc",
			columns:[[
				{field:'music_name',title:'歌曲',width:150},
				{field:'singer_name',title:'歌星',width:150},
				{field:'music_song_number',title:'总点唱次数',width:150,sortable: true},
				
			]],
			onDblClickCell:function(rowIndex, field, value){
				if(field == 'music_song_number'){
					$('#tt').datagrid('selectRow',rowIndex);  
				    var row = $('#tt').datagrid('getSelected');  
				    $('#music_id').val(row.music_id);
				    $('#win').dialog('open');
				}
				
			}
		});
	   
	}); 
	function searchMusicRank() {
		var gridparam = {
			"musicRanking.music_name" : $('#s_music_name').val(),
			"musicRanking.singer_name" : $('#s_singer_name').val()
		};
		$('#tt').datagrid({
			url : 'searchNewMusicRank.action',
			queryParams : gridparam
		});
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	
	function openSerchWin() {
		$('#searchDialog').dialog('open');
	}
	function editNumber(){
		$('#editNumber').form('submit', {
			url : 'editNewMusicRank.action',
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$.messager.alert('编辑', data.message, 'info');
					closeWin();
					$('#tt').datagrid({url:'musicRankingList.action',queryParams:"",pageNumber:1});
				}else {
                	$.messager.alert('警告',data.message,'info');
                }
			}
		});
	}
	function closeWin() {
		$('#music_id').val("");
		$('#editNumber').form('reset');
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
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
</div>

<div id="win" class="easyui-dialog" style="padding:5px;width:500px;height:300px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="editNumber" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>增加次数</td>
				<td>
					<input type="hidden" id="music_id" name="musicRanking.music_id"  />
	        		<input type="text" id="song_number" name="song_number" required="true" />
	        	</td>
			</tr>
		</table>
	</form>
</div>
<div id="dlg-buttons" >
		<table cellpadding="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="editNumber()">保存</a>
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
					<td>歌曲名称</td>
					<td><input type="text" id="s_music_name"/></td>
					<td>歌星名称</td>
					<td>
						<input type="text" id="s_singer_name"/>
					</td>
				</tr>
			</table>
		</form>
</div>
<div id="search-buttons">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchMusicRank()">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
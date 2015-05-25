<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function() {
		$('#tt').datagrid({
			title : '补歌清单',
			iconCls : 'icon-edit',
			rownumbers : true,
			height : 'auto',
			fit:true ,
			pagination : true,
			fitColumns : false,
			url : 'supportList.action',
			method : 'post',
			toolbar : "#toolbar",
			sortName : "support_time",
	     	sortOrder: "desc",
			columns : [ [
			{
				field : 'ck',
				checkbox : true
			},
			{
				field : 'song_name',
				title : '歌曲名称',
				width : 200
			},
			{
				field : 'song_year',
				title : '歌曲年代',
				width : 100
			},
			{
				field : 'star_name',
				title : '歌星名称',
				width : 60
				
			},
			{
				field : 'song_state',
				title : '补歌状态',
				width : 60,
				sortable: true,
				formatter: function(value,row,index){
					if (row.song_state==0){
						return "未处理";
					} else if(row.song_state==1) {
						return "已补歌曲";
					}else if(row.song_state==2) {
						return "忽略";
					}
					return row.song_state ;
				}
			},
			{
				field : 'support_time',
				title : '提交时间',
				width : 60,
				sortable: true
			},{
				field : 'handler_person',
				title : '操作人',
				width : 60
			},{
				field : 'handler_time',
				title : '操作时间',
				width : 60
			},{
				field : 'support_id',
				title : 'id',
				width : 60,
				hidden:true
			},{ title :'歌曲版本', field :'version',width : 200,sortable: true,
				formatter: function(value,row,index){
					if (row.version==1){
						return "MTV";
					}else if (row.version==2){
						return "演唱会";
					} else if(row.version==3) {
						return "故事情节";
					}else if(row.version==4) {
						return "风景";
					}else if(row.version==5) {
						return "人物";
					}
					return "" ;
				}
			},
			{ title :'语种', field :'language',width : 200 ,sortable: true,
				formatter: function(value,row,index){
					if (row.language==1){
						return "国语";
					} else if(row.language==2) {
						return "闽南语";
					}else if(row.language==3) {
						return "粤语";
					}else if(row.language==4) {
						return "英语";
					}else if(row.language==5) {
						return "韩语";
					}else if(row.language==6) {
						return "日语";
					}else if(row.language==7) {
						return "其他";
					}
					return "" ;
				}	
			}
		 ] ]
		});
	});
	var param ; 
	function searchLog() {
		var gridparam = {
			"support.song_name" : $("#s_songName").val(),
			"support.song_year" : $("#s_songYear").val(),
			"support.star_name" : $("#s_starName").val(),
			"support.song_state" : $("#s_songState").combobox('getValue'),
			"support.handler_person" : $("#handler_person").val(),
			"start_time" : $("#start_time").datebox("getValue"),
			"end_time" :$("#end_time").datebox("getValue"),
			"support.handler_start_time" : $("#handler_start_time").datebox("getValue"),
			"support.handler_end_time" :$("#handler_end_time").datebox("getValue"),
			"support.version":$("#s_version").datebox("getValue")	,
			"support.language":$("#s_language").datebox("getValue")
			
		};
		param = gridparam ;
		$('#tt').datagrid({
			url : 'searchSupportList.action',
			queryParams : gridparam,
			pageNumber:1
		});

		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	function post(URL, PARAMS) {
		var temp = document.createElement("form");
		temp.action = URL;
		temp.method = "post";
		temp.style.display = "none";
		for ( var x in PARAMS) {
			var opt = document.createElement("input");
			opt.name = x;
			opt.value = PARAMS[x];
			temp.appendChild(opt);
		}
		document.body.appendChild(temp);
		temp.submit();
		return temp;
	}
	function exportSupport(){
		if($('#tt').datagrid('getRows').length == 0 ){
			$.messager.alert('提示','查询为空，无法导出', 'info');		
		}else{
			post('exportSupport.action',param); 
		}
	}
	function removeMusic() {
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要删除的记录!", 'info');
			return;
		} else {
			$.messager.confirm('删除记录', '确定要删除选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].support_id + ",";
					}
				}
				$.ajax({
					url : 'removeSupport.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'supportList.action',queryParams:"",pageNumber:1});
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
	function newSupportWin(){
		$('#win').dialog('open');
	}
	function supportSong(){
		$('#supportForm').form('submit', {
		    url:'supportSong.action',
		    onSubmit: function(){
		        //校验 return flase ;
		    	return validateForm();
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$.messager.alert('提示', data.message, 'info');
					closeWin();
					$('#tt').datagrid({url:'supportList.action',queryParams:"",pageNumber:1});
				}else {
                	$.messager.alert('警告',data.message,'info');
                }
			}
		});
	}
	function closeWin(){
		$('#supportForm').form('clear');
		$('#tt').datagrid('load');
		$('#win').dialog('close');
	}
	function openSerchWin() {
		$('#searchDialog').dialog('open');
	}
	function closeSearchWin() {
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	function updateMusic(sign){
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要更改的歌曲!", 'info');
			return;
		} 
	  else {
		 var i=0;	 
		  for (i; i < rows.length; i++){  //检查是否已标记，已经标记的无需再标记
		        if( rows[i].song_state==1||rows[i].song_state==2){
		        $.messager.alert('警告', "歌曲状态已经标记，无需再次标记!", 'info');
		           return;
		           }
		      }	   
		  if(i==rows.length){
			$.messager.confirm('更改歌曲', '确定要更改选中的歌曲吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].support_id + ",";
					}
				
				$.ajax({
					url : 'updateSupport.action',
					data : {
						'ids' : ids,
						'sign': sign 
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'supportList.action',queryParams:"",pageNumber:1});
							$('#tt').datagrid('clearSelections');
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
	}
</script>
<table id="tt">
</table>
<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newSupportWin()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a> 
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeMusic()">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="updateMusic(1)">已补歌曲</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="updateMusic(2)">忽略</a>
	<a href="#"	class="easyui-linkbutton" iconCls="icon-print"  plain="true" onclick="exportSupport()">导出</a> 
	</div>
<div id="searchDialog" class="easyui-dialog"
	style="padding: 5px; width: 600px; height: 400px;" title="查询"
	iconCls="icon-ok" closed="true" modal="true" buttons="#search-buttons">
	<form id="searchForm" method="post">
		<table>
			<tr>
				<td>歌曲名称</td>
				<td><input type="text" id="s_songName"  /></td>
				<td>歌曲年代</td>
				<td><input type="text" id="s_songYear"  /></td>
			</tr>
			<tr>
				<td>歌星名称</td>
				<td><input type="text" id="s_starName"  /></td>
				<td>补歌状态</td>
				<td>
					<select id='s_songState' class="easyui-combobox" panelHeight = 'auto'>
			        	<option value="">--请选择--</option>
			        	<option value='0'>未处理</option>
			        	<option value='1'>已补歌曲</option>
			        	<option value='2'>忽略</option>
	        		</select>
				</td>
			</tr>
			<tr>
				<td>操作人</td>
				<td><input type="text" id="handler_person" /></td>
			</tr>
			<tr>
				<td>提交时间</td>
				<td><input type="text" id="start_time" class="easyui-datebox"  /></td>
				<td>至</td>
				<td><input type="text" id="end_time"  class="easyui-datebox" /></td>
			</tr>
			<tr>
				<td>操作时间</td>
				<td><input type="text" id="handler_start_time" class="easyui-datebox"  /></td>
				<td>至</td>
				<td><input type="text" id="handler_end_time"  class="easyui-datebox" /></td>
			</tr>
			<td>版本</td>
				<td>
					<select class="easyui-combobox" id="s_version" style="width:150px;"
							data-options="
									url:'getDictListS.action?type=version',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
				<td>语种</td>
				<td>
					<select class="easyui-combobox" id="s_language"   style="width:150px;" 
							data-options="
									url:'getDictListS.action?type=language',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
		</table>
		
	</form>
</div>
<div id="search-buttons">
	<table cellpadding="0" cellspacing="0" style="width: 100%">
		<tr>
			<td style="text-align: right"><a href="#"
				class="easyui-linkbutton" iconCls="icon-search"
				onclick="searchLog()">查询</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a></td>
		</tr>
	</table>
</div>





<div id="win" class="easyui-dialog"
	style="padding: 5px; width: 600px; height: 400px;"
	iconCls="icon-ok" closed="true" modal="true" buttons="#support-buttons">
	<form id="supportForm" method="post">
		<table>
			<tr>
				<td>歌曲名称</td>
				<td><input type="text" id="song_name" name="support.song_name" /></td>
				<td>歌曲年代</td>
				<td><input type="text" id="song_year" name="support.song_year"  /></td>
			</tr>
			<tr>
				<td>歌星名称</td>
				<td><input type="text" id="star_name" name="support.star_name" /></td>
			</tr>
			<tr>
			<td>版本</td>
				<td>
					<select class="easyui-combobox" id="version" name="support.versions" style="width:150px;"
							data-options="
									url:'getDictListS.action?type=version',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
				<td>语种</td>
				<td>
					<select class="easyui-combobox" id="language"  name="support.languages" style="width:150px;" 
							data-options="
									url:'getDictListS.action?type=language',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
		</table>
		
			</tr>
		</table>
		
	</form>
</div>
<div id="support-buttons">
	<table cellpadding="0" cellspacing="0" style="width: 100%">
		<tr>
			<td style="text-align: right"><a href="#"
				class="easyui-linkbutton" iconCls="icon-search"
				onclick="supportSong()">确定</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="closeWin()">关闭</a></td>
		</tr>
	</table>
</div>
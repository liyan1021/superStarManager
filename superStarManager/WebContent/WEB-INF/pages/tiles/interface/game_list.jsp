<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function() {
		$('#tt').datagrid({
			title : '游戏管理',
			iconCls : 'icon-edit',
			rownumbers : true,
			height : 'auto',
			fit:true ,
			pagination : true,
			fitColumns : false,
			url : 'gameList.action',
			method : 'post',
			toolbar : "#toolbar",
			columns : [ [
			{
				field : 'game_id',
				checkbox : true
			},
			{field:'opt',title:'',align:'center',
				formatter:function(value,row,index){
					return '<a href="#" class="editcls" onclick="editGame('+index+')"></a>';
				}
			},
			{
				field : 'game_name',
				title : '游戏名称',
				width : 200
			},
			{
				field : 'import_time',
				title : '导入时间',
				width : 100
			},
			{
				field : 'state',
				title : '状态',
				width : 60,
				formatter: function(value,row,index){
					if (row.state == 1){
						return "已发布" ;
					}else if(row.state == 0){
						return "未发布" ;
					}
						return row.is_activity ;
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
	function searchApkInfo() {
		var gridparam = {
			"game.game_name" : $("#s_gameName").val(),
			"start_time" : $("#start_time").datebox("getValue"),
			"end_time" :$("#end_time").datebox("getValue")
			
		};
		$('#tt').datagrid({
			url : 'searchGame.action',
			queryParams : gridparam,
			pageNumber:1
		});

		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	function editGame(index){
		$('#tt').datagrid('selectRow',index);// 关键在这里  
	    var row = $('#tt').datagrid('getSelected');  
	   	if (row){  
			$('#win').dialog('open').dialog('setTitle','编辑游戏');
	        $('#game_id').val(row.game_id);
	        $('#game_name').val(row.game_name);
	        $('#game_uri').val(row.game_uri);
	    }
	}
	function removeGame() {
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要删除的记录!", 'info');
			return;
		} else {
			$.messager.confirm('删除记录', '确定要删除选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].game_id + ",";
					}
				$.ajax({
					url : 'removeGame.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'gameList.action',queryParams:"",pageNumber:1});
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
	function openImportWin(){
		$('#win').dialog('open').dialog('setTitle','导入APK');
	}
	function newGame(){
		$('#win').dialog('open').dialog('setTitle','新增游戏');
	}
	function importGame(){
		$('#importGame').form('submit', {
		    url:'importGame.action',
		    onSubmit: function(){
		       //校验 return flase ;
		    	return $(this).form('validate');
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$.messager.alert('提示', data.message, 'info');
					$('#importGame').form('reset');
					$('#win').dialog('close');
					$('#tt').datagrid({url:'gameList.action',queryParams:"",pageNumber:1});
				}else {
                	$.messager.alert('警告',data.message,'info');
                }
			}
		});
	}
	function validateForm(){
		var result = $("#saveGame").validate({  
	    	rules: {
	        	"game.game_name": {
					required: true,
					maxlength: 20
					
				},
// 				"game.game_uri": {
// 					required: true,
// 					maxlength: 20
// 				}
			},
			messages: {
				"game.game_name": {
					required: "游戏名称不能为空",
					maxlength: "游戏名称长度不能超过20"
				},
// 				"game.game_uri": {
// 					required: "游戏URI不能为空",
// 					maxlength: "游戏URI长度不能超过20"
					
// 				}
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
		return result ; 
	}
	function saveGame(){
		if(!$('#game_id').val()) {
			//新增
			$('#saveGame').form('submit', {
				url : 'saveGame.action',
				onSubmit : function() {
					//校验 return flase ;
					return validateForm();
				},
				success : function(data) {
					var data = eval('(' + data + ')'); 
					if (data.success) {
						$.messager.alert('提示', data.message, 'info');
						$('#game_id').val("");
						$('#saveGame').form('reset');
						$('#win').dialog('close');
						$('#tt').datagrid({url:'gameList.action',queryParams:"",pageNumber:1});
					}else {
	                	$.messager.alert('警告',data.message,'info');
	                }
				}
			});
		} else {
			//修改
			$('#saveGame').form('submit', {
				url : 'editGame.action',
				onSubmit : function() {
					//校验 return flase ;
					return validateForm();
				},
				success : function(data) {
					
					var data = eval('(' + data + ')');
					if (data.success) {
						$.messager.alert('提示', data.message, 'info');
						$('#game_id').val("");
						$('#saveGame').form('reset');
						$('#win').dialog('close');
						$('#tt').datagrid({url:'gameList.action',queryParams:"",pageNumber:1});
					}else {
	                	$.messager.alert('警告',data.message,'info');
	                }
				}
			});
		} 
	}
	function pushGame(){
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
						ids += rows[i].game_id + ",";
					}
				
				$.ajax({
					url : 'pushGame.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'gameList.action',queryParams:"",pageNumber:1});
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
	}
	function closeWin(){
		$('#importGame').form('reset');
		$('#win').dialog('close');
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
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newGame()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a> 
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeGame()">删除</a>
	<!-- <a href="#"	class="easyui-linkbutton" iconCls="icon-print"  plain="true" onclick="openImportWin()">导入</a>  -->
	<a href="#"	class="easyui-linkbutton" iconCls="icon-print"  plain="true" onclick="pushGame()">发布</a> 
	</div>
<div id="searchDialog" class="easyui-dialog"
	style="padding: 5px; width: 600px; height: 400px;" title="查询"
	iconCls="icon-ok" closed="true" modal="true" buttons="#search-buttons">
	<form id="searchForm" method="post">
		<table>
			<tr>
				<td>游戏名称</td>
				<td><input type="text" id="s_gameName"  /></td>
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
				onclick="searchApkInfo()">查询</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a></td>
		</tr>
	</table>
</div>
<div id="win" class="easyui-dialog" style="padding:5px;width:500px;height:300px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="saveGame" method="post">
		<table>
			<tr>
				<td>游戏名称</td>
				<td>
					<input type="hidden" id="game_id" name="game.game_id"/>	
					<input type="text" id="game_name" name="game.game_name"/>
	        	</td>
<!-- 				<td>游戏URI</td> -->
<!-- 				<td> -->
<!-- 					<input type="text" id="game_uri" name="game.game_uri"/> -->
<!-- 	        	</td> -->
			</tr>
		</table>
	</form>

</div>
<div id="dlg-buttons" >
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="importGame()">上传</a> -->
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveGame()">保存</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>

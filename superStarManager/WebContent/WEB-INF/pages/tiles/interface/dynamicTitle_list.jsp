<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'跑马灯字幕',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    url:'dynamicTitleList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		columns:[[
			{field:'ck',checkbox:true},
			{field:'opt',title:'',align:'center',
				formatter:function(value,row,index){
					return '<a href="#" class="editcls" onclick="editTitle('+index+')"></a>';
				}
			},
			{field:'title_id',title:'字幕 ID',width:60,hidden:true},
			{field:'title_time_length',title:'字幕时长',width:150},
			{field:'title_number',title:'字幕次数',width:150},
			{field:'title_date_time',title:'字幕显示时间',width:150},
			{field:'title_content',title:'字幕内容',width:150},
			{field:'room_id',title:'房间 ID',width:60,hidden:true},
			{field:'room_num',title:'房间号',width:150},
			{field:'state',title:'发布状态',width:150,
				formatter: function(value,row,index){
					if (row.state == 1){
						return "已发布" ;
					}else if(row.state == 0){
						return "未发布" ;
					}
						return row.state ;
				}
			}
		
		]],
		onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
	    }
	});
});
function newTitle(){
	$('#win').dialog('open').dialog('setTitle','新增跑马灯字幕');
}
function editTitle(index){
	$('#tt').datagrid('selectRow',index);// 关键在这里  
    var row = $('#tt').datagrid('getSelected');  
   	if (row){  
		$('#win').dialog('open').dialog('setTitle','编辑跑马灯字幕');
        $('#titleId').val(row.title_id);
        $('#titleTimeLength').val(row.title_time_length);
        $('#titleNumber').val(row.title_number);
        $('#titleDateTime').datetimebox('setValue',row.title_date_time);
        $('#titleContent').val(row.title_content);
        $('#roomNum').val(row.room_num);
        $('#roomId').combobox('setText',row.room_num)
        ;
    }
}
function validateForm(){
	var result = $("#saveTitle").validate({  
    	rules: {
        	"dynamicTitle.title_time_length": {
				required: true,
				maxlength: 20,
				digits:true
				
			},
			"dynamicTitle.title_number": {
				required: true,
				maxlength: 20,
				digits:true
			},
			"dynamicTitle.title_date_time": {
				required: true
			},
			"dynamicTitle.title_content": {
				maxlength: 200
			}
		},
		messages: {
			"dynamicTitle.title_time_length": {
				required: "字幕时长不能为空",
				maxlength: "字幕时长长度不能超过20",
				digits:"字幕时长只能为数字"
			},
			"dynamicTitle.title_number": {
				required: "字幕次数不能为空",
				maxlength: "字幕次数长度不能超过20",
				digits:"字幕次数只能为数字"
			},
			"dynamicTitle.title_date_time": {
				required: "字幕显示时间不能为空"
			},
			"dynamicTitle.title_content": {
				maxlength: "字幕内容长度不能超过200"
			}
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
function saveTitle(){
	if(!$('#titleId').val()){
		//新增
		$('#saveTitle').form('submit', {
		    url:'saveTitle.action',
		    onSubmit: function(){
		       //校验 return flase ;
		    	return  validateForm();
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$.messager.alert('提示', data.message, 'info');
					$('#saveTitle').form('clear');
					$('#titleId').val("");
					$('#win').dialog('close');
					$('#tt').datagrid({url:'dynamicTitleList.action',queryParams:"",pageNumber:1});
				}else {
                	$.messager.alert('警告',data.message,'info');
                }
			}
		});
		} else {
			//修改
			$('#saveTitle').form('submit', {
				url : 'editTitle.action',
				onSubmit : function() {
					return  validateForm();
				},
				success : function(data) {
					var data = eval('(' + data + ')'); // change the JSON string to javascript object
					if (data.success) {
						$.messager.alert('提示', data.message, 'info');
						$('#titleId').val("");
						$('#saveTitle').form('clear');
						$('#win').dialog('close');
						$('#tt').datagrid({url:'dynamicTitleList.action',queryParams:"",pageNumber:1});
					}else {
	                	$.messager.alert('警告',data.message,'info');
	                }
				}
			});
		}

	}
	function removeTitle() {
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要删除的记录!", 'info');
			return;
		} else {
			$.messager.confirm('删除记录', '确定要删除选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].title_id + ",";
					}
				}
				$.ajax({
					url : 'removeTitle.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'dynamicTitleList.action',queryParams:"",pageNumber:1});
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
	function searchTitle() {
		var gridparam = {
			"dynamicTitle.title_time_length" : $('#s_titleTimeLength').val(),
			"dynamicTitle.title_number" : $('#s_titleNumber').val(),
			"start_time" : $("#start_time").datebox("getValue"),  
			"end_time" : $("#end_time").datebox("getValue"),
			"dynamicTitle.title_content" : $('#s_titleContent').val(),
			"dynamicTitle.room_id": $('#s_roomId').combobox("getValue")
		};
		$('#tt').datagrid({
			url : 'searchTitle.action',
			queryParams : gridparam,
			pageNumber:1
		});
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	function pushTitle(){
		
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
						ids += rows[i].title_id + ",";
					}
				
				$.ajax({
					url : 'pushTitle.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'dynamicTitleList.action',queryParams:"",pageNumber:1});
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
	function openSerchWin() {
		$('#searchDialog').dialog('open');
	}
	function closeWin() {
		$('#titleId').val("");
		$('#saveTitle').form('clear');
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
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newTitle()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeTitle()">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="pushTitle()">发布</a>
</div>
<div id="win" class="easyui-dialog" style="padding:5px;width:500px;height:300px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="saveTitle" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>字幕时长 <font color="red">*</font></td>
				<td>
					<input type="hidden" id="titleId" name="dynamicTitle.title_id"  />
	        		<input type="text"  id="titleTimeLength" name="dynamicTitle.title_time_length" onchange="checkNumber(this)" />
	        	</td>
				<td>字幕次数 <font color="red">*</font></td>
				<td><input type="text" id="titleNumber" name="dynamicTitle.title_number"  onchange="checkNumber(this)"/></td>
			</tr>
			<tr>
				<td>字幕显示时间 <font color="red">*</font></td>
				<td><input type="text" class="easyui-datetimebox" id="titleDateTime" name="dynamicTitle.title_date_time" 
				data-options="editable:false"/></td>
				
			</tr>
			<tr>
				<td>字幕内容</td>
				<td colspan="3">
					<!-- <input type="text"  id="titleContent" name="dynamicTitle.title_content" /> -->
					<textarea cols="40" rows="3"  id="titleContent" name="dynamicTitle.title_content"></textarea>
				</td>
				
			</tr>
			<tr>
				<td>房间号</td>
					<td>
					<input id="roomId" name="dynamicTitle.room_id" style="width:150px;" class="easyui-combobox"
							data-options="
									url:'getRoomList.action',
									method:'post',
									editable:false,
									valueField:'room_id', 
									textField:'room_num',
									panelHeight:'auto',
									onSelect:function(){
												var test = $('#roomId').combobox('getText');
												$('#roomNum').val(test);
								}"/>
					<input type="hidden"  id= "roomNum" name="dynamicTitle.room_num">
					</td>
			</tr>
		</table>
	</form>

</div>
<div id="dlg-buttons" >
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveTitle()">保存</a>
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
				<td>字幕时长</td>
				<td>
	        		<input type="text"  id="s_titleTimeLength" name="dynamicTitle.title_time_length"/>
	        	</td>
				<td>字幕次数</td>
				<td><input type="text" id="s_titleNumber" name="dynamicTitle.title_number" /></td>
			</tr>
			<tr>
				<td>字幕显示时间</td>
				<td><input type="text" class="easyui-datebox" id="start_time" name="start_time" 
				data-options="editable:false"/></td>
				<td>至</td>
				<td><input type="text" class="easyui-datebox" id="end_time" name="end_time" data-options="editable:false"/></td>
			</tr>
			<tr>
				<td>字幕内容</td>
				<td><input type="text" id="s_titleContent" name="dynamicTitle.title_content" /></td>
				
			</tr>
				<tr>
				<td>房间号</td>
					<td>
					<select id="s_roomId" name="dynamicTitle.room_id" style="width:150px;" class="easyui-combobox"
							data-options="
									url:'getRoomList.action',
									method:'post',
									editable:false,
									valueField:'room_id', 
									textField:'room_num',
									panelHeight:'auto'" >
					</select>
					</td>
			</tr>
			</table>
		</form>
</div>
<div id="search-buttons">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchTitle()">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
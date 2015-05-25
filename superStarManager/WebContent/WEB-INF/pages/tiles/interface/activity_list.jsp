<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">   
#star_head1 {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}
#star_head2 {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}  
</style> 
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'营销活动',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    url:'activityList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		columns:[[
			{field:'ck',checkbox:true},
			{field:'opt',title:'',align:'center',
				formatter:function(value,row,index){
					return '<a href="#" class="editcls" onclick="editActivity('+index+')"></a>';
				}
			},
			{field:'activity_id',title:'活动 ID',width:60,hidden:true},
			{field:'activity_name',title:'活动名称',width:150},
			{field:'activity_introduce',title:'活动介绍',width:150},
			{field:'activity_time',title:'活动时间',width:150},
			{field:'activity_sort',title:'活动顺序',width:150},
			{field:'state',title:'发布状态',width:150,
				formatter: function(value,row,index){
					
					if (row.state ==1 ){
						return "已发布" ;
					}else if(row.state == 0){
						return "未发布" ;
					}else if(row.state==2){
					    return "全部";
					}
						return row.is_activity ;
				}
			}
		]],
		onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
	    }
	});
});
function newActivity(){
	$('#win').dialog('open').dialog('setTitle','新增界面');
}
function editActivity(index){
	$('#tt').datagrid('selectRow',index);// 关键在这里  
    var row = $('#tt').datagrid('getSelected');  
   	if (row){  
		$('#win').dialog('open').dialog('setTitle','编辑界面');
        $('#activity_id').val(row.activity_id);
        $('#activity_name').val(row.activity_name);
        $('#activity_introduce').val(row.activity_introduce);
        $('#activity_time').datebox('setValue',row.activity_time);
        $('#activity_sort').val(row.activity_sort);
        $("#star_head1").attr("src",server_path+row.activity_path_1+"?tempid="+Math.random());
        $("#star_head2").attr("src",server_path+row.activity_path_2+"?tempid="+Math.random());
    }
}
function validateForm(){
	var result = $("#saveActivity").validate({  
    	rules: {
        	"marketingActivity.activity_name": {
				required: true,
				maxlength: 20
			},
			"marketingActivity.activity_introduce":{
				maxlength: 200
			},
			"marketingActivity.activity_time":{
				required: true
			},
			"marketingActivity.activity_sort":{
				required: true,
				digits:true,
				maxlength: 20
			}
		},
		messages: {
			"marketingActivity.activity_name": {
				required: "活动名称不能为空",
				maxlength: "活动名称长度不能超过20"
			},
			"marketingActivity.activity_introduce":{
				maxlength: "活动介绍长度不能超过200"
			},
			"marketingActivity.activity_time":{
				required: "活动时间不能为空"
			},
			"marketingActivity.activity_sort":{
				required: "活动顺序不能为空",
				digits:"活动顺序只能为数字",
				maxlength: "活动顺序长度不能超过20"
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
	if(result){
		if($('#star_head1').attr("src")=="" || $('#star_head2').attr("src")=="" ){
			 $.messager.alert('提示','图片不能为空', 'info');
			 return false ; 
		}
		if($('#activity_sort').val()!=""){
			var sign = false ;
			$.ajax({
				async: false,
				url:'checkActivitySort.action',
				type : 'post',
				data : {
					'marketingActivity.activity_id':$('#activity_id').val(),
					'marketingActivity.activity_sort' : $('#activity_sort').val()
				},
				success : function(data) {
					var data = eval('(' + data + ')');
					if (data) { 
						 $.messager.alert('提示','顺序已经存在', 'info');
					} else {
						sign =  true ;
					}
				}
			});
			return sign ; 
		} 
	}
	
	return result ; 
}

function saveActivity(){
	if(!$('#activity_id').val()){
		//新增
		$('#saveActivity').form('submit', {
		    url:'saveActivity.action',
		    onSubmit: function(){
		       //校验 return flase ;
		    	return validateForm();
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$.messager.alert('提示', data.message, 'info');
					$('#saveActivity').form('reset');
					$('#activity_id').val("");
					var div = document.getElementById('preview');
					div.innerHTML = "<img id='star_head1' src='' style='width: 200px;height: 200px'></img>"; 
					var div = document.getElementById('preview2');
					div.innerHTML = "<img id='star_head2' src='' style='width: 200px;height: 200px'></img>"; 
					$('#win').dialog('close');
					$('#tt').datagrid({url:'activityList.action',queryParams:"",pageNumber:1});
				}else {
                	$.messager.alert('警告',data.message,'info');
                }
			}
		});
		} else {
			//修改
			$('#saveActivity').form('submit', {
				url : 'editActivity.action',
				onSubmit : function() {
					return validateForm();
				},
				success : function(data) {
					var data = eval('(' + data + ')'); // change the JSON string to javascript object
					if (data.success) {
						$.messager.alert('提示', data.message, 'info');
						$('#activity_id').val("");
						var div = document.getElementById('preview');
						div.innerHTML = "<img id='star_head1' src='' style='width: 200px;height: 200px'></img>"; 
						var div = document.getElementById('preview2');
						div.innerHTML = "<img id='star_head2' src='' style='width: 200px;height: 200px'></img>"; 
						$('#saveActivity').form('reset');
						$('#win').dialog('close');
						$('#tt').datagrid({url:'activityList.action',queryParams:"",pageNumber:1});
					}else {
	                	$.messager.alert('警告',data.message,'info');
	                }
				}
			});
		}

	}
	function removeActivity() {
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要删除的记录!", 'info');
			return;
		} else {
			$.messager.confirm('删除记录', '确定要删除选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].activity_id + ",";
					}
				
				$.ajax({
					url : 'removeActivity.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'activityList.action',queryParams:"",pageNumber:1});
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
	function searchActivity() {
		var gridparam = {
			"marketingActivity.activity_name" : $('#s_activityName').val(),
			"marketingActivity.activity_introduce" : $('#s_activityIntroduce').val(),
			"marketingActivity.activity_time" : $('#s_activityTime').datebox("getValue"),
			"marketingActivity.activity_sort" : $('#s_activitySort').val(),
			"marketingActivity.state": $("#s_state").combobox('getValue')
		};
		$('#tt').datagrid({
			url : 'searchActivity.action',
			queryParams : gridparam,
			pageNumber:1
		});
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	
	//发布界面
	function pushActivity(){
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
						ids += rows[i].activity_id + ",";
					}
				$.ajax({
					url : 'pushActivity.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'activityList.action',queryParams:"",pageNumber:1});
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
		var div = document.getElementById('preview');
		div.innerHTML = "<img id='star_head1' src='' style='width: 200px;height: 200px'></img>"; 
		var div = document.getElementById('preview2');
		div.innerHTML = "<img id='star_head2' src='' style='width: 200px;height: 200px'></img>"; 
		$('#activity_id').val("");
		$('#saveActivity').form('reset');
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
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newActivity()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeActivity()">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="pushActivity()">发布</a>
</div>
<div id="win" class="easyui-dialog" style="padding:5px;width:600px;height:500px;" 
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="saveActivity" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>活动名称 <font color="red">*</font></td>
				<td>
					<input type="hidden" id="activity_id" name="marketingActivity.activity_id"  />
	        		<input type="text"  id="activity_name" name="marketingActivity.activity_name" />
	        	</td>
			</tr>
			<tr>
				<td>活动介绍</td>
				<td colspan="3">
	        		<textarea id="activity_introduce" name="marketingActivity.activity_introduce" cols="50" rows="3">
	        		</textarea>
				</td>
			</tr>
			<tr>
				<td>活动时间</td>
				<td><input type="text"  class="easyui-datebox" id="activity_time" name="marketingActivity.activity_time" /></td>
				<td>活动顺序 <font color="red">*</font></td>
				<td><input type="text" id="activity_sort" name="marketingActivity.activity_sort" onchange="checkNumber(this)" /></td>
			</tr>
			<tr>
				<td>
					界面图片1 <font color="red">*</font>
				</td>
				<td>
					<s:file id="uploadFile1" name="image" onchange="previewImage(this,'star_head1','preview')"></s:file>
							<!-- 默认图片显示 -->
					<div id="preview">  
						<img id="star_head1" src="" style="width: 200px;height: 200px"></img>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					界面图片2 <font color="red">*</font>
				</td>
				<td>
					<s:file id="uploadFile2" name="image" onchange="previewImage(this,'star_head2','preview2')"></s:file>
							<!-- 默认图片显示 -->
					<div id="preview2">  
						<img id="star_head2" src="" style="width: 200px;height: 200px"></img>
					</div>
				</td>
			</tr>
		</table>
	</form>

</div>
<div id="dlg-buttons" >
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveActivity()">保存</a>
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
					<td>活动名称</td>
					<td><input type="text" id="s_activityName" /></td>
					<td>活动介绍</td>
					<td>
						<input type="text" id="s_activityIntroduce" />
					</td>
				</tr>
				<tr>
					<td>活动时间</td>
					<td><input type="text" class="easyui-datebox"  id="s_activityTime" /></td>
					<td>活动顺序</td>
					<td><input type="text" id="s_activitySort" /></td>
				</tr>
				<tr>
					<td>发布状态</td>
					<td>
						<select id="s_state" class="easyui-combobox" data-options="editable:false">
							<option value="">--请选择--</option>
							<option value="2">全部</option>
							<option value="0">未发布</option>
							<option value="1">已发布</option>
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
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchActivity()">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
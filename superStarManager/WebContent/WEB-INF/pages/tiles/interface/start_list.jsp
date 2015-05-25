<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">   
#star_head {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}  
</style>  
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'初始界面管理',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    url:'interfaceList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		columns:[[
			{field:'ck',checkbox:true},
			{field:'opt',title:'',align:'center',
				formatter:function(value,row,index){
					return '<a href="#" class="editcls" onclick="editInterface('+index+')"></a>';
				}
			},
			{field:'interface_id',title:'界面 ID',width:60,hidden:true},
			{field:'interface_name',title:'界面主题',width:150},
			{field:'online_time',title:'上线时间',width:150},
			{field:'offline_time',title:'下线时间',width:150},
			{field:'interface_time',title:'播放时长',width:150},
			{field:'interface_sort',title:'播放顺序',width:150},
			{field:'state',title:'发布状态',width:150,
				formatter: function(value,row,index){
					if (row.state == 1){
						return "已发布" ;
					}else if(row.state == 0){
						return "未发布" ;
					}
						return row.is_activity ;
				}}
			
		]],
		onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
	    }
	});
});
function newInterface(){
	$('#win').dialog('open').dialog('setTitle','新增界面');
}
function editInterface(index){
	$('#tt').datagrid('selectRow',index);// 关键在这里  
    var row = $('#tt').datagrid('getSelected');  
   	if (row){  
		$('#win').dialog('open').dialog('setTitle','编辑界面');
        $('#interfaceId').val(row.interface_id);
        $('#interfaceName').val(row.interface_name);
        $('#onlineTime').datebox("setValue",row.online_time);
        $('#offlineTime').datebox("setValue",row.offline_time);
        $('#interfaceTime').val(row.interface_time);
        $('#interfaceSort').val(row.interface_sort);
        $("#star_head").attr("src",server_path+row.file_path+"?tempid="+Math.random());
    }
}
function validateForm(){
	var result = $("#saveInterface").validate({  
    	rules: {
    		"itf.interface_name":{
    			maxlength: 20
    		},
        	"itf.interface_time": {
				required: true,
				digits:true,
				min:1,
				maxlength: 20
			},
			"itf.interface_sort":{
				required: true,
				min:1,
				digits:true,
				maxlength: 20
			}
		},
		messages: {
			"itf.interface_name":{
    			maxlength: "界面主题长度不能超过20"
    		},
			"itf.interface_time": {
				required: "播放时长不能为空",
				digits:"播放时长只能输入正整数",
				min:"播放时长只能输入正整数",
				maxlength: "播放时长长度不能超过20"
			},
			"itf.interface_sort":{
				required: "播放顺序不能为空",
				digits:"播放顺序只能输入正整数",
				min:"播放顺序只能输入正整数",
				maxlength:'播放顺序长度不能超过20',
				
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
		if($('#onlineTime').datebox('getValue')==""){
			 $.messager.alert('提示','上线时间不能为空', 'info');
			 return false ; 
		}
		if($('#star_head').attr("src")==""){
			 $.messager.alert('提示','图片不能为空', 'info');
			 return false ; 
		}
		//判断时间
		if($('#onlineTime').datebox('getValue')!="" && $('#offlineTime').datebox('getValue')!=""){
			var startTime=$("#onlineTime").datebox('getValue');  
		    var start=new Date(startTime.replace("-", "/").replace("-", "/"));  
		    var endTime=$("#offlineTime").datebox('getValue');  
		    var end=new Date(endTime.replace("-", "/").replace("-", "/"));  
		    if(end<start){  
		    	$.messager.alert('提示','上线时间不能大与下线时间', 'info');
		        return false;  
		    }  
		}
		if($('#interfaceSort').val()!=""){
			var sign = false ;
			$.ajax({
				async: false,
				url:'checkInterfaceSort.action',
				type : 'post',
				data : {
					'itf.interface_type':'1', // 只查询初始界面数据 过滤条件
					'itf.interface_id':$('#interfaceId').val(),
					'itf.interface_sort' : $('#interfaceSort').val()
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
function saveInterface(){
	if(!$('#interfaceId').val()){
		//新增
		$('#saveInterface').form('submit', {
		    url:'saveInterface.action',
		    onSubmit: function(){
		       //校验 return flase ;
		    	return validateForm();
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$.messager.alert('提示', data.message, 'info');
					closeWin();
					$('#tt').datagrid({url:'interfaceList.action',queryParams:"",pageNumber:1});
				}else {
                	$.messager.alert('警告',data.message,'info');
                }
			}
		});
		} else {
			//修改
			$('#saveInterface').form('submit', {
				url : 'editInterface.action',
				onSubmit : function() {
					return validateForm();
				},
				success : function(data) {
					var data = eval('(' + data + ')'); // change the JSON string to javascript object
					if (data.success) {
						$.messager.alert('提示', data.message, 'info');
						closeWin();
						$('#tt').datagrid({url:'interfaceList.action',queryParams:"",pageNumber:1});
					}else {
	                	$.messager.alert('警告',data.message,'info');
	                }
				}
			});
		}

	}
	function removeInterface() {
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要删除的记录!", 'info');
			return;
		} else {
			$.messager.confirm('删除记录', '确定要删除选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].interface_id + ",";
					}
				
				$.ajax({
					url : 'removeInterface.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'interfaceList.action',queryParams:"",pageNumber:1});
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
	function searchInterface() {
		var gridparam = {
			"itf.interface_name" : $('#s_interfaceName').val(),
			"itf.online_start_time" : $('#s_onlineTime_start').datebox("getValue"),
			"itf.online_end_time" : $('#s_onlineTime_end').datebox("getValue"),
			"itf.offline_start_time" : $('#s_offlineTime_start').datebox("getValue"),
			"itf.offline_end_time" : $('#s_offlineTime_end').datebox("getValue"),
			"itf.interface_time" : $('#s_interfaceTime').val(),
			"itf.interface_sort" : $('#s_interfaceSort').val(),
			"itf.state": $("#s_state").combobox('getValue')
		};
		$('#tt').datagrid({
			url : 'searchInterface.action',
			queryParams : gridparam,
			pageNumber:1
		});
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	
	//发布界面
	function pushInterface(){
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要发布的记录!", 'info');
			return;
		} else {
			$.messager.confirm('发布记录', '确定要发布选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].interface_id + ",";
					}
				
				$.ajax({
					url : 'pushStartInterface.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'interfaceList.action',queryParams:"",pageNumber:1});
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
	
	function openSerchWin() {
		$('#searchDialog').dialog('open');
	}
	function closeWin() {
		var div = document.getElementById('preview');
		div.innerHTML = "<img id='star_head' src='' style='width: 200px;height: 200px'></img>";  
		$('#interfaceId').val("");
		$('#saveInterface').form('reset');
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
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newInterface()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeInterface()">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="pushInterface()">发布</a>
</div>
<div id="win" class="easyui-dialog" style="padding:5px;width:1024px;height:600px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="saveInterface" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>界面主题</td>
				<td>
					<input type="hidden" id="interfaceId" name="itf.interface_id"  />
	        		<input type="text"  id="interfaceName" name="itf.interface_name" />
	        	</td>
			</tr>
			<tr>
				<td>上线时间 <font color="red">*</font></td>
				<td><input type="text"  class="easyui-datebox" id="onlineTime" name="itf.online_time" data-options="editable:false" /></td>
				<td>下线时间</td>
				<td><input type="text"  class="easyui-datebox" id="offlineTime" name="itf.offline_time" data-options="editable:false" /></td>
			</tr>
			<tr>
				<td>
					播放时长 <font color="red">*</font>
				</td>
				<td>
					<input type="text" id="interfaceTime" name="itf.interface_time" onchange="checkNumber(this)" />秒
				</td>
				<td>
					播放顺序 <font color="red">*</font>
				</td>
				<td>
					<input type="text" id="interfaceSort" name="itf.interface_sort" onchange="checkNumber(this)" />
				</td>
			</tr>
			<tr>
				<td>
					界面图片 <font color="red">*</font>
				</td>
				<td colspan="3">
					<s:file id="uploadFile" name="image" onchange="previewImage(this,'star_head','preview')"></s:file>
							<!-- 默认图片显示 -->
					<div id="preview">  
						<img id="star_head" src="" style="width: 200px;height: 200px"></img>
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
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveInterface()">保存</a>
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
					<td>界面主题</td>
					<td><input type="text" id="s_interfaceName" name="itf.interface_name" /></td>
				</tr>
				<tr>
					<td>上线时间</td>
					<td><input type="text" class="easyui-datebox"  id="s_onlineTime_start" name="itf.online_time" data-options="editable:false" /></td>
					<td>至</td>
					<td><input type="text" class="easyui-datebox"  id="s_onlineTime_end" name="itf.offline_time"  data-options="editable:false"/></td>
				</tr>
				<tr>
					<td>下线时间</td>
					<td><input type="text" class="easyui-datebox"  id="s_offlineTime_start" name="itf.online_time"  data-options="editable:false"/></td>
					<td>至</td>
					<td><input type="text" class="easyui-datebox"  id="s_offlineTime_end" name="itf.offline_time"  data-options="editable:false"/></td>
				</tr>
				<tr>
					<td>
						播放时长
					</td>
					<td>
						<input type="text" id="s_interfaceTime" name="itf.interface_time" />
					</td>
					<td>
						播放顺序
					</td>
					<td>
						<input type="text" id="s_interfaceSort" name="itf.interface_sort" />
					</td>
				</tr>
				<tr>
					<td>发布状态</td>
					<td>
						<select id="s_state" class="easyui-combobox" data-options="editable:false">
							<option value="">--请选择--</option>
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
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchInterface()">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
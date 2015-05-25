<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">   
#star_head {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}  
</style>  
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'视频转播设置',
		iconCls:'icon-edit',
		rownumbers:true ,
		fit:true ,
	    pagination : true,
	    url:'channelList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		columns:[[
			{field:'ck',checkbox:true},
			{field:'opt',title:'',align:'center',
				formatter:function(value,row,index){
					return '<a href="#" class="editcls" onclick="editChannel('+index+')"></a>';
				}
			},
			{field:'channel_id',title:'转播ID',width:60,hidden:true},
			{field:'channel_name',title:'频道名称',width:150},
			{field:'channel_url',title:'频道URL',width:150},
			{field:'channel_content',title:'频道简介',width:150},
			{field:'sort',title:'显示顺序',width:150},
			{field:'state',title:'发布状态',width:150,
				formatter: function(value,row,index){
					if (row.state == 1){
						return "已发布" ;
					}else if(row.state == 0){
						return "未发布" ;
					}else if(row.state==2){
					  return "全部";
					}
						return row.state ;
				}}
		]],
		onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
	    }
	});
	/* //调用该方法 绑定onchange 事件替换显示本地图片
	$("#uploadFile").uploadPreview({ width: 100, height: 100, imgPreview: "#imgDiv", imgType: ["bmp", "gif", "png", "jpg"] }); */
});
function newChannel(){
	$('#win').dialog('open').dialog('setTitle','新增界面');
}
function editChannel(index){
	$('#tt').datagrid('selectRow',index);// 关键在这里  
    var row = $('#tt').datagrid('getSelected');  
   	if (row){  
		$('#win').dialog('open').dialog('setTitle','编辑界面');
        $('#channel_id').val(row.channel_id);
        $('#channel_name').val(row.channel_name);
        $('#channel_url').val(row.channel_url);
        $('#channel_content').val(row.channel_content);
        $('#sort').val(row.sort);
        
        $("#star_head").attr("src",server_path+row.file_path+"?tempid="+Math.random());
    }
}
function validateForm(){
	var result = $("#saveChannel").validate({  
    	rules: {
        	"channel.channel_name": {
				required: true,
				maxlength: 20
			},
			"channel.channel_url":{
				required:true,
				maxlength: 20
			},
			"channel.channel_content":{
				maxlength: 200
			},
			"channel.sort":{
				required:true,
				maxlength: 20,
				digits:true
			}
		},
		messages: {
        	"channel.channel_name": {
				required: "频道名称不能为空",
				maxlength: "频道名称长度不能超过20"
			},
			"channel.channel_url":{
				required: "频道URL不能为空",
				maxlength: "频道URL长度不能超过20"
			},
			"channel.channel_content":{
				maxlength: "视频简介长度不能超过200"
			},
			"channel.sort":{
				required: "显示顺序不能为空",
				digits:"显示顺序只能为数字",
				maxlength: "显示顺序长度不能超过20"
			},
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
		if($('#star_head').attr("src")==""){
			 $.messager.alert('提示','图片不能为空', 'info');
			 return false ; 
		}
		if($('#sort').val()!=""){
			var sign = false ;
			$.ajax({
				async: false,
				url:'checkChannelSort.action',
				type : 'post',
				data : {
					'channel.channel_id':$('#channel_id').val(),
					'channel.sort' : $('#sort').val()
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
function saveChannel(){
	if(!$('#channel_id').val()){
		//新增
		$('#saveChannel').form('submit', {
		    url:'saveChannel.action',
		    onSubmit: function(){
		       //校验 return flase ;
		    	return validateForm();
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$.messager.alert('提示', data.message, 'info');
					$('#saveChannel').form('reset');
					$('#channel_id').val("");
					var div = document.getElementById('preview');
					div.innerHTML = "<img id='star_head' src='' style='width: 200px;height: 200px'></img>";  
					$('#win').dialog('close');
					$('#tt').datagrid({url:'channelList.action',queryParams:"",pageNumber:1});
				}else {
                	$.messager.alert('警告',data.message,'info');
                }
			}
		});
		} else {
			//修改
			$('#saveChannel').form('submit', {
				url : 'editChannel.action',
				onSubmit : function() {
					return validateForm();
				},
				success : function(data) {
					var data = eval('(' + data + ')'); // change the JSON string to javascript object
					if (data.success) {
						$.messager.alert('提示', data.message, 'info');
						$('#channel_id').val("");
						$("#star_head").attr("src","");
						$('#saveChannel').form('reset');
						$('#win').dialog('close');
						$('#tt').datagrid({url:'channelList.action',queryParams:"",pageNumber:1});
					}else {
	                	$.messager.alert('警告',data.message,'info');
	                }
				}
			});
		}

	}
	function removeChannel() {
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要删除的记录!", 'info');
			return;
		} else {
			$.messager.confirm('删除记录', '确定要删除选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].channel_id + ",";
					}
				
				$.ajax({
					url : 'removeChannel.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'channelList.action',queryParams:"",pageNumber:1});
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
	function searchChannel() {
		var gridparam = {
			"channel.channel_name" : $('#s_channelName').val(),
			"channel.channel_url" : $('#s_channelUrl').val(),
			"channel.channel_content" : $('#s_channelContent').val(),
			"channel.state": $("#s_state").combobox('getValue')
		};
		$('#tt').datagrid({
			url : 'searchChannel.action',
			queryParams : gridparam,
			pageNumber:1
		});
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	
	//发布界面
	function pushChannel(){
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
						ids += rows[i].channel_id + ",";
					}
				
				$.ajax({
					url : 'pushChannel.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')');
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'channelList.action',queryParams:"",pageNumber:1});
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
		div.innerHTML = "<img id='star_head' src='' style='width: 200px;height: 200px'></img>"; 
		$('#channel_id').val("");
		$('#saveChannel').form('reset');
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
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newChannel()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeChannel()">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="pushChannel()">发布</a>
</div>
<div id="win" class="easyui-dialog" style="padding:5px;width:500px;height:400px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="saveChannel" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>频道名称 <font color="red">*</font></td>
				<td>
					<input type="hidden" id="channel_id" name="channel.channel_id"  />
	        		<input type="text"  id="channel_name" name="channel.channel_name" />
	        	</td>
	        	<td>频道URL <font color="red">*</font></td>
				<td>
					<input type="text"  id="channel_url" name="channel.channel_url" />
				</td>
			</tr>
			<tr>
				<td>视频简介</td>
				<td><input type="text"  id="channel_content" name="channel.channel_content" /></td>
				<td>
					显示顺序 <font color="red">*</font>
				</td>
				<td>
					<input type="text" id="sort" name="channel.sort" onchange="checkNumber(this)" />
				</td>
			</tr>
			<tr>
				<td>
					频道图片 <font color="red">*</font>
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
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveChannel()">保存</a>
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
					<td>频道名称</td>
					<td><input type="text" id="s_channelName" name="channel.channel_name" /></td>
					<td>频道URL</td>
					<td>
						<input type="text" id="s_channelUrl" name="channel.channel_url" />
					</td>
				</tr>
				<tr>
					<td>频道简介</td>
					<td><input type="text" id="s_channelContent" name="channel.channel_content" /></td>
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
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchChannel()">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
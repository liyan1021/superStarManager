<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'宣传片管理',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    url:'advertVideoList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		columns:[[
			{field:'ck',checkbox:true},
			{field:'opt',title:'',align:'center',
				formatter:function(value,row,index){
					return '<a href="#" class="editcls" onclick="editAdvert('+index+')"></a>';
				}
			},
			{field:'advert_id',title:'宣传片 ID',width:60,hidden:true},
			{field:'advert_theme',title:'宣传片名称',width:150},
			{field:'advert_content',title:'宣传片内容',width:150},
			{field:'online_time',title:'上线时间',width:150},
			{field:'offline_time',title:'下线时间',width:150},
			{field:'advert_sort',title:'播放顺序',width:150},
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
			/* ,
			{field:'is_activity',title:'是否可用',width:150,
				formatter: function(value,row,index){
					if (row.is_activity == 1){
						return "是" ;
					}else if(row.is_activity == 0){
						return "否" ;
					}
						return row.is_activity ;
				}
			}
			 */
		]],
		onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
	    }
	});
});
function newAdvert(){
	$('#win').dialog('open').dialog('setTitle','新增宣传片');
}
function editAdvert(index){
	$('#tt').datagrid('selectRow',index);// 关键在这里  
    var row = $('#tt').datagrid('getSelected');  
   	if (row){  
		$('#win').dialog('open').dialog('setTitle','编辑宣传片');
        $('#advertId').val(row.advert_id);
        $('#advertTheme').val(row.advert_theme);
        $('#advertContent').val(row.advert_content);
        $('#onlineTime').datebox('setValue',row.online_time); 
        $('#offlineTime').datebox('setValue',row.offline_time);
        /* $('#isActivity').combobox('setValue',row.is_activity); */
        $('#advertSort').val(row.advert_sort);
        $('#file_name').text(row.file_path);
        
    }
}
function validateForm(){
	var result = $("#saveVideo").validate({  
    	rules: {
    		
    		"advertVideo.advert_theme" :{
    			required: true,
    			maxlength: 20
    		},
    		"advertVideo.advert_content":{
    			required: true,
    			maxlength: 20
    		},
        	"advertVideo.advert_sort": {
				required: true,
				digits:true,
				maxlength: 20
			}
		},
		messages: {
			"advertVideo.advert_sort": {
				required: "播放顺序不能为空",
				digits:"播放顺序只能输入数字",
				maxlength: "播放顺序长度不能超过20"
			},
			"advertVideo.advert_content": {
				required: "宣传片内容不能为空",
				maxlength: "宣传片内容长度不能超过20"
			},
			"advertVideo.advert_theme": {
				required: "宣传片名称不能为空",
				maxlength: "宣传片名称长度不能超过20"
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
		if($('#advertSort').val()!=""){
			var sign = false ;
			$.ajax({
				async: false,
				url:'checkAdvertVideoSort.action',
				type : 'post',
				data : {
					'advertVideo.advert_id':$('#advertId').val(),
					'advertVideo.advert_sort' : $('#advertSort').val()
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
function saveAdvert(){
	if(!$('#advertId').val()){
		//新增
		$('#saveVideo').form('submit', {
		    url:'saveVideo.action',
		    onSubmit: function(){
		       //校验 return flase ;
		    	return validateForm();
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$.messager.alert('提示', data.message, 'info');
					$('#saveVideo').form('clear');
					$('#advertId').val("");
					$('#win').dialog('close');
					$('#tt').datagrid({url:'advertVideoList.action',queryParams:"",pageNumber:1});
				}else {
                	$.messager.alert('警告',data.message,'info');
                }
			}
		});
		} else {
			//修改
			$('#saveVideo').form('submit', {
				url : 'editVideo.action',
				onSubmit : function() {
					return validateForm();
				},
				success : function(data) {
					var data = eval('(' + data + ')'); // change the JSON string to javascript object
					if (data.success) {
						$.messager.alert('提示', data.message, 'info');
						$('#advertId').val("");
						$('#saveVideo').form('clear');
						$('#win').dialog('close');
						$('#tt').datagrid({url:'advertVideoList.action',queryParams:"",pageNumber:1});
					}else {
	                	$.messager.alert('警告',data.message,'info');
	                }
				}
			});
		}

	}
	function removeAdvert() {
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要删除的记录!", 'info');
			return;
		} else {
			$.messager.confirm('删除记录', '确定要删除选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].advert_id + ",";
					}
				
				$.ajax({
					url : 'removeVideo.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'advertVideoList.action',queryParams:"",pageNumber:1});
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
	function searchAdvert() {
		var gridparam = {
			"advertVideo.advert_theme" : $('#s_advertTheme').val(),
			"advertVideo.advert_content" : $('#s_advertContent').val(),
			"advertVideo.online_start_time" : $('#s_online_start_time').datebox('getValue'),
			"advertVideo.online_end_time" : $('#s_online_end_time').datebox('getValue'),
			"advertVideo.offline_start_time" : $('#s_offline_start_time').datebox('getValue'),
			"advertVideo.offline_end_time" : $('#s_offline_end_time').datebox('getValue'),
			"advertVideo.advert_sort" : $('#s_advertSort').val()
			/* "advertVideo.is_activity" : $('#s_isActivity').combobox('getValue'), */
		};
		$('#tt').datagrid({
			url : 'searchVideo.action',
			queryParams : gridparam,
			pageNumber:1
		});
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	function pushAdvert(){
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要发布的记录!", 'info');
			return;
		} 
		
		else 
		{     
		  var i=0;
		  for (i; i < rows.length; i++){  //检查是否已发布，已发布的不能再次发布
		        if( rows[i].state==1){
		        $.messager.alert('警告', "已发布的不能再次发布，请重新选择!", 'info');
		           return;
		           }
		      }
		if(i=rows.length){
			$.messager.confirm('发布记录', '确定要发布选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].advert_id + ",";
					}
				
				$.ajax({
					url : 'pushVideo.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'advertVideoList.action',queryParams:"",pageNumber:1});
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
		$('#advertId').val("");
		$('#file_name').text("");
		$('#saveVideo').form('reset');
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
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newAdvert()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeAdvert()">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="pushAdvert()">发布</a>
</div>
<div id="win" class="easyui-dialog" style="padding:5px;width:600px;height:300px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="saveVideo" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>宣传片名称 <font color="red">*</font></td>
				<td>
					<input type="hidden" id="advertId" name="advertVideo.advert_id"  />
	        		<input type="text"  id="advertTheme" name="advertVideo.advert_theme"  class="easyui-validatebox" required="true" />
	        	</td>
				<td>宣传片内容<font color="red">*</font></td>
				<td><input type="text" id="advertContent" name="advertVideo.advert_content" /></td>
			</tr>
			<tr>
				<td>上线时间 <font color="red">*</font></td>
				<td><input type="text" class="easyui-datebox" id="onlineTime" name="advertVideo.online_time" /></td>
				<td>下线时间</td>
				<td><input type="text" class="easyui-datebox" id="offlineTime" name="advertVideo.offline_time" /></td>
			</tr>
			<tr>
				<td>
					播放顺序 <font color="red">*</font>
				</td>
				<td>
					<input type="text" id="advertSort" name="advertVideo.advert_sort" onchange="checkNumber(this)" />
				</td>
				<%-- <td>是否可用</td>
				<td>
					<select id="isActivity" name="advertVideo.is_activity" class="easyui-combobox"  validtype="selectValueRequired" required="true" panelHeight = 'auto'>
			        	<option value=''>--请选择--</option>
			        	<option value='1'>是</option>
			        	<option value='0'>否</option>
	        		</select>
				</td> --%>
			</tr>
			<tr>
				<td>
					宣传片 <font color="red">*</font>
				</td>
				<td  colspan="3">
					<s:file id="uploadFile" name="video"></s:file>
				</td>
			</tr>
			<tr>
				<td  colspan="4">
					<a id="file_name" href="#"></a>
				</td>
			</tr>
		</table>
	</form>

</div>
<div id="dlg-buttons" >
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveAdvert()">保存</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>

<div id="searchDialog" class="easyui-dialog" style="padding:5px;width:500px;height:300px;"
			title= "查询" iconCls="icon-ok"
			closed="true" 
			modal="true"
			buttons="#search-buttons">
		<form id="searchForm" method="post">
			<table>
				<tr>
					<td>宣传片名称</td>
					<td><input type="text" id="s_advertTheme" name="advert.advert_theme" /></td>
					<td>宣传片内容</td>
					<td><input type="text" id="s_advertContent" name="advert.advert_content" /></td>
				</tr>
				<tr>
					<td>上线时间</td>
					<td><input type="text" class="easyui-datebox" id="s_online_start_time"/></td>
					<td>至</td>
					<td><input type="text" class="easyui-datebox" id="s_online_end_time"/></td>
				</tr>
				<tr>
					<td>下线时间</td>
					<td><input type="text" class="easyui-datebox" id="s_offline_start_time" /></td>
					<td>至</td>
					<td><input type="text" class="easyui-datebox" id="s_offline_end_time" /></td>
				</tr>
				<tr>
					<td>
						播放顺序
					</td>
					<td>
						<input type="text" id="s_advertSort" name="advert.advert_sort" />
					</td>
					<%-- <td>是否可用</td>
					<td>
						<select id="s_isActivity" name="advert.is_activity"  class="easyui-combobox" panelHeight = 'auto'>
			        	<option value=''>--请选择--</option>
			        	<option value='1'>是</option>
			        	<option value='0'>否</option>
	        		</select>
					</td> --%>
				</tr>
			</table>
		</form>
</div>
<div id="search-buttons">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchAdvert()">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
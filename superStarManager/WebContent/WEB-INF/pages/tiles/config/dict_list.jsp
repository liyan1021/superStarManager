<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">   
#star_head {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}  
#e_star_head {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}  
</style>  
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'字典管理',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    url:'dictList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
	   	sortName : "dict_sort",
	   	sortOrder: "asc",
		columns:[[
			{field:'ck',checkbox:true},
			{field:'opt',title:'',align:'center',
				formatter:function(value,row,index){
					if((row.dict_type_code =='theme')|| row.dict_type_code == 'song_type'){
						return '<a href="#" class="editcls" onclick="editDict('+index+')"></a>';
					}
				}
			},
			{field:'id',title:'ID',width:60,hidden:true},
			{field:'dict_type',title:'类别',width:150,sortable:true},
			{field:'dict_code',title:'字典代码',width:60,hidden:true},
			{field:'dict_value',title:'字典名称',width:200},
			{field:'import_time',title:'导入时间',width:200},
			{field:'dict_sort',title:'字典顺序',width:200,sortable:true},
		]],
		onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
	    }
	});
});
function removeDict(){
	var rows = $("#tt").datagrid("getSelections");
    var ids = "";
       if(rows.length == 0){
    	   $.messager.alert('警告',"请选择要删除的记录!",'info');
           return;
       }else{
       	   for ( var i = 0; i < rows.length; i++) {
               if(!(rows[i].dict_type_code =='theme')|| !rows[i].dict_type_code == 'song_type'){
               	 $.messager.alert('警告',"只能删除类别为主题与曲种的记录!",'info');
               	 return false ; 
               }
           }
    	   $.messager.confirm('删除记录','确定要删除选中的记录吗?', function(result){
	            if (result){
	                for ( var i = 0; i < rows.length; i++) {
	                    ids += rows[i].id+",";
	                }
		            $.ajax({
		                url : 'removeDict.action',
		                data:{
		                      'ids':ids
		                  },
		                type : 'post',
		                success : function(data) {
		                	var data = eval('(' + data + ')');  // change the JSON string to javascript object
		    		        if (data.success){
		                        var pers = rows.length;
		                        $('#tt').datagrid({url:'dictList.action',queryParams:"",pageNumber:1});
		                        $('#tt').datagrid('clearSelections');
		                        $.messager.alert('提示',"成功删除"+pers+"条记录",'info');
		                    } else {
		                    	$.messager.alert('警告',"删除失败!",'info');
		                    }
		                }
		            });
	    	   	}
            });
        }
}
var param ;
function searchDict(){
	var gridparam = { 
		"dict.dict_type" : $('#s_dictType').val(), 
		/* "dict.dict_code" : $('#s_dictCode').val(), */
		"dict.dict_value" : $('#s_dictValue').val(),
	} ;
	param = gridparam ;
	$('#tt').datagrid({url:'searchDictList.action',queryParams:gridparam,pageNumber:1});
	$('#searchForm').form('reset');
	$('#searchDialog').dialog('close');
}
function editDict(index){
	$('#tt').datagrid('selectRow',index);// 关键在这里  
    var row = $('#tt').datagrid('getSelected');  
   	if (row){  
		$('#editWin').dialog('open').dialog('setTitle','编辑字典');
        $('#dict_id').val(row.id);
        $('#dict_value').val(row.dict_value);
        $('#dict_sort').val(row.dict_sort);
        $('#e_dict_type_code').val(row.dict_type_code);
        $("#e_star_head").attr("src",server_path+row.file_path+"?tempid="+Math.random());
    }
}
function validateForm(){
	var result ; 
	if(!$('#dict_id').val()){
		//新增
		result = $("#saveDict").validate({  
	    	rules: {
	    		"dict.dict_type_code":{
	    			required: true
	    		},
	        	"dict.dict_value": {
					required: true,
					maxlength: 20
				},
				"dict.dict_sort":{
					required: true,
					digits:true,
					maxlength: 20
				}
			},
			messages: {
				"dict.dict_type_code":{
					required: "请选择类别"
	    		},
				"dict.dict_value": {
					required: "字典名称不能为空",
					maxlength: "字典名称长度不能超过20"
				},
				"dict.dict_sort":{
					required: "字典顺序不能为空",
					digits:"只能输入数字",
					maxlength:'字典顺序长度不能超过20',
					
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
			if($('#star_head').attr("src")==""){
				 $.messager.alert('提示','图片不能为空', 'info');
				 return false ; 
			}
			if($('#dictSort').val()!=""){
				$.ajax({
					async: false,
					url:'checkDictSort.action',
					type : 'post',
					data : {
						'dict.dict_type_code':$('#dict_type_code').val(), 
						'dict.dict_sort' : $('#dictSort').val()
					},
					success : function(data) {
						var data = eval('(' + data + ')');
						if (data) { 
							 $.messager.alert('提示','顺序已经存在', 'info');
							 result = false; 
						}
					}
				});
			}
			if($('#dictValue').val()!=""){
				$.ajax({
					async: false,
					url:'checkDictValue.action',
					type : 'post',
					data : {
						'dict.dict_type_code':$('#dict_type_code').val(), 
						'dict.dict_value' : $('#dictValue').val()
					},
					success : function(data) {
						var data = eval('(' + data + ')');
						if (data) { 
							 $.messager.alert('提示','字典名称已经存在', 'info');
							 result = false ; 
						}
					}
				});
			}
		}
	}else{
		//修改
		result = $("#editDict").validate({  
	    	rules: {
	    		"dict.dict_value": {
					required: true,
					maxlength: 20
				},
				"dict.dict_sort":{
					required: true,
					digits:true,
					maxlength: 20
				}
			},
			messages: {
				"dict.dict_value": {
					required: "字典名称不能为空",
					maxlength: "字典名称长度不能超过20"
				},
				"dict.dict_sort":{
					required: "字典顺序不能为空",
					digits:"只能输入数字",
					maxlength:'字典顺序长度不能超过20',
					
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
			if($('#e_star_head').attr("src")==""){
				 $.messager.alert('提示','图片不能为空', 'info');
				 return false;
			}
			if($('#dict_sort').val()!=""){
				$.ajax({
					async: false,
					url:'checkDictSort.action',
					type : 'post',
					data : {
						'dict.dict_type_code':$('#e_dict_type_code').val(), 
						'dict.id':$('#dict_id').val(),
						'dict.dict_sort' : $('#dict_sort').val()
					},
					success : function(data) {
						var data = eval('(' + data + ')');
						if (data) { 
							 $.messager.alert('提示','顺序已经存在', 'info');
							 result =  false ; 
						}
					}
				});
			}
			if($('#dict_value').val()!=""){
				$.ajax({
					async: false,
					url:'checkDictValue.action',
					type : 'post',
					data : {
						'dict.dict_type_code':$('#e_dict_type_code').val(), 
						'dict.id':$('#dict_id').val(),
						'dict.dict_value' : $('#dict_value').val()
					},
					success : function(data) {
						var data = eval('(' + data + ')');
						if (data) { 
							 $.messager.alert('提示','字典名称已经存在', 'info');
							 result =  false ; 
						}
					}
				});
			}
		}
	}
	return result ;
}
function saveDict(){
	if(!$('#dict_id').val()){
		//新增
		$('#saveDict').form('submit', {
		    url:'saveDict.action',
		    onSubmit: function(){
		    	return validateForm();
		    },
		    success:function(data){
		    	var data = eval('(' + data + ')');  // change the JSON string to javascript object
		        if (data.success){
		        	$.messager.alert('提示',data.message,'info');
		        	closeWin();
		            $('#tt').datagrid({url:'dictList.action',queryParams:"",pageNumber:1});
		        }else {
                	$.messager.alert('警告',data.message,'info');
                }
		    }
		});	
	}else{
		//修改
		$('#editDict').form('submit', {
		    url:'editDict.action',
		    onSubmit: function(){
		    	return validateForm();
		    },
		    success:function(data){
		    	var data = eval('(' + data + ')'); 
		        if (data.success){
		        	$.messager.alert('提示',data.message,'info');
		        	closeEditWin();
		        	$('#tt').datagrid({url:'dictList.action',queryParams:"",pageNumber:1});
		        }else {
                	$.messager.alert('警告',data.message,'info');
                }
		    }
		});
	}
	
}
function newDict(){
	$('#saveWin').dialog('open').dialog('setTitle','新增字典');
}
function closeWin(){
	$('#dict_id').val("");
	$('#saveDict').form('reset');
	$('#saveWin').dialog('close');
	var div = document.getElementById('preview');
	div.innerHTML = "<img id='star_head' src='' style='width: 200px;height: 200px'></img>";  
}
function closeEditWin(){
	$('#editDict').form('reset');
	$('#editWin').dialog('close');
	var div = document.getElementById('e_preview');
	div.innerHTML = "<img id='e_star_head' src='' style='width: 200px;height: 200px'></img>"; 
}
function openSerchWin(){
	$('#searchDialog').dialog('open');
}
function closeSearchWin(){
	$('#searchForm').form('reset');
	$('#searchDialog').dialog('close');
}
</script>
<table id="tt">
</table>
<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newDict()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeDict()">删除</a>
	
</div>
<div id="saveWin" class="easyui-dialog" style="padding:5px;width:600px;height:500px;"
			title="编辑" iconCls="icon-eidt"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="saveDict" method="post"  enctype="multipart/form-data">
	    <table>
				<tr>
					<td>类别 <font color="red">*</font></td>
					<td>
						<!-- <input type="text" id="dictType" name="dict.dict_type" /> -->
						<select id="dict_type_code" name="dict.dict_type_code" style="width:150px;">
							<option value="">--请选择--</option>
							<option value="theme">主题</option>
							<option value="song_type">曲种</option>
							<!-- <option value="language">语种</option>
							<option value="area">歌星归属地</option>
							<option value="singer_type">歌星类别</option>
							<option value="issue_year">年代</option>
							<option value="resolution">分辨率</option>
							<option value="track">音轨</option>
							<option value="channel">声道</option>
							<option value="storage_type">存储类型</option>
							<option value="movie_type">影视类别</option>
							<option value="version">版本</option> -->
						</select>
					</td>
				</tr>
				<tr>
					<td>字典名称 <font color="red">*</font></td>
					<td><input type="text" id="dictValue" name="dict.dict_value" /></td>
					<td>排序编号 <font color="red">*</font></td>
					<td><input type="text" id="dictSort" name="dict.dict_sort" onchange="checkNumber(this)"/></td>
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
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveDict()">保存</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
<div id="editWin" class="easyui-dialog" style="padding:5px;width:600px;height:500px;"
			title="编辑" iconCls="icon-eidt"
			closed="true"
			modal="true"
			buttons="#edit-buttons">
	<form id="editDict" method="post"  enctype="multipart/form-data">
	    <table>
	    		<tr>
	    			<td><input type="hidden" id="dict_id" name="dict.id" /></td>
	    		</tr>
				<tr>
					<td>
						字典名称 <font color="red">*</font>
					</td>
					<td>
						<input type="text" id="dict_value" name="dict.dict_value" />
						<input type="hidden" id="e_dict_type_code"/>
					</td>
					<td>
						排序编号 <font color="red">*</font>
					</td>
					<td>
						<input type="text" id="dict_sort" name="dict.dict_sort"  onchange="checkNumber(this)"/>
					</td>
				</tr>
				<tr>
					<td>
						界面图片 <font color="red">*</font>
					</td>
					<td colspan="3">
						<s:file id="uploadFile" name="image" onchange="previewImage(this,'e_star_head','e_preview')"></s:file>
								<!-- 默认图片显示 -->
						<div id="e_preview">  
							<img id="e_star_head" src="" style="width: 200px;height: 200px"></img>
						</div>
					</td>
				</tr>
			</table>
	</form>
</div>
<div id="edit-buttons" >
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveDict()">保存</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeEditWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
<div id="searchDialog" class="easyui-dialog" style="padding:5px;width:400px;height:200px;"
			title="查询" iconCls="icon-search"
			closed="true" 
			modal="true"
			buttons="#search-buttons">
		<form id="searchForm" method="post">
			<table>
				<tr>
					<td>类别</td>
					<td><input type="text" id="s_dictType" /></td>
				</tr>
				<!-- <tr>
					<td>字典代码</td>
					<td><input type="text" id="s_dictCode" /></td>
				</tr> -->
				<tr>
					<td>字典名称</td>
					<td><input type="text" id="s_dictValue" /></td>
				</tr>
			</table>
		</form>
</div>
<div id="search-buttons">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchDict()">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
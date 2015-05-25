<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'系统参数设置',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    url:'sysParamList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		columns:[[
			{field:'ck',checkbox:true},
			{field:'opt',title:'',align:'center',
				formatter:function(value,row,index){
					return '<a href="#" class="editcls" onclick="editSysParam('+index+')"></a>';
				}
			},
			{field:'param_name',title:'参数类型名称',width:150},
			{field:'param_value',title:'参数值',width:300,
				formatter: function(value,row,index){
					
					if (row.param_value == '0'){
						return "否" ;
					}else if(row.param_value == '1'){
						return "是" ;
					}
						return row.param_value ;
				}},
			{field:'device_type',title:'设备类型',width:200,
				formatter: function(value,row,index){
					if (row.device_type == 1){
						return "终端" ;
					}else if(row.device_type == 2){
						return "移动端" ;
					}else if(row.device_type == 3){
						return "公共" ;
					}
						return row.device_type ;
				}
			}
		]],
		onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
	    }
	});
});
function removeSysParam(){
	var rows = $("#tt").datagrid("getSelections");
    var ids = "";
       if(rows.length == 0){
    	   $.messager.alert('警告',"请选择要删除的记录!",'info');
           return;
       }else{
    	   $.messager.confirm('删除记录','确定要删除选中的记录吗?', function(result){
            if (result){
                for ( var i = 0; i < rows.length; i++) {
                    ids += rows[i].param_type+",";
                }
            }
            $.ajax({
                url : 'removeSysParam.action',
                data:{
                      'ids':ids
                  },
                type : 'post',
                success : function(data) {
                	var data = eval('(' + data + ')');  // change the JSON string to javascript object
    		        if (data.success){
                        var pers = rows.length;
						$('#tt').datagrid({url:'sysParamList.action',queryParams:"",pageNumber:1});
                        $('#tt').datagrid('clearSelections');
                        $.messager.alert('提示',"成功删除"+pers+"条记录",'info');
                    } else {
                    	$.messager.alert('警告',"删除失败!",'info');
                    }
                }
            });
            });
        }
}
function searchSysParam(){
	var gridparam = { 
		"sysParam.param_name" : $('#s_param_name').val(), 
		"sysParam.device_type" : $('#s_device_type').combobox('getValue')
	} ;
	$('#tt').datagrid({url:'searchSysParam.action',queryParams:gridparam,pageNumber:1});
	
	$('#searchForm').form('reset');
	$('#searchDialog').dialog('close');
}
function editSysParam(index){
	$('#tt').datagrid('selectRow',index);// 关键在这里  
    var row = $('#tt').datagrid('getSelected');  
   	if (row){  
		$('#win').dialog('open').dialog('setTitle','编辑参数');
        $('#param_type').val(row.param_type);
		$('#param_name').val(row.param_name);
        $('#param_value').val(row.param_value);
        $('#device_type').combobox('setValue',row.device_type);
    }
}
 function validateForm(){
	var result = $("#saveSysParam").validate({  
    	rules: {
    		"sysParam.param_name":{
    		    required:true,
    			maxlength: 50
    		},
    		"sysParam.param_value":{
    		    required:true,
    			maxlength:64
    		}
		},
		messages: {
		      "sysParam.param_name":{
				required: "参数名不能为空",
    			maxlength: "参数名长度不能超过50"
    		},
			  "sysParam.param_value":{
				required: "参数值不能为空",
    			maxlength: "参数值长度不能超过64"

    		}
		},
		   //重写错误显示消息方法,以alert方式弹出错误消息   
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
        //失去焦点时不验证   
        onfocusout: false,  
        onkeyup: false,
        onclick: false
         }).form();	
      if(result){
		if($('#device_type').combobox('getValue')=='0'){
			 $.messager.alert('提示','请选择设备类型', 'info');
			 return false ; 
		}
      }
	  return result ; 
  }
function saveSysParam(){
	if(!$('#param_type').val()){
		//新增
		$('#saveSysParam').form('submit', {
		    url:'saveSysParam.action',
		    onSubmit: function(){
		    	return validateForm();
		    },
		    success:function(data){
		    	var data = eval('(' + data + ')');  // change the JSON string to javascript object
		        if (data.success){
		        	$.messager.alert('提示',data.message,'info');
		        	$('#saveSysParam').form('reset');
		        	$('#param_type').val("");
		            $('#win').dialog('close');
		            $('#tt').datagrid({url:'sysParamList.action',queryParams:"",pageNumber:1});
		        }else {
                	$.messager.alert('警告',data.message,'info');
                }
		    }
		});	
	}else{
		//修改
		$('#saveSysParam').form('submit', {
		    url:'editSysParam.action',
		    onSubmit: function(){
		    	return $(this).form('validate');
		    },
		    success:function(data){
		    	var data = eval('(' + data + ')'); 
		        if (data.success){
		        	$.messager.alert('提示',data.message,'info');
		        	$('#param_type').val("");
		        	$('#saveSysParam').form('reset');
		            $('#win').dialog('close');
		            $('#tt').datagrid({url:'sysParamList.action',queryParams:"",pageNumber:1});
		        }else {
                	$.messager.alert('警告',data.message,'info');
                }
		    }
		});
	}
	
}
function newSysParam(){
	$('#win').dialog('open').dialog('setTitle','新增字典');
}
function closeWin(){
	$('#param_type').val("");
	$('#saveSysParam').form('reset');
	$('#win').dialog('close');
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
	<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newSysParam()">新增</a> -->
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
	<!--<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeSysParam()">删除</a>-->
	
</div>
<div id="win" class="easyui-dialog" style="padding:5px;width:400px;height:200px;"
			title="编辑" iconCls="icon-eidt"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="saveSysParam" method="post">
	    <table>
	    		<tr>
	    			<td><input type="hidden" id="param_type" name="sysParam.param_type" /></td>
	    		</tr>
				<tr>
					<td>参数类型名称</td>
					<td><input type="text" id="param_name" name="sysParam.param_name" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>参数值</td>
					<td><input type="text" id="param_value" name="sysParam.param_value"/></td>
				</tr>
				<tr>
					<td>设备类型</td>
					<td>
						<select id='device_type' name="sysParam.device_type" class="easyui-combobox" panelHeight = 'auto' disabled="disabled">
				        	<option value='0'>--请选择--</option>
				        	<option value='1'>终端</option>
				        	<option value='2'>移动端</option>
	        			</select>	
					</td>
				</tr>
			</table>
	</form>

</div>
<div id="dlg-buttons" >
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveSysParam()">保存</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
<div id="searchDialog" class="easyui-dialog" style="padding:5px;width:420px;height:200px;"
			title="查询" iconCls="icon-search"
			closed="true" 
			modal="true"
			buttons="#search-buttons">
		<form id="searchForm" method="post">
			<table>
				<tr>
					<td width="80px">参数类型名称</td>
					<td><input type="text" id="s_param_name" name="sysParam.param_name"/></td>
					<td>设备类型</td>
					<td>
						<select id='s_device_type' name="sysParam.device_type" class="easyui-combobox" panelHeight = 'auto'>
				        	<option value='0'>--请选择--</option>
				        	<option value='1'>终端</option>
				        	<option value='2'>移动端</option>
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
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchSysParam()">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
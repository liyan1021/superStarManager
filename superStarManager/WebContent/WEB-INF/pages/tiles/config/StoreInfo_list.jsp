<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'门店管理',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    url:'StoreInfoList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		columns:[[
			{field:'ck',checkbox:true},
			{field:'opt',title:'',align:'center',
			formatter:function(value,row,index){
				return '<a href="#" class="editcls" onclick="editStoreInfo('+index+')"></a>';
				}
			},
			{field:'store_id',title:'门店 ID',width:100,hidden:true},
			{field:'store_code',title:'门店编号',width:150},
			{field:'store_name',title:'门店名称',width:150},
			{field:'store_address',title:'门店地址',width:200},
		]],
		onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
	    }
	});
});
function removeStoreInfo(){
	var rows = $("#tt").datagrid("getSelections");
    var ids = "";
       if(rows.length == 0){
    	   $.messager.alert('警告',"请选择要删除的记录!",'info');
           return;
       }else {
    	   $.messager.confirm('删除记录','确定要删除选中的记录吗?', function(result){
	            if (result){
	                for ( var i = 0; i < rows.length; i++) {
	                    ids += rows[i].store_id+",";
	                }
	            }
		            $.ajax({
		                url : 'removeStoreInfo.action',
		                data:{
		                      'ids':ids
		                  },
		                type : 'post',
		                success : function(data) {
		                	var data = eval('(' + data + ')');  // change the JSON string to javascript object
		    		        if (data.success){
		                        var pers = rows.length;
		                        $('#tt').datagrid({url:'StoreInfoList.action',queryParams:"",pageNumber:1});
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
var param ;
function editStoreInfo(index){
	$('#tt').datagrid('selectRow',index);// 关键在这里  
	var row = $('#tt').datagrid('getSelected');
   	if (row){  
		$('#win').dialog('open').dialog('setTitle','编辑门店');
        $('#storeID').val(row.store_id);
        $('#storeCode').val(row.store_code);
        $('#storeName').val(row.store_name);
        $('#storeAddr').val(row.store_address);
    }
}
function validateForm(){
	var result = $("#saveStoreInfo").validate({  
	    	rules: {
	    		"storeInfo.store_code":{
	    			required: true,
	    			maxlength: 20
	    		},
	        	"storeInfo.store_name": {
					required: true,
					maxlength:20
				},
				"storeInfo.store_address":{
					maxlength:100
				}
			},
			messages: {
				"storeInfo.store_code":{
					required: "请输入门店编号"
	    		},
				"storeInfo.store_name": {
					required: "门店名称不能为空",
					maxlength: "门店名称长度不能超过20"
				},
				"storeInfo.store_address":{
					maxlength:'最大长度不能超过100' 
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
			var sign = false ;
				$.ajax({
					async: false,
					url:'checkStoreInfoCode.action',
					type : 'post',
					data : {
						'storeInfo.store_code':$('#storeCode').val(),
						'storeInfo.store_id':$('#storeID').val() 
					},
					success : function(data) {
						var data = eval('(' + data + ')');
						if (data) { 
							
							 $.messager.alert('提示','该编号已经存在', 'info');
							 sign = false ;
						}else{
							sign =true ;
							}
					}
				});
				return sign ; 
			}
			
	return result ;
}
function saveStoreInfo(){
	if(!$('#storeID').val()){
		//新增
		$('#saveStoreInfo').form('submit', {
		    url:'saveStoreInfo.action',
		    onSubmit: function(){
			    
		    	return validateForm();
		    },
		    success:function(data){
		    	var data = eval('(' + data + ')');  // change the JSON string to javascript object
		        if (data.success){
		        	$.messager.alert('提示',data.message,'info');
		        	$('#saveStoreInfo').form('reset');
		        	$('#storeID').val("");
		            $('#storeCode').val("");
		            $('#storeName').val("");
		            $('#storeAddr').val("");
		        	$('#win').dialog('close');
		        	$('#tt').datagrid({url:'StoreInfoList.action',queryParams:"",pageNumber:1});
		        }else {
                	$.messager.alert('警告',data.message,'info');
                }
		    }
		});	
	}else{
		//修改
		$('#saveStoreInfo').form('submit', {
		    url:'editStoreInfo.action',
		    onSubmit: function(){
		    	return validateForm();
		    },
		    success:function(data){
		    	var data = eval('(' + data + ')'); 
		        if (data.success){
		        	$.messager.alert('提示',data.message,'info');
		        	$('#storeID').val("");
		            $('#storeCode').val("");
		            $('#storeName').val("");
		            $('#storeAddr').val("");
		         	$('#saveStoreInfo').form('reset');
		         	$('#win').dialog('close');
		         	$('#tt').datagrid({url:'StoreInfoList.action',queryParams:"",pageNumber:1});
		        }else {
                	$.messager.alert('警告',data.message,'info');
                }
		    }
		});
	}
	
}
var param;
function searchStoreInfoList() {
	var gridparam = {
			'stroreInfo.store_id':$('#s_storeID').val(),
			'storeInfo.store_code':$('#s_storeCode').val(), 
			'storeInfo.store_name' : $('#s_storeName').val(),
			'storeInfo.stroe_address': $('#s_storeAddr').val()
	};
	param = gridparam;
	$('#tt').datagrid({
		url : 'searchStoreInfoList.action',
		queryParams : gridparam,
		pageNumber:1
	});
	$('#searchStoreInfo').form('reset');
	$('#searchDialog').dialog('close');
}
function newinfo(){
	$('#win').dialog('open').dialog('setTitle','新增门店');
}
function closeWin(){
    $('#storeCode').val("");
    $('#storeName').val("");
    $('#storeAddr').val("");
	$('#saveStoreInfo').form('reset');
	$('#win').dialog('close');  
}

function closeEditWin(){
	$('#saveStoreInfo').form('reset');
	$('#win').dialog('close');
	
}
function openSerchWin(){
	$('#searchDialog').dialog('open');
}
function closeSearchWin(){
	$('#searchStoreInfo').form('reset');
	$('#searchDialog').dialog('close');
}
</script>
<table id="tt">
</table>
<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true"onclick="newinfo()">新增</a>
	 <a href="#" class="easyui-linkbutton"		iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
	  <a	href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeStoreInfo()">删除</a>

</div>
<div id="win" class="easyui-dialog"	style="padding: 5px; width: 600px; height: 500px;"
 title="编辑"	iconCls="icon-eidt" closed="true" modal="true" buttons="#dlg-buttons">
	<form id="saveStoreInfo" method="post">
		<table>
			<tr>
				<td>门店编号</td>
				<td>
				<input type="hidden" id="storeID" name="storeInfo.store_id" />
					<input type="text" id="storeCode" name="storeInfo.store_code" /></td>
			</tr>
			<tr>
				<td>门店名称</td>
				<td><input type="text" id="storeName"
					name="storeInfo.store_name" /></td>
			</tr>
			<tr>
				<td>门店地址</td>
				<td><input type="text" id="storeAddr"
					name="storeInfo.store_address" /></td>
			</tr>
		</table>
	</form>

</div>
<div id="dlg-buttons">
	<table cellpadding="0" cellspacing="0" style="width: 100%">
		<tr>
			<td style="text-align: right"><a href="#"
				class="easyui-linkbutton" iconCls="icon-save"
				onclick="saveStoreInfo()">保存</a> <a href="#"
				class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWin()">关闭</a>
			</td>
		</tr>
	</table>
</div>
<div id="searchDialog" class="easyui-dialog"
	style="padding: 5px; width: 600px; height: 500px;" title="查询"
	iconCls="icon-eidt" closed="true" modal="true" buttons="#dlg-buttons">
	<form id="searchStoreInfo" method="post">
		<table>
			<tr>
				<td>门店编号</td>
				<td>
<!-- 				<input type="hidden" id="s_storeID" name="storeInfo.store_id" /> -->
					<input type="text" id="s_storeCode" name="storeInfo.store_code" /></td>
			</tr>
			<tr>
				<td>门店名称</td>
				<td><input type="text" id="s_storeName"
					name="storeInfo.store_name" /></td>
			</tr>
			<tr>
				<td>门店地址</td>
				<td><input type="text" id="s_storeAddr"
					name="storeInfo.store_address" /></td>
			</tr>
		</table>
	</form>

</div>
<div id="dlg-buttons">
	<table cellpadding="0" cellspacing="0" style="width: 100%">
		<tr>
			<td style="text-align: right"><a href="#"
				class="easyui-linkbutton" iconCls="icon-save"
				onclick="searchStoreInfoList()">查询</a> <a href="#"
				class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a>
			</td>
		</tr>
	</table>
</div>
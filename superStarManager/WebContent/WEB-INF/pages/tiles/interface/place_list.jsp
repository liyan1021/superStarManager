<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">   
#star_head {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}  
</style>  
<script type="text/javascript">

$(function(){
		loadPlace();
});
function loadPlace(){
	    $.ajax({
				url : 'placeInterfaceList.action',
				type : 'post',
				success : function(data) {
					var data = eval('(' + data + ')'); // change the JSON string to javascript object	
					$('#interfaceId').val(data.interface_id);
					$('#interfaceName').val(data.interface_name);
					$("#star_head").attr("src",server_path+data.file_path+"?tempid="+Math.random());
					if(data.interface_state=="0"){
					$("#interfaceState").val("未发布");	
					}
					else if(data.interface_state=="1"){
						$("#interfaceState").val("已发布");
					}
					else{
						$("#interfaceState").val("未发布");
					}
				}
			});
}
function validateForm(){
	var result = $("#saveInterface").validate({  
    	rules: {
    		"itf.interface_name":{
    			maxlength: 20
    		},
    		},
		messages: {
			"itf.interface_name":{
    			maxlength: "界面主题长度不能超过20"
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
	}	
	return result ; 
}
function saveInterface(){
	if(!$('#interfaceId').val()){
		//新增
		$('#saveInterface').form('submit', {
		    url:'savePlaceInterface.action',
		    onSubmit: function(){
		       //校验 return flase ;
		    	return validateForm();
			},
				success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$('#saveInterface').form('reset');
					loadPlace();
					$.messager.alert('提示', data.message, 'info');
				}else {
                	$.messager.alert('警告',data.message,'info');
                }
			}
		});
		} else {
			//修改
			$('#saveInterface').form('submit', {
				url : 'editPlaceInterface.action',
				onSubmit : function() {
					return validateForm();
				},
				success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {	
					$('#saveInterface').form('reset');
					 loadPlace();
					$.messager.alert('提示', data.message, 'info');
				}else {
                	$.messager.alert('警告',data.message,'info');
                }
			}
			});
		}

	}
	//发布界面
	function pushInterface(){
		 var state=$("#interfaceState").val();
		 if(state=="已发布"){
			 $.messager.alert("提示",'已发布的不能再次发布','info');
		 }
		 else{
              ids=$('#interfaceId').val();
				$.ajax({
					url : 'pushPlaceInterface.action',	
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							$("#interfaceState").val("已发布");					
							$.messager.alert('提示', "成功发布",'info');
						} else {
							$.messager.alert('警告', "发布失败!", 'info');
						}
				}
			});
				}
		}
</script>
<div class="easyui-panel" title="场所介绍界面设置">
	<div style="height:400px">
		<form id="saveInterface" method="post" enctype="multipart/form-data">
			<table>
				<tr>
					<td>界面主题</td>
					<td>
						<input type="hidden" id="interfaceId" name="itf.interface_id"  />
		        		<input type="text"  id="interfaceName" name="itf.interface_name" />
		        	</td>
		        	<td>状态</td>
					<td>
		        		<input type="text"  id="interfaceState" name="itf.state" disabled="disabled"/>
		        	</td>
				</tr>
				<tr>
					<td>
						界面图片 <font color="red">*</font>
					</td>
					<td colspan="3">
						<s:file id="uploadFile" name="image" onchange="previewImage(this,'star_head','preview',300,300)"></s:file>
								<!-- 默认图片显示 -->
						<div id="preview">  
							<img id="star_head" src="" style="width: 300px;height: 300px"></img>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div>
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:center">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveInterface()">保存</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="pushInterface()">发布</a>
				</td>
			</tr>
		</table>
	</div>
</div>
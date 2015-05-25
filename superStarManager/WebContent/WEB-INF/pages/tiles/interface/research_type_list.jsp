<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'调查问题管理',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    url:'researchTypeList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		columns:[[
			{field:'ck',checkbox:true},
			{field:'opt',title:'',align:'center',
				formatter:function(value,row,index){
					return '<a href="#" class="editcls" onclick="editResearchType('+index+')"></a>';
				}
			},
			{field:'question_id',title:'ID',width:60,hidden:true},
			{field:'question_name',title:'调查问题名称',width:150},
			{field:'question_type',title:'问题类别',width:150,
				formatter: function(value,row,index){
					if (row.question_type==1){
						return "个人信息";
					} else if(row.question_type==2) {
						return "调查问题";
					}
					return row.star_type ;
				}	
			},
			{field:'question_score',title:'问题分数',width:150},
			{field:'option_a',title:'选项A',width:150},
			{field:'option_a_score',title:'选项A分数',width:150},
			{field:'option_b',title:'选项B',width:150},
			{field:'option_b_score',title:'选项B分数',width:150},
			{field:'option_c',title:'选项C',width:150},
			{field:'option_c_score',title:'选项C分数',width:150},
			{field:'option_d',title:'选项D',width:150},
			{field:'option_d_score',title:'选项D分数',width:150},
			{field:'option_e',title:'选项E',width:150},
			{field:'option_e_score',title:'选项E分数',width:150},
			{field:'option_f',title:'选项F',width:150},
			{field:'option_f_score',title:'选项F分数',width:150},
			{field:'create_time',title:'创建时间',width:150}
		]],
		onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
	    }
	});
});
function removeResearchType(){
	var rows = $("#tt").datagrid("getSelections");
    var ids = "";
       if(rows.length == 0){
    	   $.messager.alert('警告',"请选择要删除的记录!",'info');
           return;
       }else{
    	   $.messager.confirm('删除记录','确定要删除选中的记录吗?', function(result){
            if (result){
                for ( var i = 0; i < rows.length; i++) {
                    ids += rows[i].question_id+",";
                }
	            $.ajax({
	                url : 'removeResearchType.action',
	                data:{
	                      'ids':ids
	                  },
	                type : 'post',
	                success : function(data) {
	                	var data = eval('(' + data + ')');  // change the JSON string to javascript object
	    		        if (data.success){
	                        var pers = rows.length;
	                        $('#tt').datagrid({url:'researchTypeList.action',queryParams:"",pageNumber:1});
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
function editResearchType(index){
	$('#tt').datagrid('selectRow',index);// 关键在这里  
    var row = $('#tt').datagrid('getSelected');  
   	if (row){  
		$('#win').dialog('open').dialog('setTitle','编辑');
        $('#question_id').val(row.question_id);
        $('#question_name').val(row.question_name);
        $('#question_type').val(row.question_type);
        changeQuestion();
        $('#question_score').numberspinner('setValue',row.question_score);
        $('#option_a').val(row.option_a);
        $('#option_a_score').numberspinner('setValue',row.option_a_score);
        $('#option_b').val(row.option_b);
        $('#option_b_score').numberspinner('setValue',row.option_b_score);
        $('#option_c').val(row.option_c);
        $('#option_c_score').numberspinner('setValue',row.option_c_score);
        $('#option_d').val(row.option_d);
        $('#option_d_score').numberspinner('setValue',row.option_d_score);
        $('#option_e').val(row.option_e);
        $('#option_e_score').numberspinner('setValue',row.option_e_score);
        $('#option_f').val(row.option_f);
        $('#option_f_score').numberspinner('setValue',row.option_f_score);
    }
}
function validateForm(){
	var result = $("#saveResearchType").validate({  
    	rules: {
        	"researchType.question_name": {
				required: true,
				maxlength: 20
				
			},
			"researchType.question_type": {
				required: true
			},
			"researchType.option_a": {
				maxlength: 20
			},
			"researchType.option_b": {
				maxlength: 20
			},
			"researchType.option_c": {
				maxlength: 20
			},
			"researchType.option_d": {
				maxlength: 20
			},
			"researchType.option_e": {
				maxlength: 20
			},
			"researchType.option_f": {
				maxlength: 20
			}
			
		},
		messages: {
			"researchType.question_name": {
				required: "问题名称不能为空",
				maxlength: "问题名称长度不能超过20"
			},
			"researchType.question_type": {
				required: "请选择问题类别"
			},
			"researchType.option_a": {
				maxlength: "选项A长度不能超过20"
			},
			"researchType.option_b": {
				maxlength: "选项B长度不能超过20"
			},
			"researchType.option_c": {
				maxlength: "选项C长度不能超过20"
			},
			"researchType.option_d": {
				maxlength: "选项D长度不能超过20"
			},
			"researchType.option_e": {
				maxlength: "选项E长度不能超过20"
			},
			"researchType.option_f": {
				maxlength: "选项F长度不能超过20"
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
	if(result&&($("#question_type").val()=='2')){
		if($('#question_score').numberspinner('getValue')==""){
			$.messager.alert('提示','请选择问题分数', 'info');
	    	return false ;
		}
		var a_score = Number($('#option_a_score').numberspinner('getValue'));
	   	var b_score = Number($('#option_b_score').numberspinner('getValue'));
	    var c_socre = Number($('#option_c_score').numberspinner('getValue'));
	    var d_score = Number($('#option_d_score').numberspinner('getValue'));
	    var e_score = Number($('#option_e_score').numberspinner('getValue'));
	    var f_score = Number($('#option_f_score').numberspinner('getValue'));
	    var question_score = Number($('#question_score').numberspinner('getValue'));
	    var score = a_score + b_score +c_socre + d_score +e_score+f_score ;
	    if(question_score!=score){
	    	$.messager.alert('提示','选项分数不等与问题分数', 'info');
	    	return false ;
	    }
	}
	
    return result ; 
}
/* function validateForm(){
	
	var a_score = $('#option_a_score').numberspinner('getValue');
   	var b_score = $('#option_b_score').numberspinner('getValue');
    var c_socre = $('#option_c_score').numberspinner('getValue');
    var d_score = $('#option_d_score').numberspinner('getValue');
    var e_score = $('#option_e_score').numberspinner('getValue');
    var f_score = $('#option_f_score').numberspinner('getValue');
    var question_score = $('#question_score').numberspinner('getValue');
  
   if(question_score !=""){
	    if(question_score!=score){
	    	$.messager.alert('提示','选项分数不等与问题分数', 'info');
	    	return false ;
	    }
    } 
} */
function saveResearchType(){
	if(!$('#question_id').val()){
		//新增
		$('#saveResearchType').form('submit', {
		    url:'saveResearchType.action',
		    onSubmit: function(){
		    	return validateForm();
		    },
		    success:function(data){
		    	var data = eval('(' + data + ')');  // change the JSON string to javascript object
		        if (data.success){
		        	$.messager.alert('提示',data.message,'info');
		        	$('#saveResearchType').form('clear');
		        	$('#question_id').val("");
		            $('#win').dialog('close');
		            $('#tt').datagrid({url:'researchTypeList.action',queryParams:"",pageNumber:1});
		        }else {
                	$.messager.alert('警告',data.message,'info');
                }
		    }
		});	
	}else{
		//修改
		$('#saveResearchType').form('submit', {
		    url:'editResearchType.action',
		    onSubmit: function(){
		    	return validateForm();
		    },
		    success:function(data){
		    	var data = eval('(' + data + ')'); 
		        if (data.success){
		        	$.messager.alert('提示',data.message,'info');
		        	$('#saveResearchType').form('clear');
		        	$('#question_id').val("");
		            $('#win').dialog('close');
		            $('#tt').datagrid({url:'researchTypeList.action',queryParams:"",pageNumber:1});
		        }else {
                	$.messager.alert('警告',data.message,'info');
                }
		    }
		});
	}
	
}
function changeQuestion(){
	if($("#question_type").val()=='1'){
		$('.spinner').hide();
		$('label').hide();
	}else{
		$('.spinner').show();
		$('label').show();
	}
}
function newResearchType(){
	$('#win').dialog('open').dialog('setTitle','新增字典');
}
function closeWin(){
	$('.spinner').show();
	$('label').show();
	$('#question_id').val("");
	$('#saveResearchType').form('reset');
	$('#win').dialog('close');
}
</script>
<table id="tt">
</table>
<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newResearchType()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeResearchType()">删除</a>
	
</div>
<div id="win" class="easyui-dialog" style="padding:5px;width:700px;height:500px;"
			title="编辑" iconCls="icon-eidt"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="saveResearchType" method="post">
	    <table>
	    		<tr>
	    			<td><input type="hidden" id="question_id" name="researchType.question_id" /></td>
	    		</tr>
				<tr>
					<td>问题名称 <font color="red">*</font></td>
					<td><input type="text" id="question_name" name="researchType.question_name"  /></td>
				</tr>
				<tr>
					<td>
						问题类别 <font color="red">*</font>
					</td>
					<td>
						<!-- <input type="text" id="question_type" name="researchType.question_type"  /> -->
						<select id="question_type" name="researchType.question_type" onchange="changeQuestion()">
							<option value="">--请选择--</option>
							<option value="1">个人信息</option>
							<option value="2">调查问题</option>
						</select>
					</td>
					<td>
						<label>问题分数</label>
					</td>
					<td><input type="text" id="question_score" name="researchType.question_score" class="easyui-numberspinner" 
								data-options="min:0"
					/></td>
				</tr>
				<tr>
					<td>
						选项A
					</td>
					<td>
					<input type="text" id="option_a" name="researchType.option_a"  />
					</td>
					<td>
						<label>选项A分数</label>
					</td>
					<td>
					<input type="text" id="option_a_score" name="researchType.option_a_score" class="easyui-numberspinner" 
						   data-options="min:0"
					/>
					</td>
				</tr>
				<tr>
					<td>
						选项B
					</td>
					<td>
						<input type="text" id="option_b" name="researchType.option_b"  />
					</td>
					<td>
						<label>选项B分数</label>
					</td>
					<td>
						<input type="text" id="option_b_score" name="researchType.option_b_score" class="easyui-numberspinner"  
							   data-options="min:0"
						/>
					</td>
				</tr>
				<tr>
					<td>
						选项C
					</td>
					<td>
						<input type="text" id="option_c" name="researchType.option_c"  />
					</td>
					<td>
						<label>选项C分数</label>
					</td>
					<td>
						<input type="text" id="option_c_score" name="researchType.option_c_score" class="easyui-numberspinner"
						       data-options="min:0"
						 />
					</td>
				</tr>
				<tr>
					<td>
						选项D
					</td>
					<td>
						<input type="text" id="option_d" name="researchType.option_d"  />
					</td>
					<td>
						<label>选项D分数</label>
					</td>
					<td>
						<input type="text" id="option_d_score" name="researchType.option_d_score" class="easyui-numberspinner" 
						       data-options="min:0"						
						/>
					</td>
				</tr>
				<tr>
					<td>
						选项E
					</td>
					<td>
						<input type="text" id="option_e" name="researchType.option_e"  />
					</td>
					<td>
						<label>选项E分数</label>
					</td>
					<td>
						<input type="text" id="option_e_score" name="researchType.option_e_score" class="easyui-numberspinner"
						       data-options="min:0"
						 />
					</td>
				</tr>
				<tr>
					<td>
						选项F
					</td>
					<td>
						<input type="text" id="option_f" name="researchType.option_f"  />
					</td>
					<td>
						<label>选项F分数</label>
					</td>
					<td>
						<input type="text" id="option_f_score" name="researchType.option_f_score" class="easyui-numberspinner"
						       data-options="min:0"
						 />
					</td>
				</tr>
			</table>
	</form>

</div>
<div id="dlg-buttons" >
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveResearchType()">保存</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>

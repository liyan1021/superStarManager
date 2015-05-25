<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'意见调查',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    url:'adviceList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		columns:[[ 
			{field:'advice_id',title:'ID',width:60,hidden:true},
			{field:'type_id',title:'调查类型',width:150},
			{field:'advice_score',title:'建议评分',width:150},
			{field:'advice_content',title:'建议内容',width:150},
			{field:'member_id',title:'会员',width:150},
			{field:'room',title:'包房',width:150},
			{field:'store',title:'门店',width:150},
			{field:'advice_time',title:'建议时间',width:150}
		]],
		onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
	    }
	});
});
</script>
<table id="tt">
</table>
<div id="toolbar">
<!-- 	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newResearchType()">意见调查统计</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeResearchType()">删除</a> -->
	
</div>

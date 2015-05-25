<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">   
#star_head {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}  
</style>  
<script type="text/javascript">
	$(function() {
		$('#tt').datagrid({
			title : '影视管理',
			iconCls : 'icon-edit',
			rownumbers : true,
			fit:true ,
			pagination : true,
			fitColumns : false,
			url : 'movieInfoList.action',
			method : 'post',
			toolbar : "#toolbar",
			columns : [ [
			{field:'ck',checkbox:true},
			{field:'opt',title:'',align:'center',
				formatter:function(value,row,index){
					return '<a href="#" class="editcls" onclick="editMovieInfo('+index+')"></a>';
				}
			},
			{
				field : 'movie_name',
				title : '影视名称',
				width : 200
			},
			{
				field : 'movie_info',
				title : '影视信息',
				width : 100
			},
			{
				field : 'row_num',
				title : '显示行数',
				width : 100
			},
			{
				field : 'column_num',
				title : '显示列数',
				width : 100
			},
			{
				field : 'sort',
				title : '顺序',
				width : 100
			},
			{
				field : 'file_path',
				title : '文件路径',
				width : 100
			},
			{
				field : 'state',
				title : '状态',
				width : 100,
				formatter: function(value,row,index){
					if (row.state == 1){
						return "已发布" ;
					}else if(row.state == 0){
						return "未发布" ;
					}
						return row.is_activity ;
				}
			}
		 ] ],
		 onLoadSuccess:function(data){  
		        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
		    }
		});
		//调用该方法 绑定onchange 事件替换显示本地图片
		//$("#uploadFile").uploadPreview({ width: 100, height: 100, imgPreview: "#imgDiv", imgType: ["bmp", "gif", "png", "jpg"] }); 
	});
	function searchMovieInfo() {
		var gridparam = {
			"movieInfo.movie_name" : $("#s_movie_name").val(),
			"movieInfo.movie_info" : $("#s_movie_info").val()
			
		};
		$('#tt').datagrid({
			url : 'searchMovieInfo.action',
			queryParams : gridparam,
			pageNumber:1
		});

		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	function removeMovieInfo() {
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要删除的记录!", 'info');
			return;
		} else {
			$.messager.confirm('删除记录', '确定要删除选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].movie_id + ",";
					}
				
				$.ajax({
					url : 'removeMovieInfo.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'movieInfoList.action',queryParams:"",pageNumber:1});
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
	function newMovieInfo(){
		$('#win').dialog('open').dialog('setTitle','新增界面');
	}
	function editMovieInfo(index){
		$('#tt').datagrid('selectRow',index);// 关键在这里  
	    var row = $('#tt').datagrid('getSelected');  
	   	if (row){  
			$('#win').dialog('open').dialog('setTitle','编辑界面');
	        $('#movie_id').val(row.movie_id);
	        $('#movie_name').val(row.movie_name);
	        $('#movie_info').val(row.movie_info);
	       /*  $('#row_num').val(row.row_num);
	        $('#column_num').val(row.column_num); */
	        $('#sort').val(row.sort);
	        $("#star_head").attr("src",server_path+row.file_path+"?tempid="+Math.random());
	    }
	}
	function validateForm(){
		var result = $("#saveMovieInfo").validate({  
	    	rules: {
	        	"movieInfo.movie_name": {
					required: true,
					maxlength: 20
				},
				"movieInfo.sort":{
					required:true,
					maxlength: 20,
					digits:true
				},
				"movieInfo.movie_info":{
					maxlength: 20
				}
			},
			messages: {
				"movieInfo.movie_name": {
					required: "影视名称不能为空",
					maxlength: "影视名称长度不能超过20"
				},
				"movieInfo.sort":{
					required: "显示顺序不能为空",
					digits:"显示顺序只能为数字",
					maxlength: "显示顺序长度不能超过20"
				},
				"movieInfo.movie_info":{
					maxlength: "文字说明长度不能超过20"
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
			if($('#movie_id').val()!=""){
				var sign = false ;
				$.ajax({
					async: false,
					url:'checkMovieSort.action',
					type : 'post',
					data : {
						'movieInfo.movie_id':$('#movie_id').val(),
						'movieInfo.sort' : $('#sort').val()
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
	function saveMovieInfo(){
		if(!$('#movie_id').val()){
			$('#saveMovieInfo').form('submit', {
			    url:'saveMovieInfo.action',
			    onSubmit: function(){
			       //校验 return flase ;
			    	return validateForm();
				},
				success : function(data) {
					var data = eval('(' + data + ')'); // change the JSON string to javascript object
					if (data.success) {
						$.messager.alert('提示', data.message, 'info');
						$('#saveMovieInfo').form('reset');
						$("#star_head").attr("src","");
						$("#file_path").val("");
						var div = document.getElementById('preview');
						div.innerHTML = "<img id='star_head' src='' style='width: 200px;height: 200px'></img>";  
						$('#win').dialog('close');
						$('#tt').datagrid({url:'movieInfoList.action',queryParams:"",pageNumber:1});
					}else {
	                	$.messager.alert('警告',data.message,'info');
	                }
				}
			});
		} else {
			//修改
			$('#saveMovieInfo').form('submit', {
				url : 'editMovieInfo.action',
				onSubmit : function() {
					return validateForm();
				},
				success : function(data) {
					var data = eval('(' + data + ')'); // change the JSON string to javascript object
					if (data.success) {
						$.messager.alert('提示', data.message, 'info');
						$('#movie_id').val("");
						$("#star_head").attr("src","");
						$('#editMovieInfo').form('reset');
						$('#win').dialog('close');
						$('#tt').datagrid({url:'movieInfoList.action',queryParams:"",pageNumber:1});
					}else {
	                	$.messager.alert('警告',data.message,'info');
	                }
				}
			});
		}
	}
	function pushMovie(){
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
		  if(i=rows.length){
			$.messager.confirm('发布记录', '确定要发布选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].movie_id + ",";
					}
				$.ajax({
					url : 'pushMovie.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'movieInfoList.action',queryParams:"",pageNumber:1});
							$('#tt').datagrid('clearSelections');
							$.messager.alert('提示'.alert('edit', "成功发布" + pers + "条记录",
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
	function closeWin(){
		var div = document.getElementById('preview');
		div.innerHTML = "<img id='star_head' src='' style='width: 200px;height: 200px'></img>";  
		$('#saveMovieInfo').form('reset');
		$('#win').dialog('close');
	}
	function openSerchWin() {
		$('#searchDialog').dialog('open');
	}
	function closeSearchWin() {
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
</script>
<table id="tt">
</table>
<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newMovieInfo()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a> 
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeMovieInfo()">删除</a>
	<a href="#"	class="easyui-linkbutton" iconCls="icon-print"  plain="true" onclick="pushMovie()">发布</a> 
	</div>
<div id="searchDialog" class="easyui-dialog"
	style="padding: 5px; width: 500px; height: 300px;" title="查询"
	iconCls="icon-ok" closed="true" modal="true" buttons="#search-buttons">
	<form id="searchForm" method="post">
		<table>
			<tr>
				<td>影视名称</td>
				<td><input type="text" id="s_movie_name"  /></td>
			</tr>
			<tr>
				<td>影视文字说明</td>
				<td><input type="text" id="s_movie_info"  /></td>
			</tr>
			
		</table>
		
	</form>
</div>
<div id="search-buttons">
	<table cellpadding="0" cellspacing="0" style="width: 100%">
		<tr>
			<td style="text-align: right"><a href="#"
				class="easyui-linkbutton" iconCls="icon-search"
				onclick="searchMovieInfo()">查询</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a></td>
		</tr>
	</table>
</div>
<div id="win" class="easyui-dialog" style="padding:5px;width:600px;height:500px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="saveMovieInfo" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td><input type="hidden" id="movie_id" name="movieInfo.movie_id"  /></td>
			</tr>
			<tr>
				<td>影视名称 <font color="red">*</font></td>
				<td><input type="text" id="movie_name" name="movieInfo.movie_name"  /></td>
			</tr>
<!-- 			<tr>
				<td>显示行数</td>
				<td><input type="text" id="row_num"  name="movieInfo.row_num" /></td>
				<td>显示列数</td>
				<td><input type="text" id="column_num" name="movieInfo.column_num" /></td>
			</tr> -->
			<tr>
				<td>显示顺序 <font color="red">*</font></td>
				<td><input type="text" id="sort" name="movieInfo.sort" onchange="checkNumber(this)" /></td>
			</tr>
			<tr>
				<td>影视文字说明</td>
				<td><input type="text" id="movie_info" name="movieInfo.movie_info"  /></td>
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
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveMovieInfo()">上传</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>

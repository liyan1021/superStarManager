<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">   
#star_head {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}  
</style>  
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'歌星管理',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    url:'singerList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
	   	sortName : "star_id",
	   	sortOrder: "asc",
		columns:[[
			{field:'ck',checkbox:true},
			{field:'opt',title:'',align:'center',
				formatter:function(value,row,index){
					return '<a href="#" class="editcls" onclick="editUser('+index+')"></a>';
				}
			},
			{
				field : 'star_id',
				title : '歌星ID',
				width : 60,
				hidden:true
			},
			{
				field : 'star_name',
				title : '歌星名字',
				width : 150,
				sortable: true
			},
			{
				field : 'other_name',
				title : '别名',
				width : 150,
				sortable: true
			},
			{
				field : 'spell_first_letter_abbreviation',
				title : '拼音',
				width : 150,
				sortable: true
			},
			{
				field : 'star_type',
				title : '歌星类别',
				width : 60,
				formatter: function(value,row,index){
					if (row.star_type==1){
						return "男";
					} else if(row.star_type==2) {
						return "女";
					}else if(row.star_type==3) {
						return "组合";
					}else if(row.star_type==4) {
						return "群星";
					}
					return row.star_type ;
				}
			},
			{
				field : 'area',
				title : '歌星归属区域',
				width : 150,
				formatter: function(value,row,index){
					if (row.area==1){
						return "大陆";
					} else if(row.area==2) {
						return "港台";
					}else if(row.area==3) {
						return "欧美";
					}else if(row.area==4) {
						return "日韩";
					}
					return row.area ;
				}
			}
			
	] ],
	onLoadSuccess : function(data) {
		$('.editcls').linkbutton({
			plain : true,
			iconCls : 'icon-edit'
		});
	}
	});
	//调用该方法 绑定onchange 事件替换显示本地图片
	//$("#uploadFile").uploadPreview({ width: 100, height: 100, imgPreview: "#imgDiv", imgType: ["bmp", "gif", "png", "jpg"] }); 
});
	function newSinger() {
		$('#win').dialog('open').dialog('setTitle', '新增歌星');
	}
	function editUser(index) {
		$('#tt').datagrid('selectRow', index);// 关键在这里  
		var row = $('#tt').datagrid('getSelected');
		if (row) {
			$('#win').dialog('open').dialog('setTitle', '编辑歌星');
			$('#star_id').val(row.star_id);
			$('#star_name').val(row.star_name);
			$('#other_name').val(row.other_name);
			$('#star_type').combobox('setValue',row.star_type);
			$('#area').combobox('setValue',row.area);
			$('#spell').val(row.spell_first_letter_abbreviation);
			$("#star_head").attr("src",server_path+row.star_head+"?tempid="+Math.random());
		}
	}
	function validateForm(){
		var result = $("#saveSinger").validate({  
	    	rules: {
	        	"singer.star_name": {
					required: true,
					maxlength: 64
					
				},
				"singer.other_name": {
					
					maxlength: 20
				},
				"singer.spell_first_letter_abbreviation":{
					chars:true
				}
			},
			messages: {
				"singer.star_name": {
					required: "歌星名称不能为空",
					maxlength: "歌星名称长度不能超过64"
				},
				"singer.other_name": {
					maxlength: "别名长度不能超过20"
					
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
			if($('#star_type').combobox('getValue')==""){
				 $.messager.alert('提示','请选择歌星类别', 'info');
				 return false ; 
			}
			if($('#area').combobox('getValue')==""){
				 $.messager.alert('提示','请选择歌星区域', 'info');
				 return false ; 
			}
		var sign = false ;
		$.ajax({
			async: false,
			url:'checkSingerName.action',
			type : 'post',
			data : {
				'singer.star_id':$('#star_id').val(),
				'singer.star_name' : $('#star_name').val()
			},
			success : function(data) {
				var data = eval('(' + data + ')');
				if (data) {
					 $.messager.alert('提示','歌星已经存在', 'info');
				} else {
					sign =  true ;
				}
			}
		});
		return sign ; 
		}
		return result ; 
	}
	function saveSinger() {
		
		if (!$('#star_id').val()) {
			//新增
			$('#saveSinger').form('submit', {
				url : 'saveSinger.action',
				onSubmit : function() {
					//校验 return flase ;
					return validateForm();
				},
				success : function(data) {
					var data = eval('(' + data + ')'); 
					if (data.success) {
						$.messager.alert('提示', data.message, 'info');
						$('#star_id').val("");
						var div = document.getElementById('preview');
						div.innerHTML = "<img id='star_head' src='' style='width: 200px;height: 200px'></img>";  
						$('#saveSinger').form('reset');
						$('#win').dialog('close');
						$('#tt').datagrid({url:'singerList.action',queryParams:"",pageNumber:1});
					}else {
	                	$.messager.alert('警告',data.message,'info');
	                }
				}
			});
		} else {
			//修改
			$('#saveSinger').form('submit', {
				url : 'editSinger.action',
				onSubmit : function() {
					//校验 return flase ;
					return validateForm();
				},
				success : function(data) {
					
					var data = eval('(' + data + ')');
					if (data.success) {
						$.messager.alert('提示', data.message, 'info');
						$('#star_id').val("");
						var div = document.getElementById('preview');
						div.innerHTML = "<img id='star_head' src='' style='width: 200px;height: 200px'></img>";  
						$('#saveSinger').form('reset');
						$('#win').dialog('close');
						$('#tt').datagrid({url:'singerList.action',queryParams:"",pageNumber:1});
					}else {
	                	$.messager.alert('警告',data.message,'info');
	                }
				}
			});
		} 

	}
	function removeSinger() {
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要删除的记录!", 'info');
			return;
		} else {
			$.messager.confirm('删除记录', '确定要删除选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].star_id + ",";
					}
				
				$.ajax({
					url : 'removeSinger.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'singerList.action',queryParams:"",pageNumber:1});
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
	var param ;
	function searchSinger() {
		var gridparam = { 
				"singer.star_name" : $('#s_starName').val(),
				"singer.other_name" : $('#s_otherName').val(),
				"singer.star_type" : $('#s_starType').combobox('getValue'),
				"singer.area" : $('#s_area').combobox('getValue'),
				"singer.spell_first_letter_abbreviation" : $('#s_spell').val(),
		} ;
		param = gridparam ;
		$('#tt').datagrid({url:'searchSingerList.action',queryParams:gridparam,pageNumber:1});
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	function post(URL, PARAMS) {
		var temp = document.createElement("form");
		temp.action = URL;
		temp.method = "post";
		temp.style.display = "none";
		for ( var x in PARAMS) {
			var opt = document.createElement("input");
			opt.name = x;
			opt.value = PARAMS[x];
			temp.appendChild(opt);
		}
		document.body.appendChild(temp);
		temp.submit();
		return temp;
	}
	function exportSinger(){
		if($('#tt').datagrid('getRows').length == 0 ){
			$.messager.alert('提示','查询为空，无法导出', 'info');		
		}else{
			post('exportSinger.action',param);
		}
	}
	function openSerchWin() {
		$('#searchDialog').dialog('open');
	}
	function closeWin() {
		var div = document.getElementById('preview');
		div.innerHTML = "<img id='star_head' src='' style='width: 200px;height: 200px'></img>";  
		$('#star_id').val("");
		$('#saveSinger').form('reset');
		$('#win').dialog('close');
	}
	function closeSearchWin() {
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	function openImportWin(){
		$('#importWin').dialog('open');
	}
	function importSinger(){
		$('#importMusic').form('submit', {
		    url:'importSinger.action',
		    onSubmit: function(){
		       //校验 return flase ;
		    },
		    success:function(data){
		    	var data = eval('(' + data + ')');  // change the JSON string to javascript object
		        if (data.success){
		        	$.messager.alert('提示',data.message,'info');
		        	$('#importSinger').form('clear');
		        	$('#importWin').dialog('close');
		            $('#win').dialog('close');
		            $('#tt').datagrid({url:'singerList.action',queryParams:"",pageNumber:1});
		        }else {
                	$.messager.alert('警告',data.message,'info');
                }
		    }
		});
	}
	function closeImportWin(){
		$('#importSinger').form('clear');
		$('#importWin').dialog('close');
	}
</script>
<table id="tt">
</table>
<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newSinger()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeSinger()">删除</a>
	<a href="#"	class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="exportSinger()">导出</a>
	<a href="#"	class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="openImportWin()">导入</a>
</div>
<div id="win" class="easyui-dialog" style="padding:5px;width:600px;height:400px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="saveSinger" method="post" enctype="multipart/form-data">
	    <table>
	    		<tr>
	    		<td><input type="hidden"  id="star_id" name="singer.star_id"/></td>
	    		</tr>
	    		<tr>
					<td>歌星头像</td>
					<td>
						<s:file id="uploadFile" name="image" onchange="previewImage(this,'star_head','preview')"></s:file>
						<!-- 默认图片显示 -->
						<div id="preview">  
							<img id="star_head" src="" style="width: 200px;height: 200px"></img>
						</div>
						
					</td>
				</tr> 
				<tr>
					<td>歌星名字<font color="red">*</font></td>
					<td><input type="text" id="star_name" name="singer.star_name"/></td>
					<td>别名</td>
					<td><input type="text" id="other_name" name="singer.other_name" /></td>
				</tr>
				<tr>
					<td>歌星类别<font color="red">*</font></td>
					<td>
					<select id="star_type" name="singer.star_type" style="width:150px;" class="easyui-combobox"
							data-options="
									url:'getDictList.action?type=singer_type',
									method:'post',
									editable:false,
									valueField:'dict_code', 
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
					</td>
					<td>歌星归属地<font color="red">*</font></td>
					<td>
						<input class="easyui-combobox" id="area"  name="singer.area"
										data-options="
												url:'getDictList.action?type=area',
												method:'post',
												editable:false,
												valueField:'dict_code', 
												textField:'dict_value',
												panelHeight:'auto'
										" />
					</td>
				</tr>
				<tr>
					<td>拼音字头</td>
					<td><input type="text" id="spell" name="singer.spell_first_letter_abbreviation" /> </td>
				</tr>
			</table>
	</form>

</div>
<div id="dlg-buttons" >
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveSinger()">保存</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>

<div id="searchDialog" class="easyui-dialog" style="padding:5px;width:600px;height:400px;"
			title="查询" iconCls="icon-ok"
			closed="true" 
			modal="true"
			buttons="#search-buttons">
		<form id="searchForm" method="post">
			<table>
				<tr>
					<td>歌星名字</td>
					<td><input type="text" id="s_starName"/></td>
					<td>别名</td>
					<td><input type="text" id="s_otherName" /></td>
				</tr>
				
				<tr>
					<td>歌星类别</td>
					<td>
						<select class="easyui-combobox" id="s_starType" style="width:150px;"
								data-options="
										url:'getDictList.action?type=singer_type',
										method:'post',
										editable:false,
										valueField:'dict_code',
										textField:'dict_value',
										panelHeight:'auto' ">
					</select>
					</td>
					<td>歌星归属地</td>
					<td>
						<select class="easyui-combobox" id="s_area" style="width:150px;"
								data-options="
										url:'getDictList.action?type=area',
										method:'post',
										editable:false,
										valueField:'dict_code',
										textField:'dict_value',
										panelHeight:'auto'" >
						</select>
					</td>
				</tr>
				<tr>
					<td>拼音字头</td>
					<td><input type="text" id="s_spell"/> </td>
				</tr>
				
			</table>
		</form>
</div>
<div id="search-buttons">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchSinger()">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>


<div id="importWin" class="easyui-dialog" style="padding:5px;width:600px;height:400px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#import-buttons">
		<form id="importMusic" method="post" enctype="multipart/form-data">
			<s:file id="importFile" name="importFile"></s:file>
		</form>
</div>
<div id="import-buttons" >
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="importSinger()">导入</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeImportWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
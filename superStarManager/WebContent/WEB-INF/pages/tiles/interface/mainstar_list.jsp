<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'主打歌星',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    url:'mainStarList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		columns:[[
			{field:'ck',checkbox:true},
			
			{
				field : 'star_name',
				title : '歌星名字',
				width : 150
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
					}
					else if(row.star_type==3) {
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
			},
			{
				field : 'introduce_time',
				title : '推荐日期',
				width : 150
			},
			{
				field : 'introduce_sort', 
				title : '推荐度',
				width : 150
			},
			/* {field:'introduce_sort',title:'推荐度',width:150}, */
			{
				field : 'state',
				title : '发布状态',
				width : 150,
				formatter: function(value,row,index){
					if (row.state == 1){
						return "已发布" ;
					}else if(row.state == 0){
						return "未发布" ;
					}
						return row.state ;
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
});
	function newSinger() {
		$('#win').dialog('open').dialog('setTitle', '新增主打歌星');
	}
	function selectSinger(){
		$('#singerGrid').datagrid({
			rownumbers:true ,
			height : 'auto',
			fit:true ,
			singleSelect:true, //多选
		    pagination : true,
		    url:'unMainStarList.action',
		    queryParams:"",
		    method: 'post',
			columns:[[
				{field:'ck',checkbox:true},
				{field : 'star_id',title : '歌星ID',width : 60,hidden:true},
				{field : 'star_name',title : '歌星名字',width : 60},
				{field : 'spell_first_letter_abbreviation',title : '歌星名的拼音字头',width : 60},
				{field : 'star_type',title : '性别',width : 60,
					formatter: function(value,row,index){
						if (row.star_type==1){
							return "男";
						} else if(row.star_type==2) {
							return "女";
						}else if(row.star_type==3) {
							return "组合";
						}
						return row.star_type ;
					}
				},
				{field : 'area',title : '歌星归属区域',width : 60,
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
					}}
			]]
		});
		$('#singerDialog').dialog('open').dialog('setTitle', '歌星列表');
	}
	function validateForm(){
		var result = $("#saveMainStar").validate({  
	    	rules: {
	        	"mainStar.singer_name": {
					required: true
				},
				"mainStar.introduce_sort": {
					required: true,
					digits:true
				}
		
			},
			messages: {
				"mainStar.singer_name": {
					required: "请选择歌星"
				},
				"mainStar.introduce_sort": {
					required: "推荐度不能为空",
					digits:"推荐度只能为数字"
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
			if($('#singer_id').val()!=""){
				var sign = false ;
				$.ajax({
					async: false,
					url:'checkMainStarSort.action',
					type : 'post',
					data : {
						'mainStar.introduce_id':$('#introduce_id').val(),
						'mainStar.introduce_sort' : $('#introduce_sort').val()
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
	function saveSinger() {
		//新增
		$('#saveMainStar').form('submit', {
		    url:'setMainStar.action',
		    onSubmit: function(){
		       //校验 return flase ;
		    	return validateForm();
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$.messager.alert('编辑', data.message, 'info');
					$('#tt').datagrid({url:'mainStarList.action',queryParams:"",pageNumber:1});
					$('#tt').datagrid('clearSelections');
					$('#saveMainStar').form('clear');
					$('#introduce_id').val("");
					$('#win').dialog('close');
				}else {
                	$.messager.alert('警告',data.message,'info');
                }
			}
		});
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
						ids += rows[i].introduce_id + ",";
					}
				
				$.ajax({
					url : 'removeMainStar.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'mainStarList.action',queryParams:"",pageNumber:1});
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
	function pushSinger(){
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要发布的记录!", 'info');
			return;
		} else {
			$.messager.confirm('发布记录', '确定要发布选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].introduce_id + ",";
					}
				
				$.ajax({
					url : 'pushMainStar.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'mainStarList.action',queryParams:"",pageNumber:1});
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
	function searchSinger() {
		var gridparam = { 
				"mainStar.singer.star_name" : $('#s_starName').val(),
				"mainStar.singer.star_type" : $('#s_singer_type').combobox('getValue'),
				"mainStar.singer.area" : $('#s_area').combobox('getValue'),
		} ;
		$('#tt').datagrid({url:'searchMainStarList.action',queryParams:gridparam,pageNumber:1});
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	function fitSinger(){
		var rows = $("#singerGrid").datagrid("getSelections");
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择记录!", 'info');
			return;
		} else {
			$('#singer_id').val(rows[0].star_id);
			$('#singer_name').val(rows[0].star_name);
			$('#singerDialog').dialog('close');
			$('#s_singer').form('reset');
		}
	}
	function searchStar(){
		var gridparam = {
				"singer.star_name" : $('#s_singer_name').val(),
				"singer.star_type" : $('#s_star_type').combobox('getValue')
		};
		$('#singerGrid').datagrid({
			url : 'unMainStarList.action',
			queryParams : gridparam
		});
	}
	function closeSingerDialog(){
		$('#singerDialog').dialog('close');
		$('#s_singer').form('reset');
	}
	function openSerchWin() {
		$('#searchDialog').dialog('open');
	}
	function closeWin() {
		$('#star_id').val("");
		$('#saveSinger').form('reset');
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
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newSinger()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeSinger()">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="pushSinger()">发布</a>
</div>
<div id="win" class="easyui-dialog" style="width:500px;height:200px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="saveMainStar" method="post">
		<table>
				<tr>
					<td>
						<input type="button" value="选择歌星" onclick="selectSinger()" />
					</td>
				</tr>
				<tr>
					<td>歌星名字 <font color="red">*</font></td>
					<td>
						<input type="hidden" id="introduce_id" name="mainStar.introduce_id"/>
						<input type="hidden" id="singer_id" name="mainStar.singer.star_id"/>
						<input type="text" id="singer_name" name="mainStar.singer_name"/>
					</td> 
				</tr>
				<tr>
					<td>推荐度 <font color="red">*</font></td>
					<td>
						<input type="text" id="introduce_sort" name="mainStar.introduce_sort" onchange="checkNumber(this)"/>
					</td>
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
					<td>歌星类别</td>
					<td>
						<select id="s_singer_type" style="width:150px;" class="easyui-combobox"
							data-options="
									url:'getDictListS.action?type=singer_type',
									method:'post',
									editable:false,
									valueField:'dict_code', 
									textField:'dict_value',
									panelHeight:'auto'" >
						</select>
					</td>
				</tr>
				
				<tr>
					<td>歌星归属地</td>
					<td>
						<input class="easyui-combobox" id="s_area"  name="singer.area"
										data-options="
												url:'getDictListS.action?type=area',
												method:'post',
												editable:false,
												valueField:'dict_code', 
												textField:'dict_value',
												panelHeight:'auto'
										" />
					</td> 
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

<div id="singerDialog" class="easyui-dialog" style="width:500px;height:400px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#singer-buttons"
			toolbar="#singer-toolbar" >
			
			<table id="singerGrid">
			</table>
</div>
<div id="singer-buttons">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="fitSinger()">确定</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeSingerDialog()">关闭</a>
				</td>
			</tr>
		</table>
</div>
<div id="singer-toolbar" style="padding:2px 0">
	<form id="s_singer" method="post">
        <table cellpadding="0" cellspacing="0" style="width:100%">
            <tr>
            	<td>
            		歌星名称<input type="text" id="s_singer_name"/>
            	</td>
            	<td>
            		歌星类别
            		<select id="s_star_type" style="width:150px;" class="easyui-combobox"
							data-options="
									url:'getDictListS.action?type=singer_type',
									method:'post',
									editable:false,
									valueField:'dict_code', 
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
            	</td>
            	<td>
            		<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchStar()">搜索</a>
            	</td>
            </tr>
        </table>
    </form>
</div>
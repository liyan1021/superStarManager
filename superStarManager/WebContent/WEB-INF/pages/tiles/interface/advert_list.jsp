<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<style type="text/css">   
#star_head {filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=image);}  
</style>  
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'广告发布',
		iconCls:'icon-edit',
		rownumbers:true ,
		fitcolumns :true,
	    pagination : true,
	    fit:true,
	    url:'advertList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		sortName : "advert_sort",
	   	sortOrder: "asc",
		columns:[[
			{field:'ck',checkbox:true},
			{field:'opt',title:'',align:'center',
				formatter:function(value,row,index){
					return '<a href="#" class="editcls" onclick="editAdvert('+index+')"></a>'+
					'<a href="#" class="addcls" onclick="editAdvertMusic('+index+')"></a>';
				}
			},
			{field:'advert_id',title:'广告 ID',width:60,hidden:true},
			{field:'advert_name',title:'广告名称',width:150},
			{field:'advert_content',title:'广告内容',width:150},
			{field:'online_time',title:'上线时间',width:150},
			{field:'offline_time',title:'下线时间',width:150},
			{field:'advert_time',title:'播放时长',width:150},
			{field:'advert_sort',title:'播放顺序',width:150,sortable: true},
			{field:'is_index_advert',title:'广告类别',width:150,
				formatter: function(value,row,index){
					if (row.is_index_advert == 1){
						return "首页" ;
					}else if(row.is_index_advert == 2){
						return "广告位" ;
					}
						return row.is_index_advert ;
				}},
			{field:'state',title:'发布状态',width:150,
				formatter: function(value,row,index){
					if (row.state == 1){
						return "已发布" ;
					}else if(row.state == 0){
						return "未发布" ;
					}
						return row.is_activity ;
				}
			}
			/* ,{field:'is_activity',title:'是否可用',width:150,
				formatter: function(value,row,index){
					if (row.is_activity == 1){
						return "是" ;
					}else if(row.is_activity == 0){
						return "否" ;
					}
						return row.is_activity ;
				}
			} */
			
		]],
		onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
	        $('.addcls').linkbutton({plain:true,iconCls:'icon-add'});  
	    },
	    view: detailview,
	    detailFormatter:function(index,row){    
	        return '<div style="padding:2px"><table id="ddv-' + index + '"></table></div>';    
	    },    
	    onExpandRow: function(index,row){    
	        $('#ddv-'+index).datagrid({    
	            url:'advertSongList.action?advert.advert_id='+row.advert_id,    
	            singleSelect:true,    
	            rownumbers:true,    
	            loadMsg:'',    
	            height:'auto',   
	            columns:[[    
	                {field:'song_name',title:'歌曲名称',width:150},    
	                {field:'singer_name',title:'歌手名称',width:150},
	                {field:'opt',title:'',align:'center',
	    				formatter:function(value,row,index){
	    					return '<a href="#" class="delcls" onclick="removeAdvertMusic(\''+row.advert_music_id+'\')"></a>';
	    				}
	    			}
	            ]],    
	            onResize:function(){    
	                $('#tt').datagrid('fixDetailRowHeight',index);    
	            },    
	            onLoadSuccess:function(){    
	                setTimeout(function(){    
	                    $('#tt').datagrid('fixDetailRowHeight',index);    
	                },0); 
        	        $('.delcls').linkbutton({plain:true,iconCls:'icon-remove'});  
	            }    
	        });    
	        $('#tt').datagrid('fixDetailRowHeight',index);    
	    }
	});
	
});
function newAdvert(){
	$('#win').dialog('open').dialog('setTitle','新增广告');
}
function editAdvert(index){
	$('#tt').datagrid('selectRow',index);// 关键在这里  
    var row = $('#tt').datagrid('getSelected');  
   	if (row){  
		$('#win').dialog('open').dialog('setTitle','编辑广告');
        $('#advertId').val(row.advert_id);
        $('#advertName').val(row.advert_name);
        $('#advertContent').val(row.advert_content);
        $('#onlineTime').datebox('setValue',row.online_time); 
        $('#offlineTime').datebox('setValue',row.offline_time);
        /* $('#isActivity').combobox('setValue',row.is_activity); */
        $('#advertTime').val(row.advert_time);
        $('#advertSort').val(row.advert_sort);
        $('#is_index_advert').combobox('setValue',row.is_index_advert);
        //添加 随机数参数，使浏览器不使用缓存图片
        $("#star_head").attr("src",server_path+row.file_path+"?tempid="+Math.random());
    }
}
function editAdvertMusic(index){
	$('#musicGrid').datagrid({
		rownumbers:true ,
		height : 'auto',
		fit:true ,
		singleSelect:false,
	    pagination : true,
	    url:'musicList.action',
	    method: 'post',
		columns:[[
			{field:'ck',checkbox:true},
			{field:'song_name',title:'歌曲',width:150},
			{field:'singer_name',title:'歌手',width:150}
		]]
	});
	$('#musicWin').dialog('open');
}
function saveAdvertMusic(){
	var rows = $("#musicGrid").datagrid("getSelections");
	var row = $('#tt').datagrid('getSelected');  
	var ids = "";
	if (rows.length == 0) {
		$.messager.alert('Warning', "请选择歌曲!", 'info');
		return;
	} else {
		$.messager.confirm('Confirm', '确定要更改选中的歌曲吗?', function(result) {
			if (result) {
				for ( var i = 0; i < rows.length; i++) {
					ids += rows[i].song_id + ",";
				}
			
			$.ajax({
				url : 'saveAdvertMusic.action',
				data : {
					'advert.advert_id':row.advert_id,
					'ids' : ids
				},
				type : 'post',
				success : function(data) {
					var data = eval('(' + data + ')'); // change the JSON string to javascript object
					if (data.success) {
						var pers = rows.length;
						$('#tt').datagrid({url:'advertList.action',queryParams:"",pageNumber:1});
						$('#tt').datagrid('clearSelections');
						$('#musicWin').dialog('close');
						$.messager.alert('edit', "成功更改" + pers + "条记录",
								'info');
					} else {
						$.messager.alert('Warning', "更改失败!", 'info');
					}
				}
			});
			}
		});
	}
}
function validateForm(){
	var result = $("#saveAdvert").validate({  
    	rules: {
    		"advert.advert_name":{
    			maxlength :20
    		},
			"advert.advert_content":{
				maxlength : 100
			},
			"advert.is_index_advert":{
				required : true
			},
        	"advert.advert_time": {
				required: true,
				digits:true,
				maxlength: 20
			},
			"advert.advert_sort":{
				required: true,
				digits:true,
				maxlength: 20
			}
		},
		messages: {
			"advert.advert_name":{
    			maxlength :"广告名称长度不能超过20"
    			
    		},
			"advert.advert_content":{
				maxlength : "广告内容不能超过100"
				
			},
			"advert.is_index_advert":{
				required : "请选择广告类别"
			},
			"advert.advert_time": {
				required: "广告时长不能为空",
				digits:"只能输入数字",
				maxlength: "广告时长长度不能超过20"
			},
			"advert.advert_sort":{
				required: "广告顺序不能为空",
				digits:"只能输入数字",
				maxlength:'广告顺序长度不能超过20',
				
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
		if($('#star_head').attr("src")==""){
			 $.messager.alert('提示','图片不能为空', 'info');
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
				url:'checkAdvertSort.action',
				type : 'post',
				data : {
					'advert.advert_id':$('#advertId').val(),
					'advert.advert_sort' : $('#advertSort').val(),
					'advert.is_index_advert':$('#is_index_advert').combobox('getValue')
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
		$('#saveAdvert').form('submit', {
		    url:'saveAdvert.action',
		    onSubmit: function(){
		       //校验 return flase ;
		    	return validateForm();
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$.messager.alert('edit', data.message, 'info');
					var div = document.getElementById('preview');
					div.innerHTML = "<img id='star_head' src='' style='width: 200px;height: 200px'></img>";  
					$('#saveAdvert').form('reset');
					$('#advertId').val("");
					$('#win').dialog('close');
					$('#tt').datagrid({url:'advertList.action',queryParams:"",pageNumber:1});
				}else {
                	$.messager.alert('Warning',data.message,'info');
                }
			}
		});
		} else {
			//修改
			$('#saveAdvert').form('submit', {
				url : 'editAdvert.action',
				onSubmit : function() {
					return validateForm();
				},
				success : function(data) {
					var data = eval('(' + data + ')'); // change the JSON string to javascript object
					if (data.success) {
						$.messager.alert('edit', data.message, 'info');
						$('#advertId').val("");
						var div = document.getElementById('preview');
						div.innerHTML = "<img id='star_head' src='' style='width: 200px;height: 200px'></img>";  
						$('#saveAdvert').form('reset');
						$('#win').dialog('close');
						$('#tt').datagrid({url:'advertList.action',queryParams:"",pageNumber:1});
					}else {
	                	$.messager.alert('Warning',data.message,'info');
	                }
				}
			});
		}

	}
	function removeAdvert() {
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('Warning', "请选择要删除的记录!", 'info');
			return;
		} else {
			$.messager.confirm('Confirm', '确定要删除选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].advert_id + ",";
					}
				
				$.ajax({
					url : 'removeAdvert.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'advertList.action',queryParams:"",pageNumber:1});
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
			"advert.advert_name" : $('#s_advertName').val(),
			"advert.advert_content" : $('#s_advertContent').val(),
			"advert.online_start_time" : $('#s_onlineTime_start').datebox("getValue"),
			"advert.online_end_time" : $('#s_onlineTime_end').datebox("getValue"),
			"advert.offline_start_time" : $('#s_offlineTime_start').datebox("getValue"),
			"advert.offline_end_time" : $('#s_offlineTime_end').datebox("getValue"),
			/* "advert.is_activity" : $('#s_isActivity').combobox('getValue'), */
			"advert.advert_time" : $('#s_advertTime').val(),
			"advert.advert_sort" : $('#s_advertSort').val(),
			"advert.state": $("#s_state").combobox('getValue'),
			"advert.is_index_advert" :$('#s_is_index_advert').combobox('getValue')
		};
		$('#tt').datagrid({
			url : 'searchAdvert.action',
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
			$.messager.alert('Warning', "请选择要发布的记录!", 'info');
			return;
		} else {
		   var i=0;
		   for (i; i < rows.length; i++){  //检查是否已发布，已发布的不能再次发布
		        if( rows[i].state==1){
		        $.messager.alert('Warning', "已发布的不能再次发布，请重新选择!", 'info');
		           return;
		           }
		      }
		if(i==rows.length){
			$.messager.confirm('Confirm', '确定要发布选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].advert_id + ",";
					}
				
				$.ajax({
					url : 'pushAdvert.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'advertList.action',queryParams:"",pageNumber:1});
							$('#tt').datagrid('clearSelections');
							$.messager.alert('edit', "成功发布" + pers + "条记录",
									'info');
						} else {
							$.messager.alert('Warning', "发布失败!", 'info');
						}
					}
				});
				}
			});
		}
	 }
	}
	//歌曲查询
	function searchMusic(){
		var gridparam = {
				"song.song_name" : $('#s_music_songName').val(),
				"song.singer_name" : $('#s_music_singerName').val()
			};
			$('#musicGrid').datagrid({
				url : 'musicList.action',
				queryParams : gridparam
			});
			$('#s_music').form('reset');
	}
	function openSerchWin() {
		$('#searchDialog').dialog('open');
	}
	function closeWin() {
		var div = document.getElementById('preview');
		div.innerHTML = "<img id='star_head' src='' style='width: 200px;height: 200px'></img>";  
		$('#advertId').val("");
		$('#saveAdvert').form('reset');
		$('#win').dialog('close');
	}
	function closeSearchWin() {
		$('#searchForm').form('reset');
		$('#searchDialog').dialog('close');
	}
	function removeAdvertMusic(advert_music_id){
		$.ajax({
			url : 'removeAdvertMusic.action?advert_music_id='+advert_music_id,
			type : 'post',
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$('#tt').datagrid({url:'advertList.action',queryParams:"",pageNumber:1});
					$.messager.alert('edit', data.message,
							'info');
				} else {
					$.messager.alert('Warning', "删除失败!", 'info');
				}
			}
		});
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
<div id="win" class="easyui-dialog" style="padding:5px;width:600px;height:500px;" 
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="saveAdvert" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>广告名称</td>
				<td>
					<input type="hidden" id="advertId" name="advert.advert_id"  />
	        		<input type="text"  id="advertName" name="advert.advert_name"  />
	        	</td>
			</tr>
			<tr>
				<td>广告内容</td>
				<td colspan="3"> 
					<textarea rows="3" cols="50" id="advertContent" name="advert.advert_content" ></textarea>
				</td>
			</tr>
			<tr>
				<td>上线时间<font color="red">*</font></td>
				<td><input type="text" class="easyui-datebox" id="onlineTime" name="advert.online_time" data-options="editable:false" /></td>
				<td>下线时间</td>
				<td><input type="text" class="easyui-datebox" id="offlineTime" name="advert.offline_time" data-options="editable:false"/></td>
			</tr>
			<tr>
				<td>
					播放时长<font color="red">*</font>
				</td>
				<td>
					<input type="text" id="advertTime" name="advert.advert_time" onchange="checkNumber(this)" />
				</td>
				<td>
					播放顺序<font color="red">*</font>
				</td>
				<td>
					<input type="text" id="advertSort" name="advert.advert_sort" onchange="checkNumber(this)"/>
				</td>
			</tr>
			<tr>
				<td>广告类别<font color="red">*</font></td>
				<td>
					<select id="is_index_advert" name="advert.is_index_advert" class="easyui-combobox" panelHeight = 'auto'>
			        	<option value=''>--请选择--</option>
			        	<option value='1'>首页</option>
			        	<option value='2'>广告位</option>
	        		</select>
				</td>
			</tr>
			
			<tr>
				<td>
					广告图片<font color="red">*</font>
				</td>
				<td>
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
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveAdvert()">保存</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>

<div id="searchDialog" class="easyui-dialog" style="padding:5px;width:500px;height:300px;"
			title="查询" iconCls="icon-ok"
			closed="true" 
			modal="true"
			buttons="#search-buttons">
		<form id="searchForm" method="post">
			<table>
				<tr>
					<td>广告名称</td>
					<td><input type="text" id="s_advertName" name="advert.advert_name" /></td>
					<td>广告内容</td>
					<td><input type="text" id="s_advertContent" name="advert.advert_content" /></td>
				</tr>
				<tr>
					<td>上线时间</td>
					<td><input type="text" class="easyui-datebox"  id="s_onlineTime_start" data-options="editable:false" /></td>
					<td>至</td>
					<td><input type="text" class="easyui-datebox"  id="s_onlineTime_end"  data-options="editable:false"/></td>
				</tr>
				<tr>
					<td>下线时间</td>
					<td><input type="text" class="easyui-datebox"  id="s_offlineTime_start" data-options="editable:false" /></td>
					<td>至</td>
					<td><input type="text" class="easyui-datebox"  id="s_offlineTime_end" data-options="editable:false" /></td>
				</tr>
				<tr>
					<td>
						播放时长
					</td>
					<td>
						<input type="text" id="s_advertTime" name="advert.advert_time" />
					</td>
					<td>
						播放顺序
					</td>
					<td>
						<input type="text" id="s_advertSort" name="advert.advert_sort" />
					</td>
				</tr>
				<%-- <tr>
					<td>是否可用</td>
					<td>
						<select id="s_isActivity" name="advert.is_activity"  class="easyui-combobox" panelHeight = 'auto'>
			        	<option value=''>--请选择--</option>
			        	<option value='1'>是</option>
			        	<option value='0'>否</option>
	        		</select>
					</td>
				</tr> --%>
				<tr>
					<td>广告类别<font color="red">*</font></td>
					<td>
						<select id="s_is_index_advert" class="easyui-combobox" data-options="editable:false,panelHeight :'auto'">
				        	<option value=''>--请选择--</option>
				        	<option value='1'>首页</option>
				        	<option value='2'>广告位</option>
		        		</select>
					</td>
					<td>发布状态</td>
					<td>
						<select id="s_state" class="easyui-combobox" data-options="editable:false,panelHeight:'auto'">
							<option value="">--请选择--</option>
							<option value="0">未发布</option>
							<option value="1">已发布</option>
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
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchAdvert()">查询</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
<div id="musicWin"  class="easyui-dialog"
			title="My Dialog" iconCls="icon-ok"
			closed="true" 
			modal="true"
			style="width:700px;height:400px;"
			buttons="#music-buttons"
			toolbar='#music-toolbar'
			>
		<table id="musicGrid">
		</table>
</div>
<div id="music-buttons">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="saveAdvertMusic()">确定</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="$('#musicWin').dialog('close')">关闭</a>
				</td>
			</tr>
		</table>
</div>
<div id="music-toolbar" style="padding:2px 0">
	<form id="s_music" method="post">
        <table cellpadding="0" cellspacing="0" style="width:100%">
            <tr>
            	<td>
            		歌曲名称<input type="text" id="s_music_songName"/>
            	</td>
            	<td>
            		歌星名称<input type="text" id="s_music_singerName"/>
            	</td>
            	<td>
            		<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchMusic()">搜索</a>
            	</td>
            </tr>
        </table>
        
    </form>
</div>
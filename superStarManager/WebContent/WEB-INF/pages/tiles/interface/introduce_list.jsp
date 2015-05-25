<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function(){
	$('#tt').datagrid({
		title:'新歌推荐',
		iconCls:'icon-edit',
		rownumbers:true ,
		height : 'auto',
		fit:true ,
	    pagination : true,
	    url:'introduceList.action',
	    method: 'post',
	   	toolbar: "#toolbar",
		columns:[[
			{field:'ck',checkbox:true},
			{field:'opt',title:'',align:'center',
				formatter:function(value,row,index){
					return '<a href="#" class="editcls" onclick="editIntroduce('+index+')"></a>';
				}
			},
			{field:'introduce_id',title:'ID',width:60,hidden:true},
			{field:'music_name',title:'歌曲',width:150},
			{field:'singer_name',title:'歌星',width:150},
			{field:'introduce_sort',title:'推荐度',width:150},
			{field:'introduce_time',title:'推荐日期',width:150},
			{field:'state',title:'发布状态',width:150,
				formatter: function(value,row,index){
					if (row.state == 1){
						return "已发布" ;
					}else if(row.state == 0){
						return "未发布" ;
					}
						return row.state ;
				}
			}
			
		]],
		onLoadSuccess:function(data){  
	        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
	    }
	});
});
//打开新增窗口
function newIntroduce(){
	$('#win').dialog('open').dialog('setTitle','新增推荐歌曲');
}
//加载编辑数据
function editIntroduce(index){
	$('#tt').datagrid('selectRow',index);// 关键在这里  
    var row = $('#tt').datagrid('getSelected');  
   	if (row){  
		$('#win').dialog('open').dialog('setTitle','编辑推荐歌曲');
        $('#introduceId').val(row.introduce_id);
        $('#songId').val(row.music_id);
        $('#songName').val(row.music_name);
   		$('#singerId').val(row.singer_id);
        $('#singerName').val(row.singer_name);
        $('#introduceSort').val(row.introduce_sort);
    }
}
function validateForm(){
	var result = $("#saveIntroduce").validate({  
    	rules: {
        	"introduce.music_name": {
				required: true
			},
			"introduce.introduce_sort": {
				required: true,
				digits:true,
				maxlength:10
			}
	
		},
		messages: {
			"introduce.music_name": {
				required: "请选择歌曲"
			},
			"introduce.introduce_sort": {
				required: "推荐度不能为空",
				digits:"推荐度只能为数字",
				maxlength:"推荐度长度不能超过10"
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
	return result ; 
}
   function saveIntroduce(){
	if(!$('#introduceId').val()){
		//新增
		$('#saveIntroduce').form('submit', {
		    url:'saveIntroduce.action',
		    onSubmit: function(){
		       //校验 return flase ;
		    	return validateForm();
			},
			success : function(data) {
				var data = eval('(' + data + ')'); // change the JSON string to javascript object
				if (data.success) {
					$.messager.alert('提示', data.message, 'info');
					$('#saveIntroduce').form('clear');
					$('#introduceId').val("");
					$('#win').dialog('close');
					$('#tt').datagrid({url:'introduceList.action',queryParams:"",pageNumber:1});
				}else {
                	$.messager.alert('警告',data.message,'info');
                }
			}
		});
		} else {
			//修改
			$('#saveIntroduce').form('submit', {
				url : 'editIntroduce.action',
				onSubmit : function() {
					return validateForm();
				},
				success : function(data) {
					var data = eval('(' + data + ')'); // change the JSON string to javascript object
					if (data.success) {
						$.messager.alert('提示', data.message, 'info');
						$('#introduceId').val("");
						$('#saveIntroduce').form('reset');
						$('#win').dialog('close');
						$('#tt').datagrid({url:'introduceList.action',queryParams:"",pageNumber:1});
					}else {
	                	$.messager.alert('警告',data.message,'info');
	                }
				}
			});
		}

	}
	//删除推荐歌曲
	function removeIntroduce() {
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
						url : 'removeIntroduce.action',
						data : {
							'ids' : ids
						},
						type : 'post',
						success : function(data) {
							var data = eval('(' + data + ')'); // change the JSON string to javascript object
							if (data.success) {
								var pers = rows.length;
								$('#tt').datagrid({url:'introduceList.action',queryParams:"",pageNumber:1});
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
	//选择歌曲DIALOG加载歌曲信息
	function selectMusic(){
		$('#musicGrid').datagrid({
			rownumbers:true ,
			height : 'auto',
			fit:true ,
			singleSelect:true,
		    pagination : true,
		    url:'introMusicList.action',
		    queryParams:'',
		    method: 'post',
			columns:[[
				{field:'ck',checkbox:true},
				{field:'song_name',title:'歌曲',width:150},
				{field:'singer_name',title:'歌手',width:150}
			]]
		});
		$('#musicWin').dialog('open');
	}
	//将选中的歌曲与歌手信息ID 写入对应编辑表单
	function fitMusic(){
		var rows = $("#musicGrid").datagrid("getSelections");
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择记录!", 'info');
			return;
		} else {
			$('#songId').val(rows[0].song_id);
			$('#songName').val(rows[0].song_name);
			$('#singerId').val(rows[0].singer_id);
			$('#singerName').val(rows[0].singer_name);
			$('#musicWin').dialog('close');
			
		}
	}
	//歌曲查询
	function searchMusic(){
		var gridparam = {
				"song.song_name" : $('#s_music_songName').val(),
				"song.singer_name" : $('#s_music_singerName').val()
			};
			$('#musicGrid').datagrid({
				url : 'introMusicList.action',
				queryParams : gridparam
			});
			$('#s_music').form('reset');
	}
	//过路歌曲搜索
	function searchIntroduce() {
		var gridparam = {
			"introduce.song.song_name" : $('#s_songName').val(),
			"introduce.song.singer_name" : $('#s_singerName').val(),
			"introduce.introduce_sort" : $('#s_introduceSort').val(),
			 
		};
		$('#tt').datagrid({
			url : 'searchIntroduce.action',
			queryParams : gridparam,
			pageNumber:1
		});
		$('#searchForm').form('clear');
		$('#searchDialog').dialog('close');
	}
	function pushIntroduce(){
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
		   if(i==rows.length){
			$.messager.confirm('发布记录', '确定要发布选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].introduce_id + ",";
					}
				
				$.ajax({
					url : 'pushIntroduce.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'introduceList.action',queryParams:"",pageNumber:1});
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
	}
	//打开搜索框
	function openSerchWin() {
		$('#searchDialog').dialog('open');
	}
	//关闭搜索框
	function closeSearchWin() {
		$('#searchForm').form('clear');
		$('#searchDialog').dialog('close');
	}
	//关闭编辑框
	function closeWin() {
		$('#introduceId').val("");
		$('#saveIntroduce').form('clear');
		$('#win').dialog('close');
	}
</script>
<!-- 主grid -->
<table id="tt">
</table>
<div id="toolbar">
	<a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newIntroduce()">新增</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeIntroduce()">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="pushIntroduce()">发布</a>
</div>
<!-- 编辑窗口 -->
<div id="win" class="easyui-dialog" style="padding:5px;width:600px;height:400px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="saveIntroduce" method="post">
		<table>
			<tr>
				<td>
					<input type="button" value="选择歌曲" onclick="selectMusic()" />
				</td>
			</tr>
			<tr>
				<td>歌曲 <font color="red">*</font></td>
				<td>
					<input type="hidden" id="introduceId" name="introduce.introduce_id"  />
	        		<input type="text"  id="songName" name="introduce.music_name"  readonly="readonly"/>
	        		<input type="hidden"  id="songId" name="introduce.song.song_id" />
	        	</td>
				<td>歌星 <font color="red">*</font></td>
				<td>
					<input type="text" id="singerName"  readonly="readonly"/>
				</td>
			</tr>
			<tr>
				<td>推荐度 <font color="red">*</font></td>
				<td><input type="text" id="introduceSort" name="introduce.introduce_sort" onchange="checkNumber(this)"/></td>
			</tr>
		</table>
	</form>

</div>
<div id="dlg-buttons" >
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveIntroduce()">保存</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
<!-- 搜索win -->
<div id="searchDialog" class="easyui-dialog" style="padding:5px;width:600px;height:400px;"
			title="查询" iconCls="icon-ok"
			closed="true" 
			modal="true"
			buttons="#search-buttons">
		<form id="searchForm" method="post">
			<table>
				<tr>
					<td>歌曲</td>
					<td>
		        		<input type="text"  id="s_songName" name="introduce.song.song_name"  />
		        	</td>
					<td>歌星</td>
					<td><input type="text" id="s_singerName" /></td>
				</tr>
				<tr>
					<td>推荐度</td>
					<td><input type="text" id="s_introduceSort" name="introduce.introduce_sort" /></td>
				</tr>
			</table>
		</form>
</div>
<div id="search-buttons">
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchIntroduce()">查询</a>
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
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="fitMusic()">确定</a>
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
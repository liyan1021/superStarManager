<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function() {
		$('#tt').datagrid({
			title : '曲库管理',
			iconCls : 'icon-edit',
			rownumbers : true,
			height : 'auto',
			fit:true ,
			pagination : true,
			fitColumns : false,
			url : 'musicList.action',
			method : 'post',
			toolbar : "#toolbar",
			sortName : "song_name",
		   	sortOrder: "asc",
			columns : [ [
			{
				field : 'ck',
				checkbox : true
			},
			{
				field : 'opt',
				title : '',
				align : 'center',
				formatter : function(value, row, index) {
					return '<a href="#" class="editcls" onclick="editMusic('
							+ index + ')"></a>';
				}
			},
			{ title : 'song_id', field : 'song_id',width : 60,hidden:true  },
			{ title :'歌曲名', field :'song_name',width : 200,sortable: true },
			{ title :'拼音', field :'spell_first_letter_abbreviation',width : 200,sortable: true },
			{ title :'歌星', field :'singer_id',width : 200,hidden:true},
			{ title :'歌星名称', field :'singer_name',width : 200,sortable: true },
			{ title :'歌星类别', field :'singer_type',width : 200,sortable: true,
				formatter: function(value,row,index){
					if (row.singer_type==1){
						return "男";
					} else if(row.singer_type==2) {
						return "女";
					}else if(row.singer_type==3) {
						return "组合";
					}else if(row.singer_type==4) {
						return "群星";
					}
					return "" ;
				}	
			},
			{ title :'版本', field :'version',width : 200,sortable: true,
				formatter: function(value,row,index){
					if (row.version==1){
						return "MTV";
					}else if (row.version==2){
						return "演唱会";
					} else if(row.version==3) {
						return "故事情节";
					}else if(row.version==4) {
						return "风景";
					}else if(row.version==5) {
						return "人物";
					}
					return "" ;
				}
			},
			{ title :'伴唱音量', field :'accompany_volume',width : 200,sortable: true },
			{ title :'原唱音量', field :'karaoke_volume',width : 200,sortable: true },
			{ title :'歌词', field :'lyric',width : 200 ,sortable: true},
			{ title :'年代', field :'issue_year',width : 200,sortable: true,
				formatter: function(value,row,index){
					if (row.issue_year==1){
						return "60";
					} else if(row.issue_year==2) {
						return "70";
					}else if(row.issue_year==3) {
						return "80";
					}else if(row.issue_year==4) {
						return "90";
					}else if(row.issue_year==5) {
						return "其他";
					}
					return "" ;
				}	
			},
			{ title :'分辨率', field :'resolution',width : 200 ,sortable: true,
				formatter: function(value,row,index){
					if (row.resolution==1){
						return "标清";
					} else if(row.resolution==2) {
						return "高清";
					}else if(row.resolution==3) {
						return "超清";
					}
					return "" ;
				}	
			},
			{ title :'唱片公司', field :'record_company',width : 200,sortable: true },
			{ title :'主题', field :'song_theme',width : 200 ,sortable: true,
				formatter: function(value,row,index){
					if (row.song_theme==1){
						return "儿歌";
					} else if(row.song_theme==2) {
						return "军歌";
					}else if(row.song_theme==3) {
						return "祝福";
					}else if(row.song_theme==4) {
						return "独创";
					}else if(row.song_theme==5) {
						return "摇滚";
					}else if(row.song_theme==6) {
						return "校园歌曲";
					}else if(row.song_theme==7) {
						return "情歌对唱";
					}else if(row.song_theme==8) {
						return "红歌";
					}
					return "" ;
				}		
			},
			{ title :'影视类别', field :'movie_type',width : 200 ,sortable: true,
				formatter: function(value,row,index){
					if (row.movie_type==1){
						return "电视剧";
					} else if(row.movie_type==2) {
						return "电影";
					}else if(row.movie_type==3) {
						return "娱乐节目";
					}
					return "" ;
				}	
			},
			{ title :'影视类别说明', field :'movie_type_info',width : 200 ,sortable: true},
			{ title :'曲种', field :'song_type',width : 200 ,sortable: true,
				
				formatter: function(value,row,index){
					if (row.song_type==1){
						return "京剧";
					} else if(row.song_type==2) {
						return "黄梅戏";
					}else if(row.song_type==3) {
						return "通俗";
					}else if(row.song_type==4) {
						return "豫剧";
					}else if(row.song_type==5) {
						return "昆剧";
					}else if(row.song_type==6) {
						return "越剧";
					}
					return "" ;
				}	
			},
			{ title :'语种', field :'language',width : 200 ,sortable: true,
				formatter: function(value,row,index){
					if (row.language==1){
						return "国语";
					} else if(row.language==2) {
						return "闽南语";
					}else if(row.language==3) {
						return "粤语";
					}else if(row.language==4) {
						return "英语";
					}else if(row.language==5) {
						return "韩语";
					}else if(row.language==6) {
						return "日语";
					}else if(row.language==7) {
						return "其他";
					}
					return "" ;
				}	
			},
			{ title :'点唱次数', field :'click_number',width : 200,sortable: true },
			{ title :'收藏次数', field :'collection_number',width : 200,sortable: true },
			{ title :'作词', field :'authors',width : 200,sortable: true },
			{ title :'作曲', field :'compose',width : 200 ,sortable: true},
			{ title :'原唱音轨', field :'original_track',width : 200 ,sortable: true,
				formatter: function(value,row,index){
					if (row.original_track==0){
						return "音轨1";
					} else if(row.original_track==1) {
						return "音轨2";
					}
					return "" ;
				}	
			},
			{ title :'原唱声道', field :'original_channel',width : 200 ,sortable: true,
				formatter: function(value,row,index){
					if (row.original_channel==2){
						return "左声道";
					} else if(row.original_channel==3) {
						return "右声道";
					}
					return "" ;
				}	
			},
			{ title :'伴唱音轨', field :'accompany_track',width : 200,sortable: true,
				formatter: function(value,row,index){
					if (row.accompany_track==0){
						return "音轨1";
					} else if(row.accompany_track==1) {
						return "音轨2";
					}
					return "" ;
				}		
			},
			{ title :'伴唱声道', field :'accompany_channel',width : 200 ,sortable: true,
				formatter: function(value,row,index){
					if (row.accompany_channel==2){
						return "左声道";
					} else if(row.accompany_channel==3) {
						return "右声道";
					}
					return "" ;
				}		
			},
			{ title :'存储类型', field :'storage_type',width : 200,sortable: true,
				formatter: function(value,row,index){
					if (row.storage_type==1){
						return "本地磁盘";
					} else if(row.storage_type==2) {
						return "本地存储";
					}else if(row.storage_type==3) {
						return "云端";
					}
					return "" ;
				}	
			},
			{ title :'文件路径', field :'file_path',width : 200 },
			{ title :'文件大小', field :'file_length',width : 200 },
			{ title :'文件格式', field :'file_format',width : 200 },
			{ title :'灯光设置', field :'light_control_set',width : 200 },
			{ title :'播放时长', field :'play_time',width : 200 },
			{ title :'流格式', field :'flow_type',width : 200 },
			{ title :'码率', field :'song_bit_rate',width : 200 },
			{ title :'入库时间', field :'import_time',width : 200 },
			{ title :'idx_sha1', field :'idx_sha1',width : 200 },
			{ title :'dgx_sha1', field :'dgx_sha1',width : 200 },
			{ title :'备注', field :'remark',width : 200 }
			] ],
			onLoadSuccess:function(data){  
		        $('.editcls').linkbutton({plain:true,iconCls:'icon-edit'});  
		    }
		});
	});
	var param;
	function searchLog() {
		var gridparam = {
				"song.song_id" : $('#s_song_id').val(),
				"song.file_name" : $('#s_file_name').val(),
				"song.superstar_id" : $('#s_superstar_id').val(),
				"song.unsuperstar_id" : $('#s_unsuperstar_id').val(),
				"song.song_name" : $('#s_song_name').val(),
				"song.spell_first_letter_abbreviation" : $('#s_spell_first_letter_abbreviation').val(),
				"song.singer_id" : $('#s_singer_id').val(),
				"song.singer_name" : $('#s_singer_name').val(),
				"song.singer_type" : $('#s_singer_type').combobox('getValue'),
				"song.version" : $('#s_version').combobox('getValue'),
				/* "song.accompany_volume" : $('#s_accompany_volume').slider('getValue'),
				"song.karaoke_volume" : $('#s_karaoke_volume').slider('getValue'), */
				"song.lyric" : $('#s_lyric').val(),
				"song.issue_year" : $('#s_issue_year').combobox('getValue'),
				"song.resolution" : $('#s_resolution').combobox('getValue'),
				"song.record_company" : $('#s_record_company').val(),
				"song.song_theme" : $('#s_song_theme').combobox('getValue'),
				"song.movie_type" : $('#s_movie_type').combobox('getValue'),
				"song.movie_type_info" : $('#s_movie_type_info').val(),
				"song.song_type" : $('#s_song_type').combobox('getValue'),
				"song.language" : $('#s_language').combobox('getValue'),
				"song.click_number" : $('#s_click_number').val(),
				"song.collection_number" : $('#s_collection_number').val(),
				"song.authors" : $('#s_authors').val(),
				"song.compose" : $('#s_compose').val(),
				"song.original_track" : $('#s_original_track').combobox('getValue'),
				"song.original_channel" : $('#s_original_channel').combobox('getValue'),
				"song.accompany_track" : $('#s_accompany_track').combobox('getValue'),
				"song.accompany_channel" : $('#s_accompany_channel').combobox('getValue'),
				"song.storage_type" : $('#s_storage_type').combobox('getValue'),
				"song.file_path" : $('#s_file_path').val(),
				"song.file_length" : $('#s_file_length').val(),
				"song.file_format" : $('#s_file_format').val(),
				"song.light_control_set" : $('#s_light_control_set').val(),
				"song.play_time" : $('#s_play_time').timespinner('getValue'),
				"song.flow_type" : $('#s_flow_type').val(),
				"song.song_bit_rate" : $('#s_song_bit_rate').val(),
				"song.import_time" : $('#s_import_time').val(),
				"song.idx_sha1" : $('#s_idx_sha1').val(),
				"song.dgx_sha1" : $('#s_dgx_sha1').val(),
				"song.remark" : $('#s_remark').val()

		};
		param = gridparam;
		$('#tt').datagrid({
			url : 'searchMusicList.action',
			queryParams : gridparam,
			pageNumber:1
		});

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
	function exportMusic(){
		if($('#tt').datagrid('getRows').length == 0 ){
			$.messager.alert('提示','查询为空，无法导出', 'info');		
		}else{
		    $.messager.confirm('导出记录', '确定要导出曲库列表吗?', function(result){
		    if(result){
			    post('exportMusic.action',param); 
			}
		  });
		}
	}
	function removeMusic() {
		var rows = $("#tt").datagrid("getSelections");
		var ids = "";
		if (rows.length == 0) {
			$.messager.alert('警告', "请选择要删除的记录!", 'info');
			return;
		} else {
			$.messager.confirm('删除记录', '确定要删除选中的记录吗?', function(result) {
				if (result) {
					for ( var i = 0; i < rows.length; i++) {
						ids += rows[i].song_id + ",";
					}
				}
				$.ajax({
					url : 'removeMusic.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid({url:'musicList.action',queryParams:"",pageNumber:1});
							$('#tt').datagrid('clearSelections');
							$.messager.alert('提示', "成功删除" + pers + "条记录",
									'info');
						} else {
							$.messager.alert('警告', "删除失败!", 'info');
						}
					}
				});
			});
		}
	}
	function editMusic(index){
		$('#tt').datagrid('selectRow',index);// 关键在这里  
	    var row = $('#tt').datagrid('getSelected');  
	   	if (row){  
			$('#win').dialog('open').dialog('setTitle','编辑歌曲');
			$('#song_id').val(row.song_id);
			$('#song_name').val(row.song_name);
			$('#spell_first_letter_abbreviation').val(row.spell_first_letter_abbreviation);
			$('#version').combobox('setValue',row.version);
			$('#accompany_volume').slider('setValue',row.accompany_volume);
			$('#karaoke_volume').slider('setValue',row.karaoke_volume);
			$('#lyric').val(row.lyric);
			$('#issue_year').combobox('setValue',row.issue_year);
			$('#resolution').combobox('setValue',row.resolution);
			$('#record_company').val(row.record_company);
			$('#song_theme').combobox('setValue',row.song_theme);
			$('#movie_type').combobox('setValue',row.movie_type);
			$('#movie_type_info').val(row.movie_type_info);
			$('#song_type').combobox('select',row.song_type);
			$('#language').combobox('setValue',row.language);
/* 			$('#click_number').val(row.click_number);
			$('#collection_number').val(row.collection_number); */
			$('#authors').val(row.authors);
			$('#compose').val(row.compose);
			$('#original_track').combobox('setValue',row.original_track);
			$('#original_channel').combobox('setValue',row.original_channel);
			$('#accompany_track').combobox('setValue',row.accompany_track);
			$('#accompany_channel').combobox('setValue',row.accompany_channel);
			$('#storage_type').combobox('setValue',row.storage_type);
			$('#play_time').timespinner('setValue',row.play_time);
			$('#flow_type').val(row.flow_type);
			$('#song_bit_rate').val(row.flow_type);
			$('#remark').val(row.remark);
	    }
	}
	function validateForm(){
		var result = $("#editMusic").validate({  
	    	rules: {
	        	"song.song_name": {
					required: true,
					maxlength: 20
				},
				"song.spell_first_letter_abbreviation":{
					maxlength: 20,
					chars:true
				},
				"song.version":{
					maxlength: 20
				},
				"song.lyric":{
					maxlength: 200
				},
				"song.record_company":{
					maxlength: 20
				},
				"song.movie_type":{
					maxlength: 20
				},
				"song.movie_type_info":{
					maxlength: 20
				},
				"song.authors":{
					maxlength: 20
				},
				"song.compose":{
					maxlength: 20
				},
				"song.flow_type":{
					maxlength: 20
				},
				"song.song_bit_rate":{
					maxlength: 20,
					number:true
				},
				"song.remark":{
					maxlength: 20
				}
			},
			messages: {
				"song.song_name": {
					required: "歌曲名称不能为空",
					maxlength: "歌曲名称长度不能超过20",
					remote:"歌曲已经存在"
				},
				"song.spell_first_letter_abbreviation":{
					maxlength:'拼音长度不能超过20',
					chars:'拼音只能是字母'
				},
				"song.version":{
					maxlength: "版本长度不能超过20"
				},
				"song.lyric":{
					maxlength: "歌词长度不能超过200"
				},
				"song.record_company":{
					maxlength: "唱片公司长度不能超过20"
				},
				"song.movie_type":{
					maxlength: "影视类别长度不能超过20"
				},
				"song.movie_type_info":{
					maxlength: "影视类别说明长度不能超过20"
				},
				"song.authors":{
					maxlength: "作词长度不能超过20"
				},
				"song.compose":{
					maxlength: "作曲长度不能超过20"
				},
				"song.flow_type":{
					maxlength: "流格式长度不能超过20"
				},
				"song.song_bit_rate":{
					maxlength: "码率长度不能超过20",
					number:"码率只能是数字"
				},
				"song.remark":{
					maxlength: "备注长度不能超过20"
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
		/* if(result){
			if($('#original_track').combobox('getValue')==""){
				 $.messager.alert('提示','请选择原唱音轨', 'info');
				 return false ; 
			}
			if($('#original_channel').combobox('getValue')==""){
				 $.messager.alert('提示','请选择原唱声道', 'info');
				 return false ; 
			}
			if($('#accompany_track').combobox('getValue')==""){
				 $.messager.alert('提示','请选择伴唱音轨', 'info');
				 return false ; 
			}
			if($('#accompany_channel').combobox('getValue')==""){
				 $.messager.alert('提示','请选择伴唱声道', 'info');
				 return false ; 
			}
		} */
		return result ; 
	}
	function saveMusic(){
		//修改
		$('#editMusic').form('submit', {
		    url:'editMusic.action',
		    onSubmit: function(){
		       //校验 return flase ;
		       
		    	return validateForm();
		    },
		    success:function(data){
		    	var data = eval('(' + data + ')');  // change the JSON string to javascript object
		        if (data.success){
		        	$.messager.alert('提示',data.message,'info');
		        	$('#song_id').val("");
		        	$('#editMusic').form('clear');
		            $('#win').dialog('close');
		            $('#tt').datagrid({url:'musicList.action',queryParams:"",pageNumber:1});
		        }else {
                	$.messager.alert('警告',data.message,'info');
                }
		    }
		});
	}
	
	function openImportMusic(){
		$('#importWin').dialog('open');
	}
	function importMusic(){
		$('#importMusic').form('submit', {
		    url:'importMusic.action',
		    onSubmit: function(){
		       //校验 return flase ;
		    },
		    success:function(data){
		    	var data = eval('(' + data + ')');  // change the JSON string to javascript object
		        if (data.success){
		        	$.messager.alert('提示',data.message,'info');
		        	closeImportWin();
		            $('#win').dialog('close');
		            $('#tt').datagrid({url:'musicList.action',queryParams:"",pageNumber:1});
		        }else {
                	$.messager.alert('警告',data.message,'info');
                }
		    }
		});
	}
	function closeImportWin(){
		$('#importMusic').form('clear');
		$('#importWin').dialog('close');
	}
	function openSerchWin() {
		$('#searchDialog').dialog('open');
	}
	
	function closeWin(){
		$('#song_id').val("");
		$('#editMusic').form('reset');
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
	<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增</a> -->
	<a href="#" class="easyui-linkbutton" iconCls="icon-search" plain="true" onclick="openSerchWin()">查询</a> 
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeMusic()">删除</a>
	<a href="#"	class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="exportMusic()">导出</a>
	<a href="#"	class="easyui-linkbutton" iconCls="icon-print" plain="true" onclick="openImportMusic()">导入</a>
	</div>
<div id="searchDialog" class="easyui-dialog"
	style="padding: 5px; width: 600px; height: 400px;" title="查询"
	iconCls="icon-ok" closed="true" modal="true" buttons="#search-buttons">
	<form id="searchForm" method="post">
		<table>
			<tr>
				<td>歌曲名</td>
				<td><input type="text" id="s_song_name" name="song.song_name" /></td>
				<td>拼音</td>
				<td><input type="text" id="s_spell_first_letter_abbreviation"  name="song.spell_first_letter_abbreviation" /></td>
			</tr>
			<tr>
				<td>歌星名称</td>
				<td><input type="text" id="s_singer_name"  name="song.singer_name" /></td>
				
				<td>歌星类别</td>
				<td>
					<select class="easyui-combobox" id="s_singer_type" name="song.singer_type" style="width:150px;"
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
				<td>版本</td>
				<td>
					<select class="easyui-combobox" id="s_version" name="song.version" style="width:150px;"
							data-options="
									url:'getDictListS.action?type=version',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
				<td>歌词</td>
				<td>
					<input type="text" id="s_lyric"  name="song.lyric" />
				</td>
			</tr>
			<!-- <tr>
				<td>原唱音量</td> 
				<td>
					<input class="easyui-slider" id="s_karaoke_volume"   name="song.karaoke_volume"  data-options="showtip:true" style="margin-top:20px;"/>
				</td>
				<td>伴唱音量</td>
				<td><input class="easyui-slider" id="s_accompany_volume"  name="song.accompany_volume" data-options="showtip:true" style="margin-top:20px;"/></td>
			</tr> -->
			<tr>
				<td>年代</td>
				<td>
					<select class="easyui-combobox" id="s_issue_year" name="song.issue_year" style="width:150px;"
							data-options="
									url:'getDictListS.action?type=issue_year',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
					
				</td>
				<td>分辨率</td>
				<td>
					<select class="easyui-combobox" id="s_resolution" name="song.resolution" style="width:150px;"
							data-options="
									url:'getDictListS.action?type=resolution',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
			</tr>
			<tr>
				<td>唱片公司</td>
				<td><input type="text" id="s_record_company"  name="song.record_company" /></td>
				<td>主题</td>
				<td>
					<select class="easyui-combobox" id="s_song_theme"  name="song.song_theme" style="width:150px;" 
							data-options="
									url:'getDictListS.action?type=theme',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
			</tr>
			<tr>
				<td>影视类别</td>
				<td>
					<select class="easyui-combobox" id="s_movie_type" name="song.movie_type" style="width:150px;" 
							data-options="
									url:'getDictListS.action?type=movie_type',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
					
				</td>
				<td>影视类别说明</td>
				<td>
					<input type="text" id="s_movie_type_info"  name="song.movie_type_info" />
				</td>
			</tr>
			<tr>
				<td>曲种</td>
				<td>
					<select class="easyui-combobox" id="s_song_type"  name="song.song_type" style="width:150px;" 
							data-options="
									url:'getDictListS.action?type=song_type',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
				<td>语种</td>
				<td>
					<select class="easyui-combobox" id="s_language"  name="song.language" style="width:150px;" 
							data-options="
									url:'getDictListS.action?type=language',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
			</tr>
			<tr>
				<td>点唱次数</td>
				<td><input type="text" id="s_click_number"  name="song.click_number" /></td>
				<td>收藏次数</td>
				<td><input type="text" id="s_collection_number"  name="song.collection_number" /></td>
			</tr>
			<tr>
				<td>作词</td>
				<td><input type="text" id="s_authors"  name="song.authors" /></td>
				<td>作曲</td>
				<td><input type="text" id="s_compose"  name="song.compose" /></td>
			</tr>
			<tr>
				<td>原唱音轨</td>
				<td>
					<select class="easyui-combobox" id="s_original_track" name="song.original_track" style="width:150px;"
							data-options="
									url:'getDictListS.action?type=track',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
				<td>原唱声道</td>
				<td>
					<select class="easyui-combobox" id="s_original_channel" name="song.original_channel" style="width:150px;"
							data-options="
									url:'getDictListS.action?type=channel',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
			</tr>
			<tr>
				<td>伴唱音轨</td>
				<td>
					<select class="easyui-combobox" id="s_accompany_track" name="song.accompany_track" style="width:150px;"
							data-options="
									url:'getDictListS.action?type=track',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
				<td>伴唱声道</td>
				<td>
					<select class="easyui-combobox" id="s_accompany_channel" name="song.accompany_channel" style="width:150px;"
							data-options="
									url:'getDictListS.action?type=channel',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
			</tr>
			<tr>
				<td>存储类型</td>
				<td>
					<select class="easyui-combobox" id="s_storage_type" name="song.storage_type" style="width:150px;"
							data-options="
									url:'getDictListS.action?type=storage_type',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>	
				</td>
				<td>播放时长</td>
				<td><input type="text" id="s_play_time"  name="song.play_time" class="easyui-timespinner"  data-options="editable:false,showSeconds:true"/></td>
				
			</tr>
				<tr>
				<td>流格式</td>
				<td><input type="text" id="s_flow_type"  name="song.flow_type" /></td>
				<td>码率</td>
				<td><input type="text" id="s_song_bit_rate"  name="song.song_bit_rate" class="easyui-numberspinner" />M</td>
			</tr>
			<tr>
				<td>备注</td>
				<td><input type="text" id="s_remark"  name="song.remark" /></td>
			</tr>
		</table>
		
	</form>
</div>
<div id="search-buttons">
	<table cellpadding="0" cellspacing="0" style="width: 100%">
		<tr>
			<td style="text-align: right"><a href="#"
				class="easyui-linkbutton" iconCls="icon-search"
				onclick="searchLog()">查询</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="closeSearchWin()">关闭</a></td>
		</tr>
	</table>
</div>


<div id="win" class="easyui-dialog" style="padding:5px;width:700px;height:500px;"
			title="My Dialog" iconCls="icon-ok"
			closed="true"
			modal="true"
			buttons="#dlg-buttons"
			>
	<form id="editMusic" method="post">
	    <table>
		    <tr>
			    <td>
			    	<input type="hidden" id="song_id" name="song.song_id" />
			    </td>
		    </tr>
			<tr>
				<td>歌曲名<font color="red">*</font></td>
				<td><input type="text" id="song_name" name="song.song_name" /></td>
				<td>拼音</td>
				<td><input type="text" id="spell_first_letter_abbreviation"  name="song.spell_first_letter_abbreviation" /></td>
			</tr>
			<!-- 			<tr>
				<td>歌星名称</td>
				<td><input type="text" id="singer_name"  name="song.singer_name" /></td>
				<td>歌星类别</td>
				<td><input type="text" id="singer_type"  name="song.singer_type" /></td>
			</tr> -->
			<tr>
				<td>版本</td>
				<td>
					<select class="easyui-combobox" id="version" name="song.version" style="width:150px;"
							data-options="
									url:'getDictList.action?type=version',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
				<td>歌星名称</td>
				<td><input type="text" id="singer_name"  name="song.singer_name"  /></td>
			</tr>
			
			<tr>
				<td>歌词</td>
				<td colspan="3">
					<!-- <input type="" id="lyric"  name="song.lyric" /> -->
					<textarea rows="10" cols="50" id="lyric" name="song.lyric"></textarea>
				</td>
			</tr>
			<tr style="line-height: 40px">
				<td>原唱音量</td> 
				<td>
					<input class="easyui-slider" id="karaoke_volume"   name="song.karaoke_volume" style="width:150px;" data-options="showTip:true,
					tipFormatter: function(value){
						return value+'%';
					}" />
				</td>
				<td>伴唱音量</td>
				<td>
					<input class="easyui-slider" id="accompany_volume"  name="song.accompany_volume" style="width:150px;" data-options="showTip:true,
					tipFormatter: function(value){
						return value+'%';
					}" />
				</td>
			</tr>
			<tr>
				<td>年代</td>
				<td>
					<select class="easyui-combobox" id="issue_year" name="song.issue_year" style="width:150px;"
							data-options="
									url:'getDictList.action?type=issue_year',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
				<td>分辨率</td>
				<td>
					<select class="easyui-combobox" id="resolution" name="song.resolution" style="width:150px;" 
							data-options="
									url:'getDictList.action?type=resolution',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
			</tr>
			<tr>
				<td>唱片公司</td>
				<td><input type="text" id="record_company"  name="song.record_company" /></td>
				<td>主题</td>
				<td>
					<select class="easyui-combobox" id="song_theme"  name="song.song_theme" style="width:150px;" 
						   data-options="
									url:'getDictList.action?type=theme',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
			</tr>
			<tr>
				<td>影视类别</td>
				<td>
				
					<select class="easyui-combobox" id="movie_type" name="song.movie_type" style="width:150px;"
							data-options="
									url:'getDictList.action?type=movie_type',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
				<td>影视类别说明</td>
				<td>
					<input type="text" id="movie_type_info"  name="song.movie_type_info" />
				</td>
			</tr>
			<tr>
				<td>曲种</td>
				<td>
					<select class="easyui-combobox" id="song_type"  name="song.song_type" style="width:150px;" 
							data-options="
									url:'getDictList.action?type=song_type',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
				</select>
				</td>
				<td>语种</td>
				<td>
					<select class="easyui-combobox" id="language"  name="song.language" style="width:150px;" 
						   data-options="
									url:'getDictList.action?type=language',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'">
					</select>
				</td>
			</tr>
			<!-- <tr>
				<td>点唱次数</td>
				<td><input type="text" id="click_number"  name="song.click_number" /></td>
				<td>收藏次数</td>
				<td><input type="text" id="collection_number"  name="song.collection_number" /></td>
			</tr> -->
			<tr>
				<td>作词</td>
				<td><input type="text" id="authors"  name="song.authors" /></td>
				<td>作曲</td>
				<td><input type="text" id="compose"  name="song.compose" /></td>
			</tr>
			<tr>
				<td>原唱音轨<font color="red">*</font></td>
				<td>
					<select class="easyui-combobox" id="original_track" name="song.original_track" style="width:150px;"
							data-options="
									url:'getDictList.action?type=track',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
				<td>原唱声道<font color="red">*</font></td>
				<td>
					<select class="easyui-combobox" id="original_channel" name="song.original_channel" style="width:150px;"
							data-options="
									url:'getDictList.action?type=channel',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
			</tr>
			<tr>
				<td>伴唱音轨<font color="red">*</font></td>
				<td>
					<select class="easyui-combobox" id="accompany_track" name="song.accompany_track" style="width:150px;"
							data-options="
									url:'getDictList.action?type=track',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
				<td>伴唱声道<font color="red">*</font></td>
				<td>
					<select class="easyui-combobox" id="accompany_channel" name="song.accompany_channel" style="width:150px;"
							data-options="
									url:'getDictList.action?type=channel',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>
				</td>
			</tr>
			<tr>
				<td>存储类型</td>
				<td>
					<select class="easyui-combobox" id="storage_type" name="song.storage_type" style="width:150px;"
							data-options="
									url:'getDictList.action?type=storage_type',
									method:'post',
									editable:false,
									valueField:'dict_code',
									textField:'dict_value',
									panelHeight:'auto'" >
					</select>				
				</td>
				<td>播放时长</td>
				<td><input type="text" id="play_time"  name="song.play_time" class="easyui-timespinner"  data-options="editable:false,showSeconds:true"/></td>
				
			</tr>
				<tr>
				<td>流格式</td>
				<td><input type="text" id="flow_type"  name="song.flow_type" /></td>
				<td>码率</td>
				<td><input type="text" id="song_bit_rate"  name="song.song_bit_rate" />M</td>
			</tr>
			<tr>
				<td>备注</td>
				<td><input type="text" id="remark"  name="song.remark" /></td>
			</tr>
		
		</table>
	</form>

</div>
<div id="dlg-buttons" >
		<table cellpadding="0" cellspacing="0" style="width:100%">
			<tr>
				<td style="text-align:right">
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveMusic()">保存</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWin()">关闭</a>
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
					<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="importMusic()">导入</a>
					<a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeImportWin()">关闭</a>
				</td>
			</tr>
		</table>
</div>
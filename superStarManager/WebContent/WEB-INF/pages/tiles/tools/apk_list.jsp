<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(function() {
	$('#tt').datagrid( {
		title : 'APK版本管理',
		iconCls : 'icon-edit',
		rownumbers : true,
		height : 'auto',
		fit : true,
		pagination : true,
		fitColumns : false,
		url : 'apkInfoList.action',
		method : 'post',
		toolbar : "#toolbar",
		sortName : "create_time",
		sortOrder : "desc",
		columns : [ [ {
			field : 'apk_id',
			checkbox : true
		}, {
			field : 'apk_name',
			title : 'APK名称',
			width : 200
		}, {
			field : 'apk_version',
			title : 'APK版本号',
			width : 100
		}, {
			field : 'create_time',
			title : '导入时间',
			width : 60,
			formatter : function(value, row, index) {
				if (row.create_time) {
					return row.create_time.substring(0, 19);
				} else {
					return "";
				}
			}

		}, {
			field : 'state',
			title : '发布状态',
			width : 150,
			formatter : function(value, row, index) {
				if (row.state == 1) {
					return "已发布";
				} else if (row.state == 0) {
					return "未发布";
				} else if (row.state == 2) {
					return "预发布"
				}
				return row.is_activity;
			}
		} ] ]
	});
});
function searchApkInfo() {
	var gridparam = {
		"apkInfo.apk_name" : $("#s_apkName").val(),
		"apkInfo.apk_version" : $("#s_apkVersion").val(),
		"start_time" : $("#start_time").datebox("getValue"),
		"end_time" : $("#end_time").datebox("getValue")

	};
	$('#tt').datagrid( {
		url : 'searchApkInfo.action',
		queryParams : gridparam,
		pageNumber : 1
	});

	$('#searchForm').form('reset');
	$('#searchDialog').dialog('close');
}
function removeApkInfo() {
	var rows = $("#tt").datagrid("getSelections");
	var ids = "";
	if (rows.length == 0) {
		$.messager.alert('警告', "请选择要删除的记录!", 'info');
		return;
	} else {
		$.messager.confirm('删除记录', '确定要删除选中的记录吗?', function(result) {
			if (result) {
				for ( var i = 0; i < rows.length; i++) {
					ids += rows[i].apk_id + ",";
				}
				$.ajax( {
					url : 'removeApkInfo.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid( {
								url : 'apkInfoList.action',
								queryParams : "",
								pageNumber : 1
							});
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
function importApk() {
	$('#win').dialog('open').dialog('setTitle', '导入APK');
}

function validateForm() {
	var result = $("#saveApkInfo").validate( {
		//重写错误显示消息方法,以alert方式弹出错误消息   
		showErrors : function(errorMap, errorList) {
			var msg = "";
			var obj = errorList[0];
			if (obj != null) {

				msg = obj.message;
			}
			if (msg != "") {
				$.messager.alert('提示', msg, 'info');

			}
		},
		//失去焦点时不验证   
		onfocusout : false,
		onkeyup : false,
		onclick : false
	}).form();
	if (result) {
		var filepath = $('#apk_file').val();
		if (filepath.toString().length == 0) {
			$.messager.alert('提示', '文件路径为空，请选择要导入的文件', 'info');
			return false;
		} else {
			var strRegex = "(.apk)$"; //用于验证图片扩展名的正则表达式
			var re = new RegExp(strRegex);
			if (!re.test(filepath.toLowerCase())) {
				$.messager.alert('提示', '只能上传APK文件，请重新选择', 'info');
				return false;
			}
		}
		if (getFileName(filepath).length > 20) {
			$.messager.alert('提示', '文件名称过长', 'info');
			return false;
		}

	}
	return result;
}

function saveApkInfo() {
	$('#saveApkInfo').form('submit', {
		url : 'saveApkInfo.action',
		onSubmit : function() {
			//校验 return flase ;
		return validateForm();
		//return $(this).form('validate');
	},
	success : function(data) {
		var data = eval('(' + data + ')'); // change the JSON string to javascript object
		if (data.success) {
			$.messager.alert('提示', data.message, 'info');
			$('#saveApkInfo').form('reset');
			$('#win').dialog('close');
			$('#tt').datagrid( {
				url : 'apkInfoList.action',
				queryParams : "",
				pageNumber : 1
			});
		} else {
			$.messager.alert('警告', data.message, 'info');
		}
	}
	});
}

function closeWin() {
	$('#saveApkInfo').form('reset');
	$('#win').dialog('close');
}
function openSerchWin() {
	$('#searchDialog').dialog('open');
}
function closeSearchWin() {
	$('#searchForm').form('reset');
	$('#searchDialog').dialog('close');
}
function closeRoomWin() {
	$('#roomWin').dialog('close');
}

function pushApk() {
	var rows = $("#tt").datagrid("getSelections");
	var ids = "";
	if (rows.length == 0) {
		$.messager.alert('警告', "请选择要发布的记录!", 'info');
		return;
	} else if (rows.length > 1) {
		$.messager.alert('警告', "一次只能发布一条记录，请从新选择!", 'info');
		return;
	} else {
		if (rows[0].state == 1) {
			$.messager.alert('警告', "已发布的不能再次发布，请重新选择!", 'info');
			return;
		}
		$.messager.confirm('发布记录', '确定要发布选中的记录吗?', function(result) {
			if (result) {
				for ( var i = 0; i < rows.length; i++) {
					ids += rows[i].apk_id + ",";
				}
				$.ajax( {
					url : 'pushApk.action',
					data : {
						'ids' : ids
					},
					type : 'post',
					success : function(data) {
						var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = rows.length;
							$('#tt').datagrid( {
								url : 'apkInfoList.action',
								queryParams : "",
								pageNumber : 1
							});
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

function selectRoom() {
	var rows = $("#tt").datagrid("getSelections");
	if (rows.length == 0) {
		$.messager.alert('警告', "请选择要预发布的记录!", 'info');
		return;
	} else if (rows.length > 1) {
		$.messager.alert('警告', "一次只能预发布一条记录，请重新选择!", 'info');
	} else {
		if (rows[0].state == 2) {
			$.messager.alert('警告', "已预发布的不能再次预发布，请重新选择!", 'info');
			return;
		} else if (rows[0].state == 1) {
			$.messager.alert('警告', "已发布的不能预发布，请重新选择!", 'info');
			return;
		}
		$('#roomGrid').datagrid( {
			rownumbers : true,
			height : 'auto',
			fit : true,
			singleSelect : true,
			pagination : true,
			url : 'getRoomNum.action',
			queryParams : "",
			method : 'post',
			columns : [ [ {
				field : 'ck',
				checkbox : true
			}, {
				field : 'room_no',
				title : '包房编号',
				width : 150
			}, {
				field : 'oid',
				title : 'ID',
				width : 60,
				hidden : true
			} ] ]
		});
		$('#roomWin').dialog('open');
	}
}

function pushApkReady() {
	var ids = "";
	var rows = $("#roomGrid").datagrid("getSelections");
	var pushrows = $("#tt").datagrid("getSelections");
if (rows.length == 0) {
			$.messager.alert('警告', "请选择包房编号!", 'info');
			return;
		} 
		else
		{
			var roomId=rows[0].oid;
			var roomNum=rows[0].room_no;
			for ( var i = 0; i < pushrows.length; i++) {
						ids += pushrows[i].apk_id + ",";
			}
					
				$.ajax({
					url : 'pushApkReady.action',
					data : {
						'ids' : ids,
						'room_id' : roomId,
			            'room_num' : roomNum,
					},
					type : 'post',
					success : function(data) {
			        var data = eval('(' + data + ')'); // change the JSON string to javascript object
						if (data.success) {
							var pers = pushrows.length;
							$('#tt').datagrid({url:'apkInfoList.action',queryParams:"",pageNumber:1});
							$('#tt').datagrid('clearSelections');
							$('#roomWin').dialog('close');
							$.messager.alert('提示', "成功预发布" + pers + "条记录",
									'info');
						} else {
							$.messager.alert('警告', "预发布失败!", 'info');
						}
					}
				});
			}
		}
	
</script>
<table id="tt">
</table>
<div id="toolbar">
	<!-- <a href="#" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">新增</a> -->
	<a href="#" class="easyui-linkbutton" iconCls="icon-search"
		plain="true" onclick="openSerchWin()">查询</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove"
		plain="true" onclick="removeApkInfo()">删除</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-print" plain="true"
		onclick="importApk()">导入</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove"
		plain="true" onclick="selectRoom()">预发布</a>
	<a href="#" class="easyui-linkbutton" iconCls="icon-remove"
		plain="true" onclick="pushApk()">发布</a>
</div>
<div id="searchDialog" class="easyui-dialog"
	style="padding: 5px; width: 600px; height: 400px;" title="查询"
	iconCls="icon-ok" closed="true" modal="true" buttons="#search-buttons">
	<form id="searchForm" method="post">
		<table>
			<tr>
				<td>
					APK名称
				</td>
				<td>
					<input type="text" id="s_apkName" />
				</td>
				<td>
					APK版本
				</td>
				<td>
					<input type="text" id="s_apkVersion" />
				</td>
			</tr>
			<tr>
				<td>
					提交时间
				</td>
				<td>
					<input type="text" id="start_time" class="easyui-datebox"
						data-options="editable:false" />
				</td>
				<td>
					至
				</td>
				<td>
					<input type="text" id="end_time" class="easyui-datebox"
						data-options="editable:false" />
				</td>
			</tr>
		</table>

	</form>
</div>
<div id="search-buttons">
	<table cellpadding="0" cellspacing="0" style="width: 100%">
		<tr>
			<td style="text-align: right">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search"
					onclick="searchApkInfo()">查询</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
					onclick="closeSearchWin()">关闭</a>
			</td>
		</tr>
	</table>
</div>
<div id="win" class="easyui-dialog"
	style="padding: 5px; width: 500px; height: 300px;" title="My Dialog"
	iconCls="icon-ok" closed="true" modal="true" buttons="#dlg-buttons">
	<form id="saveApkInfo" method="post" enctype="multipart/form-data">
		<table>
			<tr>
				<td>
					上传APK文件
				</td>
				<td>
					<input type="hidden" id="apk_id" name="apkInfo.apk_id" />
					<s:file id="apk_file" name="apk_file"></s:file>
				</td>

			</tr>
			<%--			<tr>--%>
			<%--				<td>APK版本号</td>--%>
			<%--				<td><input type="text" id="apk_version" name="apkInfo.apk_version"  /></td>--%>
			<%--			</tr>--%>
		</table>
	</form>

</div>
<div id="dlg-buttons">
	<table cellpadding="0" cellspacing="0" style="width: 100%">
		<tr>
			<td style="text-align: right">
				<a href="#" class="easyui-linkbutton" iconCls="icon-save"
					onclick="saveApkInfo()">上传</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
					onclick="closeWin()">关闭</a>
			</td>
		</tr>
	</table>
</div>
<div id="roomWin" class="easyui-dialog" title="My Dialog"
	iconCls="icon-ok" closed="true" modal="true"
	style="width: 700px; height: 400px;" buttons="#music-buttons"
	toolbar='#music-toolbar'>
	<table id="roomGrid">
	</table>
</div>
<div id="music-buttons">
	<table cellpadding="0" cellspacing="0" style="width: 100%">
		<tr>
			<td style="text-align: right">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search"
					onclick="pushApkReady()">确定</a>
				<a href="#" class="easyui-linkbutton" iconCls="icon-cancel"
					onclick="closeRoomWin()">关闭</a>
			</td>
		</tr>
	</table>
</div>
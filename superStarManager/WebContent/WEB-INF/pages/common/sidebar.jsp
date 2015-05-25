<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(function () {
	    $("#tree").tree();
	}); 
</script>

<ul id="tree">
<s:if test="%{#session['key.session.current.user'].role_id == 1}">
				<li>
					<span>曲库管理</span>
					<ul>
						<li><a href="<s:url namespace="/library" action="goSingerList" />">歌星管理</a></li>
						<li><a href="<s:url namespace="/library" action="goMusicList" />">曲库管理</a></li>
						<%-- <li><a href="<s:url namespace="/" action="" />" >歌曲处理</a></li>
						<li><a href="<s:url namespace="/" action="" />" >曲库同步</a></li> --%>
						<li><a href="<s:url namespace="/library" action="goSupportList" />" >补歌清单</a></li>
					</ul>
				</li>
</s:if>

<s:if test="%{#session['key.session.current.user'].role_id == 2}">
				<li>
					<span>云K终端设置</span> 
					<ul>
						<li><a href="<s:url namespace="/interface" action="goStartList" />" >初始界面设置</a></li>
						<li><a href="<s:url namespace="/interface" action="goSleepList" />" >屏保界面设置</a></li>
						<li><a href="<s:url namespace="/interface" action="goPlaceList" />" >场所介绍界面设置</a></li>
						<li><a href="<s:url namespace="/interface" action="goFirePicList" />" >消防图解界面设置</a></li>
						<li><a href="<s:url namespace="/interface" action="goAdvert" />" >广告发布</a></li>
<!--						<li><a href="<s:url namespace="/interface" action="goAdvertVideo" />" >宣传片管理</a></li>-->
						<li><a href="<s:url namespace="/interface" action="goAdvertVideo1" />" >宣传片管理</a></li>
						<li><a href="<s:url namespace="/interface" action="goActivity" />" >营销活动</a></li>
						<li><a href="<s:url namespace="/interface" action="goPassMusic" />" >过路歌曲设置</a></li>
						<li><a href="<s:url namespace="/interface" action="goIntroduce" />" >新歌推荐(歌曲推荐)</a></li>
<%-- 						<li><a href="<s:url namespace="/interface" action="goNewStar" />" >新星推荐</a></li> --%>
						<li><a href="<s:url namespace="/interface" action="goMainStar" />" >主打歌星(歌星推荐)</a></li>
						<li>
							<span>排行统计</span> 
							<ul>
								<li><a href="<s:url namespace="/interface" action="goMusicRank" />" >歌曲排行</a></li>
								<li><a href="<s:url namespace="/interface" action="goNewMusicRank" />" >新歌排行</a></li>
								<li><a href="<s:url namespace="/interface" action="goVodMusicRank" />" >大歌星排行</a></li>
							</ul>
						</li>
						
						<li><a href="<s:url namespace="/interface" action="gojuke" />" >点唱记录</a></li>
						<li><a href="<s:url namespace="/interface" action="goMovieInfo" />" >影视管理</a></li>
						<li><a href="<s:url namespace="/interface" action="goChannel" />" >视频转播设置</a></li>
						<li><a href="<s:url namespace="/interface" action="goGame" />" >游戏管理</a></li>
						<li><a href="<s:url namespace="/interface" action="goDynamicTitle" />" >跑马灯字幕</a></li>
						<li><a href="<s:url namespace="/interface" action="goResearchType" />" >意见调查类型设置</a></li>
						<li><a href="<s:url namespace="/interface" action="goAdvice" />" >意见调查</a></li>
					</ul>
				</li>
</s:if>

<s:if test="%{#session['key.session.current.user'].role_id == 3}">
				<li>
					<span>设备管理</span> 
					<ul>
						<li><a href="<s:url namespace="/tools" action="goApkList" />" >APK版本管理</a></li>
						<li><a href="<s:url namespace="/tools" action="goSqliteList" />" >SQLite版本管理</a></li>
						<li><a href="<s:url namespace="/tools" action="goDeviceMonitor" />" >包房设备监控</a></li>
						<li><a href="<s:url namespace="/" action="" />" >包房设备远程配置</a></li>
						<li><a href="<s:url namespace="/" action="" />" >设备软件更新</a></li>
					</ul>
				</li>
</s:if>

<s:if test="%{#session['key.session.current.user'].role_id == 4}">
				<li>
					<span>系统管理</span>
					<ul>
						<li><a href="<s:url namespace='/config' action='goUserList' />" >人员管理</a></li>
						<li><a href="<s:url namespace='/config' action='goStoreInfoList' />" >门店管理</a></li> 
						<%-- <li><a href="<s:url namespace='/config' action='goMemberList' />" >会员管理</a></li> --%>
						<li><a href="<s:url namespace='/config' action='goSysLogList' />" >系统日志</a></li>
						<li><a href="<s:url namespace='/config' action='goOperationList' />" >操作日志</a></li>
						<li><a href="<s:url namespace='/config' action='goDictList' />" >字典管理</a></li>
						<li><a href="<s:url namespace='/config' action='goSysParam' />" >系统参数</a></li>
					</ul>
				</li>
</s:if>
	</ul>
<%-- 
<s:if test="%{#session['key.session.current.user'].role_id == 1}">
	<ul>
		<li><a href="<s:url namespace="/library" action="goSingerList" />" ">歌星管理</a></li>
		<li><a href="<s:url namespace="/library" action="goMusicList" />" ">曲库管理</a></li>
		<li><a href="<s:url namespace="/" action="" />" ">歌曲处理</a></li>
		<li><a href="<s:url namespace="/" action="" />" ">曲库同步</a></li>
		<li><a href="<s:url namespace="/library" action="goSupportList" />" ">补歌清单</a></li>
	</ul>


</s:if>

<s:if test="%{#session['key.session.current.user'].role_id == 2}">
	<ul>
		<li><a href="<s:url namespace="/interface" action="goStartList" />" ">初始界面设置</a></li>
		<li><a href="<s:url namespace="/interface" action="goSleepList" />" ">屏保界面设置</a></li>
		<li><a href="<s:url namespace="/interface" action="goAdvert" />" ">广告发布</a></li>
		<li><a href="<s:url namespace="/interface" action="goAdvertVideo" />" ">宣传片管理</a></li>
		<li><a href="<s:url namespace="/interface" action="goActivity" />" ">营销活动</a></li>
		<li><a href="<s:url namespace="/interface" action="goPassMusic" />" ">过路歌曲设置</a></li>
		<li><a href="<s:url namespace="/interface" action="goIntroduce" />" ">新歌推荐(歌曲推荐)</a></li>
		<li><a href="<s:url namespace="/interface" action="goNewStar" />" ">新星推荐</a></li>
		<li><a href="<s:url namespace="/interface" action="goMainStar" />" ">主打歌星(歌星推荐)</a></li>
		<li><a href="<s:url namespace="/interface" action="goMusicRank" />" ">点唱排行</a></li>
		<li><a href="<s:url namespace="/interface" action="gojuke" />" ">点唱记录</a></li>
		<li><a href="<s:url namespace="/interface" action="goMovieInfo" />" ">影视管理</a></li>
		<li><a href="<s:url namespace="/interface" action="goChannel" />" ">视频转播设置</a></li>
		<li><a href="<s:url namespace="/interface" action="goGame" />" ">游戏管理</a></li>
			<li><a href="<s:url namespace="/interface" action="goDynamicTitle" />" ">跑马灯字幕</a></li>
		<li><a href="<s:url namespace="/interface" action="goResearchType" />" ">意见调查类型设置</a></li>
		<li><a href="<s:url namespace="/interface" action="goAdvice" />" ">意见调查</a></li>
	</ul>

</s:if>

<s:if test="%{#session['key.session.current.user'].role_id == 3}">
	<ul>
		<li><a href="<s:url namespace="/tools" action="goApkList" />" ">APK版本管理</a></li>
		<li><a href="<s:url namespace="/tools" action="goSqliteList" />" ">SQLite版本管理</a></li>
		<li><a href="<s:url namespace="/tools" action="goDeviceMonitor" />" ">包房设备监控</a></li>
		<li><a href="<s:url namespace="/" action="" />" ">包房设备远程配置</a></li>
		<li><a href="<s:url namespace="/" action="" />" ">设备软件更新</a></li>
	</ul>
</s:if>
<s:if test="%{#session['key.session.current.user'].role_id == 4}">

	<ul>
		<li><a href="<s:url namespace='/config' action='goUserList' />" ">人员管理</a></li>
		<li><a href="<s:url namespace='/config' action='goMemberList' />" ">会员管理</a></li>
		<li><a href="<s:url namespace='/config' action='goSysLogList' />" ">系统日志</a></li>
		<li><a href="<s:url namespace='/config' action='goOperationList' />" ">操作日志</a></li>
		<li><a href="<s:url namespace='/config' action='goDictList' />" ">字典管理</a></li>
		<li><a href="<s:url namespace='/config' action='goSysParam' />" ">系统参数</a></li>
	</ul>
</s:if> --%>




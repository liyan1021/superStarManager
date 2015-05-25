package com.liyan.superstar.service.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyan.common.AppContext;
import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.CommonFiled;
import com.liyan.superstar.dao.MusicRankingDao;
import com.liyan.superstar.model.MusicRanking;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.MusicRankingService;
import com.liyan.superstar.service.OperationLogService;

@Service
@Transactional
public class MusicRankingServiceImpl implements MusicRankingService {
	
	@Autowired
	private MusicRankingDao musicRankingDao;
	
	@Autowired
	private MsgBean msgBean ; 
	
	@Autowired
	private OperationLogService optLogService ; 
	
	public MusicRankingDao getMusicRankingDao() {
		return musicRankingDao;
	}

	public void setMusicRankingDao(MusicRankingDao musicRankingDao) {
		this.musicRankingDao = musicRankingDao;
	}

	public MsgBean getMsgBean() {
		return msgBean;
	}

	public void setMsgBean(MsgBean msgBean) {
		this.msgBean = msgBean;
	}

	public OperationLogService getOptLogService() {
		return optLogService;
	}

	public void setOptLogService(OperationLogService optLogService) {
		this.optLogService = optLogService;
	}

	@Override
	public Pagination<MusicRanking> query(MusicRanking musicRanking,
			Integer page, Integer rows, String sort, String order) {
		return this.musicRankingDao.query(musicRanking, page, rows, sort, order);
	}

	@Override
	public Pagination<MusicRanking> searchMusicRank(MusicRanking musicRanking,
			Integer page, Integer rows, String sort, String order) {
		return this.musicRankingDao.query(musicRanking, page, rows, sort, order);
	}

	@Override
	public MsgBean editMusicRank(MusicRanking musicRanking, int song_number) {
		try{
			MusicRanking find = this.musicRankingDao.find(musicRanking.getMusic_id());
			Integer music_all_song_number = new Integer(find.getMusic_all_song_number())+song_number;
			Integer music_year_song_number = new Integer(find.getMusic_year_song_number())+song_number;
			Integer music_month_song_number = new Integer(find.getMusic_month_song_number())+song_number;
			find.setMusic_all_song_number(music_all_song_number.toString());
			find.setMusic_year_song_number(music_year_song_number.toString());
			find.setMusic_month_song_number(music_month_song_number.toString());
			this.musicRankingDao.modify(find);
			
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "点唱排行" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY ; 
			getOptLogService().setLog(currentUser,function,type);
			msgBean.setSign(true);
			msgBean.setMsg("修改成功");
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("修改失败");
		}
		return msgBean ;
	}
	
	
}

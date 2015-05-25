package com.liyan.superstar.service.impl;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyan.common.AppContext;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.CommonFiled;
import com.liyan.superstar.dao.JukeRankDao;
import com.liyan.superstar.dto.JukeRankDto;
import com.liyan.superstar.model.JukeRank;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.JukeRankService;
import com.liyan.superstar.service.OperationLogService;

@Service
@Transactional
public class JukeRankServiceImpl implements JukeRankService {
	@Autowired
	private JukeRankDao jukeRankDao ;
	
	@Autowired
	private OperationLogService optLogService ;
	
	@Override
	public Pagination<JukeRankDto> statJukeRank(JukeRank jukeRank,Integer page,Integer rows) {
		
		return this.jukeRankDao.statJukeRank(jukeRank, page, rows);
	}

	@Override
	public Pagination<JukeRank> searchJuke(JukeRank jukeRank, Integer page,
			Integer rows) {
		
		Pagination<JukeRank> query = this.jukeRankDao.query(jukeRank, page, rows);
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "点唱记录" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH; 

		this.optLogService.setLog(currentUser,function,type);
		return query;
	}

	@Override
	public Pagination<JukeRank> query(JukeRank jukeRank, Integer page,
			Integer rows) {
		return this.jukeRankDao.query(jukeRank,page,rows);
	}

	public JukeRankDao getJukeRankDao() {
		return jukeRankDao;
	}

	public void setJukeRankDao(JukeRankDao jukeRankDao) {
		this.jukeRankDao = jukeRankDao;
	}

	public OperationLogService getOptLogService() {
		return optLogService;
	}

	public void setOptLogService(OperationLogService optLogService) {
		this.optLogService = optLogService;
	} 
}

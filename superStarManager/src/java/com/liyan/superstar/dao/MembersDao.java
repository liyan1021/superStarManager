package com.liyan.superstar.dao;

import org.springframework.stereotype.Repository;

import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.model.Members;

@Repository
public class MembersDao extends GenericDaoImpl<Members, String>{

	public Pagination<Members> query(Members members, Integer page, Integer rows) {
		QueryCriteria query = new QueryCriteria();
		if(members!=null){
			if(!CommonUtils.isEmpty(members.getMember_name())){
				
				query.addEntry("member_name", "like","%"+members.getMember_name()+"%");
			}
			if(!CommonUtils.isEmpty(members.getMember_code())){
				
				query.addEntry("member_code", "like","%"+members.getMember_code()+"%");
			}
			if(!CommonUtils.isEmpty(members.getMember_tel())){
				
				query.addEntry("member_tel", "like","%"+members.getMember_tel()+"%");
			}
		}
		return this.findPage(query, page, rows);
	}

}

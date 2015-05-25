package com.liyan.superstar.dao;


import java.util.List;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Repository;

import com.liyan.superstar.model.User;
import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;

@Repository
public class UserDao extends GenericDaoImpl<User, String> {

	public Pagination<User> query(User user,String start_time,String end_time, Integer page, Integer rows,String sort,String order) {
		
		QueryCriteria queryCriteria = new QueryCriteria();
		if(user!=null){
			
			if(!CommonUtils.isEmpty(user.getUser_code())){
				queryCriteria.addEntry("user_code", "like", "%" + user.getUser_code() + "%");
				
			}
			if(!CommonUtils.isEmpty(user.getUser_name())){
				queryCriteria.addEntry("user_name", "like", "%" + user.getUser_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(user.getOrg_name())){
				queryCriteria.addEntry("org_name", "like", "%" + user.getOrg_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(user.getOffi_name())){
				queryCriteria.addEntry("offi_name", "like", "%" + user.getOffi_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(user.getRole_id()) && !user.getRole_id().equals("0")){
				queryCriteria.addEntry("role_id", "=", user.getRole_id() );
			}
			if(!CommonUtils.isEmpty(user.getTel_num())){
				queryCriteria.addEntry("tel_num", "like","%"+ user.getTel_num()+"%" );
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(start_time))){
				queryCriteria.addEntry("create_time", ">=", start_time +" 00:00:00");
			}
			if(!CommonUtils.isEmpty(CommonUtils.nullToBlank(end_time))){
				queryCriteria.addEntry("create_time", "<=", end_time +" 23:59:59");
			}
		}
		queryCriteria.addEntry("is_activity", "=", "0");
		if(!CommonUtils.isEmpty(sort)){
			if(order.equals("asc")){
				queryCriteria.asc(sort);
			}else if(order.equals("desc")){
				queryCriteria.desc(sort);
			}
		}
		return this.findPage(queryCriteria,page,rows);
		
	}

	public User getUserByUsercode(String user_code) {
		User result = null;
		try {
            result = (User)entityManager.createQuery("SELECT o FROM User o WHERE o.user_code = :user_code and o.is_activity = :is_activity")
                    .setParameter("user_code", user_code)
                    .setParameter("is_activity","0")
                    .getSingleResult();
            
        } catch (NoResultException e) {
        	  e.printStackTrace();
        }
        return result;
	}

	public List<User> queryList(User user) {
		QueryCriteria queryCriteria = new QueryCriteria();
		if(user!=null){
			
			if(!CommonUtils.isEmpty(user.getUser_code())){
				queryCriteria.addEntry("user_code", "like", "%" + user.getUser_code() + "%");
				
			}
			if(!CommonUtils.isEmpty(user.getUser_name())){
				queryCriteria.addEntry("user_name", "like", "%" + user.getUser_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(user.getOrg_name())){
				queryCriteria.addEntry("org_name", "like", "%" + user.getOrg_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(user.getOffi_name())){
				queryCriteria.addEntry("offi_name", "like", "%" + user.getOffi_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(user.getRole_id()) && !user.getRole_id().equals("0")){
				queryCriteria.addEntry("role_id", "=", user.getRole_id() );
			}
			if(!CommonUtils.isEmpty(user.getTel_num())){
				queryCriteria.addEntry("tel_num", "like","%"+ user.getTel_num()+"%" );
			}
			
		}
		queryCriteria.addEntry("is_activity", "=", "0");
		return this.find(queryCriteria);
	}

	public List<User> findByUserCode(User user) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("user_code", "=",user.getUser_code());
		query.addEntry("is_activity", "=", "0");
		return this.find(query);
	}


}

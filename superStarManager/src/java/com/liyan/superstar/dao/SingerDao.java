package com.liyan.superstar.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.liyan.superstar.model.Singer;
import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
@Repository
public class SingerDao  extends GenericDaoImpl<Singer, String>{

	public Pagination<Singer> query(Singer singer, Integer page, Integer rows, String sortName, String sortOrder) {
		QueryCriteria query = new QueryCriteria();
		if(singer!=null){
			
			if(!CommonUtils.isEmpty(singer.getStar_name())){
				query.addEntry("star_name", "like", "%" + singer.getStar_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(singer.getOther_name())){
				query.addEntry("other_name", "like", "%" + singer.getOther_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(singer.getSpell_first_letter_abbreviation())){
				query.addEntry("spell_first_letter_abbreviation", "like", "%" + singer.getSpell_first_letter_abbreviation() + "%");
				
			}
			if(!CommonUtils.isEmpty(singer.getStar_type())){
				query.addEntry("star_type", "like", "%" + singer.getStar_type() + "%");
				
			}
			if(!CommonUtils.isEmpty(singer.getArea())){
				query.addEntry("area", "like", "%" + singer.getArea() + "%");
				
			}
			if(!CommonUtils.isEmpty(singer.getClick_number())){
				query.addEntry("click_number", "like", "%" + singer.getClick_number() + "%");
				
			}
			if(!CommonUtils.isEmpty(singer.getRemark())){
				query.addEntry("remark", "like", "%" + singer.getRemark() + "%");
				
			}
		}
		query.addEntry("is_activity", "=", "0");
		if(!CommonUtils.isEmpty(sortName)){
			if(sortOrder.equals("asc")){
				query.asc(sortName);
			}else if(sortOrder.equals("desc")){
				query.desc(sortName);
			}
		}
		return this.findPage(query,page,rows);
	}

	public ArrayList<Singer> query(Singer singer) {
		QueryCriteria query = new QueryCriteria();
		if(singer!=null){
			
			if(!CommonUtils.isEmpty(singer.getStar_name())){
				query.addEntry("star_name", "like", "%" + singer.getStar_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(singer.getOther_name())){
				query.addEntry("other_name", "like", "%" + singer.getOther_name() + "%");
				
			}
			if(!CommonUtils.isEmpty(singer.getSpell_first_letter_abbreviation())){
				query.addEntry("spell_first_letter_abbreviation", "like", "%" + singer.getSpell_first_letter_abbreviation() + "%");
				
			}
			if(!CommonUtils.isEmpty(singer.getStar_type())){
				query.addEntry("star_type", "like", "%" + singer.getStar_type() + "%");
				
			}
			if(!CommonUtils.isEmpty(singer.getArea())){
				query.addEntry("area", "like", "%" + singer.getArea() + "%");
				
			}
			if(!CommonUtils.isEmpty(singer.getClick_number())){
				query.addEntry("click_number", "like", "%" + singer.getClick_number() + "%");
				
			}
			if(!CommonUtils.isEmpty(singer.getRemark())){
				query.addEntry("remark", "like", "%" + singer.getRemark() + "%");
				
			}
		}
		query.addEntry("is_activity", "=", "0");
		return (ArrayList<Singer>) this.find(query);
	}


	public List<Singer> findByName(String singerName) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("star_name", "=", singerName);
		query.addEntry("is_activity", "=", "0");
		List<Singer> find = this.find(query);
		/*if(find.size() != 0){
			singer = find.get(0);
		}*/
		return find ; 
	}
	public List<Singer> findLikeByName(String singerName) {
		Singer singer = null ; 
		QueryCriteria query = new QueryCriteria();
		query.addEntry("star_name", "like", singerName+"%");
		query.addEntry("is_activity", "=", "0");
		List<Singer> find = this.find(query);
		/*if(find.size() != 0){
			singer = find.get(0);
		}*/
		return find ; 
	}

	public List<Singer> getSingerList() {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("is_activity", "=", "0");
		List<Singer> find = this.find(query);
		return find ; 
	}


}

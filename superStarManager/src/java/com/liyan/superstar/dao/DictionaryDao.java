package com.liyan.superstar.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.liyan.superstar.model.Dictionary;
import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.impl.GenericDaoImpl;
import com.liyan.common.util.CommonUtils;
@Repository
public class DictionaryDao extends GenericDaoImpl<Dictionary, String> {
	
	public Pagination<Dictionary> query(Dictionary dict,Integer page, Integer rows, String sort, String order){
		
		QueryCriteria queryCriteria = new QueryCriteria();
		if(dict!=null){
			if(!CommonUtils.isEmpty(dict.getDict_type())){
				queryCriteria.addEntry("dict_type", "like", "%" + dict.getDict_type() + "%");
				
			}
			if(!CommonUtils.isEmpty(dict.getDict_code())){
				queryCriteria.addEntry("dict_code", "like", "%" + dict.getDict_code() + "%");
				
			}
			if(!CommonUtils.isEmpty(dict.getDict_value())){
				queryCriteria.addEntry("dict_value", "like", "%" + dict.getDict_value() + "%");
				
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
		return this.findPage(queryCriteria, page, rows);
	}

	/**
	 * 通过类型与code 获取名称
	 * @param type
	 * @param code
	 * @return
	 */
	public Dictionary getDictByTypeCode(String type, String code) {
		QueryCriteria queryCriteria = new QueryCriteria();
			if(!CommonUtils.isEmpty(type)){
				queryCriteria.addEntry("dict_type", "=", type);
				
			}
			if(!CommonUtils.isEmpty(code)){
				queryCriteria.addEntry("dict_code", "=", code);
				
			}
			queryCriteria.addEntry("is_activity", "=", "0");
			List<Dictionary> find = this.find(queryCriteria);
			if(find.size()>0){
				return find.get(0);
			}else{
				return null; 
			}
	}

	/**
	 * 通过类型CODE或字典值 获取DICT
	 * @param string
	 * @param value
	 * @return
	 */
	public Dictionary getDictByTypeName(String type, String value) {
		QueryCriteria queryCriteria = new QueryCriteria();
		if(!CommonUtils.isEmpty(type)){
			queryCriteria.addEntry("dict_type_code", "=", type);
			
		}
		if(!CommonUtils.isEmpty(value)){
			queryCriteria.addEntry("dict_value", "=", value);
			
		}
		queryCriteria.addEntry("is_activity", "=", "0");
		List<Dictionary> find = this.find(queryCriteria);
		if(find.size()>0){
			return find.get(0);
		}else{
			return null; 
		}
	}

	public List<Dictionary> findBySort(Dictionary dict) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("dict_sort", "=",dict.getDict_sort());
		query.addEntry("is_activity", "=", "0");
		query.addEntry("dict_type_code", "=", dict.getDict_type_code());
		return this.find(query);
	}

	public List<Dictionary> findByDictValue(Dictionary dict) {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("dict_value", "=",dict.getDict_value());
		query.addEntry("is_activity", "=", "0");
		query.addEntry("dict_type_code", "=", dict.getDict_type_code());
		return this.find(query);
	}

	public List<Dictionary> getDictList() {
		QueryCriteria query = new QueryCriteria();
		query.addEntry("is_activity", "=", "0");
		return this.find(query);
	}
}

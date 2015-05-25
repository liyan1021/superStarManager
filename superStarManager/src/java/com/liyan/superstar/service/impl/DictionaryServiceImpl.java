package com.liyan.superstar.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyan.common.action.MsgBean;
import com.liyan.common.util.CommonUtils;
import com.liyan.superstar.dao.DictionaryDao;
import com.liyan.superstar.model.Dictionary;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.service.DictionaryService;

@Service
@Transactional
public class DictionaryServiceImpl implements DictionaryService{
	
	@Autowired
	private DictionaryDao dictDao ;
	@Autowired
	private MsgBean msgBean ; 
	@Override
	public MsgBean checkDictSort(Dictionary dict) {
		try{
			Dictionary find = this.dictDao.find(CommonUtils.nullToBlank(dict.getId()));
			//修改验证唯一
			if(find!=null){
				//1：先判断是否修改
				if(!find.getDict_sort().equals(dict.getDict_sort())){ 
					//2：验证修改的名称是否存在数据库
					List<Dictionary> list = this.dictDao.findBySort(dict);
					if(list.size()!=0){
						msgBean.setSign(true);
					}else{
						msgBean.setSign(false);
					}
				}else{
					msgBean.setSign(false);
				}
				
			}else{
				//新增验证是否存在数据库
				List<Dictionary> list = this.dictDao.findBySort(dict);
				if(list.size()!=0){
					msgBean.setSign(true);
				}else{
					msgBean.setSign(false);
				}
				return msgBean ; 
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return msgBean;
		
	}
	
	@Override
	public MsgBean checkDictValue(Dictionary dict) {
		try{
			Dictionary find = this.dictDao.find(CommonUtils.nullToBlank(dict.getId()));
			//修改验证唯一
			if(find!=null){
				//1：先判断是否修改
				if(!find.getDict_value().equals(dict.getDict_value())){ 
					//2：验证修改的名称是否存在数据库
					List<Dictionary> list = this.dictDao.findByDictValue(dict);
					if(list.size()!=0){
						msgBean.setSign(true);
					}else{
						msgBean.setSign(false);
					}
				}else{
					msgBean.setSign(false);
				}
				
			}else{
				//新增验证是否存在数据库
				List<Dictionary> list = this.dictDao.findByDictValue(dict);
				if(list.size()!=0){
					msgBean.setSign(true);
				}else{
					msgBean.setSign(false);
				}
				return msgBean ; 
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return msgBean;
	}

	public DictionaryDao getDictDao() {
		return dictDao;
	}
	public void setDictDao(DictionaryDao dictDao) {
		this.dictDao = dictDao;
	}
	
}

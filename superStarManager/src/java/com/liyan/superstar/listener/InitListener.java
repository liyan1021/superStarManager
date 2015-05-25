package com.liyan.superstar.listener;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.liyan.superstar.dao.DictionaryDao;
import com.liyan.superstar.dao.RoomDao;
import com.liyan.superstar.dao.StoreInfoDao;
import com.liyan.superstar.model.Dictionary;
import com.liyan.superstar.model.Room;
import com.liyan.superstar.model.StoreInfo;
import com.liyan.common.AppContext;



public class InitListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent event) {

    }
    
    @Autowired
    private DictionaryDao dictionaryDao;
    @Autowired
    private StoreInfoDao storeInfoDao;
    @Autowired
    private RoomDao  roomDao;
    
    @Override
    public void contextInitialized(ServletContextEvent event) {
    	WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(event.getServletContext());
    	dictionaryDao = (DictionaryDao) applicationContext.getBean("dictionaryDao"); 
    	storeInfoDao = (StoreInfoDao) applicationContext.getBean("storeInfoDao");
    	roomDao = (RoomDao) applicationContext.getBean("roomDao");
    	List<Dictionary> dictList = dictionaryDao.findAll(); // 取出所有字典
    	List<StoreInfo> storeInfoList = storeInfoDao.findAll();
    	List<Room> roomList = roomDao.findAll();
    	Map<String, Map<String, String>> dictMap = new HashMap<String, Map<String, String>>();
    	Map<String, String> storeInfoMap = new HashMap<String,  String>();
    	Map<String, String> roomMap = new HashMap<String,  String>();
    	Set<String> dictTypeSet = new HashSet<String>();
    	Set<String> storeInfoTypeSet = new HashSet<String>();
    	Set<String> dynamicTitleSet = new HashSet<String>();
    	for (Dictionary dictionary : dictList) {
			dictTypeSet.add(dictionary.getDict_type_code());
		}
    	for (String dictType : dictTypeSet) {
			Map<String, String> tmpMap = new TreeMap<String, String>();
			dictMap.put(dictType, tmpMap);
			for (Dictionary dictionary : dictList) {
				if (dictType.equals(dictionary.getDict_type_code())) {
					tmpMap.put(dictionary.getDict_code(), dictionary.getDict_value());
				}
			}
		}
    	//门店
    	for(StoreInfo storeInfo : storeInfoList){
    		if(storeInfo.getIs_activity().equals("0"))
    			storeInfoTypeSet.add(storeInfo.getStore_name());	
    	}
			for (StoreInfo storeInfo : storeInfoList) {
				if(storeInfo.getIs_activity().equals("0"))
					storeInfoMap.put(storeInfo.getStore_code(),storeInfo.getStore_name());
	
		}
    	
			for(Room room:roomList){
    		dynamicTitleSet.add(room.getRoom_no());
			}
    	for(Room room:roomList)	{
    		roomMap.put(room.getOid(),room.getRoom_no());
    	}
			
    	AppContext.getInstance().put(AppContext.getInstance().getString("key.dict"), dictMap); // 放入AppContext对象中，appContext中setAttribute()放入JSP页中的appContext对象
    	AppContext.getInstance().put(AppContext.getInstance().getString("key.storeinfo"),storeInfoMap ); // 放入AppContext对象中，appContext中setAttribute()放入JSP页中的appContext对象
    	AppContext.getInstance().put(AppContext.getInstance().getString("key.room"),roomMap ); // 放入AppContext对象中，appContext中setAttribute()放入JSP页中的appContext对象

    }
	public DictionaryDao getDictionaryDao() {
		return dictionaryDao;
	}
	public void setDictionaryDao(DictionaryDao dictionaryDao) {
		this.dictionaryDao = dictionaryDao;
	}
	public StoreInfoDao getStoreInfoDao() {
		return storeInfoDao;
	}
	public void setStoreInfoDao(StoreInfoDao storeInfoDao) {
		this.storeInfoDao = storeInfoDao;
	}
	public RoomDao getRoomDao() {
		return roomDao;
	}
	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}
	
    
}

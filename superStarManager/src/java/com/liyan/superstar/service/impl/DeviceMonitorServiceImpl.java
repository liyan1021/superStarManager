package com.liyan.superstar.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyan.common.AppContext;
import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.common.util.Param;
import com.liyan.superstar.dao.DeviceMonitorDao;
import com.liyan.superstar.dao.RoomDao;
import com.liyan.superstar.model.DeviceMonitor;
import com.liyan.superstar.model.Room;
import com.liyan.superstar.model.User;
import com.liyan.superstar.service.DeviceMonitorService;
import com.liyan.superstar.service.OperationLogService;

@Service
@Transactional
public class DeviceMonitorServiceImpl implements DeviceMonitorService{
	@Autowired
	private DeviceMonitorDao deviceMonitorDao;

	@Autowired
	private OperationLogService optLogService ; 
	
	@Autowired
	private RoomDao roomDao; 
	
	private MsgBean msgBean ; 
	private static String ip = "192.169.1.12";
	public static final Log log = LogFactory.getLog(DeviceMonitorServiceImpl.class);
	@Override
	public Pagination<DeviceMonitor> query(DeviceMonitor deviceMonitor,
			Integer page, Integer rows, String sort, String order) {
		return this.deviceMonitorDao.query(deviceMonitor, page, rows, sort, order);
	}

	@Override
	public Pagination<DeviceMonitor> searchDeviceMonitor(
			DeviceMonitor deviceMonitor, Integer page, Integer rows,
			String sort, String order) {
		Pagination<DeviceMonitor> query = this.deviceMonitorDao.query(deviceMonitor, page, rows, sort, order);
		
		
		//操作日志录入
		HttpSession session = ServletActionContext.getRequest().getSession();
		String key = AppContext.getInstance().getString("key.session.current.user");
	    //操作人
		User currentUser = (User) session.getAttribute(key);
		//操作功能
		String function = "包房设备监控" ; 		
	    //操作类型
		String type = CommonFiled.OPT_LOG_SEARCH; 

		this.optLogService.setLog(currentUser,function,type);
		
		return query;
	}

	@Override
	public Pagination<Room> openRoomList(Integer page, Integer rows,
			String sort, String order) {
		Pagination<Room> query = this.roomDao.findPage(new QueryCriteria(), page, rows);
		
		return query;
	}
	private static void openRoom(String room_no){
		//向业务管控发送指令，查看包房的状态
		Socket socket = null;
		BufferedWriter writer = null;
		try {
			socket = new Socket(ip,Param.SOCKET_KLT_PORT);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Param.CHARSET_UTF8));
			JSONObject obj = new JSONObject();
			JSONObject data = new JSONObject();
			obj.put("command", "open");
			obj.put("roomNo", room_no);
			data.put("oldRoomNo", "");
			data.put("transactionId", UUID.randomUUID().toString());
			obj.put("data", data);
			writer.write(obj.toString());
			writer.flush();
			log.info("发送开房指令信息："+ obj.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
                if(writer != null) writer.close();
            } catch (Exception ex) {
            	ex.printStackTrace();
            }
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void closeRoom(String room_no){
		//向业务管控发送指令，查看包房的状态
		Socket socket = null;
		BufferedWriter writer = null;
		
		try {
			socket = new Socket(ip,Param.SOCKET_KLT_PORT);
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), Param.CHARSET_UTF8));
			JSONObject obj = new JSONObject();
			JSONObject data = new JSONObject();
			obj.put("command", "close");
			obj.put("roomNo", room_no);
			data.put("oldRoomNo", "");
			data.put("transactionId", UUID.randomUUID().toString());
			obj.put("data", data);
			writer.write(obj.toString());
			writer.flush();
			log.info("发送关房指令信息："+ obj.toString());
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
                if(writer != null) writer.close();
            } catch (Exception ex) {
            	ex.printStackTrace();
            }
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public MsgBean setRoomState(List<String> list,String roomState) {
		try{
			
			for(String room_no :list){
				final String room = room_no ; 
				if(roomState.equals("1")){
					new Thread(new Runnable(){
						@Override
						public void run(){	
							openRoom(room);
						}
					}).start();
				}else if(roomState.equals("2")){
					new Thread(new Runnable(){
						@Override
						public void run(){	
							closeRoom(room);
						}
					}).start();
				}
			}
			msgBean.setSign(true);
			msgBean.setMsg("开房成功");
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(true);
			msgBean.setMsg("开房成功");
		}
		return msgBean; 
	}

	public DeviceMonitorDao getDeviceMonitorDao() {
		return deviceMonitorDao;
	}

	public void setDeviceMonitorDao(DeviceMonitorDao deviceMonitorDao) {
		this.deviceMonitorDao = deviceMonitorDao;
	}

	public OperationLogService getOptLogService() {
		return optLogService;
	}

	public void setOptLogService(OperationLogService optLogService) {
		this.optLogService = optLogService;
	}

	public RoomDao getRoomDao() {
		return roomDao;
	}

	public void setRoomDao(RoomDao roomDao) {
		this.roomDao = roomDao;
	}

	public MsgBean getMsgBean() {
		return msgBean;
	}

	public void setMsgBean(MsgBean msgBean) {
		this.msgBean = msgBean;
	}
	
	
}

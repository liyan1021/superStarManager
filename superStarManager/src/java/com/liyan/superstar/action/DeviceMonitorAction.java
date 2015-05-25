package com.liyan.superstar.action;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.liyan.common.action.MsgBean;
import com.liyan.common.action.PageAwareActionSupport;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.DateUtil;
import com.liyan.superstar.model.DeviceMonitor;
import com.liyan.superstar.model.Room;
import com.liyan.superstar.service.DeviceMonitorService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class DeviceMonitorAction  extends PageAwareActionSupport<DeviceMonitor>{
	@Autowired
	private DeviceMonitorService deviceMonitorService;

	private DeviceMonitor deviceMonitor ;
	private String ids ; 
	private String roomState;
	public String init(){
		return Action.SUCCESS;
	}
	public void deviceMonitorList(){
		try{
			Pagination<DeviceMonitor> result = this.deviceMonitorService.query(deviceMonitor,page,rows,sort,order);
			List<DeviceMonitor> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(DeviceMonitor deviceMonitor : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("monitor_id", deviceMonitor.getMonitor_id());
				jsonObject.element("device_id", deviceMonitor.getDevice_id());
				jsonObject.element("device_name", deviceMonitor.getDevice_name());
				jsonObject.element("net_state", deviceMonitor.getNet_state());
				jsonObject.element("device_state", deviceMonitor.getDevice_state());
				jsonObject.element("room_state", deviceMonitor.getRoom_state());
				jsonObject.element("store", deviceMonitor.getStore());
				jsonObject.element("room", deviceMonitor.getRoom());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void searchDeviceMonitor(){
		try{
			Pagination<DeviceMonitor> result = this.deviceMonitorService.searchDeviceMonitor(deviceMonitor,page,rows,sort,order);
			List<DeviceMonitor> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(DeviceMonitor deviceMonitor : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("monitor_id", deviceMonitor.getMonitor_id());
				jsonObject.element("device_id", deviceMonitor.getDevice_id());
				jsonObject.element("device_name", deviceMonitor.getDevice_name());
				jsonObject.element("net_state", deviceMonitor.getNet_state());
				jsonObject.element("device_state", deviceMonitor.getDevice_state());
				jsonObject.element("room_state", deviceMonitor.getRoom_state());
				jsonObject.element("store", deviceMonitor.getStore());
				jsonObject.element("room", deviceMonitor.getRoom());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void openRoomList(){
		try{
			Pagination<Room> result = this.deviceMonitorService.openRoomList(page,rows,sort,order);
			List<Room> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Room room : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("oid", room.getOid());
				jsonObject.element("room_no", room.getRoom_no());
				jsonObject.element("ip_add", room.getIp_add());
				jsonObject.element("device_id", room.getDevice_id());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void setRoomState(){
		try {
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			MsgBean msgBean = this.deviceMonitorService.setRoomState(list,roomState);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public DeviceMonitor getDeviceMonitor() {
		return deviceMonitor;
	}

	public void setDeviceMonitor(DeviceMonitor deviceMonitor) {
		this.deviceMonitor = deviceMonitor;
	}

	public DeviceMonitorService getDeviceMonitorService() {
		return deviceMonitorService;
	}

	public void setDeviceMonitorService(DeviceMonitorService deviceMonitorService) {
		this.deviceMonitorService = deviceMonitorService;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getRoomState() {
		return roomState;
	}
	public void setRoomState(String roomState) {
		this.roomState = roomState;
	}
	
	
}

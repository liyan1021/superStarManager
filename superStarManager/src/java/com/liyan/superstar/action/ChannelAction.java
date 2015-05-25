package com.liyan.superstar.action;

import java.io.File;
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
import com.liyan.superstar.model.Channel;
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.service.ChannelService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class ChannelAction extends PageAwareActionSupport<Channel> {
	
	@Autowired
	private ChannelService channelService ; 
	private String ids ;
	private Channel channel ;
	private File image; // 上传的文件
	private String imageFileName; // 文件名称
	private String imageContentType; // 文件类型
	
	public String init(){
		return Action.SUCCESS;
	}
	
	public void channelList(){
		try{
			Pagination<Channel> result = this.channelService.query(channel,page,rows,sort,order);
			List<Channel> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Channel channel : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("channel_id", channel.getChannel_id());
				jsonObject.element("channel_name", channel.getChannel_name());
				jsonObject.element("channel_url", channel.getChannel_url());
				jsonObject.element("channel_content",channel.getChannel_content());
				jsonObject.element("sort",channel.getSort());
				jsonObject.element("state", channel.getState());
				jsonObject.element("file_path",channel.getFile_path());
				
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
	
	public void removeChannel(){
		try {
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			MsgBean msgBean = this.channelService.removeChannel(list);
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
	
	public void saveChannel(){
		try {
			MsgBean msgBean = this.channelService.saveChannel(channel,image,imageFileName);
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
	
	public void searchChannel(){
		try{
			Pagination<Channel> result = this.channelService.searchChannel(channel,page,rows,sort,order);
			List<Channel> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Channel channel : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("channel_id", channel.getChannel_id());
				jsonObject.element("channel_name", channel.getChannel_name());
				jsonObject.element("channel_url", channel.getChannel_url());
				jsonObject.element("channel_content",channel.getChannel_content());
				jsonObject.element("sort",channel.getSort());
				jsonObject.element("state", channel.getState());
				jsonObject.element("file_path",channel.getFile_path());
				
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
	
	public void editChannel(){
		try {
			MsgBean msgBean = this.channelService.editChannel(channel,image,imageFileName);
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
	
	public void pushChannel(){
		try {
			MsgBean msgBean = this.channelService.pushChannel(ids);
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
	public void checkChannelSort(){
		try{
			MsgBean msgBean = this.channelService.checkChannelSort(channel);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(msgBean.isSign());
			out.flush();
			out.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String getIds() {
		return ids;
	}


	public void setIds(String ids) {
		this.ids = ids;
	}


	public Channel getChannel() {
		return channel;
	}


	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public ChannelService getChannelService() {
		return channelService;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public String getImageContentType() {
		return imageContentType;
	}

	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}
	
}

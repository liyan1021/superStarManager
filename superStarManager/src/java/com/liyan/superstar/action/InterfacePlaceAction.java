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
import com.liyan.superstar.model.Interface;
import com.liyan.superstar.service.InterfaceService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class InterfacePlaceAction extends PageAwareActionSupport<Interface>{	
	/**
	 * 
	 */
	private static final long serialVersionUID = 984395982975125968L;
	@Autowired
	private InterfaceService  interfaceService ;
	private Interface itf ; 
	private String ids; 
	private File image; // 上传的文件
	private String imageFileName; // 文件名称
	private String imageContentType; // 文件类型
	
	/**
	 * 场所介绍界面初始化
	 * @return
	 */
	public String placeInit(){
		return Action.SUCCESS;
	}
	
	/**
	 * 场所介绍界面初始化列表
	 */
	public void placeInterfaceList(){
		try{
			List<Interface> resultList = this.interfaceService.queryPlace(itf);
		
			JSONObject jsonObject = new JSONObject();
			if(resultList.size()>0){
			Interface itf =resultList.get(0);
				jsonObject.element("interface_id", itf.getInterface_id());
				jsonObject.element("interface_name", itf.getInterface_name());
				jsonObject.element("file_path", itf.getFile_path());	
				jsonObject.element("interface_state", itf.getState());
			}
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		System.out.println(jsonObject.toString());
		out.println(jsonObject.toString());
		out.flush();
		out.close();			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	/**
	 * 编辑场所介绍界面设置
	 */
	public void editPlaceInterface(){
		try {
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = interfaceService.editPlaceInterface(itf,image,imageFileName,imageContentType);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 * 保存场所介绍界面设置
	 */
	public void savePlaceInterface(){
		try {
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = this.interfaceService.createPlace(itf,image,imageFileName,imageContentType);

			JSONObject json = new JSONObject();

			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 初始化消防图解界面
	 * @return
	 */
	public String firePicInit(){
		
		return Action.SUCCESS;
	}
	/**
	 * 场所介绍界面初始化列表
	 */
	public void firePicInterfaceList(){
		try{
			List<Interface> resultList = this.interfaceService.queryFirePic(itf);
		
			JSONObject jsonObject = new JSONObject();
			if(resultList.size()>0){
			   Interface itf =resultList.get(0);
				jsonObject.element("interface_id", itf.getInterface_id());
				jsonObject.element("interface_name", itf.getInterface_name());
				jsonObject.element("file_path",itf.getFile_path());	
				jsonObject.element("interface_state", itf.getState());	
			}
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		System.out.println(jsonObject.toString());
		out.println(jsonObject.toString());
		out.flush();
		out.close();			
		}catch(Exception e){
			e.printStackTrace();
		}		
	}
	/**
	 * 保存消防图解界面设置
	 */
	public void saveFirePicInterface(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.interfaceService.createFirePic(itf,image,imageFileName,imageContentType); 

			JSONObject json = new JSONObject();

			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 编辑消防图解界面设置
	 */
	public void editFirePicInterface(){
		try {
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = interfaceService.editPlaceInterface(itf,image,imageFileName,imageContentType);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	/**
	 *  发布场所介绍界面设置
	 */
	public void pushPlaceInterface(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			
			MsgBean msgBean = this.interfaceService.pushPlaceInterface(ids); 

			JSONObject json = new JSONObject();

			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 *  发布消防图解设置
	 */
	public  void pushFirePicInterface(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.interfaceService.pushFirePicInterface(ids); 

			JSONObject json = new JSONObject();

			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public InterfaceService getInterfaceService() {
		return interfaceService;
	}
	public void setInterfaceService(InterfaceService interfaceService) {
		this.interfaceService = interfaceService;
	}
	public Interface getItf() {
		return itf;
	}
	public void setItf(Interface itf) {
		this.itf = itf;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
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
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
import com.liyan.superstar.model.Advert;
import com.liyan.superstar.model.AdvertMusic;
import com.liyan.superstar.service.AdvertService;
import com.opensymphony.xwork2.Action;
/**
 * @author Administrator
 * 广告发布
 */
@Controller
@Scope("prototype")
public class AdvertAction extends PageAwareActionSupport<Advert>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4075046548066130508L;
	
	@Autowired
	private AdvertService advertService ; 
	private String ids ; 
	private Advert advert ; 
	private File image; // 上传的文件
	private String imageFileName; // 文件名称
	private String imageContentType; // 文件类型
	private String advert_music_id ; 
	private MsgBean msgBean ; 
	
	/**
	 * 广告发布页面初始化
	 * @return
	 */
	public String init(){
		
		return Action.SUCCESS;
	}
	
	/**
	 * 广告发布初始化
	 */
	public void advertList (){
		try{
			
			Pagination<Advert> result = this.advertService.query(advert,page,rows,sort,order);
			List<Advert> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Advert advert : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("advert_id", advert.getAdvert_id());
				jsonObject.element("advert_name", advert.getAdvert_name());
				jsonObject.element("advert_content", advert.getAdvert_content());
				jsonObject.element("online_time",advert.getOnline_time());
				jsonObject.element("offline_time",advert.getOffline_time());
				jsonObject.element("is_activity",advert.getIs_activity());
				jsonObject.element("state",advert.getState());
				jsonObject.element("file_path",advert.getFile_path());
				jsonObject.element("advert_time",advert.getAdvert_time());
				jsonObject.element("advert_sort",advert.getAdvert_sort());
				jsonObject.element("is_index_advert", advert.getIs_index_advert());
				
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
//			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
			
		}catch(Exception e){
			
		}
	}
	
	/**
	 * 广告发布搜索
	 */
	public void searchAdvert(){
		try{
			Pagination<Advert> result = this.advertService.search(advert,page,rows,sort,order);
			List<Advert> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(Advert advert : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("advert_id", advert.getAdvert_id());
				jsonObject.element("advert_name", advert.getAdvert_name());
				jsonObject.element("advert_content", advert.getAdvert_content());
				jsonObject.element("online_time",advert.getOnline_time());
				jsonObject.element("offline_time",advert.getOffline_time());
				jsonObject.element("is_activity",advert.getIs_activity());
				jsonObject.element("state",advert.getState());
				jsonObject.element("file_path",advert.getFile_path());
				jsonObject.element("advert_time",advert.getAdvert_time());
				jsonObject.element("advert_sort",advert.getAdvert_sort());
				jsonObject.element("is_index_advert", advert.getIs_index_advert());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
//			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			
		}
	}
	
	/**
	 *  新增广告
	 */
	public void saveAdvert(){
		try{
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();

			msgBean = this.advertService.create(advert,image,imageFileName,imageContentType);

			JSONObject json = new JSONObject();

			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			
		}
	}
	
	/**
	 * 编辑广告
	 */
	public void editAdvert(){
		try{
//			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			msgBean = advertService.editAdvert(advert,image,imageFileName,imageContentType);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			
		}
	}
	
	/**
	 * 删除广告
	 */
	public void removeAdvert(){
		try{
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			msgBean = this.advertService.removeAdvert(list);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			
		}
	}
	/**
	 * 发布广告
	 */
	public void pushAdvert(){
		try{
			msgBean = this.advertService.pushAdvert(ids);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			
		}
	}
	
	public void advertSongList (){
		try{
			Pagination<AdvertMusic> result = this.advertService.searchAdvertSong(advert,page,rows);
			List<AdvertMusic> resultList = result.getResultList();
			JSONArray jsonArray = new JSONArray();
			for(AdvertMusic advertMusic : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("advert_music_id",advertMusic.getAdvert_music_id());
				jsonObject.element("song_id",advertMusic.getSong().getSong_id() );
				jsonObject.element("song_name",advertMusic.getSong().getSong_name() );
				jsonObject.element("singer_name",advertMusic.getSong().getSinger_name());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", result.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response=ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
//			System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			
		}
	}
	
	public void removeAdvertMusic(){
		try{
			msgBean = this.advertService.removeAdvertMusic(this.advert_music_id);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			
		}
	}
	
	public void  saveAdvertMusic(){
		try{
			msgBean = this.advertService.saveAdvertMusic(ids,advert);
			JSONObject json = new JSONObject();
			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			
		}
	}
	public void checkAdvertSort(){
		try{
			MsgBean msgBean = this.advertService.checkAdvertSort(advert);
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
	public AdvertService getAdvertService() {
		return advertService;
	}

	public void setAdvertService(AdvertService advertService) {
		this.advertService = advertService;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Advert getAdvert() {
		return advert;
	}

	public void setAdvert(Advert advert) {
		this.advert = advert;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public MsgBean getMsgBean() {
		return msgBean;
	}

	public void setMsgBean(MsgBean msgBean) {
		this.msgBean = msgBean;
	}

	public String getAdvert_music_id() {
		return advert_music_id;
	}

	public void setAdvert_music_id(String advert_music_id) {
		this.advert_music_id = advert_music_id;
	}
	
	
}

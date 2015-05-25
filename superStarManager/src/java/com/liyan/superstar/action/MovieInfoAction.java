package com.liyan.superstar.action;

import java.io.File;
import java.io.PrintWriter;
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
import com.liyan.superstar.model.Game;
import com.liyan.superstar.model.MovieInfo;
import com.liyan.superstar.service.GameService;
import com.liyan.superstar.service.MovieInfoService;
import com.opensymphony.xwork2.Action;

@Controller
@Scope("prototype")
public class MovieInfoAction extends PageAwareActionSupport<MovieInfo> {
	@Autowired
	private MovieInfoService movieInfoService ;
	private MovieInfo movieInfo ; 
	private String ids ; 
	private String start_time ;
	private String end_time;  
	private File image; // 上传的文件
	private String imageFileName; // 文件名称
	private String imageContentType; // 文件类型
	
	public String init(){
		
		return Action.SUCCESS;
	}
	public void movieInfoList(){
		try{
			Pagination<MovieInfo> result = this.movieInfoService.query(movieInfo,page,rows,sort,order);
			List<MovieInfo> resultList = result.getResultList();
			
			JSONArray jsonArray = new JSONArray();
			for(MovieInfo movieInfo : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("movie_id",movieInfo.getMovie_id() );
				jsonObject.element("movie_name",movieInfo.getMovie_name());
				jsonObject.element("movie_info",movieInfo.getMovie_info());
				jsonObject.element("row_num",movieInfo.getRow_num());
				jsonObject.element("column_num",movieInfo.getColumn_num());
				jsonObject.element("sort",movieInfo.getSort());
				jsonObject.element("state",movieInfo.getState());
				jsonObject.element("file_path",movieInfo.getFile_path());
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
	public void removeMovieInfo(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.movieInfoService.removeMovieInfo(ids);

			JSONObject json = new JSONObject();

			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void saveMovieInfo(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.movieInfoService.saveMovieInfo(movieInfo,image,imageFileName);

			JSONObject json = new JSONObject();

			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void searchMovieInfo(){
		try{
			Pagination<MovieInfo> result = this.movieInfoService.search(movieInfo,page,rows,sort,order);
			List<MovieInfo> resultList = result.getResultList();
			
			JSONArray jsonArray = new JSONArray();
			
			for(MovieInfo movieInfo : resultList){
				JSONObject jsonObject = new JSONObject();
				jsonObject.element("movie_id",movieInfo.getMovie_id() );
				jsonObject.element("movie_name",movieInfo.getMovie_name());
				jsonObject.element("movie_info",movieInfo.getMovie_info());
				jsonObject.element("row_num",movieInfo.getRow_num());
				jsonObject.element("column_num",movieInfo.getColumn_num());
				jsonObject.element("sort",movieInfo.getSort());
				jsonObject.element("state",movieInfo.getState());
				jsonObject.element("file_path",movieInfo.getFile_path());
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
	public void pushMovie(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.movieInfoService.pushMovie(ids);
			JSONObject json = new JSONObject();

			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void editMovieInfo(){
		try{
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.movieInfoService.editMovieInfo(movieInfo,this.image,this.imageFileName);
			JSONObject json = new JSONObject();

			json.put("success", msgBean.isSign());
			json.put("message", msgBean.getMsg());

			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void checkMovieSort(){
		try{
			MsgBean msgBean = this.movieInfoService.checkAdvertSort(movieInfo);
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
	public MovieInfoService getMovieInfoService() {
		return movieInfoService;
	}
	public void setMovieInfoService(MovieInfoService movieInfoService) {
		this.movieInfoService = movieInfoService;
	}
	public MovieInfo getMovieInfo() {
		return movieInfo;
	}
	public void setMovieInfo(MovieInfo movieInfo) {
		this.movieInfo = movieInfo;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
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

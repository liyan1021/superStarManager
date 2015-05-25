package com.liyan.superstar.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.liyan.superstar.model.Singer;
import com.liyan.superstar.service.SingerService;
import com.opensymphony.xwork2.Action;

/**
 * @author Administrator
 * 歌星管理
 */
@Controller
@Scope("prototype")
public class SingerAction extends PageAwareActionSupport<Singer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3589086543408499595L;
	@Autowired
	private SingerService singerService;
	
	private Singer singer;
	private String ids;
	private File image; // 上传的文件
	private String imageFileName; // 文件名称
	private String imageContentType; // 文件类型
	
	private File importFile ;
	private String importFileContentType;
	private String importFileFileName ;
	
	
	private String order ; 
	private String sort ; 
	/**
	 * 歌星管理初始化页面
	 * @return
	 */
	public String init() {
		return Action.SUCCESS;
	}

	/**
	 * 歌星列表数据初始化
	 */
	public void singerList() {
		try {
			Pagination<Singer> pageList = singerService.query(singer, page,
					rows,sort,order);
			List<Singer> resultList = pageList.getResultList();
			JSONArray jsonArray = new JSONArray();
			for (Singer singer : resultList) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.element("star_id", singer.getStar_id());
				jsonObject.element("star_name", singer.getStar_name());
				jsonObject.element("other_name", singer.getOther_name());
				jsonObject.element("star_type", singer.getStar_type());
				jsonObject.element("area", singer.getArea());
				jsonObject.element("spell_first_letter_abbreviation",
						singer.getSpell_first_letter_abbreviation());
				jsonObject.element("star_head", singer.getStar_head());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", pageList.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 条件查询歌星列表
	 */
	public void searchSingerList() {
		try {
			Pagination<Singer> pageList = singerService.search(singer, page,
					rows,sort,order);
			List<Singer> resultList = pageList.getResultList();
			JSONArray jsonArray = new JSONArray();
			for (Singer singer : resultList) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.element("star_id", singer.getStar_id());
				jsonObject.element("star_name", singer.getStar_name());
				jsonObject.element("other_name", singer.getOther_name());
				jsonObject.element("star_type", singer.getStar_type());
				jsonObject.element("area", singer.getArea());
				jsonObject.element("spell_first_letter_abbreviation",
						singer.getSpell_first_letter_abbreviation());
				jsonObject.element("star_head", singer.getStar_head());
				jsonArray.add(jsonObject);
			}
			JSONObject json = new JSONObject();
			json.put("total", pageList.getRecordCount());
			json.put("rows", jsonArray);
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			// System.out.println(json.toString());
			out.println(json.toString());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除歌星
	 */
	public void removeSinger() {
		try {
			String[] idsList = ids.split(",");
			List<String> list = Arrays.asList(idsList);
			MsgBean msgBean = this.singerService.removeSinger(list);
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

	/**
	 * 保存歌星
	 */
	public void saveSinger() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();

			MsgBean msgBean = this.singerService.create(singer, image, imageFileName, request);

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
	 * 编辑歌星
	 */
	public void editSinger() {
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = singerService.editSinger(singer, image, imageFileName, request);
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

	@SuppressWarnings("resource")
	public void getSingerPic() {
		String serverPath = "";
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			if (image != null) {
				// 生成文件路径
				imageFileName = imageFileName.substring(
						imageFileName.lastIndexOf("."), imageFileName.length());
				imageFileName = "temp" + imageFileName;
				String path = ServletActionContext.getServletContext()
						.getRealPath("upload");
				path = path + "\\" + imageFileName;
				new File(path).delete();
				// imageFileName = singer.getStar_id() ;
				File imgPath = new File(path);
				FileOutputStream fos = new FileOutputStream(imgPath);
				// 建立文件上传流
				FileInputStream fis = new FileInputStream(image);
				byte[] buffer = new byte[1024];
				int len = 0;
				while ((len = fis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}
				String contextPath = request.getContextPath();
				String basePath = request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort() + contextPath + "/upload/";
				serverPath = basePath + imageFileName;

			}
			JSONObject json = new JSONObject();
			json.put("success", true);
			json.put("picUrl", serverPath);
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
	public void importSinger(){
		try {
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = this.singerService.importSinger(importFile);
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
	public void checkSingerName(){
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			MsgBean msgBean = this.singerService.checkSingerName(singer);
			response.setContentType("text/html;charset=utf-8");
			PrintWriter out = response.getWriter();
			out.println(msgBean.isSign());
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void exportSinger(){
		this.singerService.exportSinger(singer);
	}
	public Singer getSinger() {
		return singer;
	}

	public void setSinger(Singer singer) {
		this.singer = singer;
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

	public SingerService getSingerService() {
		return singerService;
	}

	public void setSingerService(SingerService singerService) {
		this.singerService = singerService;
	}

	public File getImportFile() {
		return importFile;
	}

	public void setImportFile(File importFile) {
		this.importFile = importFile;
	}

	public String getImportFileContentType() {
		return importFileContentType;
	}

	public void setImportFileContentType(String importFileContentType) {
		this.importFileContentType = importFileContentType;
	}

	public String getImportFileFileName() {
		return importFileFileName;
	}

	public void setImportFileFileName(String importFileFileName) {
		this.importFileFileName = importFileFileName;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

}

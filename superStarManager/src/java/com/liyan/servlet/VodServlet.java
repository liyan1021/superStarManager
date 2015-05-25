package com.liyan.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.liyan.servlet.bean.DictBean;
import com.liyan.servlet.service.DictService;
import com.liyan.superstar.dto.KappSong;

public class VodServlet extends HttpServlet{
	JdbcTemplate jdbcTemplate;
	
	private Logger log = Logger.getLogger(VodServlet.class);

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setHeader("Pragma", "no-cache");
		//resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html;charset=UTF-8");
		String uri = req.getRequestURI();
		int size = uri.lastIndexOf("/");
		String request = uri.substring(size+1);
		PrintWriter writer = resp.getWriter();
		String roomPwd = req.getParameter("roomPwd");
		String roomNo = req.getParameter("roomNo");
		boolean flag = checkLogin(roomNo, roomPwd);
		log.info(uri);
		JSONObject result = new JSONObject();
		if(!flag){
			result.put("result", Param.FAILURE);
			result.put("data", "");
			writer.write(result.toString());
			writer.close();
		}else{
			if(request.equals(Commons.METHOD_ACTION_UPDATE_DIC_DATA)){
				List<DictBean> list = DictService.findDictList(jdbcTemplate);
				result.put("result", Param.SUCCESS);
				result.put("data", list);
				writer.write(result.toString());
			}
			writer.flush();
			writer.close();
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.info(req.getRequestURI());
	}
	
	boolean checkLogin(String roomNo, String roomPwd){
		boolean flag = false;
		String sql = "select transaction_id from vod_store_room_state where close_time is null and room_no='"+roomNo+"' and random_pwd='"+roomPwd+"' ";
		SqlRowSet set = getJdbcTemplate().queryForRowSet(sql);
		if(set.next()){
			String transactionId = set.getString("transaction_id");
			flag = true;
		}
/*		getJdbcTemplate().query(sql, new RowCallbackHandler(){
			@Override
			public void processRow(ResultSet rs) {
				try {
					if(rs.next()){
						String transactionId = rs.getString("TRANSACTION_ID");
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		});;
*/		return true;
		//return flag;
	}

	public JdbcTemplate getJdbcTemplate() {
		WebApplicationContext wc = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		jdbcTemplate = (JdbcTemplate)wc.getBean("jdbcTemplate");
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}

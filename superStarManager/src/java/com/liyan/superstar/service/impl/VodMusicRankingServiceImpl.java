package com.liyan.superstar.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.liyan.common.AppContext;
import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.common.util.CommonFiled;
import com.liyan.common.util.CommonUtils;
import com.liyan.common.util.ExcelUtil;
import com.liyan.superstar.dao.SingerDao;
import com.liyan.superstar.dao.SongDao;
import com.liyan.superstar.dao.VodMusicRankingDao;
import com.liyan.superstar.model.Singer;
import com.liyan.superstar.model.Song;
import com.liyan.superstar.model.User;
import com.liyan.superstar.model.VodMusicRanking;
import com.liyan.superstar.service.OperationLogService;
import com.liyan.superstar.service.VodMusicRankingService;

@Service
@Transactional
public class VodMusicRankingServiceImpl implements VodMusicRankingService{
	
	@Autowired
	private VodMusicRankingDao  vodMusicRankDao; 
	
	@Autowired
	private MsgBean msgBean ; 
	
	@Autowired 
	private SongDao songDao ; 
	
	@Autowired
	private SingerDao singerDao ; 
	@Autowired
	private OperationLogService optLogService ;
	
	@Override
	public Pagination<VodMusicRanking> query(VodMusicRanking musicRanking,
			Integer page, Integer rows, String sort, String order) {
		return vodMusicRankDao.query(musicRanking, page, rows, sort, order);
	}

	@Override
	public Pagination<VodMusicRanking> searchMusicRank(
			VodMusicRanking musicRanking, Integer page, Integer rows,
			String sort, String order) {
		return vodMusicRankDao.query(musicRanking, page, rows, sort, order);
	}

	@Override
	public MsgBean editMusicRank(VodMusicRanking musicRanking, int song_number) {
		try{
			VodMusicRanking find = this.vodMusicRankDao.find(musicRanking.getMusic_id());
			Integer music_song_number = new Integer(find.getMusic_ranking())+song_number;
			find.setMusic_ranking(music_song_number.toString());
			this.vodMusicRankDao.modify(find);
			
			//操作日志录入
			HttpServletRequest request = ServletActionContext.getRequest();
			HttpSession session = request.getSession();
			String key = AppContext.getInstance().getString("key.session.current.user");
		    //操作人
			User currentUser = (User) session.getAttribute(key);
			//操作功能
			String function = "大歌星排行" ; 		
		    //操作类型
			String type = CommonFiled.OPT_LOG_MODIFY ; 
			getOptLogService().setLog(currentUser,function,type);
			msgBean.setSign(true);
			msgBean.setMsg("修改成功");
		}catch(Exception e){
			e.printStackTrace();
			msgBean.setSign(false);
			msgBean.setMsg("修改失败");
		}
		return msgBean ;
	}

	
	@Override
	public MsgBean importRank(File importFile) {
		String alertStr = "";
		HSSFWorkbook workbook;
		try {
			workbook = new HSSFWorkbook(new FileInputStream(importFile));
		
    		HSSFSheet sheet = (HSSFSheet) workbook.getSheetAt(0);
    		for(int i = 2 ; i <= sheet.getLastRowNum() ; i ++){
    			HSSFRow row = sheet.getRow(i);
    			if (row == null) {
    				msgBean.setSign(false);
    				msgBean.setMsg("上传失败：文件为空");
					return msgBean ;
				}
    			VodMusicRanking musicRank = new VodMusicRanking();
    			//歌曲名称 验证
    			String song_name = ExcelUtil.getCellFormatValue(row.getCell(1)).trim();
    			if(song_name.isEmpty()){
    				msgBean.setSign(false);
    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：歌曲名称不能为空");
					return msgBean ;
    			}
    			
    			//歌星名称 验证
    			String star_name = ExcelUtil.getCellFormatValue(row.getCell(2)).trim();
    			if(star_name.isEmpty()){
    				msgBean.setSign(false);
    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：歌星名称不能为空");
					return msgBean ;
    			}
    			//验证歌曲是否存在
    			Song song = new Song();
    			song.setSong_name(song_name);
    			song.setSinger_name(star_name);
    			List<Song> songList = this.songDao.query(song);
    			if(songList.size()==0){
    				msgBean.setSign(false);
    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：歌曲不存在");
					return msgBean ;
    			}
    			//排行
    			String rank = ExcelUtil.getCellFormatValue(row.getCell(3)).trim();
    			if(rank.isEmpty()){
    				msgBean.setSign(false);
    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：排行不能为空");
					return msgBean ;
    			}
    			if(rank.matches("/^([0-9]+)$/")){
    				msgBean.setSign(false);
    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：只能输入数字");
					return msgBean ;
    			}
    			//验证排行是否存在
    			List<VodMusicRanking> rankList = this.vodMusicRankDao.findRankByRank(rank);
    			if(rankList.size()>0){
    				msgBean.setSign(false);
    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：排行已经存在");
					return msgBean ;
    			}
    			song = songList.get(0);
    			//验证歌曲是否在排行中以存在
    			VodMusicRanking vodMusicRank = this.vodMusicRankDao.find(song.getSong_id());
    			if(vodMusicRank!=null){
    				msgBean.setSign(false);
    				msgBean.setMsg("上传失败：/n 第"+i+"行数据错误：排行已经存在");
					return msgBean ;
    			}
    			List<Singer> singerList = singerDao.findByName(song.getSinger_name());
    			Singer singer = singerList.get(0);
    			musicRank.setMusic_id(song.getSong_id());
    			musicRank.setMusic_name(song.getSong_name());
    			musicRank.setSinger_name(song.getSinger_name());
    			musicRank.setMusic_ranking(rank);
    			musicRank.setSinger_picture(singer.getStar_id());
    			musicRank.setSinger_picture_path(singer.getStar_head_absolute());
    			musicRank.setUpdate_date(CommonUtils.currentDateTimeString());
    			this.vodMusicRankDao.create(musicRank);
    		}
    		msgBean.setSign(true);
			msgBean.setMsg("上传成功:<br>"+alertStr);
		
		} catch (FileNotFoundException e) {
			msgBean.setSign(false);
			msgBean.setMsg("上传失败:文件未找到");
			e.printStackTrace();
		} catch (IOException e) {
			msgBean.setSign(false);
			msgBean.setMsg("上传失败:文件错误");
		}
		return msgBean;
	}

	public VodMusicRankingDao getVodMusicRankDao() {
		return vodMusicRankDao;
	}

	public void setVodMusicRankDao(VodMusicRankingDao vodMusicRankDao) {
		this.vodMusicRankDao = vodMusicRankDao;
	}

	public MsgBean getMsgBean() {
		return msgBean;
	}

	public void setMsgBean(MsgBean msgBean) {
		this.msgBean = msgBean;
	}

	public OperationLogService getOptLogService() {
		return optLogService;
	}

	public void setOptLogService(OperationLogService optLogService) {
		this.optLogService = optLogService;
	}

	public SongDao getSongDao() {
		return songDao;
	}

	public void setSongDao(SongDao songDao) {
		this.songDao = songDao;
	}
}

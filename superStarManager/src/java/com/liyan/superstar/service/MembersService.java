package com.liyan.superstar.service;

import com.liyan.common.action.MsgBean;
import com.liyan.common.dao.Pagination;
import com.liyan.superstar.model.Members;

public interface MembersService {

	/**
	 * 分页查询会员
	 * @param members
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<Members> memberList(Members members, Integer page, Integer rows);
	/**
	 * 分页查询会员，并记录日志
	 * @param members
	 * @param page
	 * @param rows
	 * @return
	 */
	Pagination<Members> searchMember(Members members, Integer page, Integer rows);
	/**
	 * 删除会员
	 * @param idsList
	 * @return
	 */
	MsgBean removeMember(String[] idsList);
	/**
	 * 保存会员
	 * @param members
	 * @return
	 */
	MsgBean saveMembers(Members members);
	/**
	 * 编辑会员
	 * @param members
	 * @return
	 */
	MsgBean editMembers(Members members);

}

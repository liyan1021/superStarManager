package com.liyan.common.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.liyan.common.dao.Pagination;

public class PageAwareActionSupport<T> extends CoreActionSupport {

    private static final long serialVersionUID = -1959121286421695553L;

    protected Pagination<T> pagination;

    protected Integer page = 1;
    
    protected Integer rows = 10 ; 
    
    protected String sort = "" ;
    protected String order = "" ;
    HttpServletRequest request = ServletActionContext.getRequest();
    
    public Pagination<T> getPagination() {
        return pagination;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
    	String pageNo = request.getParameter("page");
    	if(!pageNo.isEmpty()){
    		page = Integer.parseInt(pageNo);
    	}
        if (page == null) {
            this.page = 1;
        } else {
            this.page = page <= 0 ? 1 : page;
        }
    }

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		String rowNum = request.getParameter("rows");
    	if(!rowNum.isEmpty()){
    		rows = Integer.parseInt(rowNum);
    	}
        if (rows == null) {
            this.rows = 1;
        } else {
            this.rows = rows <= 0 ? 10 : rows;
        }
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
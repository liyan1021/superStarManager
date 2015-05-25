package com.liyan.common.dao;

import java.io.Serializable;
import java.util.List;

public class Pagination<E> implements Serializable {

    private static final long serialVersionUID = -678664722059756045L;

    private List<E> resultList;
    private Integer pageSize;
    private Integer pageNo;
    private Integer recordCount;

    private Integer pageCount;
    
    public Pagination(List<E> resultList, Integer pageSize, Integer pageNo,
			Integer recordCount, Integer pageCount) {
		super();
		this.resultList = resultList;
		this.pageSize = pageSize;
		this.pageNo = pageNo;
		this.recordCount = recordCount;
		this.pageCount = pageCount;
	}

	public Pagination() {
		super();
	}

	public List<E> getResultList() {
        return resultList;
    }

    public void setResultList(List<E> resultList) {
        this.resultList = resultList;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        if (pageSize == null) {
            this.pageSize = 0;
        } else {
            this.pageSize = pageSize < 0 ? 0 : pageSize;
        }
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        if (pageNo == null) {
            this.pageNo = 1;
        } else {
            this.pageNo = pageNo <= 0 ? 1 : pageNo;
        }
    }

    public Integer getRecordCount() {
        return recordCount;
    }

    public void setRecordCount(Integer recordCount) {
        this.recordCount = recordCount;
    }


    public Integer getPageCount() {
        if (this.pageSize > 0) {
            this.pageCount = this.recordCount / this.pageSize;
            if ((this.recordCount % this.pageSize) > 0) {
                this.pageCount++;
            }
        }

        return this.pageCount;
    }

}

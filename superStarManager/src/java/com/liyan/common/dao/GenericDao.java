package com.liyan.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**公共的数据访问接口
 *
 * @param <T>
 * @param <ID>
 */
public interface GenericDao<T extends Serializable,ID extends Serializable> {
	
	/**保存对象
	 *	@param t 待保存对象
	 */
	void create(final T t);
	
	/**批量创建
	 * @param ts
	 */
	void batchCreate(final Collection<T> ts);
	
	/**删除一个实体
	 * @param t
	 */
	void remove(final T t);
	
	/**批量删除
	 * @param ids
	 */
	void batchRemove(final Collection<ID> ids);
	
	/**
     * 删除所有对象
     * <p/>
     *
     * @see IHiddenable
     */
    void removeAll();	
    
	
	/**更新一个实体
	 * @param t
	 */
	void modify(final T t);
	
	/**批量更新
	 * @param ts
	 */
	void batchModify(Collection<T> ts);
	
	/**根据id加载实体
	 * @param id
	 */
	T load(final ID id);
	
	/**根据ID查找实体
	 * @param id
	 */
	T find(final ID id);
	
	/**根据sql查找实体集合
	 * @param jpql
	 * @param args
	 */
	List<T> find(QueryCriteria queryCriteria);
	
	/**分页
	 * @param pagesize 页宽
	 * @param pageno　　页码
	 * @param jpql   
	 * @param args
	 */
	Pagination<T> findPage(QueryCriteria queryCriteria, Integer pageNo, Integer pageSize);
	
	/**查找所有
	 */
	List<T> findAll();
	
	void flushAndClear();

}
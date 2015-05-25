package com.liyan.common.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.liyan.common.dao.Fetch;
import com.liyan.common.dao.GenericDao;
import com.liyan.common.dao.Order;
import com.liyan.common.dao.Pagination;
import com.liyan.common.dao.QueryCriteria;
import com.liyan.common.dao.QueryParamEntry;
import com.liyan.common.util.CommonUtils;

/**公共的数据访问接口实现
 *
 * @param <T>
 * @param <ID>
 */
@SuppressWarnings({"unchecked"})
public class GenericDaoImpl<T extends Serializable ,ID extends Serializable> implements GenericDao<T, ID> {
	 protected Log log = LogFactory.getLog(getClass());

    @PersistenceContext
    protected EntityManager entityManager;  //JPA 实例管理对象

    private Class<T> clazz;  


    public GenericDaoImpl() {

        Type[] types = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments();

        if (types[0] instanceof ParameterizedType) {
            // If the class has parameterized types, it takes the raw type.
            ParameterizedType type = (ParameterizedType) types[0];
            clazz = (Class<T>) type.getRawType();
        } else {
            clazz = (Class<T>) types[0];
        }

    }

    public GenericDaoImpl(Class<T> clazz) {
        this.clazz = clazz;
    }
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }
	@Override
	public void create(final T object) {
        entityManager.persist(object);
	}

	@Override
	public void batchCreate(final Collection<T> objects) {
		for (T object : objects) {
			create(object);
        }
	}

	@Override
	public void remove(final T object) {
		 entityManager.remove(object); 
	}

	@Override
	public void batchRemove(final Collection<ID> ids) {
		int i = 1; 
		for(ID id : ids){
 			i++;
 			Object object = this.find(id);
 			entityManager.remove(object);
 			if(i%20 == 0)
 				this.flushAndClear();
 		}
	}

	@Override
	public void removeAll() {
		deleteAll(findAll(), false);
	}
	
	private void deleteAll(final Collection<T> objects, boolean checkIdDefault) throws UnsupportedOperationException {
		 for (T object : objects) {
	            entityManager.remove(object);
	       }
		
        
    }
	@Override
	public void modify(final T t) {
		entityManager.merge(t);
	}
	
	@Override
	public T load(ID id) {
		 return entityManager.getReference(clazz, id);
	}

	@Override
	public T find(final ID id) {
		return (T) entityManager.find(clazz, id);
	}

	@Override
	public List<T> find(QueryCriteria queryCriteria) {
	    	List<QueryParamEntry> entries = queryCriteria.getEntries();
	        List<Fetch> fetches = queryCriteria.getFetchList();
	        List<Order> orders = queryCriteria.getOrderList();

	        Map<String, Object> parameterMap = new HashMap<String, Object>();

	        StringBuffer whereSqlBuffer = new StringBuffer();
	        Boolean wherePresent = false;

	        processEntries(entries, parameterMap, whereSqlBuffer, wherePresent);
	        
	        if (queryCriteria.hasWhereClause()) {
	        	processWhereClause(queryCriteria, parameterMap, whereSqlBuffer, wherePresent);
	        }

	        StringBuffer jpqlDataBuffer = new StringBuffer();
	        jpqlDataBuffer.append(String.format("SELECT o FROM %s o", clazz.getSimpleName()));
	        
	        for (Fetch fetch : fetches) {
	        	
	        	switch(fetch.getJoinType()) {
	        	case Fetch.JOINTYPE_INNER : 
	        		jpqlDataBuffer.append(String.format(" INNER JOIN FETCH o.%s", fetch.getTargetObjectName()));
	        		break;
	        	case Fetch.JOINTYPE_LEFT :
	        		jpqlDataBuffer.append(String.format(" LEFT JOIN FETCH o.%s", fetch.getTargetObjectName()));
	        		break;
	        	case Fetch.JOINTYPE_RIGHT :
	        		jpqlDataBuffer.append(String.format(" RIGHT JOIN FETCH o.%s", fetch.getTargetObjectName()));
	        		break;
	        	}
	            
	        }

	        jpqlDataBuffer.append(whereSqlBuffer);

	        StringBuffer orderByBuffer = new StringBuffer();
	        boolean orderByPresent = false;
	        for (Order order : orders) {
	            orderByBuffer.append(orderByPresent ? "," : " ORDER BY");
	            orderByBuffer.append(" o." + order.getParamName());
	            orderByBuffer.append(" " + order.getOrder());
	        }

	        jpqlDataBuffer.append(orderByBuffer);

	        Query query = entityManager.createQuery(jpqlDataBuffer.toString());
	        
	        if (!CommonUtils.isEmpty(queryCriteria.getRowCount())) {
	        	query.setFirstResult(0);
	        	query.setMaxResults(queryCriteria.getRowCount());
	        }
	        
	        for (Iterator<String> iterator = parameterMap.keySet().iterator(); iterator.hasNext();) {
	            String key = iterator.next();
	            Object value = parameterMap.get(key);
	            query.setParameter(key, value);
	        }

	        return query.getResultList();
		
	}
	
	@Override
	public Pagination<T> findPage(QueryCriteria queryCriteria, Integer pageNo, Integer pageSize) {
			List<QueryParamEntry> entries = queryCriteria.getEntries();
	        List<Fetch> fetches = queryCriteria.getFetchList();
	        List<Order> orders = queryCriteria.getOrderList();
	
	        Pagination<T> pagination = new Pagination<T>();
	        pagination.setPageSize(pageSize);
	        pagination.setPageNo(pageNo);
	
	        Map<String, Object> parameterMap = new HashMap<String, Object>();
	
	        StringBuffer whereSqlBuffer = new StringBuffer();
	        Boolean wherePresent = false;
	
	        processEntries(entries, parameterMap, whereSqlBuffer, wherePresent);
	        
	        if (queryCriteria.hasWhereClause()) {
	        	processWhereClause(queryCriteria, parameterMap, whereSqlBuffer, wherePresent);
	        }
	
	        StringBuffer jpqlCountBuffer = new StringBuffer();
	        jpqlCountBuffer.append(String.format("SELECT COUNT(o) FROM %s o", clazz.getSimpleName()));
	        
	        for (Fetch fetch : fetches) {
	        	
	        	switch(fetch.getJoinType()) {
	        	case Fetch.JOINTYPE_INNER : 
	        		jpqlCountBuffer.append(String.format(" INNER JOIN o.%s", fetch.getTargetObjectName()));
	        		break;
	        	case Fetch.JOINTYPE_LEFT :
	        		jpqlCountBuffer.append(String.format(" LEFT JOIN o.%s", fetch.getTargetObjectName()));
	        		break;
	        	case Fetch.JOINTYPE_RIGHT :
	        		jpqlCountBuffer.append(String.format(" RIGHT JOIN o.%s", fetch.getTargetObjectName()));
	        		break;
	        	}
	            
	        }
	
	        jpqlCountBuffer.append(whereSqlBuffer);
	
	        // Retrieve record count
	        Query query = entityManager.createQuery(jpqlCountBuffer.toString());
	        for (Iterator<String> iterator = parameterMap.keySet().iterator(); iterator.hasNext();) {
	            String key = iterator.next();
	            Object value = parameterMap.get(key);
	            query.setParameter(key, value);
	        }
	
	        Integer recordCount = ((Long) query.getSingleResult()).intValue();
	        pagination.setRecordCount(recordCount);
	
	        if (recordCount == 0) {
	            pagination.setResultList(new ArrayList<T>());
	            return pagination;
	        }
	
	        // Retrieve paged objects
	        StringBuffer jpqlDataBuffer = new StringBuffer();
	        jpqlDataBuffer.append(String.format("SELECT o FROM %s o", clazz.getSimpleName()));
	
	        for (Fetch fetch : fetches) {
	        	
	        	switch(fetch.getJoinType()) {
	        	case Fetch.JOINTYPE_INNER : 
	        		jpqlDataBuffer.append(String.format(" INNER JOIN FETCH o.%s", fetch.getTargetObjectName()));
	        		break;
	        	case Fetch.JOINTYPE_LEFT :
	        		jpqlDataBuffer.append(String.format(" LEFT JOIN FETCH o.%s", fetch.getTargetObjectName()));
	        		break;
	        	case Fetch.JOINTYPE_RIGHT :
	        		jpqlDataBuffer.append(String.format(" RIGHT JOIN FETCH o.%s", fetch.getTargetObjectName()));
	        		break;
	        	}
	            
	        }
	
	        jpqlDataBuffer.append(whereSqlBuffer);
	
	        StringBuffer orderByBuffer = new StringBuffer();
	        boolean orderByPresent = false;
	        for (Order order : orders) {
	            orderByBuffer.append(orderByPresent ? "," : " ORDER BY");
	            orderByBuffer.append(" o." + order.getParamName());
	            orderByBuffer.append(" " + order.getOrder());
	        }
	
	        jpqlDataBuffer.append(orderByBuffer);
	
	        query = entityManager.createQuery(jpqlDataBuffer.toString());
	        for (Iterator<String> iterator = parameterMap.keySet().iterator(); iterator.hasNext();) {
	            String key = iterator.next();
	            Object value = parameterMap.get(key);
	            query.setParameter(key, value);
	        }
	
	        query.setFirstResult(pagination.getPageSize() * (pagination.getPageNo() - 1));
	        query.setMaxResults(pagination.getPageSize());
	
	        pagination.setResultList(query.getResultList());
	        return pagination;
	}
	  private void processEntries(List<QueryParamEntry> entries, Map<String, Object> parameterMap, StringBuffer whereSqlBuffer, Boolean wherePresent) {
			
	    	Integer keyId = 0;
	        for (Iterator<QueryParamEntry> iteratorParams = entries.iterator(); iteratorParams.hasNext();) {
	            QueryParamEntry entry = iteratorParams.next();

	            String fieldName = entry.getParamName();
	            String paramName = StringUtils.replace(entry.getParamName(), ".", "_") + (keyId++);

	            String queryCase = entry.getQueryCase();
	            Object value = entry.getValue();
	            whereSqlBuffer.append(wherePresent ? " AND" : " WHERE");
	            whereSqlBuffer.append(" o." + fieldName);
	            whereSqlBuffer.append(" " + queryCase);
	            whereSqlBuffer.append(" :" + paramName);
	            parameterMap.put(paramName, value);
	            wherePresent = true;
	        }
		}
	    
	    private void processWhereClause(QueryCriteria queryCriteria, Map<String, Object> parameterMap, StringBuffer whereSqlBuffer, Boolean wherePresent) {
			String whereClause = queryCriteria.getWhereClause();
			
			whereSqlBuffer.append(wherePresent ? " AND" : " WHERE");
			List<Object> whereClauseValues = queryCriteria.getWhereClauseValues();
			
			Pattern p = Pattern.compile("(\\?)");
			Matcher m = p.matcher(whereClause);
			
			int index = 0;
			while (m.find()) {
				whereClause = StringUtils.replace(whereClause, m.group(1), ":__param__" + index);
				parameterMap.put("__param__" + index, whereClauseValues.get(index));
			}
			
			Pattern p2 = Pattern.compile("([a-z\\.0-9]+)\\s*(like|=|<=|>=|<>|>|<|in)", Pattern.CASE_INSENSITIVE);
			Matcher m2 = p2.matcher(whereClause);
			String latestMatched = null;
			while (m2.find()) {
				if (!m2.group(1).equals(latestMatched))
					whereClause = StringUtils.replace(whereClause, m2.group(1), "o." + m2.group(1));
				latestMatched = m2.group(1);
			}
			
			whereSqlBuffer.append(" (" + whereClause + ")");
			
			wherePresent = true;
		}
	@Override
	public List<T> findAll() {
		StringBuffer jpqlBuffer = new StringBuffer();
        jpqlBuffer.append(String.format("SELECT o FROM %s o", clazz.getSimpleName()));

        Map<String, Object> parameterMap = new HashMap<String, Object>();

        Query query = entityManager.createQuery(jpqlBuffer.toString());

        for (Iterator<String> iterator = parameterMap.keySet().iterator(); iterator.hasNext();) {
            String key = iterator.next();
            Object value = parameterMap.get(key);
            query.setParameter(key, value);
        }

        return query.getResultList();
		
	}

	@Override
    public void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }

	@Override
	public void batchModify(Collection<T> ts) {
		int i = 1; 
		for(T t : ts){
 			i++;
 			entityManager.merge(t);
 			if(i%20 == 0)
 				this.flushAndClear();
 		}
	}

}
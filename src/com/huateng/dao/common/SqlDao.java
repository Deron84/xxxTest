package com.huateng.dao.common;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.huateng.struts.system.action.BaseAction;

/**
 * project JSBConsole date 2013-3-1
 * 因为hibernate在查询char类型字段可能存在点问题、所有有时候用JdbcDaoSupport
 * @author 樊东东
 */
public class SqlDao extends JdbcDaoSupport {
	private static Logger log = Logger.getLogger(BaseAction.class);
	private void info(String sql){
		log.info(sql);
	}
	/**
	 * @param ps
	 */
	private void info(String[] ps) {
		StringBuffer buff = new StringBuffer("");
		for(int i=0,len=ps.length;i<len;i++){
			buff.append(ps[i]);
			if(i!=len-1){
				buff.append(",");
			}
		}

		info(buff.toString());
	}
	public void execute(String sql) {
		info(sql);
		this.getJdbcTemplate().execute(sql);
	}

	public int[] batchUpdate(String[] sqls) {
		for(String sql:sqls){
			info(sql);
		}
		return this.getJdbcTemplate().batchUpdate(sqls);
	}

	public int queryForInt(String sql) {
		info(sql);
		return this.getJdbcTemplate().queryForInt(sql);
	}

	public List queryForList(String sql, Class clazz) {
		info(sql);
		return this.getJdbcTemplate().queryForList(sql, clazz);
	}

	public List queryForList(String sql) {
		info(sql);
		return this.getJdbcTemplate().queryForList(sql);
	}

	public <T> T queryForObjectAndCast(String sql, Class<T> clazz) {
		info(sql);
		return (T) this.getJdbcTemplate().queryForObject(sql, clazz);
	}

	/**
	 * @param getSql
	 * @return
	 */
	public Map<String, String> queryforStrMap(String sql) {
		info(sql);
		Map map = this.getJdbcTemplate().queryForMap(sql);
		Map<String, String> strMap = new HashMap<String, String>();
		for (Object obj : map.keySet()) {
			Object value = map.get(obj) == null || map.get(obj) == "null"?"":map.get(obj);
			strMap.put(obj.toString(), StringUtils.trim(String.valueOf(value)));
		}
		return strMap;
	}
	/**
	 * @param sql
	 * @param ps
	 */
	public Boolean execute(String sql, final String[] ps) {
		info(sql);
		info(ps);
		return (Boolean) this.getJdbcTemplate().execute(sql, new PreparedStatementCallback(){
			
			public Object doInPreparedStatement(PreparedStatement sta)
					throws SQLException, DataAccessException {
				for(int i=0,len=ps.length;i<len;i++){
					if(StringUtils.isEmpty(ps[i])){
						ps[i]="";
					}
					sta.setString(i+1, ps[i]);
				}
				return sta.execute();
			}
			
		});
		
	}
	
	/**
	 * @param sql
	 */
	public int update(String sql) {
		info(sql);
		return this.getJdbcTemplate().update(sql);
	}
	/**
	 * @param sql
	 * @param pl
	 */
	public void execute(String sql, List<String> pl) {
		String[] ps = new String[pl.size()];
		for(int i=0,len=ps.length;i<len;i++){
			ps[i] = pl.get(i);
		}
		this.execute(sql, ps);
	}
	
	/**
	 * @param sql
	 * @param ps
	 */
	public int updateSql(String sql, final String[] ps) {
		info(sql);
		info(ps);
		return (Integer) this.getJdbcTemplate().execute(sql, new PreparedStatementCallback(){
			
			public Object doInPreparedStatement(PreparedStatement sta)
					throws SQLException, DataAccessException {
				for(int i=0,len=ps.length;i<len;i++){
					if(StringUtils.isEmpty(ps[i])){
						ps[i]="";
					}
					sta.setString(i+1, ps[i]);
				}
				return sta.executeUpdate();
			}
			
		});
		
	}
	/**
	 * @param updateSql
	 * @param pl
	 * @return
	 */
	public int update(String updateSql, List<String> pl) {
		String[] ps = new String[pl.size()];
		for(int i=0,len=ps.length;i<len;i++){
			ps[i] = pl.get(i);
		}
		return this.updateSql(updateSql, ps);
	}

}

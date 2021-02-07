package cn.business.main.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 访问的是业务库而非平台库。
 * @author chenfuqiang
 */
public interface CommonMapper {
	List<Map<String,Object>> getListInfos(String sql);
	List<Map<String,Object>> getListForMap(String sql);
	Map<String,Object> getMapInfo(String sql);
	List<String> getList(String sql);
	void transactionUpdate(List<String> sqlList);
	void executeSQL(@Param("sql") String sql);
	int executeForNum(@Param("sql") String sql);
}

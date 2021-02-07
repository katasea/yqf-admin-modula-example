package cn.business.main.service.util;

import cn.business.bean.dto.HandlerStateDTO;
import cn.business.myenum.HandlerType;
import cn.business.common.Global;
import cn.business.main.mapper.CommonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Transactional
@Component
public class VersionControllService {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private CommonMapper commonDao;

	public HandlerStateDTO runVersion(Integer year) {
		HandlerStateDTO businessStateDTO = new HandlerStateDTO();
		try {
			//处理语句
			List<String> sqlList = new ArrayList<>();
			StringBuilder sqlBuffer = new StringBuilder();
			//获取版本
			String version = this.getCurrentVersion();
			//处理版本
			if (version == null) {
				version = "1";
				this.addCurrentVersion(version);
			}
			int versionInt = Integer.parseInt(version);
			//版本1
			if (versionInt <= 1) {
//				sqlList.clear();
//				logger.info("---版本：1 自动修复数据启动[设置初始值图表类型、系统标识以及求和汇总]---");
//				//TODO
//				versionInt++;
//				logger.info("---版本：1 自动修复数据结束---");
			}

			//写入版本
			this.updateCurrentVersion(String.valueOf(versionInt));
			logger.info("---更新当前报表最新数据库版本：" + versionInt + "---");
		} catch (Exception e) {
			e.printStackTrace();
			businessStateDTO.setRetCode(HandlerType.UNKNOWN.getRetCode());
			businessStateDTO.setRetMsg(e.getMessage());
		}
		return businessStateDTO;
	}

	private String getCurrentVersion() {
		String sql = "SELECT PARAMSVALUE FROM AIO_PARAMS WHERE PARAMSKEY = 'AIO_DB_VERSION' AND PARAMSTYPE='2'";
		Map<String, Object> result = commonDao.getMapInfo(sql);
		if (result != null && result.keySet().size() > 0) {
			return String.valueOf(result.get("PARAMSVALUE"));
		} else {
			return null;
		}
	}

	private void updateCurrentVersion(String version) {
		String sql = "UPDATE AIO_PARAMS SET PARAMSVALUE = '" + version + "' WHERE PARAMSKEY = 'AIO_DB_VERSION' AND PARAMSTYPE='2'";
		commonDao.executeSQL(sql);
	}

	private void addCurrentVersion(String version) {
		String sql = "INSERT INTO AIO_PARAMS(PARAMSNO,PARAMSKEY,PARAMSVALUE,PARAMSTYPE) values('"+ Global.createUUID()+"','AIO_DB_VERSION','" + version + "','2')";
		commonDao.executeSQL(sql);
	}
}

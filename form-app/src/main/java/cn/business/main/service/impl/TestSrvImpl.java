package cn.business.main.service.impl;

import cn.business.bean.dto.HandlerStateDTO;
import cn.business.bean.po.UserInfo;
import cn.business.bean.vo.RequestVO;
import cn.business.bean.vo.ResponseVO;
import cn.business.common.RedisLock;
import cn.business.main.aop.LogAnnotation;
import cn.business.main.service.TestSrv;
import cn.business.main.service.UserInfoService;
import cn.business.myenum.HandlerType;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.segments.MergeSegments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Transactional
@Service
public class TestSrvImpl implements TestSrv {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Resource
	private RedisLock redisLock;

	@Resource
	private UserInfoService userInfoService;

	@LogAnnotation(moduleName = "测试服务", option = "测试方法")
	public ResponseVO testService(RequestVO requestVO) {
		//获取支付配置相关信息
		ResponseVO response = new ResponseVO(requestVO);
		try {
			log.info("准备获取锁...");
			redisLock.init("Test",20*1000);
			if (redisLock.lock()) {
				log.info("成功获取锁，入参:{}", JSON.toJSONString(requestVO));
				Map<String, Object> resultMap = new HashMap<>();
				resultMap.put("no", "123456");
				resultMap.put("name", "test");
				HandlerStateDTO handlerStateDTO = new HandlerStateDTO();
				response.setBizObj(resultMap);
				if (!HandlerType.isSuccessful(handlerStateDTO)) {
					response.setRetInfo(handlerStateDTO);
				}
				log.info("开始进入睡眠模式");
				Thread.sleep(5000);
				log.info("睡眠模式解除");
				redisLock.unlock();
				log.info("释放锁");
			}
		} catch (Exception e) {
			e.printStackTrace();
			redisLock.unlock();
			response.setRetInfo(HandlerType.UNKNOWN);
			log.error("失败了，{}" + e.toString());
		}
		log.info("结束返回");
		return response;
	}

	@Override
	public ResponseVO<UserInfo> testUserRepository(RequestVO requestVO) {
		ResponseVO<UserInfo> response = new ResponseVO<>(requestVO);

		try {
			Map<String, Object> param = (Map<String, Object>) requestVO.getBizObj();
			UserInfo userInfo = new UserInfo();
			userInfo.setMindexId("123");
			userInfo.setUserName("123");
			userInfo.setUserSex("1");
			userInfo.setIdNo("23443434");
			userInfo.setIdType("1");
			userInfoService.save(userInfo);
			QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>(userInfo);
			response.setBizObj(userInfoService.getOne(queryWrapper));
		} catch (Exception e) {
			e.printStackTrace();
			response.setRetInfo(HandlerType.UNKNOWN);
			log.error("失败了，{}" + e.toString());
		}
		return response;
	}


}

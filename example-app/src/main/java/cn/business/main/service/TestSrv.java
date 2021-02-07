package cn.business.main.service;

import cn.business.bean.po.UserInfo;
import cn.business.bean.vo.RequestVO;
import cn.business.bean.vo.ResponseVO;

public interface TestSrv {
	ResponseVO testService(RequestVO requestVO);
	ResponseVO<UserInfo> testUserRepository(RequestVO requestVO);
}

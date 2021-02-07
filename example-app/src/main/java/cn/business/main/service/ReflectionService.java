package cn.business.main.service;

import cn.business.bean.vo.RequestVO;
import cn.business.bean.vo.ResponseVO;

/**
 * @author katasea
 * 2019/9/16 10:32
 */
public interface ReflectionService {
	ResponseVO<Object> doHandler(RequestVO requestVO);
}

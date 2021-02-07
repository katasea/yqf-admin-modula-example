package cn.business.config.web;

import cn.business.bean.po.Login;
import cn.business.bean.po.UserInfo;
import cn.business.common.Global;
import cn.business.myenum.HandlerType;
import cn.business.util.CommonUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author katasea
 * 2020/6/5 10:38
 */
@Configuration
@Slf4j
public class LoginHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Value("${web.login.valid.enable}")
	private int enableFilter;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return (null != methodParameter.getParameterAnnotation(Login.class) && UserInfo.class == methodParameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
		HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
		UserInfo userInfo = null;
		try {
			String tokenParam = String.valueOf(request.getSession().getAttribute(Global.TOKENHEADER));
			if(CommonUtil.isEmpty(tokenParam)) {
				tokenParam = request.getParameter(Global.TOKENHEADER);
				if (CommonUtil.isNotEmpty(tokenParam)) {
					//不为空就存放于session方便后续接口不需要传递token
					//这边注意 避免每次都放入session，会造成永远不会登录过期或登录过期时间与admin不符合。
					request.getSession().setAttribute(Global.TOKENHEADER, tokenParam);
				}
			}
			if (CommonUtil.isEmpty(tokenParam)) {
				log.info("未发现HEADER入参 ： " + Global.TOKENHEADER);
			} else {
				// 验证tokenParam是否正确
				try {
					JSONObject userInfoObj = JSONObject.parseObject((String) redisTemplate.opsForValue().get(tokenParam));
					userInfo = new UserInfo();
					userInfo.setBean(userInfoObj);
				}catch (Exception e) {
					e.printStackTrace();
					log.error("获取登录用户信息错误：{}",e.getMessage());
				}
			}
			if (userInfo == null) {
				if (enableFilter == 0) {
					userInfo = getMonickUserInfo();
				}
			}
			return userInfo;
		} catch (Exception e) {
			return getMonickUserInfo();
		}
	}

	private UserInfo getMonickUserInfo() {
		UserInfo userInfo = new UserInfo();
		userInfo.setUserid("test");
		userInfo.setUsername("test");
		userInfo.setAdmin(true);
		logger.info("无法获取登录用户信息，模拟返回用户：{} 要修改检索：LoginHandlerMethodArgumentResolver.java",JSONObject.toJSONString(userInfo));
		return userInfo;
	}
}

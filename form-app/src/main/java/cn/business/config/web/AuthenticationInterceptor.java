package cn.business.config.web;

import cn.business.bean.po.UserInfo;
import cn.business.bean.vo.ResponseVO;
import cn.business.common.Global;
import cn.business.myenum.HandlerType;
import cn.business.util.CommonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {
	@Value("${web.login.ttl:24}")
	private long ttl;
	@Value("${web.login.valid.enable}")
	private int enableFilter;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Resource
	private RedisTemplate<String, Object> redisTemplate;


	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		if (enableFilter == 0) {
			return true;
		} else {
			String tokenParam = String.valueOf(request.getSession().getAttribute(Global.TOKENHEADER));
			if(CommonUtil.isEmpty(tokenParam)) {
				tokenParam = request.getParameter(Global.TOKENHEADER);
			}

			if (StringUtils.isNotBlank(tokenParam)) {
				//验证tokenParam是否正确
				try {
					JSONObject userInfo = JSONObject.parseObject((String) redisTemplate.opsForValue().get(tokenParam));

					if (userInfo != null) {
//						log.info("登录拦截器：登录成功！{}",userInfo.toString());
						return true;
					} else {
						return this.responseFalse(response, HandlerType.USER_NO_LOGIN);
					}
				} catch (Exception e) {
					e.printStackTrace();
					return this.responseFalse(response, HandlerType.SYSTEM_ERROR);
				}
			} else {
				log.info("未发现HEADER入参 ： " + Global.TOKENHEADER);
				return this.responseFalse(response, HandlerType.LOGIN_MTOKEN_NOFIND);
			}
		}
	}


	private boolean responseFalse(HttpServletResponse response, HandlerType handlerType) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		ResponseVO<String> responseVO = new ResponseVO<>();
		responseVO.setRetInfo(handlerType);
		log.info("登录拦截器：登录失败！信息:{}", JSON.toJSONString(responseVO));
		PrintWriter out = response.getWriter();
		out.write(JSON.toJSONString(responseVO));
		out.flush();
		out.close();
		return false;
	}


	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
	}
}

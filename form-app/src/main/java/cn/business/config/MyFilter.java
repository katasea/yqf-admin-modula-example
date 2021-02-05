package cn.business.config;


import cn.business.bean.vo.ResponseVO;
import cn.business.common.Global;
import cn.business.myenum.HandlerType;
import cn.business.util.CommonUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebFilter(filterName = "myFilter", urlPatterns = "/*")
public class MyFilter implements Filter {
	@Value("${web.login.valid.enable}")
	private int enableFilter;
	@Value("${yqf.admin.login_url}")
	private String loginUrl;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void destroy() {
		System.out.println("过滤器销毁");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		if (enableFilter != 0) {
			String tokenParam = String.valueOf(req.getSession().getAttribute(cn.business.common.Global.TOKENHEADER));
			if (CommonUtil.isEmpty(tokenParam)) {
				tokenParam = request.getParameter(Global.TOKENHEADER);
			}
			try {
				JSONObject userInfo = JSONObject.parseObject((String) redisTemplate.opsForValue().get(tokenParam));
				if (userInfo == null) {
					this.responseFalse(resp, HandlerType.USER_NO_LOGIN);
				}
			} catch (Exception e) {
				e.printStackTrace();
				this.responseFalse(resp, HandlerType.SYSTEM_ERROR);
			}
		}


		chain.doFilter(request, response);
	}

	private boolean responseFalse(HttpServletResponse response, HandlerType handlerType) throws IOException {
		String errorMsg = "当前模块未检测到登录信息，登录超时或未登录，请重新登录！";
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		ResponseVO<String> responseVO = new ResponseVO<>();
		responseVO.setRetInfo(handlerType);
		logger.info("登录拦截器：登录失败！信息:{}", JSON.toJSONString(responseVO));
		PrintWriter out = response.getWriter();
		out.println("<script language='javascript'>");
		out.println("window.alert('" + errorMsg + "');");
		out.println("var frameArray = window.top.frames;");
		out.println("window.parent.top.location.href='" + loginUrl + "';");
		out.println("</script>");
		out.flush();
		out.close();
		return false;
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}

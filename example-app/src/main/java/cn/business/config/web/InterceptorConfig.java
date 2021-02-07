package cn.business.config.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author katasea
 * 2020/5/12 13:48
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
	@Resource
	private InterceptorIgnoreValue interceptorIgnoreValue;

	@Resource
	LoginHandlerMethodArgumentResolver userArgumentResolver;

	@Resource
	AuthenticationInterceptor authenticationInterceptor;

//	@Resource
//	AuthorizationInterceptor authorizationInterceptor;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(userArgumentResolver);
	}

	/**
	 * 添加静态资源
	 * @param registry
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

	/**
	 * 添加拦截器，忽略的URL不填则会判断是否登录
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authenticationInterceptor)
				.addPathPatterns("/**")
				.excludePathPatterns(interceptorIgnoreValue.getPortal());
//		registry.addInterceptor(authorizationInterceptor)
//				.addPathPatterns("/**")
//				.excludePathPatterns(interceptorIgnoreValue.getAdmin());
		super.addInterceptors(registry);
	}


}

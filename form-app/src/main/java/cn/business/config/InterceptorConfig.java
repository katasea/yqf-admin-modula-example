package cn.business.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @author katasea
 * 2020/5/12 13:48
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
	/**
	 * 添加静态资源
	 * @param registry
	 */
	@Override
	protected void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
	}

//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new SsoClientInterceptor()).addPathPatterns("/**").excludePathPatterns("/toLogin","/login");
//		super.addInterceptors(registry);
//	}
}

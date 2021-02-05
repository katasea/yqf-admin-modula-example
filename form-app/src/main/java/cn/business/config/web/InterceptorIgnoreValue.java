package cn.business.config.web;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author katasea
 * 2020/7/29 14:39
 */

@Component
@Data
@ConfigurationProperties(prefix = "web.interceptor.ignore")
public class InterceptorIgnoreValue {
	private List<String> portal;
}

package cn.business.bean.po;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 权限验证
 * 默认都验证，不验证的话请配置 ignore
 * @author katasea
 * 2020/5/18 17:43
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAuthority {
	boolean ignore() default false;

	/**
	 * 对应resource表的auth字段
	 * @return
	 */
	String auth() default "";
}

package cn.business.main.handler;

import cn.business.bean.po.Login;
import cn.business.bean.po.UserInfo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author katasea
 * 2021/2/3 15:12
 */
@Controller
public class FormHandler {
	@GetMapping("/test")
	public String test(@Login UserInfo userInfo) {
		System.out.println(JSONObject.toJSONString(userInfo));
		return "function/test";
	}
}

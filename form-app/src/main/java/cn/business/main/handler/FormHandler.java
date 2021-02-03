package cn.business.main.handler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author katasea
 * 2021/2/3 15:12
 */
@Controller
public class FormHandler {
	@GetMapping("/test")
	public String test() {
		return "function/test";
	}
}

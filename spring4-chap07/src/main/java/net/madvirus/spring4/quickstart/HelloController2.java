package net.madvirus.spring4.quickstart;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloController2 {

	@RequestMapping("/hello.do2")
	public String hello(Model model) {
		model.addAttribute("greeting", "�ݰ����ϴ�.");
		return "hello";
	}
}


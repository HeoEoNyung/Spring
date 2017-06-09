package com.test.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.test.Mybatis.Member;
import com.test.Mybatis.MemberDAOService;

@Controller
public class MybatisController {
	
	
	@Autowired
	private MemberDAOService memberDAOService;
	
	private static final Logger logger = LoggerFactory.getLogger(MybatisController.class);
	
	@RequestMapping("/main")
	public ModelAndView main(Locale locale, Model model) {
		logger.info("welcome main.", locale);
		
		ModelAndView result = new ModelAndView();
		
		List<Member> memberList = memberDAOService.getMembers();
		result.addObject("result", memberList);
		result.setViewName("/main");
		return result;
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public ModelAndView insert(HttpServletRequest request) {
		
		Member member = new Member();
		member.set_name((String)request.getParameter("name"));
		member.set_email((String)request.getParameter("email"));
		member.set_phone((String)request.getParameter("phone"));
		
		memberDAOService.inserMember(member);
		System.out.println("insetComplet");
		
		ModelAndView result = new ModelAndView();
		List<Member> memberList = memberDAOService.getMembers();
		result.addObject("result", memberList);
		result.setViewName("result");
		return result;
			
	}
	
	
}

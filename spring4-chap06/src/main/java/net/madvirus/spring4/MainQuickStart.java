package net.madvirus.spring4;


import org.springframework.context.support.GenericXmlApplicationContext;

import net.madvirus.spring4.board.NewArticleRequest;
import net.madvirus.spring4.board.ReadArticleService;
import net.madvirus.spring4.board.WriteArticleService;
import net.madvirus.spring4.member.MemberRegRequest;
import net.madvirus.spring4.member.MemberService;

public class MainQuickStart {
	
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = new GenericXmlApplicationContext("classpath:acQuickStart.xml");
		
		WriteArticleService writeArticleService = ctx.getBean("writeArticleService", WriteArticleService.class);
	
		writeArticleService.write(new NewArticleRequest("writer", "title", "content"));
		ReadArticleService readArticleService = ctx.getBean(ReadArticleService.class);
		readArticleService.read(1);
		readArticleService.read(1);
	
		MemberService memberService = ctx.getBean(MemberService.class);
		MemberRegRequest memberRegRequest = new MemberRegRequest("id", "name", "password");
		memberService.regist(memberRegRequest);
		
		ctx.close();
		
	}
}

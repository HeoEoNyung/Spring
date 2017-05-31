package net.madvirus.chap02;

import net.madvirus.chap02.AuthenticationService;
import net.madvirus.chap02.PasswordChangeService;

import org.springframework.context.support.GenericXmlApplicationContext;


public class MainByXmlNamespace {
	
	public static void main(String[] args) {
		GenericXmlApplicationContext ctx = 
				new GenericXmlApplicationContext("classpath:config-with-*.xml");
		AuthenticationService authSvc = 
				ctx.getBean("authenticationService", AuthenticationService.class);
		authSvc.authenticate("bkchoi", "1234");
		PasswordChangeService pwChgSvc = ctx.getBean(PasswordChangeService.class);
		pwChgSvc.changePassword("bkchoi", "1234", "5678");
		authSvc.authenticate("bkchoi", "5678");
		ctx.close();
	}
	
}

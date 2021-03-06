package net.madvirus.chap02.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import net.madvirus.chap02.AuthFailLogger;
import net.madvirus.chap02.AuthenticationService;
import net.madvirus.chap02.PasswordChangeService;
import net.madvirus.chap02.UserRepository;


@Configuration
public class Config1 {
	
	@Autowired
	private UserRepository userRepository;
	
	@Bean
	public PasswordChangeService pwChangeSvc() {
		return new PasswordChangeService(userRepository);
	}

	@Bean
	public AuthFailLogger authFailLogger() {
		AuthFailLogger logger = new AuthFailLogger();
		logger.setThreshold(2);
		return logger;
	}

	@Bean
	public AuthenticationService authenticationService() {
		AuthenticationService authSvc = new AuthenticationService();
		authSvc.setFailLogger(authFailLogger());
		authSvc.setUserRepository(userRepository);
		return authSvc;
	}
	
}

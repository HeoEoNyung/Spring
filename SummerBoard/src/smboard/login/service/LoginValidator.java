package smboard.login.service;

import org.springframework.validation.Validator;
import org.springframework.validation.Errors;
import smboard.login.model.LoginSessionModel;


public class LoginValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		
		return LoginSessionModel.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LoginSessionModel loginModel = (LoginSessionModel) target;
		
		if(loginModel.getUserId() == null || loginModel.getUserId().trim().isEmpty()) {
			errors.rejectValue("userId", "required");
		}
		
		if(loginModel.getUserPw() == null || loginModel.getUserPw().trim().isEmpty()){
			errors.rejectValue("userPw", "required");
		}
		
	}
	
	
	
}

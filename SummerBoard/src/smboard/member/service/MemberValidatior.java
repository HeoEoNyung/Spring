package smboard.member.service;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import smboard.member.model.MemberModel;

public class MemberValidatior implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return MemberModel.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
	MemberModel memberModel = (MemberModel) target;
		
		if(memberModel.getUserId() == null || memberModel.getUserId().trim().isEmpty()){
			errors.rejectValue("userId", "required");
		}
		
		if(memberModel.getUserPw() == null || memberModel.getUserPw().trim().isEmpty()){
			errors.rejectValue("userPw", "required");
		}
		
		if(memberModel.getUserName() == null || memberModel.getUserName().trim().isEmpty()){
			errors.rejectValue("userName", "required");
		}

	}
	
	
	
}

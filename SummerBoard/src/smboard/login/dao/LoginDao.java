package smboard.login.dao;

import smboard.login.model.LoginSessionModel;

public interface LoginDao {
	
	LoginSessionModel checkUserId(String userId);
	
}

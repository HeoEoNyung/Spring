package net.madvirus.chap02;

public class User {
	
	private String id;
	private String password;
	
	public User(String id, String password) {
		this.id = id;
		this.password = password;
	}

	public String getId() {
		return id;
	}
	
	public boolean matchPassword(String inputPasswd) {
		
		return password.equals(inputPasswd);
	}
	
	public void changePassword(String oldPassword, String newPassword) {
		if(!matchPassword(oldPassword)) {
			System.out.println("변경 실패");
			throw new IllegalArgumentException("illegal password");
		}
		System.out.println("변경 성공");
		password = newPassword;
	}
	
}	

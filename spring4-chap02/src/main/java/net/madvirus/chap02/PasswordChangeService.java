package net.madvirus.chap02;

public class PasswordChangeService {
	
	private UserRepository userRepository;
	
	public PasswordChangeService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	public void changePassword(String userId, String oldPw, String newPw) {
		User user = userRepository.findUserById(userId);
		if(user == null) {
			System.out.println("아이디 틀려서 비번 변경 불가");
			throw new UserNotFoundException();
		}
		System.out.println("비밀번호 변경 성공"+userId+newPw);
		user.changePassword(oldPw, newPw);
	}
	
	
}

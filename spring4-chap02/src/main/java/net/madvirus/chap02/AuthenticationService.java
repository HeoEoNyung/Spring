package net.madvirus.chap02;

public class AuthenticationService {
	
	private UserRepository userRepository;
	private AuthFailLogger failLogger;
	
	public AuthInfo authenticate(String id, String password) {
		User user = userRepository.findUserById(id);
		if(user == null) {
			System.out.println("id 없음");
			throw new UserNotFoundException();
		}
		
		if(!user.matchPassword(password)) {
			failLogger.insertBadPw(id, password);
			System.out.println("로그인 실패");
			throw new AuthException();
		}
		
		System.out.println("로그인 성공"+user.getId() +password);
		return new AuthInfo(user.getId());
	}

	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void setFailLogger(AuthFailLogger failLogger) {
		this.failLogger = failLogger;
	}
	
	
	
}

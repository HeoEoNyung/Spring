package first.commons.util;

import java.util.UUID;

public class CommonsUtils {
	
	public static String getRandomString() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}

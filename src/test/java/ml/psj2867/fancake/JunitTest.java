package ml.psj2867.fancake;

import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Testable
public class JunitTest {

	@Test
	public void testLog() {
		
	}

	@Test
	public void encodePassword(){
		String rawPassword = "ECaVQYmn";
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(rawPassword);
		System.out.println(rawPassword);
		System.out.println(encodedPassword);
	}

}

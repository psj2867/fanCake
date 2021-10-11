package ml.psj2867.demo.service.user;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ml.psj2867.demo.configure.security.AuthEnum;
import ml.psj2867.demo.dao.AuthoritiesEntityDao;
import ml.psj2867.demo.dao.UserEntityDao;
import ml.psj2867.demo.entity.AuthoritiesEntity;
import ml.psj2867.demo.entity.UserEntity;
import ml.psj2867.demo.service.user.model.UserForm;
import ml.psj2867.demo.service.user.model.auth.LoginTypeEnum;

@ActiveProfiles("test")
@SpringBootTest
public class UserServiceTest {

	@Autowired
	private  UserService userService;


	@Autowired
	private  UserEntityDao userDao;
	@Autowired
	private  AuthoritiesEntityDao authDao;



	@Test
	void addAuthTest() {
		String id = "psj2867";
		String password = "1234";
		UserEntity UserEntity = userDao.findByIdIsAndLoginTypeIs(id, LoginTypeEnum.ORIGIN).get();
		AuthoritiesEntity auth = userService.addAuth(UserEntity.getIdx(), AuthEnum.USER);
		
		Assert.assertEquals(UserEntity, auth.getUser());
	}


	@Test
	void sessionTest() {
		String id = "psj2867";
		String password = "1234";
		UserEntity userEntity = userDao.findByIdIsAndLoginTypeIs(id, LoginTypeEnum.ORIGIN).get();
		List<AuthoritiesEntity> auth = authDao.findByUserIs(userEntity);
		System.out.println(auth);
		// Assert.assertEquals(UserEntity, auth.getUser());
	}

}

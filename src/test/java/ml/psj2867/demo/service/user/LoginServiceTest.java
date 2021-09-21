package ml.psj2867.demo.service.user;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import ml.psj2867.demo.dao.UserEntityDao;
import ml.psj2867.demo.entity.UserEntity;
import ml.psj2867.demo.service.user.model.LoginTypeEnum;
import ml.psj2867.demo.service.user.model.UserForm;

@ActiveProfiles("test")
@SpringBootTest
class LoginServiceTest {

	@Autowired
	private  UserService loginService;


	@Autowired
	private  UserEntityDao userDao;



	@Test
	void addOriginUserTest() {
		String id = "user1";
		String password = "1234";
		UserEntity UserEntity = loginService.addOriginUser(new UserForm(id, password));
		UserEntity dbUser = userDao.findByIdIsAndPasswdIsAndTypeIs(id, password, LoginTypeEnum.ORIGIN.name()).get();
		Assert.assertEquals(UserEntity.getIdx(), dbUser.getIdx());
	}



}

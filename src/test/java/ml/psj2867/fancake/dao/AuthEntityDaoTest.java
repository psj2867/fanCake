package ml.psj2867.fancake.dao;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ml.psj2867.fancake.configure.security.AuthEnum;
import ml.psj2867.fancake.dao.AuthoritiesEntityDao;
import ml.psj2867.fancake.dao.UserEntityDao;
import ml.psj2867.fancake.entity.AuthoritiesEntity;
import ml.psj2867.fancake.entity.UserEntity;
import ml.psj2867.fancake.service.user.model.auth.LoginTypeEnum;

@ActiveProfiles("test")
@SpringBootTest
public class AuthEntityDaoTest {

	@Autowired
	private  UserEntityDao userDao;
	@Autowired
	private  AuthoritiesEntityDao authDao;

	@Transactional
	@Test
	void addAuthTest(){
		String id ="psj2867";
		UserEntity user = userDao.findByIdIsAndLoginTypeIs(id, LoginTypeEnum.ORIGIN).get();
		AuthoritiesEntity auth = AuthoritiesEntity.builder()
											.auth(AuthEnum.USER)
											.build();
		auth.setUser(user);
		authDao.save(auth);
		System.out.println(authDao.getById(auth.getIdx()));
		System.out.println(authDao.getById(auth.getIdx()).getUser());	
	}


}

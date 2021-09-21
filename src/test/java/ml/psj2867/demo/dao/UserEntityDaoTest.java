package ml.psj2867.demo.dao;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ml.psj2867.demo.entity.UserEntity;
import ml.psj2867.demo.service.user.model.LoginTypeEnum;

@ActiveProfiles("test")
@SpringBootTest
class UserEntityDaoTest {

	@Autowired
	private  UserEntityDao userDao;


	@Test
	void UserEntitySaveTest() {
		String id = "psj2867";
		String password = "1234";
		String name = "someOne";
		String type =  LoginTypeEnum.ORIGIN.name();

		UserEntity user  = UserEntity.builder()
								.id(id)
								.passwd(password)
								.name(name)
								.type(type)
								.build();
		userDao.save(user);
		UserEntity dbUser = userDao.findByIdIsAndTypeIs(id,type).get();
		Assert.assertEquals(user.getIdx(), dbUser.getIdx());
	}


	@Test
	
	void findByIdIsAndPasswdIsTest(){
		String id = "psj2867";
		String password = "passsword";
		UserEntity userEntity = userDao.findByIdIsAndPasswdIsAndTypeIs(id, password,LoginTypeEnum.ORIGIN.name()).get();
		System.out.println( userEntity );
		Assert.assertEquals(userEntity.getId(), id);
	}

	@Test
	void getAllTest() {		
		System.out.println( userDao.findAll() );
	}


}

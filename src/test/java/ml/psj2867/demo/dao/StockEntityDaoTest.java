package ml.psj2867.demo.dao;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ml.psj2867.demo.entity.UserEntity;
import ml.psj2867.demo.entity.VideoEntity;
import ml.psj2867.demo.service.user.model.LoginTypeEnum;

@ActiveProfiles("test")
@SpringBootTest
public class StockEntityDaoTest {

	@Autowired
	private  UserEntityDao userDao;
	@Autowired
	private  AuthoritiesEntityDao authDao;
	@Autowired
	private  VideoEntityDao videoDao;
	@Autowired
	private  StockEntityDao stockDao;

	@Transactional
	@Test
	void addVideoTest(){
		String id ="psj2867";
		// UserEntity user = userDao.findByIdIsAndLoginTypeIs(id, LoginTypeEnum.ORIGIN).get();
		VideoEntity video = VideoEntity.builder()
						.videoId("asdf654e")
						.build();
		// video.setOwner(user);
		videoDao.save(video);
		// System.out.println(videoDao.getById(video.getIdx()).getOwner());		
		// System.out.println(videoDao.getById(video.getIdx()));		
	}


}

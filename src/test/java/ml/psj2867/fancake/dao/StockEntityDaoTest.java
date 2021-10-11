package ml.psj2867.fancake.dao;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ml.psj2867.fancake.dao.VideoEntityDao;
import ml.psj2867.fancake.entity.VideoEntity;

@ActiveProfiles("test")
@SpringBootTest
public class StockEntityDaoTest {
	@Autowired
	private  VideoEntityDao videoDao;

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

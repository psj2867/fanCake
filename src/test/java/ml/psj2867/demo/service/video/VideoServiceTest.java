package ml.psj2867.demo.service.video;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;

import ml.psj2867.demo.entity.VideoEntity;
import ml.psj2867.demo.service.video.model.VideoListForm;

@ActiveProfiles("test")
@SpringBootTest
public class VideoServiceTest {
    @Autowired
    private VideoService videoService;

    @Test
    public void listTest(){
        VideoListForm form = VideoListForm.builder()
                                            .q("")
                                            .build();
        Page<VideoEntity> list= videoService.getVideoList(form);
        System.out.println(list);
        System.out.println(list.getContent());
    }


}

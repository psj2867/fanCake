package ml.psj2867.fancake.service.video;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import ml.psj2867.fancake.entity.VideoEntity;
import ml.psj2867.fancake.service.video.VideoService;
import ml.psj2867.fancake.service.video.model.VideoDto;
import ml.psj2867.fancake.service.video.model.VideoListForm;


@ActiveProfiles("test")
@SpringBootTest
public class VideoServiceTest {
    @Autowired
    private VideoService videoService;

    @Test
    public void listTest(){
        VideoListForm form = VideoListForm.builder()
                                            .videoTitle("title")
                                            .page(2)
                                            .sort("idx")
                                            .asc(false)
                                            .build();
        Page<VideoDto> list= videoService.getVideoList(form);
        System.out.println(list);
        System.out.println(list.getContent());
        list.getContent().stream().forEach(System.out::println);
    }



}

package ml.psj2867.demo;

import ml.psj2867.demo.configure.ConfigureProperties;
import ml.psj2867.demo.configure.properties.GoogleProperty;
import ml.psj2867.demo.entity.ChannelEntity;
import ml.psj2867.demo.service.channel.model.ChannelForm;
import ml.psj2867.demo.service.youtube.YoutubeService;

public class Test {
    public static void main(String[] args) throws Exception {
        f1();
    }

    public static void f1() throws Exception {
        ConfigureProperties config = ConfigureProperties.builder()
                .google(GoogleProperty.builder().api_key("AIzaSyCjItvNoT8fOdYQ7X4OMgh7SVFtvJKq6hI").build()).build();
        YoutubeService youtube = new YoutubeService(config);
        System.out.println(youtube.getChannelList("떼껄룩"));

    }

    public static void f2() throws Exception {
        ChannelForm form = ChannelForm.builder().channel_id("asdf").build();
        ChannelEntity entity = form.toEntity();
        System.out.println(entity);

    }
}
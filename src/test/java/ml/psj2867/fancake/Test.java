package ml.psj2867.fancake;

import ml.psj2867.fancake.configure.ConfigureProperties;
import ml.psj2867.fancake.configure.properties.GoogleProperty;
import ml.psj2867.fancake.entity.ChannelEntity;
import ml.psj2867.fancake.service.channel.model.ChannelForm;
import ml.psj2867.fancake.service.youtube.YoutubeService;
import ml.psj2867.fancake.util.exceptionwrap.ExceptionOptional;

public class Test {
    public static void main(String[] args) throws Exception {
        f2();
    }

    public static void f1() throws Exception {
        ConfigureProperties config = ConfigureProperties.builder()
                .google(GoogleProperty.builder().api_key("AIzaSyCjItvNoT8fOdYQ7X4OMgh7SVFtvJKq6hI").build()).build();
        YoutubeService youtube = new YoutubeService(config);
        System.out.println(youtube.getChannelList("떼껄룩"));

    }

    public static void f2() throws Exception {
        String a ="";
        Object b = a.split(",");
        System.out.println(b);
    }
}
package ml.psj2867.fancake.service.youtube;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.Video;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ml.psj2867.fancake.configure.ConfigureProperties;
import ml.psj2867.fancake.service.video.model.VideoForm;
import ml.psj2867.fancake.service.youtube.modoel.YoutubeThumbnailEnum;

@Slf4j
@Transactional
@Service
public class YoutubeService  {
    private static final String YOUTUBE_CHANNEL_URL = "https://www.youtube.com/channel/%s";
    private final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    private final GsonFactory GSON_FACTORY = new GsonFactory();
    private final YouTube youtube;
    
    private List<String> SNIPPET = Arrays.asList("snippet");
    private final String ACCESS_KEY;
    public YoutubeService(ConfigureProperties configureProperties){
        this.ACCESS_KEY = configureProperties.getGoogle().getApi_key();  
        this.youtube = new YouTube.Builder(HTTP_TRANSPORT
                                            , GSON_FACTORY
                                            , new HttpRequestInitializer() {
                                                public void initialize(HttpRequest request) throws IOException {}
                                            })
                                    .setApplicationName("youtube-cmdline-search-sample")                             
                                    .build();
    }
   
    public String getViedoThumbnailUrl(VideoForm videoForm, YoutubeThumbnailEnum size){
        return size.getUrl(videoForm.getVideoId());
    }

    public Optional<Video> getVideoInfo(String videoId){
        try {        
            List<Video> items = this.youtube.videos().list(SNIPPET)
                .setId(Arrays.asList(videoId))
                .setKey(ACCESS_KEY)
                .execute()
                .getItems();
            if(items.size() == 1){
                return Optional.of( items.get(0) );
            }else{
                return Optional.empty();
            }
        } catch (Exception e) {
            log.warn("", e);
            return Optional.empty();
        }
    }
    public Optional<Channel> getChannelInfo(String channelId){
        try {        
            List<Channel> items = this.youtube.channels().list(SNIPPET)
                .setKey(ACCESS_KEY)
                .setId(Arrays.asList(channelId))
                .setPart(SNIPPET)
                .setFields("")
                .execute()
                .getItems();
            if(items.size() == 1){
                return Optional.of( items.get(0) );
            }else{
                return Optional.empty();
            }
        } catch (Exception e) {
            log.warn("", e);
            return Optional.empty();
        }
    }
    private final List<String> TYPE_CHANNEL = Arrays.asList("channel");
    private final long DEFAULT_MAX_RESULTS = 10;
    public Optional<SearchListResponse> getChannelList(String channel_name){
        try {        
            SearchListResponse reponse = this.youtube.search().list(SNIPPET)
                .setKey(ACCESS_KEY)
                .setType(TYPE_CHANNEL)
                .setFields("items/snippet(channelId,channelTitle,thumbnails(default)),nextPageToken,pageInfo")
                .setMaxResults(DEFAULT_MAX_RESULTS)
                .setQ(channel_name)
                .execute();
            return Optional.of(reponse);
        } catch (Exception e) {
            log.warn("", e);
            return Optional.empty();
        }
    }

    public static String getChannelUrl(String channelIdx){
        return String.format(YoutubeService.YOUTUBE_CHANNEL_URL , channelIdx);
    }
}
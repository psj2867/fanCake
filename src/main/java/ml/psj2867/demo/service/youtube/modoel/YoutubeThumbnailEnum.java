package ml.psj2867.demo.service.youtube.modoel;

import lombok.Data;
import lombok.Getter;

@Getter
public enum YoutubeThumbnailEnum{
      zero("0")
    , one("1")
    , two("2")
    , three("3")
    , default_size("default")
    , high("hqdefault")
    ;

    private String imageSize;
    private YoutubeThumbnailEnum(){ this.imageSize = this.name();}
    private YoutubeThumbnailEnum(String imageSize){this.imageSize = imageSize;}

    public static String URL = "https://img.youtube.com/vi/%s/%s";

    private String getJpgName(){
        return this.getImageSize() + ".jpg";
    }
    public String getUrl(String videoId){
        return String.format(URL, videoId, this.getJpgName());
    }
}
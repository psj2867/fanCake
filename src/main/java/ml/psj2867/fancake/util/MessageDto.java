package ml.psj2867.fancake.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MessageDto {
    private String message;


    public static MessageDto of(String message) {
        return new MessageDto(message);
    }
    public static MessageDto success() {
        return MessageDto.of("success");
    }
}
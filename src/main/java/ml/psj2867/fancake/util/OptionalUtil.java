package ml.psj2867.fancake.util;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

public class OptionalUtil{


    public static Optional<Integer> parseInt(String s){
        try {
            return Optional.ofNullable( Integer.parseInt(s) );
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    public static Optional<Long> parseLong(String s){
        try {
            return Optional.ofNullable( Long.parseLong(s) );
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    public static Optional<String> toJson(Object s){
        try {
            ObjectMapper mapper = new ObjectMapper();
            return Optional.ofNullable(mapper.writeValueAsString(s));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
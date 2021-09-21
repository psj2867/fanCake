package ml.psj2867.demo.util;

import java.util.Optional;

public class OptionalUtil{


    public static Optional<Integer> paseInt(String s){
        try {
            return Optional.ofNullable( Integer.parseInt(s) );
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    public static Optional<Long> paseLong(String s){
        try {
            return Optional.ofNullable( Long.parseLong(s) );
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
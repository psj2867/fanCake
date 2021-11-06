package ml.psj2867.fancake.util;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.util.StringUtils;

public class GeneralUtil {

   private static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
   private static SecureRandom rnd = new SecureRandom();

   public static String randomString(int len) {
      StringBuilder sb = new StringBuilder(len);
      for (int i = 0; i < len; i++)
         sb.append(AB.charAt(rnd.nextInt(AB.length())));
      return sb.toString();
   }

   public static <T> Optional<T> convertJson(String json, TypeReference<T> clazz) {
      ObjectMapper mapper = new ObjectMapper();
      if (!StringUtils.hasLength(json))
         return Optional.empty();
      T result = null;
      try {
         result = mapper.readValue(json, clazz);
         return Optional.ofNullable(result);
      } catch (Exception e) {
         return Optional.empty();
      }
   }

   public static <T> Optional<T> convertJson(String json, Class<T> clazz) {
      ObjectMapper mapper = new ObjectMapper();
      if (!StringUtils.hasLength(json))
         return Optional.empty();
      T result = null;
      try {
         result = mapper.readValue(json, clazz);
         return Optional.ofNullable(result);
      } catch (Exception e) {
         return Optional.empty();
      }
   }
   public static Map<String,String> convertJson2Map(String json) {
      return convertJson(json, new TypeReference<Map<String, String>>() {})
               .orElse(new HashMap<>());
   }
}
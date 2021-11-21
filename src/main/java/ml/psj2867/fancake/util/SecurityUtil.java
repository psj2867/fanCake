package ml.psj2867.fancake.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

public class SecurityUtil {

    public static Optional<String> getName(){
        return getCredentaial().map(pL -> (String)pL );
    }
    public static Optional<Integer> getUserIdx(){
        return getPrincipal().flatMap(pL -> OptionalUtil.parseInt((String)pL));
    }
    public static List<GrantedAuthority> getGrants(){
        Collection<? extends GrantedAuthority> grants = getAuth().map(Authentication::getAuthorities)
                                                                .orElse(Collections.emptyList());
        return new ArrayList<>(grants);
    }

    private static Optional<Object> getPrincipal(){
        return getAuth().map(Authentication::getPrincipal);
    }
    private static Optional<Object> getCredentaial(){
        return getAuth().map(Authentication::getCredentials);
    }
    public static boolean isAuth(){
        return hasNotAuth("ROLE_ANONYMOUS");
    }
    public static Optional<Authentication> getAuth(){
        return Optional.ofNullable( SecurityContextHolder.getContext().getAuthentication() );
    }
    public static boolean hasNotAuth(String... grant){
        return ! hasAuth(grant);
    }
    public static boolean hasAuth(String... grant){
        return  CollectionUtils.containsAny(
              getGrants().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())
            , Arrays.asList(grant) );
    }
}
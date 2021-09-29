package ml.psj2867.demo.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

public class SecurityUtil {

    public static Optional<Authentication> getAuth(){
        return Optional.ofNullable( SecurityContextHolder.getContext().getAuthentication() );
    }
    public static Optional<String> getName(){
        return getDetail().map(detailL ->(String)detailL );
    }
    public static Optional<Integer> getUserIdx(){
        return getAuth().map(authL -> (Integer)authL.getPrincipal());
    }

    public static Optional<UserDetails> getUser(){
        return getAuth().map(authL -> (UserDetails)authL.getPrincipal());
    }

    public static List<GrantedAuthority> getGrants(){
        Collection<? extends GrantedAuthority> grants = getAuth().map(authL -> authL.getAuthorities() ).orElse(new ArrayList<>()) ;
        return new ArrayList<>(grants);
    }
    public static Optional<Object> getDetail(){
        return getAuth().map(authL -> authL.getDetails() );
    }
    
    public static boolean hasAuth(GrantedAuthority... grant){
        return  CollectionUtils.containsAny(getGrants(), Arrays.asList(grant) );
    }
}
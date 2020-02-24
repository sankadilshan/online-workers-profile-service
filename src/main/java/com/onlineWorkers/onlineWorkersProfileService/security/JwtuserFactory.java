package com.onlineWorkers.onlineWorkersProfileService.security;

import com.onlineworkers.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JwtuserFactory {
    public static  List<GrantedAuthority> create(User user){
        return mapToGrantAuthorities(new ArrayList<String>(Arrays.asList("_role"+user.getRole())));
    }

    private static List<GrantedAuthority> mapToGrantAuthorities(ArrayList<String> authorities) {
        return authorities.stream().map(Authority -> new SimpleGrantedAuthority(Authority)).collect(Collectors.toList());

    }
}

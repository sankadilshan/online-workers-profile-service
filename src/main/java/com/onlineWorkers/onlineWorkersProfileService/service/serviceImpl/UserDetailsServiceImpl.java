package com.onlineWorkers.onlineWorkersProfileService.service.serviceImpl;

import com.onlineWorkers.onlineWorkersProfileService.enums.Value;
import com.onlineWorkers.onlineWorkersProfileService.security.JwtUser;
import com.onlineWorkers.onlineWorkersProfileService.security.JwtuserFactory;
import com.onlineworkers.models.PostUserDto;
import com.onlineworkers.models.User;
import com.onlineWorkers.onlineWorkersProfileService.repository.UserRepository;
import com.onlineWorkers.onlineWorkersProfileService.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserService, UserDetailsService {
   Logger logger=LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public String initiate(PostUserDto postUser) {
        User user= modelMapper.map(postUser, User.class);
        logger.info("-------------user id--------"+user.getId());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "user id : "+user.getId();

    }

    @Override
    public String getUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return user.get().getUsername();
        }
        return null;
    }

    @Override
    public long getCount() {
        Integer count = userRepository.findCount();
        return count!=null?count:  Value.ZERO.getValue();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user= userRepository.findByUsernameIgnoreCase(username);
        if(user==null){
            throw new UsernameNotFoundException(String.format("No user found with username %s",username));
        }

      return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),JwtuserFactory.create(user));
    }


}

package com.onlineWorkers.onlineWorkersProfileService.controller;

import com.onlineWorkers.onlineWorkersProfileService.enums.Response;
import com.onlineWorkers.onlineWorkersProfileService.security.JwtTokenUtil;
import com.onlineWorkers.onlineWorkersProfileService.service.UserService;
import com.onlineWorkers.onlineWorkersProfileService.service.serviceImpl.UserDetailsServiceImpl;
import com.onlineworkers.models.LoginUserDto;
import com.onlineworkers.models.PostUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(value = "http://localhost:4200",allowedHeaders = "*")
public class JwtAuthenticateController {
    Logger logger= LoggerFactory.getLogger(JwtAuthenticateController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Value("${jwt.header")
    private String tokenHeader;

    @GetMapping("/health")
    public String health(){
        return "hello profile service-custom";
    }
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserDto loginUser, HttpServletRequest request, HttpServletResponse response)throws Exception{

       try {
          authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
       }catch (BadCredentialsException e){
          throw new Exception("invalid username & password",e);
       }
        final UserDetails userDetails= userDetailsService.loadUserByUsername(loginUser.getUsername());
        final String token=jwtTokenUtil.generateToken(userDetails);
        return  ResponseEntity.ok(token);

    }

    @PostMapping("/register")
    public ResponseEntity<?> initiate(@RequestBody PostUserDto user){
        String response= userService.initiate(user);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public String getUser( @PathVariable("id") long id){
        String user = userService.getUser(id);
        if (user!=null)
            return user;
        return Response.Unknown.toString() ;
    }
    @GetMapping("/members")
    public long getCount(){
        return userService.getCount();
    }


}

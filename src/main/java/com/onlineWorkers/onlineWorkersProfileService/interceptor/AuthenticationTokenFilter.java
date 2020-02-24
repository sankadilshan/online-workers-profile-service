package com.onlineWorkers.onlineWorkersProfileService.interceptor;

import com.onlineWorkers.onlineWorkersProfileService.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class AuthenticationTokenFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
          final String authToken= request.getHeader(tokenHeader);
          String username=null;
          String jwt=null;
          if(authToken!=null && authToken.startsWith("Bearer")){
              jwt=authToken.substring(7);
              username=jwtTokenUtil.extractUserName(jwt);
          }

          if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
              UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
              boolean isValid=jwtTokenUtil.validateToken(jwt,userDetails);
              if (isValid){
                  UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                  usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                  SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
              }
          }
          filterChain.doFilter(request,response);
    }
}

package com.onlineWorkers.onlineWorkersProfileService.controller;

import com.onlineWorkers.onlineWorkersProfileService.service.UserInformationService;
import com.onlineworkers.models.PostUserInformation;
import com.onlineworkers.models.ResponseUserInfo;
import com.onlineworkers.models.UserInformation;
import org.apache.catalina.LifecycleState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(value = "http://localhost:4200",allowedHeaders = "*")
@RequestMapping("/user-info")
public class UserInformationController {

    Logger logger= LoggerFactory.getLogger(UserInformationController.class);

    @Autowired
    private UserInformationService userInformationService;

    @RequestMapping("/health")
    public String health(){
        logger.info("-----------------user-info controller-------------------");
        return  "hello profile service-userinfo";
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserInfo(
            @PathVariable("id") long id ){
        UserInformation userInformation=userInformationService.getUserInfo(id);
        if (userInformation!=null)
            return new ResponseEntity<>(userInformation, HttpStatus.OK);
        else
            return new ResponseEntity<>("no content",HttpStatus.NOT_FOUND);
    }
    @PostMapping("/{id}")
    public ResponseEntity<?> initiate(
            @PathVariable("id") long userId,
            @RequestParam(defaultValue = "") String name,
            @RequestBody PostUserInformation userInfo){
        String msg=userInformationService.initiate(userId,name,userInfo);
        if (msg!=null)
            return new ResponseEntity<>(msg,HttpStatus.CREATED);
        else
            return new ResponseEntity<>("post error --> user information",HttpStatus.BAD_REQUEST);
    }
    @GetMapping
    public ResponseEntity<?> getUserInfo(){
        List<ResponseUserInfo> responseUserInfos=userInformationService.get();
       return responseUserInfos.isEmpty()? new ResponseEntity<>(null,HttpStatus.NO_CONTENT) : new ResponseEntity<>(responseUserInfos,HttpStatus.OK);
    }

}

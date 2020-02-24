package com.onlineWorkers.onlineWorkersProfileService.service;


import com.onlineworkers.models.PostUserDto;

public interface UserService {
    String initiate(PostUserDto user);

    String getUser(long id);

    long getCount();
}

package com.onlineWorkers.onlineWorkersProfileService.service;

import com.onlineworkers.models.PostUserInformation;
import com.onlineworkers.models.ResponseUserInfo;
import com.onlineworkers.models.UserInformation;

import java.util.List;

public interface UserInformationService {
    UserInformation getUserInfo(long id);

    String initiate(long userId, String name, PostUserInformation userInfo);

    List<ResponseUserInfo> get();
}

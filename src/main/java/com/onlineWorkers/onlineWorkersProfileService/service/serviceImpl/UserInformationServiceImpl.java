package com.onlineWorkers.onlineWorkersProfileService.service.serviceImpl;

import com.onlineWorkers.onlineWorkersProfileService.repository.UserInformationRepository;
import com.onlineWorkers.onlineWorkersProfileService.service.UserInformationService;
import com.onlineworkers.models.PostUserInformation;
import com.onlineworkers.models.ResponseUserInfo;
import com.onlineworkers.models.UserInformation;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserInformationServiceImpl implements UserInformationService {

    Logger logger= LoggerFactory.getLogger(UserInformationServiceImpl.class);

    @Autowired
    private UserInformationRepository userInformationRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${social-service-url}")
    private String url;

    @Override
    public UserInformation getUserInfo(long id) {
        Optional<UserInformation> userInformation = userInformationRepository.findById(id);
        if(userInformation.isPresent()){
            logger.info("-------------- get user information --> true--------------");
            return userInformation.get();
        }
        else {
            logger.error("-------------get user informatioon --> false --------------");
            return null;
        }
    }

    @Override
    public String initiate(long userId, String name, PostUserInformation postUserInformation) {
        UserInformation userInformation=modelMapper.map(postUserInformation,UserInformation.class);
        logger.info("-------------user id-------- "+userInformation.getId());
        logger.info("-------------user address-------- "+userInformation.getAddress());
        userInformation.setUserId(userId);
        userInformation.setName(name);
        UserInformation saveUser = userInformationRepository.save(userInformation);
        logger.info("----------info----"+saveUser);
       if(saveUser!=null)
           return "user-information id "+ userInformation.getId();
       else
           return null;
    }

    @Override
    public List<ResponseUserInfo> get() {
        List<UserInformation> allUserInfo = userInformationRepository.findAll(Sort.by("registerDate").descending());
        if(!allUserInfo.isEmpty()){
            List<ResponseUserInfo> responseUserInfo= Arrays.asList(modelMapper.map(allUserInfo,ResponseUserInfo[].class));
            logger.info(responseUserInfo+"-----------------");
            return responseUserInfo;
        }
        return null;
    }


}

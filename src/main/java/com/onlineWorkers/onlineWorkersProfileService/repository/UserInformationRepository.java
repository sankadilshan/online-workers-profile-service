package com.onlineWorkers.onlineWorkersProfileService.repository;

import com.onlineworkers.models.UserInformation;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation,Long> {


}

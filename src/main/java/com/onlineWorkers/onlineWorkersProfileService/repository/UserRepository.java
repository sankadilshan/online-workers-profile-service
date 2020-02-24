package com.onlineWorkers.onlineWorkersProfileService.repository;

import com.onlineworkers.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("select count(*) from User")
    Integer findCount();

    User findByUsernameIgnoreCase(String username);
}

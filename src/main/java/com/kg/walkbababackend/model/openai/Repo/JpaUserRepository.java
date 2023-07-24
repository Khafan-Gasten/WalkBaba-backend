package com.kg.walkbababackend.model.openai.Repo;

import com.kg.walkbababackend.model.openai.DB.RouteInfo;
import com.kg.walkbababackend.model.openai.DB.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaUserRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByUserName(String userName);


 @Query( value =  "select saveRoute from UserInfo user join user.saveRoute saveRoute where user.userId = :id")
    List<RouteInfo> findSavedRouteByUserId(long id) ;
}

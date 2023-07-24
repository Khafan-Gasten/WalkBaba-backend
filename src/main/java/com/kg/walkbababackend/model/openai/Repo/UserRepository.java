package com.kg.walkbababackend.model.openai.Repo;

import com.kg.walkbababackend.model.openai.DB.RouteInfo;
import com.kg.walkbababackend.model.openai.DB.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {
    @Autowired
    JpaUserRepository repo;


    public UserInfo getUserByUserName(String userName) {
        return repo.findByUserName(userName);
    }

    public UserInfo getUserById(long id) {
        return repo.findById( id).orElse(null);
    }

    public List<RouteInfo> getSavedRoute(long id) {
      return repo.findSavedRouteByUserId( id);
    }

    public UserInfo saveUser(UserInfo user) {
        return repo.save(user);
    }
}

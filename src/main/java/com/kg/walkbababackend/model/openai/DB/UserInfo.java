package com.kg.walkbababackend.model.openai.DB;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
public class UserInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId ;
    private String userName;
    private String password;
    @OneToMany(cascade = CascadeType.ALL)
    private List<RouteInfo> saveRoute;

    public UserInfo() {
        this.saveRoute = new ArrayList<>() ;
    }

    public UserInfo(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.saveRoute = new ArrayList<>() ;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<RouteInfo> getSaveRoute() {
        return saveRoute;
    }

    public void setSaveRoute(List<RouteInfo> saveRoute) {
        this.saveRoute = saveRoute;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}

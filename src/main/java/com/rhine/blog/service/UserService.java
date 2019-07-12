package com.rhine.blog.service;


import com.rhine.blog.po.UserBean;

public interface UserService {

    UserBean findByName(String name);

    UserBean findById(String id);

    String  addUser(UserBean user);

    String  getBigId();
}

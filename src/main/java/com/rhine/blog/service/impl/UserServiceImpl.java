package com.rhine.blog.service.impl;

import com.rhine.blog.mapper.UserMapper;
import com.rhine.blog.po.UserBean;
import com.rhine.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserBean findByName(String name) {
        // 查询用户是否存在
        UserBean bean = userMapper.findByName(name);
        if (bean != null) {
            // 查询用户信息、角色、权限
          UserBean  beans = findById(bean.getId());
          if (beans != null){//新注册的用户没有角色和权限，返回用户信息，不能返回空
              bean=beans;
          }
        }
        return bean;
    }

    @Override
    public UserBean findById(String id) {
        return  userMapper.findById(id);
    }

    @Override
    public String addUser(UserBean user) {
        if (userMapper.add(user)){
            return  getBigId();
        }
        return  "0";

    }

    public  String getBigId(){
        return  userMapper.getBigId().getId();
    }

}
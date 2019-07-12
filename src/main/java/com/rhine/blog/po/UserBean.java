package com.rhine.blog.po;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author rhine
 * @description 用户类
 */
public class UserBean implements Serializable {
    private String id;
    private String name;
    private String password;
    private Set<RoleBean> roles = new HashSet<>();

    public  UserBean(){
        super();
    }
    public UserBean(String id,String name, String password) {
        this.id=id;
        this.name = name;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleBean> getRole() {
        return roles;
    }

    public void setRole(Set<RoleBean> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

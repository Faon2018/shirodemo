package com.rhine.blog.controller;


import com.rhine.blog.po.UserBean;
import com.rhine.blog.service.impl.UserServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class MainController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @RequestMapping("/main")
    public String index(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("root", request.getContextPath());

        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("root", request.getContextPath());

        return "login";
    }
    @RequestMapping("/register")//登录
    public String register(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("root", request.getContextPath());
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        if (!StringUtils.isEmpty(userName)){
            String  id;
            UserBean  user=userServiceImpl.findByName(userName);
            if (user != null){
                request.setAttribute("msg","用户名已被占用！");
                return "register";
            }else {

                String hashAlgorithName = "MD5";
                //加密次数
                int hashIterations = 1024;
                ByteSource credentialsSalt = ByteSource.Util.bytes(userName);//28e5ea71eb6600afb02132dcf27b8e75
                Object obj =  new SimpleHash(hashAlgorithName, password, credentialsSalt, hashIterations);
                String  userId=Integer.parseInt(userServiceImpl.getBigId())+1+"";
                id=userServiceImpl.addUser(new UserBean(userId,userName,obj.toString()));
                System.out.println("id:"+id);
            }
            if (id.equals("0")){
                request.setAttribute("msg","注册失败！");
                return "register";
            }else {//注册成功自动登录
                return this.verifyLogin(userName,password,request);
            }

        }
        return "register";
    }

    @RequestMapping("/login")//注册
    public String login(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("root", request.getContextPath());
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        return this.verifyLogin(userName,password,request);
    }

    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            subject.logout();
        }
        return "redirect:/main";
    }

    @RequestMapping("/error/unAuth")
    public String unAuth(){
        return "/error/unAuth";
    }

    /**
     * 验证登录方法
     */
    public  String   verifyLogin(String userName,String password,HttpServletRequest request){
        if(!StringUtils.isEmpty(userName)){
            // 1.获取Subject
            Subject subject = SecurityUtils.getSubject();
            // 2.封装用户数据
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            // 3.执行登录方法
            try{
                subject.login(token);
                return "redirect:/main";
            } catch (UnknownAccountException e){
                System.out.println("用户名不存在！");
                request.setAttribute("msg","用户名不存在！");
            } catch (IncorrectCredentialsException e){
                System.out.println("密码错误！");
                request.setAttribute("msg","密码错误！");
            }
        }
        return  "login";
    }
}

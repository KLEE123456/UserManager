package com.klee.UserManager.controller;

import com.klee.UserManager.pojo.User;
import com.klee.UserManager.service.UserService;
import com.klee.UserManager.utils.Md5Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@Controller
@RequestMapping(value = "/user/*")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "findUser")
    public String login(Model model,User user){
        String userPwd = user.getUserPwd();
        user.setUserPwd(Md5Encrypt.MD5(userPwd));
        User user1 = userService.findUser(user);
        if (user1!=null){
            model.addAttribute("user",user1);
            return "home";
        }
        else {
            model.addAttribute("msg","用户名或密码错误!");
            model.addAttribute("userName",user.getUserName());
            return "login";
        }

    }
    @RequestMapping(value = "checkName")
    public void checkName(String userName, HttpServletResponse response) throws IOException {
        User user = userService.checkName(userName);
        PrintWriter out=response.getWriter();
        if (user==null){
           out.print(1);
        }
        else {
            out.print(0);
        }
    }
    @RequestMapping(value = "register")
    public String register(User user,Model model){
        String pwd=user.getUserPwd();
        user.setUserPwd( Md5Encrypt.MD5(pwd));
        int rows = userService.register(user);
        if (rows>0){
            return "login";
        }
        else {
            model.addAttribute("reg","注册失败!");
            return "register";
        }
    }
}

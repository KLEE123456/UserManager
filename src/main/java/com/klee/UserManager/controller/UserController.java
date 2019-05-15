package com.klee.UserManager.controller;

import com.github.pagehelper.PageInfo;
import com.klee.UserManager.pojo.User;
import com.klee.UserManager.service.UserService;
import com.klee.UserManager.utils.Md5Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@Controller
@RequestMapping(value = "/user/*")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "findUser")
    public String login(Model model, User user, HttpSession session){
        String userPwd = user.getUserPwd();
        user.setUserPwd(Md5Encrypt.MD5(userPwd));
        User user1 = userService.login(user);
        if (user1!=null){
            model.addAttribute("user",user1);
            session.setAttribute("userMsg",user1);
            return "forward:findAllUser.action";
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
    @RequestMapping(value = "findAllUser")
    public String findAllUser(Model model,HttpSession session,Integer pageNum){
        System.out.println(pageNum);
        List<User> userList = userService.findUser(null,pageNum);
        PageInfo pageInfo=new PageInfo(userList);
        session.setAttribute("page",pageInfo);
        model.addAttribute("userList",userList);
        return "home";
    }
    @RequestMapping(value = "userQuit")
    public String userQuit(HttpSession session){
        session.invalidate();
        return "login";
    }

}

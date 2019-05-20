package com.klee.UserManager.controller;

import com.github.pagehelper.PageInfo;
import com.klee.UserManager.pojo.User;
import com.klee.UserManager.service.UserService;
import com.klee.UserManager.utils.Md5Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    @RequestMapping(value = "insertUser")
    public  String  insertUser(User user,Model model){
        String pwd=user.getUserPwd();
        user.setUserPwd(Md5Encrypt.MD5(pwd));
        int rows = userService.insertUser(user);
        if (rows>0){
            return  "forward:findAllUser.action";
        }
        else {
            model.addAttribute("ist","添加失败!请和管理员联系");
            return "add";
        }
    }
    @RequestMapping(value = "editXr")
    public  String  editXr(Integer userId,Integer pageNum,Model model,HttpSession session){
        session.setAttribute("pageNum",pageNum);
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        List<User> user = userService.findUser(map, pageNum);
        model.addAttribute("user",user.get(0));
        return  "userEdit";
    }
    @RequestMapping(value = "editUser")
    public String editUser(User user,HttpSession session,Model model){
        Integer pageNum=(Integer)session.getAttribute("pageNum");
        System.out.println(user);
        int rows = userService.updateUser(user);
        if (rows>0){
            return "forward:findAllUser.action?pageNum="+pageNum;
        }
        else {
            model.addAttribute("msg","编辑错误!");
           return "userEdit";
        }
    }
    @RequestMapping(value = "testJson")
    @ResponseBody
    public User testJson(@RequestBody User user){
        System.out.println(user);
        return  user;
    }
    @RequestMapping(value = "deleteUser")
    public String deleteUser(Integer userId, Integer pageNum,HttpServletResponse response) throws IOException {
        int rows = userService.deleteUser(userId);
        if (rows>0){
            return "forward:findAllUser.action?pageNum="+pageNum;
        }
        else {
            PrintWriter out = response.getWriter();
            out.print("<script>alert('删除失败！')</script>");
           return "forward:findAllUser.action?pageNum="+pageNum;
        }

    }
}

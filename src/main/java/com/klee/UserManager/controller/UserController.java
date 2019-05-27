package com.klee.UserManager.controller;

import com.github.pagehelper.PageInfo;
import com.klee.UserManager.pojo.User;
import com.klee.UserManager.service.UserService;
import com.klee.UserManager.utils.Md5Encrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


@Controller
@RequestMapping(value = "/user/*")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping(value = "login")
    public String login(Model model, User user, HttpSession session){
        if (user.getUserName()==null||user.getUserPwd()==null){
            model.addAttribute("msg","用户名或密码错误!");
            return "login";
        }
        String userPwd = user.getUserPwd();
        user.setUserPwd(Md5Encrypt.MD5(userPwd));
        User user1 = userService.login(user);
        if (user1!=null){
            model.addAttribute("user",user1);
            session.setAttribute("userMsg",user1);
            return "forward:home.action";
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
    @RequestMapping(value = "home")
    public String findAllUser(Model model, HttpSession session, Integer pageNum,String userNames,String method){
        if (userNames!=null){
            session.setAttribute("userNames",userNames);
        }
        List<User> userList=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        String userNames1 = (String) session.getAttribute("userNames");
        if (userNames1!=null&&method==null){
            map.put("userName",userNames1);
        }
        else {
            map.put("userName",null);
        }
        userList = userService.findUser(map,pageNum);
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
    @RequestMapping(value = "add")
    public  String  insertUser(User user,Model model){
        String pwd=user.getUserPwd();
        user.setUserPwd(Md5Encrypt.MD5(pwd));
        int rows = userService.insertUser(user);
        if (rows>0){
            return  "forward:home.action?method=add";
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
    @RequestMapping(value = "userEdit")
    public String editUser(User user,HttpSession session,Model model){
        Integer pageNum=(Integer)session.getAttribute("pageNum");
        int rows = userService.updateUser(user);
        if (rows>0){

            return "forward:home.action?method=edit&pageNum="+pageNum;
        }
        else {
            model.addAttribute("msg","编辑错误!");

           return "userEdit";
        }
    }
    @RequestMapping(value = "deleteUser")
    public String deleteUser(Integer userId, Integer pageNum,HttpServletResponse response) throws IOException {
        int rows = userService.deleteUser(userId);
        if (rows>0){
            return "forward:home.action?method=delete&pageNum="+pageNum;
        }
        else {
            PrintWriter out=response.getWriter();
            out.println("<script>alert('批量删除失败!');location.href='/user/home.action?pageNum='"+pageNum+"</script>");
            return "";
        }

    }
    @RequestMapping(value = "batchDelete")
    public String batchDeleteUser(int[] userIdArray,Integer pageNum,HttpServletResponse response) throws IOException {
        int rows = userService.batchDelUser(userIdArray);
        if (rows>0){
            return "forward:home.action?pageNum="+pageNum;
        }
        else {
            PrintWriter out=response.getWriter();
            out.println("<script>alert('批量删除失败!');location.href='/user/home.action?pageNum='"+pageNum+"</script>");
            return "";
        }
    }

}

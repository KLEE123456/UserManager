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
import java.io.UnsupportedEncodingException;
import java.util.*;


@Controller
@RequestMapping(value = "/user/*")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录控制器方法
     * @param model
     * @param user
     * @param session
     * @return
     */
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

    /**
     * 用户名检测控制器方法
     * @param userName
     * @param model
     * @return
     */
    @RequestMapping(value = "checkName")
    @ResponseBody
    public String checkName(String userName,Model model){
        if (userName==null){
            model.addAttribute("reg","请输入检测信息!");
            return "register";
        }
        User user = userService.checkName(userName);
        if (user==null){
           return "true";
        }
        else {
            return "false";
        }

    }

    /**
     * 跳转到注册界面
     * @return
     */
    @RequestMapping(value = "toRegister")
    public String toRegister(){
        return "register";
    }

    /**
     *跳转到新增页面
     * @return
     */
    @RequestMapping(value = "toAdd")
    public String toAddUser(){
        return "add";
    }

    /**
     * 注册控制器方法
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "register")
    public String register(User user,Model model){
        String userName=user.getUserName();
        if (userName==null){
            model.addAttribute("reg","请完善信息!");
            return "register";
        }
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

    /**
     * 查询控制器方法
     * @param model
     * @param session
     * @param pageNum
     * @param userNames
     * @param userSexs
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "home")
    public String findAllUser(Model model, HttpSession session, Integer pageNum,String userNames,String userSexs) throws UnsupportedEncodingException {
        if (userNames!=null){
            userNames = new String(userNames.getBytes("ISO-8859-1"), "UTF-8");
            session.setAttribute("userNames",userNames);
        }
        List<User> userList=new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        String userNames1 = (String) session.getAttribute("userNames");
        String userSex1=(String) session.getAttribute("userSex");
        if (userNames1!=null){
            map.put("userName",userNames1);
        }
        else if (userSex1!=null){
            map.put("userSex",userSex1);
        }
        else if (userSexs!=null){
            userSexs=new String(userSexs.getBytes("ISO-8859-1"),"UTF-8");
            session.setAttribute("userSex",userSexs);
            map.put("userSex",userSexs);
        }
        else {
            map.put("userName",null);
            map.put("userSex",null);
        }
        userList = userService.findUser(map,pageNum);
        PageInfo pageInfo=new PageInfo(userList);
        session.setAttribute("page",pageInfo);
        model.addAttribute("userList",userList);
        return "home";
    }

    /**
     * 安全退出控制器方法
     * @param session
     * @return
     */
    @RequestMapping(value = "userQuit")
    public String userQuit(HttpSession session){
        session.invalidate();
        return "login";
    }

    /**
     * 新增控制器方法
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "add")
    public  String  insertUser(User user,Model model){
        String pwd=user.getUserPwd();
        user.setUserPwd(Md5Encrypt.MD5(pwd));
        int rows = userService.insertUser(user);
        if (rows>0){
            return  "forward:home.action";
        }
        else {
            model.addAttribute("ist","添加失败!请和管理员联系");
            return "add";
        }
    }

    /**
     * 编辑渲染控制器方法
     * @param userId
     * @param pageNum
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "editXr")
    public  String  editXr(Integer userId,Integer pageNum,Model model,HttpSession session){
        session.setAttribute("pageNum",pageNum);
        Map<String,Object> map=new HashMap<>();
        map.put("userId",userId);
        List<User> user = userService.findUser(map, pageNum);
        model.addAttribute("user",user.get(0));
        return  "userEdit";
    }

    /**
     * 编辑控制器方法
     * @param user
     * @param session
     * @param model
     * @return
     */
    @RequestMapping(value = "userEdit")
    public String editUser(User user,HttpSession session,Model model){
        Integer pageNum=(Integer)session.getAttribute("pageNum");
        int rows = userService.updateUser(user);
        if (rows>0){
            return "forward:home.action?pageNum="+pageNum;
        }
        else {
            model.addAttribute("msg","编辑错误!");

           return "userEdit";
        }
    }

    /**
     * 删除控制器方法
     * @param userId
     * @param pageNum
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "deleteUser")
    public String deleteUser(Integer userId, Integer pageNum,HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        int rows = userService.deleteUser(userId);
            if (rows>0){
                return "forward:home.action?pageNum="+pageNum;
            }

            else {
                PrintWriter out=response.getWriter();
                out.println("<script>alert('Delete failed!');location.href='/user/home.action?pageNum="+pageNum+"'</script>");
                out.flush();
                out.close();
                return "";
             }

    }

    /**
     * 批量删除控制器方法
     * @param userIdArray
     * @param pageNum
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "batchDelete")
    public String batchDeleteUser(int[] userIdArray,Integer pageNum,HttpServletResponse response) throws IOException {
        int rows = userService.batchDelUser(userIdArray);
        if (rows>0){
            return "forward:home.action?pageNum="+pageNum;
        }
        else {
            PrintWriter out=response.getWriter();
            out.println("<script>alert('Batch delete failed!');location.href='/user/home.action?pageNum="+pageNum+"'</script>");
            out.flush();
            out.close();
            return "";
        }
    }

    /**
     * 返回主页控制器方法
     * @param session
     * @return
     */
    @RequestMapping(value = "backHome")
    public String backHome(HttpSession session){
        session.setAttribute("userNames",null);
        session.setAttribute("userSex",null);
        return  "forward:home.action";
    }

}

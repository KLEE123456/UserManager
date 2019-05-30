package com.klee.UserManager.interceptor;

import com.klee.UserManager.pojo.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String url=request.getRequestURI();
        if (url.indexOf("login")!=-1){
            return  true;
        }
        else if(url.indexOf("register")!=-1){
            return true;
        }
        else if (url.indexOf("toRegister")!=-1){
            return true;
        }
        else  if (url.indexOf("checkName")!=-1){
            return true;
        }
        else {
            HttpSession session=request.getSession();
            User user=(User) session.getAttribute("userMsg");
            if (user==null){
                request.setAttribute("msg","您还没有进行登录,请先登录!");
                request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);
                return  false;
            }
            else {
                return true;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

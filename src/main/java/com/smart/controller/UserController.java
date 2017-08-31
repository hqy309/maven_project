package com.smart.controller;

/*import com.smart.annotation.Log;
import com.smart.base.activeMQ.service.RemindService;
import com.smart.base.shiro.ServiceParam;*/

import com.smart.common.ServiceParam;
import com.smart.entity.User;
import com.smart.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Mars on 2017/8/30.
 */

@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @RequestMapping("/toAdmin")
    public String toAdmin() {
        return "/jsp/admin";
    }

    @RequestMapping("/dologin") //url
//    @Log(oper="user login")
    public String dologin(User user, Model model) {
        logger.info("login ....");
        String info = loginUser(user);
        if (!"SUCC".equals(info)) {
            model.addAttribute("failMsg", "User does not exist or password error!");
            return "/jsp/fail";
        } else {
            model.addAttribute("successMsg", "login Succ!");
            model.addAttribute("name", user.getUsername());
            return "/jsp/success";
        }
    }

    private String loginUser(User user) {
        if (isRelogin(user)) return "SUCC";

        return shiroLogin(user);
    }

    private boolean isRelogin(User user) {
        Subject us = SecurityUtils.getSubject();
        if (us.isAuthenticated()) {
            return true;
        }
        return false;
    }

    private String shiroLogin(User user) {

        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword().toCharArray(), null);
        token.setRememberMe(true);

        try {
            SecurityUtils.getSubject().login(token);
        } catch (UnknownAccountException ex) {
            return "User does not exist or password error!";
        } catch (IncorrectCredentialsException ex) {
            return "User does not exist or password error!";
        } catch (AuthenticationException ex) {
            ex.printStackTrace();
            return ex.getMessage();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Internal error, please try again!";
        }
        return "SUCC";
    }


    @RequestMapping("/register")
//    @Log(oper="user register")
    @ResponseBody
    public ServiceParam register(User user, Model model){
        if(!StringUtils.isEmpty(user.getUsername())){
            user.setRole("user");
            User userDb = userService.findByUserName(user.getUsername());
            User userDB = userService.findByEmail(user.getEmail());
            if (userDb != null)return new ServiceParam("user exist!");
            if(userDB != null) return new ServiceParam("email exist!");
            userService.insertUser(user);

//            remindService.sendRegisterRemind(user);
            return new ServiceParam(null,"register Succ!",true);
        }
        return new ServiceParam("paramErr!");
    }


    @RequestMapping("/userList")
    public String userList(HttpServletRequest request,Model model){
        List<User> uList = userService.queryAll();
        model.addAttribute("uList", uList);
        return "userList";
    }
}

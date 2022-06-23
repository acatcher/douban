package com.yzx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {

    @GetMapping("/registerDemo.html")
    public ModelAndView getRegister(){
        ModelAndView mav = new ModelAndView("/register");
        return mav;
    }

    @PostMapping("/register")
    @ResponseBody
    public Map register(String vc, String username, String password , String nickname , HttpServletRequest request){
        Map res = new HashMap();
        System.out.println("1");
        //verify code Ground Truth
        String verifyCode = (String)request.getSession().getAttribute("kaptchaVerifyCode");
        System.out.println("2");
        //check V C
        if(vc != null && verifyCode != null && !verifyCode.equalsIgnoreCase(vc)){
            res.put("code", "VC01");
            res.put("msg", "验证码错误");
        }else{
            res.put("code", "0");
            res.put("msg", "register Success!");
        }
        System.out.println("3");

        return res;
    }

}

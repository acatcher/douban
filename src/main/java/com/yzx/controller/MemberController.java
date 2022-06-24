package com.yzx.controller;

import com.yzx.entity.Evaluation;
import com.yzx.entity.Member;
import com.yzx.service.EvaluationService;
import com.yzx.service.MemberService;
import com.yzx.service.exception.BussinessException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MemberController {

    @Resource
    private MemberService memberService;

    @Resource
    private EvaluationService evaluationService;

    @GetMapping("/register.html")
    public ModelAndView getRegister() {
        ModelAndView mav = new ModelAndView("/register");
        return mav;
    }

    @PostMapping("/registernow")
    @ResponseBody
    public Map register(String vc, String username, String password, String nickname, HttpServletRequest request) {
        Map res = new HashMap();
        //verify code Ground Truth
        String verifyCode = (String) request.getSession().getAttribute("kaptchaVerifyCode");

        //check verify code
        if (vc != null && verifyCode != null && !verifyCode.equalsIgnoreCase(vc)) {
            res.put("code", "VC01");
            res.put("msg", "验证码错误");
        } else {
            try {
                //add user
                memberService.register(username, password, nickname);
                res.put("code", "0");
                res.put("msg", "register Success!");
            } catch (BussinessException e) {
                // user already exists
                e.printStackTrace();
                res.put("code", e.getCode());
                res.put("msg", e.getMsg());
            }
        }

        return res;
    }


    @GetMapping("/login.html")
    public ModelAndView getLoginDemo() {
        ModelAndView mav = new ModelAndView("/login");
        return mav;
    }

    @PostMapping("/check_login")
    @ResponseBody
    public Map login(String username, String password, String vc, HttpSession session) {
        Map res = new HashMap();
        //verify code Ground Truth
        String verifyCode = (String) session.getAttribute("kaptchaVerifyCode");

        //check verify code
        if (vc != null && verifyCode != null && !verifyCode.equalsIgnoreCase(vc)) {
            res.put("code", "VC01");
            res.put("msg", "Verify code error!");
        } else {
            try {
                Member member = memberService.login(username, password);
                // add user info to session
                session.setAttribute("loginMember", member);
                res.put("code", "0");
                res.put("msg", "Login Success!");

            } catch (BussinessException ex) {
                // login error
                ex.printStackTrace();
                res.put("code", ex.getCode());
                res.put("msg", ex.getMsg());
            }
        }

        return res;
    }


    @PostMapping("update_read_state")
    @ResponseBody
    public Map updateReadState(Long memberId, Long bookId, Integer readState) {
        Map res = new HashMap();

        try {
            memberService.modifyMemberReadState(memberId, bookId, readState);
            res.put("code", "0");
            res.put("msg", "read State update success!");
        } catch (BussinessException ex) {
            ex.printStackTrace();
            res.put("code", ex.getCode());
            res.put("msg", ex.getMsg());
        }

        return res;
    }


    @PostMapping("/evaluate")
    @ResponseBody
    public Map addEvaluation(Long memberId, Long bookId, Integer score, String content){

        Map res = new HashMap();
        try{
            evaluationService.addEvaluation(memberId, bookId, score, content);
            res.put("code", "0");
            res.put("msg", "add new evaluation success!");
        }catch (BussinessException ex){
            ex.printStackTrace();
            res.put("code", ex.getCode());
            res.put("msg", ex.getMsg());

        }
        return res;
    }

    @PostMapping("/enjoy")
    @ResponseBody
    public Map likeEvaluation(Long evaluationId){
        HashMap res = new HashMap();
        try {
            Evaluation evaluation = evaluationService.likeEvaluation(evaluationId);
            res.put("code", "0");
            res.put("msg", "Like success!");
            res.put("evaluation", evaluation);
        }catch (BussinessException ex){
            ex.printStackTrace();
            res.put("code", ex.getCode());
            res.put("msg", ex.getMsg());
        }

        return res;
    }

}

package com.yzx.controller.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/m")
public class ManagementController {

    @GetMapping("/index.html")
    public ModelAndView getManagementIndex(){
        ModelAndView mav = new ModelAndView("/management/index");
        return mav;
    }

    @GetMapping("/movie.html")
    public ModelAndView getManagementMovie(){
        ModelAndView mav = new ModelAndView("/management/movie");
        return mav;
    }

    @GetMapping("/review.html")
    public ModelAndView getManagementReview(){
        ModelAndView mav = new ModelAndView("/management/review");
        return mav;
    }


}

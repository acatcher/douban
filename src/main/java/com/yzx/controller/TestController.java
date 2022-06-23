package com.yzx.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
public class TestController {

    @GetMapping("/test/ftl")
    public ModelAndView testFtl(){
        return new ModelAndView("/test");
    }

    @GetMapping("/test/json")
    @ResponseBody
    public Map testJson(){
        Map m = new HashMap();
        m.put("seeker","2333");
        m.put("luosifu", "666");
        return m;
    }



}

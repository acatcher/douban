package com.yzx.service.Impl;

import com.yzx.entity.Member;
import com.yzx.service.MemberService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MemberServiceImplTest {

    @Resource
    MemberService memberService;

    @Test
    public void testRegister(){

        Member m = memberService.register("seeker1", "999999", "myseeker");

        System.out.println(m);

    }

}
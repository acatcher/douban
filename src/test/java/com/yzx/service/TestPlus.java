package com.yzx.service;


import com.yzx.entity.PlusEntity;
import com.yzx.mapper.PlusMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class TestPlus {

    @Resource
    private PlusMapper plusMapper;

    @Test
    public void insertTest(){
        PlusEntity pe = new PlusEntity();
        pe.setHhh2("天下无贼");
        plusMapper.insert(pe);

    }

}

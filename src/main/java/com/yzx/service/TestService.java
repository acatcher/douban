package com.yzx.service;

import com.yzx.mapper.TestMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    @Transactional
    public void batchInsert(){
        for (int j = 0; j < 3; j++) {

//            if(j == 2){
//                throw new RuntimeException("haha");
//            }

            testMapper.insertTest();
        }
    }

}

package com.yzx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzx.entity.Member;
import com.yzx.mapper.MemberMapper;
import com.yzx.service.MemberService;
import com.yzx.service.exception.BussinessException;
import com.yzx.utils.MD5Utils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("memberService")
public class MemberServiceImpl implements MemberService {

    @Resource
    MemberMapper memberMapper;


    @Override
    public Member register(String username, String password, String nickname) {
        QueryWrapper<Member> qw = new QueryWrapper<>();
        qw.eq("username", username);
        List<Member> members = memberMapper.selectList(qw);

        if(members.size() > 0){
            throw new BussinessException("M01","User Exists!");

        }
        //generate password
        int salt = new Random().nextInt(1000) + 1000; //盐值
        String md5PassWd = MD5Utils.md5Digest(password, salt);

        Member member = new Member();
        member.setUsername(username);
        member.setNickname(nickname);
        member.setSalt(salt);
        member.setPassword(md5PassWd);
        member.setCreateTime(new Date());
        memberMapper.insert(member);

        return member;

    }
}

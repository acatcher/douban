package com.yzx.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yzx.entity.Member;
import com.yzx.entity.MemberReadState;
import com.yzx.mapper.MemberMapper;
import com.yzx.mapper.MemberReadStateMapper;
import com.yzx.service.MemberService;
import com.yzx.service.exception.BussinessException;
import com.yzx.utils.MD5Utils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service("memberService")
@Transactional
public class MemberServiceImpl implements MemberService {

    @Resource
    MemberMapper memberMapper;

    @Resource
    MemberReadStateMapper memberReadStateMapper;


    @Override
    public Member register(String username, String password, String nickname) {
        QueryWrapper<Member> qw = new QueryWrapper<>();
        qw.eq("username", username);
        List<Member> members = memberMapper.selectList(qw);

        // already exists
        if(members.size() > 0){
            throw new BussinessException("M01","User Exists!");
        }

        // generate password
        int salt = new Random().nextInt(1000) + 1000; //盐值
        String md5PassWd = MD5Utils.md5Digest(password, salt);

        // add new member
        Member member = new Member();
        member.setUsername(username);
        member.setNickname(nickname);
        member.setSalt(salt);
        member.setPassword(md5PassWd);
        member.setCreateTime(new Date());
        memberMapper.insert(member);

        return member;

    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public Member login(String username, String password) {

        QueryWrapper<Member> qw = new QueryWrapper<>();
        qw.eq("username", username);

        Member member = memberMapper.selectOne(qw);
        if(member == null){
            throw new BussinessException("M02", "No such user!");
        }
        String md5PassWd = MD5Utils.md5Digest(password, member.getSalt());
        if( !md5PassWd.equals(member.getPassword()) ){
            throw new BussinessException("M03", "Password Error!");
        }

        return member;
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public MemberReadState selectMemberReadState(Long memberId, Long bookId) {

        QueryWrapper<MemberReadState> qw = new QueryWrapper<>();
        qw.eq("member_id", memberId);
        qw.eq("book_id", bookId);
        MemberReadState memberReadState = memberReadStateMapper.selectOne(qw);

        return memberReadState;
    }

    @Override
    public MemberReadState modifyMemberReadState(Long memberId, Long bookId, Integer readState) {
        QueryWrapper<MemberReadState> qw = new QueryWrapper<>();
        qw.eq("member_id", memberId);
        qw.eq("book_id", bookId);
        MemberReadState memberReadState = memberReadStateMapper.selectOne(qw);

        if(memberReadState != null){
            memberReadState.setReadState(readState);
            memberReadStateMapper.updateById(memberReadState);
        }else{
            memberReadState = new MemberReadState();
            memberReadState.setMemberId(memberId);
            memberReadState.setBookId(bookId);
            memberReadState.setReadState(readState);
            memberReadState.setCreateTime(new Date());
            memberReadStateMapper.insert(memberReadState);
        }


        return memberReadState;
    }
}

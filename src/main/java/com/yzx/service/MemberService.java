package com.yzx.service;

import com.yzx.entity.Member;
import com.yzx.entity.MemberReadState;

public interface MemberService {
    // register
    public Member register(String username, String password, String nickname);

    // login
    public Member login(String username, String password);

    // read state get
    public MemberReadState selectMemberReadState(Long memberId, Long bookId);

    // read state change
    public MemberReadState modifyMemberReadState(Long memberId, Long bookId, Integer readState);

}

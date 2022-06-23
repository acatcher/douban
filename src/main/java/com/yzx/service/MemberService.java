package com.yzx.service;

import com.yzx.entity.Member;

public interface MemberService {
    public Member register(String username, String password, String nickname);
}

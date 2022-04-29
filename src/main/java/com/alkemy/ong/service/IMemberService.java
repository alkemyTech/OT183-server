package com.alkemy.ong.service;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.response.PostMembersDTO;

public interface IMemberService {
    public PostMembersDTO createMember(MemberDTO memberDTO);
}

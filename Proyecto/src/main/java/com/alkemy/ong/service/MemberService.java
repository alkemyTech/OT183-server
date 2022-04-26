package com.alkemy.ong.service;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.response.PostMembersDTO;

import org.springframework.http.ResponseEntity;

public interface MemberService {
    public PostMembersDTO createMember(MemberDTO memberDTO);
}

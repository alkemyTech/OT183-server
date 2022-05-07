package com.alkemy.ong.service;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.MembersPageDTO;
import com.alkemy.ong.dto.response.PostMembersDTO;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMemberService {
    public PostMembersDTO createMember(MemberDTO memberDTO);
    ResponseEntity<?> updateMember(Long id, MemberDTO memberUpdate);
    MembersPageDTO returnList(int page);

    void delete(Long id);
}

package com.alkemy.ong.service;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.response.PostMembersDTO;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IMemberService {
    public PostMembersDTO createMember(MemberDTO memberDTO);
    ResponseEntity<?> updateMember(Long id, MemberDTO memberUpdate);
    List<MemberDTO> returnList();

    void delete(Long id);
}

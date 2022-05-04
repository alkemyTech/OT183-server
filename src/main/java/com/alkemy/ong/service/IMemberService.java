package com.alkemy.ong.service;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.response.PostMembersDTO;

import java.util.List;

public interface IMemberService {
    public PostMembersDTO createMember(MemberDTO memberDTO);

    List<MemberDTO> returnList();

    void delete(Long id);
}

package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.response.PostMembersDTO;
import com.alkemy.ong.mapper.MemberMapperPost;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.IMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements IMemberService{
    private final MemberRepository memberRepository;

    @Override
    public PostMembersDTO createMember(MemberDTO memberDTO){
        MemberMapperPost member = new MemberMapperPost();

        Member memberSaved = memberRepository.save(member.mapperMember(memberDTO));

        PostMembersDTO response = new PostMembersDTO();
        response.setId(memberSaved.getId());
        response.setUrl("/members/"+memberSaved.getId());

        return response;
        
    }
    
}

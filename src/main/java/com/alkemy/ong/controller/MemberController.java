package com.alkemy.ong.controller;

import javax.validation.Valid;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.response.PostMembersDTO;
import com.alkemy.ong.service.impl.MemberServiceImpl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController{
    
    private final MemberServiceImpl memberService;

    @PostMapping
    public ResponseEntity<PostMembersDTO> createMember(@Valid @RequestBody MemberDTO memberDTO){
        
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(memberDTO));
    }

}

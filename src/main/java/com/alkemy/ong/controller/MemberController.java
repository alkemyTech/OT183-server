package com.alkemy.ong.controller;

import javax.validation.Valid;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.response.PostMembersDTO;
import com.alkemy.ong.service.IMemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController{

    @Autowired
    private final IMemberService memberService;

    @PostMapping
    public ResponseEntity<PostMembersDTO> createMember(@Valid @RequestBody MemberDTO memberDTO){
        
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(memberDTO));
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(memberService.returnList());
    }

}

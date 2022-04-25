package com.alkemy.ong.service;

import com.alkemy.ong.dto.MemberDTO;

import org.springframework.http.ResponseEntity;

public interface MemberService {
    public ResponseEntity<?> createMember(MemberDTO memberDTO);
}

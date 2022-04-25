package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.builders.MemberBuilderPost;
import com.alkemy.ong.dto.response.ErrorPostMembersDTO;
import com.alkemy.ong.dto.response.PostMembersDTO;
import com.alkemy.ong.mapper.MemberMapperPost;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.MemberService;
import com.alkemy.ong.util.helpers.Helpers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;

    public ResponseEntity<?> createMember(MemberDTO memberDTO){
        if(memberDTO.getName() == null)
        return new ResponseEntity<>(
            mapperError("Error the name can't be empty",406),
            HttpStatus.NOT_ACCEPTABLE); 

        if(Helpers.controlNameString(memberDTO.getName()) == false)
            return new ResponseEntity<>(
            mapperError("Error the name can't have contain simbols or numbers",406),
            HttpStatus.NOT_ACCEPTABLE);  

        if(memberDTO.getImage() == null)
            return new ResponseEntity<>(
                mapperError("Error image can't be empty",406),
                HttpStatus.NOT_ACCEPTABLE); 
    
        //TODO in the future added more restrictions to this method
        if(Helpers.controlEmptyField(memberDTO.getImage()))
            return new ResponseEntity<>(
            mapperError("Error image can't be empty",406),
            HttpStatus.NOT_ACCEPTABLE);
        

        MemberMapperPost member = new MemberMapperPost();

        Member memberSaved = memberRepository.save(member.mapperMember(memberDTO));
        String url = "/api/v1/members/"+memberSaved.getId();

        PostMembersDTO response = new PostMembersDTO();
        response.setId(memberSaved.getId());
        response.setUrl(url);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    private ErrorPostMembersDTO mapperError(String message, int number){
        ErrorPostMembersDTO error = new ErrorPostMembersDTO();
        error.setError(true);
        error.setMessage(message);
        error.setNumber(number);

        return error;
    }
    
}

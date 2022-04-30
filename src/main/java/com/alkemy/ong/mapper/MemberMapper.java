package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.builders.MemberBuilderPost;
import com.alkemy.ong.model.Member;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MemberMapperPost {

    public Member mapperMember(MemberDTO memberDTO){
        MemberBuilderPost builder = new MemberBuilderPost();

        //Builder pattern
        return builder.setName(memberDTO.getName())
            .setDescription(memberDTO.getDescription())
            .setFacebook(memberDTO.getFacebookUrl())
            .setInstagram(memberDTO.getInstagramUrl())
            .setLinkedin(memberDTO.getLinkedinUrl())
            .setImage(memberDTO.getImage())
            .memberBuilderPost();
    } 
    
}

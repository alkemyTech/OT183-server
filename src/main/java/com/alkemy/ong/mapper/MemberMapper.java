package com.alkemy.ong.mapper;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.builders.MemberBuilderPost;
import com.alkemy.ong.dto.builders.MemberBuilderUpdate;
import com.alkemy.ong.model.Member;

import com.alkemy.ong.util.MapperUtil;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@NoArgsConstructor
@Component
public class MemberMapper {

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

    public Member mapperMember(MemberDTO memberDTO, Member member){
        MemberBuilderUpdate builder = new MemberBuilderUpdate();

        //Builder pattern
        return builder.setName(memberDTO.getName(), member.getName())
        .setDescription(memberDTO.getDescription(), member.getDescription())
        .setFacebook(memberDTO.getFacebookUrl(), member.getFacebookUrl())
        .setInstagram(memberDTO.getInstagramUrl(), member.getInstagramUrl())
        .setLinkedin(memberDTO.getLinkedinUrl(), member.getLinkedinUrl())
        .setImage(memberDTO.getImage(), member.getImage())
        .memberBuilderUpdate(memberDTO, member);
    }

    public List<MemberDTO> listMemberDto(List<Member> list) {
        return MapperUtil.streamListNonNull(list, this::toMemberDto);
    }

    public MemberDTO toMemberDto(Member entity) {
        return MemberDTO.builder().name(entity.getName())
                .facebookUrl(entity.getFacebookUrl())
                        .instagramUrl(entity.getInstagramUrl())
                                .linkedinUrl(entity.getLinkedinUrl())
                                        .image(entity.getImage())
                                                .description(entity.getDescription())
                                                        .build();
    }
    
}

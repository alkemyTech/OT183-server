package com.alkemy.ong.dto.builders;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.builders.interfaces.IMemberBuilder;
import com.alkemy.ong.model.Member;

public class MemberBuilderPost implements IMemberBuilder{
    private String name;
    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
    private String image;
    private String description;

    public MemberBuilderPost setName(String name){
        this.name = name;
        return this;
    }

    public MemberBuilderPost setFacebook(String facebook){
        this.facebookUrl = facebook;
        return this;
    }

    public MemberBuilderPost setInstagram(String instagram){
        this.instagramUrl = instagram;
        return this;
    }

    public MemberBuilderPost setLinkedin(String linkedin){
        this.linkedinUrl = linkedin;
        return this;
    }

    public MemberBuilderPost setImage(String newImage){
        this.image = newImage;
        return this;
    }

    public MemberBuilderPost setDescription(String newDescription){
        this.description = newDescription;
        return this;
    }


    @Override
    public Member memberBuilderPost() {
        Member member = new Member();

        member.setName(this.name);
        member.setDescription(this.description);
        member.setImage(this.image);
        member.setFacebookUrl(this.facebookUrl);
        member.setInstagramUrl(this.instagramUrl);
        member.setLinkedinUrl(this.linkedinUrl);

        return member;
    }

    @Override
    public Member memberBuilderUpdate(MemberDTO memberDTO, Member member) {
        // TODO Auto-generated method stub
        return null;
    }
    
}

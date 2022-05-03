package com.alkemy.ong.dto.builders;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.builders.interfaces.IMemberBuilder;
import com.alkemy.ong.model.Member;

public class MemberBuilderUpdate implements IMemberBuilder{
    private String name;
    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
    private String image;
    private String description;

    public MemberBuilderUpdate setName(String updateName, String name){
        if(updateName!=null){
            this.name = updateName;
        }else{
            this.name = name;
        }
        return this;
    }

    public MemberBuilderUpdate setFacebook(String updateFacebook,String facebook){
        if(updateFacebook!=null){
            this.facebookUrl = updateFacebook;
        }else{
            this.facebookUrl = facebook;
        }
        return this;
    }

    public MemberBuilderUpdate setInstagram(String updateInstagram,String instagram){
        if(updateInstagram!=null){
            this.instagramUrl = updateInstagram;
        }else{
            this.instagramUrl = instagram;
        }
        return this;
    }

    public MemberBuilderUpdate setLinkedin(String updateLinkedin,String linkedin){
        if(updateLinkedin!=null){
            this.linkedinUrl = updateLinkedin;
        }else{
            this.linkedinUrl = linkedin;
        }
        return this;
    }

    public MemberBuilderUpdate setImage(String updateImage,String Image){ 
        if(updateImage!=null){
            this.image = updateImage;
        }else{
            this.image = Image;
        }
        return this;
    }

    public MemberBuilderUpdate setDescription(String updateDesc,String Description){
        if(updateDesc!=null){
            this.description = updateDesc;
        }else{
            this.description = Description;
        }
        return this;
    }

    @Override
    public Member memberBuilderPost() {
        return null;
    }

    @Override
    public Member memberBuilderUpdate(MemberDTO memberDTO, Member member) {
        member.setName(this.name);
        member.setDescription(this.description);
        member.setImage(this.image);
        member.setFacebookUrl(this.facebookUrl);
        member.setInstagramUrl(this.instagramUrl);
        member.setLinkedinUrl(this.linkedinUrl);
        return member;
    }

    
}

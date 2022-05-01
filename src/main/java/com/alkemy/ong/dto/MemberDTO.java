package com.alkemy.ong.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO{
    
    private String name;

    private String facebookUrl;

    private String instagramUrl;
    
    private String linkedinUrl;
    
    private String image;
    
    private String description;
}

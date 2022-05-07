package com.alkemy.ong.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDTO{
    @NotNull(message = "{error.empty_field}")
    @Column(nullable = false)
    private String name;

    private String facebookUrl;

    private String instagramUrl;
    
    private String linkedinUrl;
    
    @NotNull(message = "{error.empty_field}")
    @Column(nullable = false)
    private String image;
    
    private String description;
}

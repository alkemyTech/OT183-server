package com.alkemy.ong.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MembersPageDTO {
    String nextPage;
    List<MemberDTO> response;
}

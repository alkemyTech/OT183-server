package com.alkemy.ong.dto.builders.interfaces;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.model.Member;

public interface IMemberBuilder {
    Member memberBuilderPost();
    Member memberBuilderUpdate(MemberDTO memberDTO,Member member);
}

package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.response.PostMembersDTO;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.IMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements IMemberService{
    private final MemberRepository memberRepository;

    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private MessageSource message;


    @Override
    public PostMembersDTO createMember(MemberDTO memberDTO){
        MemberMapper member = new MemberMapper();

        Member memberSaved = memberRepository.save(member.mapperMember(memberDTO));

        PostMembersDTO response = new PostMembersDTO();
        response.setId(memberSaved.getId());
        response.setUrl("/members/"+memberSaved.getId());

        return response;
        
    }

    @Override
    public List<MemberDTO> returnList() {
        List<Member> entityList = memberRepository.findAll();
        if (entityList.size() == 0) {
            throw new NullListException(message.getMessage("error.null_list", null, Locale.US));
        }
        return memberMapper.listMemberDto(entityList);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        memberRepository.findById(id)
                .orElseThrow(()->
                        new EntityNotFoundException(message.getMessage("data.not.found", null,Locale.US)));
        memberRepository.deleteById(id);
    }

}

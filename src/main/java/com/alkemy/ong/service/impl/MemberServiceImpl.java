package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.MembersPageDTO;
import com.alkemy.ong.dto.response.PostMembersDTO;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.mapper.MemberMapper;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.IMemberService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements IMemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final MessageSource message;


    @Override
    public PostMembersDTO createMember(MemberDTO memberDTO) {
        MemberMapper member = new MemberMapper();

        Member memberSaved = memberRepository.save(member.mapperMember(memberDTO));

        PostMembersDTO response = new PostMembersDTO();
        response.setId(memberSaved.getId());
        response.setUrl("/members/" + memberSaved.getId());

        return response;

    }

    @Override
    public MembersPageDTO returnList(int page) {
        List<Member> entityList = 
        memberRepository.findAll(PageRequest.of(page, 10)).getContent();

        if (entityList.size() == 0) {
            throw new NullListException(message.getMessage("error.null_list", null, Locale.US));
        }
        int nextPage = calculateNextPage(entityList.size(), page);
        List<MemberDTO> response = memberMapper.listMemberDto(entityList);

        MembersPageDTO memberList = new MembersPageDTO("/members?page="+nextPage, response);
        return memberList;
    }

    private Integer calculateNextPage(int sizeListMembers,int actualPage){
        if(sizeListMembers < 10){
            return null;
        }else{
            List<Member> futureList = 
            memberRepository.findAll(PageRequest.of(actualPage+1, 10))
            .getContent();
            return(sizeListMembers < 10)?null:actualPage+1;
        }
    }

    @Override
    public ResponseEntity<?> updateMember(Long id, MemberDTO memberUpdate) {
        Optional<Member> member = memberRepository.findById(id);
        if(member.isPresent()){
           memberRepository.save(memberMapper.mapperMember(memberUpdate, member.get()));
           PostMembersDTO response = new PostMembersDTO();
           response.setId(member.get().getId());
           response.setUrl("/members/"+member.get().getId());
           return ResponseEntity.status(HttpStatus.OK).body(response);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(message.getMessage("data.not.found", null, Locale.US));
        }
    }

    @Transactional
    public void delete(Long id) {
        if (!memberRepository.existsById(id)) throw new EntityNotFoundException("Members", "id", id);
        memberRepository.deleteById(id);
    }
}

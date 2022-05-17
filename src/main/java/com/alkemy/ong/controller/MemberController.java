package com.alkemy.ong.controller;

import javax.validation.Valid;

import com.alkemy.ong.dto.MemberDTO;
import com.alkemy.ong.dto.MembersPageDTO;
import com.alkemy.ong.dto.response.PostMembersDTO;
import com.alkemy.ong.service.IMemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Api(tags = "Members")
@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController{

    @Autowired
    private final IMemberService memberService;
    @Autowired
    private MessageSource message;

    @ApiOperation(
        value = "Create a new Member",
        notes = "Create a new Member and returns his endpoint",
        nickname = "Add new Member")
    @ApiResponses({
            @ApiResponse(code = 201, message = "The memeber was created"),
            @ApiResponse(code = 401, message = "Unauthorized - You can't acces to this service"),
            @ApiResponse(code = 403, message = "Forbbiden - You don't have permission to access this resource"),
            @ApiResponse(code = 404, message = "Not Found - Member not found by id ")
    })
    @ApiImplicitParams({
    @ApiImplicitParam(
            name = "memberDTO",
            value = "Member to be added",
            required = true,
            paramType = "body",
            dataType = "MemberDTO"
    )
    }
    )
    @PostMapping
    public ResponseEntity<PostMembersDTO> createMember(@Valid @RequestBody MemberDTO memberDTO){
        
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(memberDTO));
    }


    @ApiOperation(value = "Get members", notes = "Returns a collection of members by page")
    @ApiResponses(value = {
            @ApiResponse(
                    code = 200,
                    message = "Successfully request",
                    response = MembersPageDTO.class),
            @ApiResponse(code = 404, message = "Not Found - The members was not found"),
            @ApiResponse(code = 401, message = "Unauthorized - You can't acces to this service"),
            @ApiResponse(code = 403, message = "Forbbiden - You don't have permission to access this resource")
    })
    @GetMapping
    public ResponseEntity<MembersPageDTO> getAll(@RequestParam int page){
        return ResponseEntity.status(HttpStatus.OK).body(memberService.returnList(page));
    }

    @ApiOperation(
            value = "Update a Member",
            notes = "Update an existing Member and returns his endpoint",
            nickname = "Update Member")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The member was updated"),
            @ApiResponse(code = 401, message = "Unauthorized - You can't acces to this service"),
            @ApiResponse(code = 403, message = "Forbbiden - You don't have permission to access this resource"),
            @ApiResponse(code = 404, message = "Not Found - Member not found by id ")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "id",
                    value = "News id",
                    required = true,
                    paramType = "path",
                    dataType = "long",
                    example = "1"
            ),
            @ApiImplicitParam(
                    name = "memberUpdate",
                    value = "Member to be updated",
                    required = true,
                    paramType = "body",
                    dataType = "MemberDTO"
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<?> updateMember(@PathVariable("id") long id,@Valid @RequestBody MemberDTO memberUpdate) {
        return memberService.updateMember(id, memberUpdate);
    }

    @ApiOperation(
        value = "Delete a Member",
        notes = "Update an existing Member by id",
        nickname = "Delete Member")
    @ApiResponses({
            @ApiResponse(code = 200, message = "The memeber was deleted"),
            @ApiResponse(code = 401, message = "Unauthorized - You can't acces to this service"),
            @ApiResponse(code = 403, message = "Forbbiden - You don't have permission to access this resource"),
            @ApiResponse(code = 404, message = "Not Found - Member not found")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "id",
                    value = "Members id",
                    required = true,
                    paramType = "path",
                    dataType = "long",
                    example = "1"
            )
    }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

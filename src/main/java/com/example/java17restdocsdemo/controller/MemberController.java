package com.example.java17restdocsdemo.controller;

import com.example.java17restdocsdemo.domain.Member;
import com.example.java17restdocsdemo.dto.MemberSaveDto;
import com.example.java17restdocsdemo.response.EmbeddedResponse;
import com.example.java17restdocsdemo.response.IdResponse;
import com.example.java17restdocsdemo.response.MemberResponse;
import com.example.java17restdocsdemo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/members")
public class MemberController {

    private final MemberService memberService;


    @GetMapping
    public ResponseEntity getMembers(
            @RequestParam(value = "name", required = false) String name,
            Pageable pageable,
            PagedResourcesAssembler<Member> assembler
    ) {
        Page<Member> members = memberService.getAll(name, pageable);
        PagedModel<MemberResponse> pagedModel = assembler.toModel(members, MemberResponse::new);
        pagedModel.add(linkTo(IndexController.class).slash("docs/index.html#resources-get-members").withRel("profile"));
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getMember(
            @PathVariable(value = "id") Long id
    ) {
        Member member = memberService.get(id);
        EmbeddedResponse<MemberResponse> response = new EmbeddedResponse<>("member", new MemberResponse(member));
        response.add(
                linkTo(methodOn(MemberController.class).getMember(id)).withSelfRel(),
                linkTo(IndexController.class).slash("docs/index.html#resources-get-member").withRel("profile")
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity insertMember(
            @RequestBody @Valid MemberSaveDto memberSaveDto
    ) {
        Member member = memberService.insert(memberSaveDto);
        EmbeddedResponse<MemberResponse> response = new EmbeddedResponse<>("member", new MemberResponse(member));
        response.add(
                linkTo(methodOn(MemberController.class).insertMember(null)).withSelfRel(),
                linkTo(IndexController.class).slash("docs/index.html#resources-insert-member").withRel("profile")
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity updateMember(
            @PathVariable(value = "id") Long id,
            @RequestBody @Valid MemberSaveDto memberSaveDto
    ) {
        Member member = memberService.update(id, memberSaveDto);
        EmbeddedResponse<MemberResponse> response = new EmbeddedResponse<>("member", new MemberResponse(member));
        response.add(
                linkTo(methodOn(MemberController.class).updateMember(id, null)).withSelfRel(),
                linkTo(IndexController.class).slash("docs/index.html#resources-update-member").withRel("profile")
        );
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteMember(
            @PathVariable(value = "id") Long id
    ) {
        memberService.delete(id);
        EmbeddedResponse<IdResponse> response = new EmbeddedResponse<>("member", new IdResponse(id));
        response.add(
                linkTo(methodOn(MemberController.class).deleteMember(id)).withSelfRel(),
                linkTo(IndexController.class).slash("docs/index.html#resources-delete-member").withRel("profile")
        );
        return ResponseEntity.ok(response);
    }
}
